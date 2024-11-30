package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.service.UserService;

import java.io.IOException;

@WebServlet("/profile")
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

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Пароли не совпадают");
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            return;
        }

        if (!(userService.findByEmail(email).isEmpty()) && !email.equals(user.getEmail())) {
            req.setAttribute("errorMessage", "Пользователь с таким email уже существует");
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            return;
        }

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        boolean updateUser = userService.update(user);
        if (updateUser) {
            req.setAttribute("successMessage", "Данные успешно обновлены!");
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorMessage", "Произошла ошибка при обновлении данных");
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        }

    }
}
