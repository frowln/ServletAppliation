package ru.kpfu.itis.kasimov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kasimov.dao.ProgressDao;
import ru.kpfu.itis.kasimov.entity.Lesson;
import ru.kpfu.itis.kasimov.entity.User;
import ru.kpfu.itis.kasimov.service.LessonService;
import ru.kpfu.itis.kasimov.service.ProgressService;
import ru.kpfu.itis.kasimov.service.UserCourseService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/lesson")
public class LessonServlet extends HttpServlet {
    private final LessonService lessonService = LessonService.getINSTANCE();
    private final ProgressService progressService = ProgressService.getINSTANCE();
    private final UserCourseService userCourseService = UserCourseService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParam = req.getParameter("id");
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        User user = (User) req.getSession().getAttribute("user");

        if (lessonIdParam == null || lessonIdParam.isEmpty()) {
            resp.sendRedirect("/courses?error=invalid_lesson_id");
            return;
        }

        try {
            int lessonId = Integer.parseInt(lessonIdParam);
            Optional<Lesson> lessonOptional = lessonService.findLessonById(lessonId);

            if (lessonOptional.isEmpty()) {
                resp.sendRedirect("/courses?error=lesson_not_found");
                return;
            }
            Lesson lesson = lessonOptional.get();
            boolean isCompleted = false;
            boolean enrolled = false;

            if (user != null) {
                enrolled = userCourseService.isUserEnrolledInCourse(user.getId(), lesson.getCourseId());
                isCompleted = progressService.isCompleted(userId, lessonId);
            }

            req.setAttribute("lesson", lesson);
            req.setAttribute("isCompleted", isCompleted);
            req.setAttribute("enrolled", enrolled);

            req.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/courses?error=invalid_lesson_id_format");
        }
    }
}