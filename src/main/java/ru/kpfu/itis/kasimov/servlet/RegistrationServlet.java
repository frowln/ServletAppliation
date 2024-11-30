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
import java.util.Optional;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String confirmPassword = req.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Пароли не совпадают");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
            return;
        }
        Optional<User> userExist = userService.findByEmail(email);

        if (userExist.isPresent()) {
            req.setAttribute("error", "Пользователь с таким email уже существует");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
            return;
        }
        User user = new User(0, name, email, password, role);
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(password);

        Optional<User> newUser = userService.register(user);
        if (newUser.isPresent()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", newUser.get());
            session.setAttribute("userId", newUser.get().getId());
            resp.sendRedirect("/home");
        } else {
            req.setAttribute("error", "Ошибка при регистрации. Попробуйте ещё раз");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}

