<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Управление курсом</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/home.css" rel="stylesheet" type="text/css">
    <link href="/css/manageCourse.css" rel="stylesheet" type="text/css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark sticky-top">
    <div class="container">
        <a class="navbar-brand" href="#">
            <i class="fas fa-graduation-cap me-2"></i>
            Chill Study
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto align-items-center">
                <li class="nav-item">
                    <a class="nav-link" href="/home">
                        <i class="fas fa-home me-1"></i> Главная
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="/courses">
                        <i class="fas fa-book me-1"></i> Мои курсы
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/profile">
                        <i class="fas fa-user me-1"></i> Профиль
                    </a>
                </li>

                <c:if test="${empty user}">
                    <li class="nav-item">
                        <a class="nav-link" href="/login">
                            <i class="fas fa-sign-in-alt me-1"></i> Войти
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/registration">
                            <i class="fas fa-user-plus me-1"></i> Регистрация
                        </a>
                    </li>
                </c:if>

                <c:if test="${not empty user}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" id="navbarDropdown"
                           role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="${user.avatarUrl != null ? user.avatarUrl : '/images/default-avatar.png'}"
                                 alt="Аватар" class="user-avatar me-2">
                                ${user.name}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="/profile">
                                <i class="fas fa-user-circle me-2"></i>Мой профиль
                            </a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="/logout">
                                <i class="fas fa-sign-out-alt me-2"></i>Выйти
                            </a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<div class="manage-course-container">
    <div class="page-header">
        <div class="header-content">
            <h1 class="page-title">Управление курсом</h1>
            <p class="page-subtitle">${course.name}</p>
        </div>
    </div>

    <div class="course-content">
        <div class="course-info-card">
            <div class="card-header">
                <i class="fas fa-info-circle"></i>
                <h3>Информация о курсе</h3>
            </div>
            <div class="card-body">
                <div class="info-group">
                    <label><i class="fas fa-align-left"></i> Описание</label>
                    <p>${course.description}</p>
                </div>
                <div class="info-group">
                    <label><i class="fas fa-chalkboard-teacher"></i> Преподаватель</label>
                    <p>${teacherName}</p>
                </div>
                <div class="action-buttons">
                    <a href="/editCourse?id=${course.id}" class="btn btn-edit">
                        <i class="fas fa-edit"></i>
                        Редактировать
                    </a>
                    <form action="/deleteCourse" method="post" class="delete-form">
                        <input type="hidden" name="id" value="${course.id}">
                        <button type="submit" class="btn btn-delete"
                                onclick="return confirm('Вы уверены, что хотите удалить этот курс?');">
                            <i class="fas fa-trash-alt"></i>
                            Удалить курс
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <div class="lessons-card">
            <div class="card-header">
                <i class="fas fa-book-reader"></i>
                <h3>Уроки курса</h3>
                <a href="/addLesson?courseId=${course.id}" class="btn btn-add-lesson">
                    <i class="fas fa-plus"></i>
                    Добавить урок
                </a>
            </div>

            <div class="card-body">
                <div class="lessons-list">
                    <c:forEach var="lesson" items="${lessons}" varStatus="status">
                        <div class="lesson-item">
                            <div class="lesson-info">
                                <div class="lesson-number">${status.index + 1}</div>
                                <div class="lesson-details">
                                    <h4 class="lesson-title">${lesson.name}</h4>
                                </div>
                            </div>
                            <div class="lesson-actions">
                                <a href="/editLesson?id=${lesson.id}&courseId=${course.id}" class="btn btn-edit-lesson">
                                    <i class="fas fa-edit"></i>
                                    Редактировать
                                </a>
                                <form action="/deleteLesson" method="post" class="delete-lesson-form">
                                    <input type="hidden" name="id" value="${lesson.id}">
                                    <input type="hidden" name="courseId" value="${course.id}">
                                    <button type="submit" class="btn btn-delete-lesson"
                                            onclick="return confirm('Вы уверены, что хотите удалить этот урок?');">
                                        <i class="fas fa-trash-alt"></i>
                                        Удалить
                                    </button>
                                </form>

                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty lessons}">
                        <div class="empty-lessons">
                            <i class="fas fa-book"></i>
                            <p>В этом курсе пока нет уроков</p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="students-card">
            <div class="card-header">
                <i class="fas fa-users"></i>
                <h3>Записанные студенты</h3>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="students-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Имя</th>
                            <th>Email</th>
                            <th>Действия</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td>${student.id}</td>
                                <td>
                                    <div class="student-info">
                                        <img src="${student.avatarUrl != null ? student.avatarUrl : '/images/default-avatar.png'}"
                                             alt="Аватар" class="student-avatar">
                                        <span>${student.name}</span>
                                    </div>
                                </td>
                                <td>${student.email}</td>
                                <td>
                                    <form action="/removeStudentFromCourse" method="post" class="remove-student-form">
                                        <input type="hidden" name="courseId" value="${course.id}">
                                        <input type="hidden" name="studentId" value="${student.id}">
                                        <button type="submit" class="btn btn-remove">
                                            <i class="fas fa-user-minus"></i>
                                            Отчислить
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addLessonModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Добавить новый урок</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="/addLesson" method="post">
                <div class="modal-body">
                    <input type="hidden" name="courseId" value="${course.id}">
                    <div class="mb-3">
                        <label for="lessonTitle" class="form-label">
                            <i class="fas fa-heading"></i>
                            Название урока
                        </label>
                        <input type="text" class="form-control" id="lessonTitle" name="title" required>
                    </div>
                    <div class="mb-3">
                        <label for="lessonDescription" class="form-label">
                            <i class="fas fa-align-left"></i>
                            Описание урока
                        </label>
                        <textarea class="form-control" id="lessonDescription" name="description" rows="4"
                                  required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                    <button type="submit" class="btn btn-primary">Добавить урок</button>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
