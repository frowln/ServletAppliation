package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.service.UserService;
import ru.kpfu.itis.kasimov.util.CloudinaryUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 5 * 1024 * 1024,  // 5 MB
        maxRequestSize = 20 * 1024 * 1024 // 20 MB
)
public class ProfileServlet extends HttpServlet {
    private final UserService userService = UserService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        Part avatarPart = req.getPart("avatar");

        if (!password.isBlank() && !password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Пароли не совпадают");
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            return;
        }

        if (!userService.findByEmail(email).isEmpty() && !email.equals(user.getEmail())) {
            req.setAttribute("errorMessage", "Пользователь с таким email уже существует");
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            return;
        }

        user.setName(name);
        user.setEmail(email);

        if (!password.isBlank()) {
            user.setPassword(password);
        }

        if (avatarPart != null && avatarPart.getSize() > 0) {
            try (InputStream inputStream = avatarPart.getInputStream()) {
                File tempFile = File.createTempFile("avatar", ".png");
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Map<String, Object> uploadResult = CloudinaryUtil.getInstance()
                        .uploader()
                        .upload(tempFile, new HashMap<>());

                String avatarUrl = (String) uploadResult.get("secure_url");
                user.setAvatarUrl(avatarUrl);

                tempFile.delete();
            } catch (Exception e) {
                req.setAttribute("errorMessage", "Ошибка при загрузке аватара: " + e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
                return;
            }
        }

        boolean updateUser = userService.update(user);

        if (updateUser) {
            session.setAttribute("user", user);
            req.setAttribute("successMessage", "Данные успешно обновлены!");
        } else {
            req.setAttribute("errorMessage", "Произошла ошибка при обновлении данных");
        }

        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }
}

