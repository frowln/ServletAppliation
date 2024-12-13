<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Управление курсом</title>
    <link href="/css/home.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css2?family=Segoe+UI&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Образовательная Платформа</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Переключить навигацию">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/home">Главная</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/courses">Мои курсы</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/profile">Профиль</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <h2 class="mb-4">Управление курсом: ${course.name}</h2>

    <!-- Информация о курсе -->
    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Информация о курсе</h5>
            <p><strong>Описание:</strong> ${course.description}</p>
            <p><strong>Преподаватель:</strong> ${teacherName}</p>
            <a href="/editCourse?id=${course.id}" class="btn btn-secondary">Редактировать</a>
            <form action="/deleteCourse" method="post" style="display:inline;">
                <input type="hidden" name="id" value="${course.id}">
                <button type="submit" class="btn btn-danger" onclick="return confirm('Вы уверены, что хотите удалить этот курс?');">
                    Удалить курс
                </button>
            </form>

        </div>
    </div>

    <!-- Список студентов -->
    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Записанные студенты</h5>
            <table class="table">
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
                        <td>${student.name}</td>
                        <td>${student.email}</td>
                        <td>
                            <form action="/removeStudentFromCourse" method="post" style="display:inline;">
                                <input type="hidden" name="courseId" value="${course.id}">
                                <input type="hidden" name="studentId" value="${student.id}">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Вы уверены, что хотите удалить этого студента из курса?');">
                                    Удалить
                                </button>
                            </form>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Управление уроками -->
    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Уроки курса</h5>
            <a href="/addLesson?courseId=${course.id}" class="btn btn-primary mb-3">Добавить урок</a>
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Действия</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="lesson" items="${lessons}">
                    <tr>
                        <td>${lesson.id}</td>
                        <td>${lesson.name}</td>
                        <td style="text-align: left;">
                            <c:choose>
                                <c:when test="${fn:length(fn:trim(lesson.body)) > 100}">
                                    ${fn:substring(fn:trim(lesson.body), 0, 20)}...
                                </c:when>
                                <c:otherwise>
                                    ${fn:trim(lesson.body)}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="/editLesson?id=${lesson.id}" class="btn btn-secondary btn-sm">Редактировать</a>
                            <form action="/deleteLesson" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${lesson.id}">
                                <input type="hidden" name="courseId" value="${lesson.courseId}">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Вы уверены, что хотите удалить этот урок?');">
                                    Удалить
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

<footer class="text-center mt-5 py-3 bg-light">
    <p class="mb-0">Платформа для онлайн-обучения | Контакты | Социальные сети</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
