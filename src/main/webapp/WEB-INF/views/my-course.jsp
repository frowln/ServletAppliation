<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мои Курсы</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/home.css" rel="stylesheet" type="text/css">
    <link href="/css/myCourses.css" rel="stylesheet" type="text/css">
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
                    <a class="nav-link active" href="/my-courses">
                        <i class="fas fa-book-open me-1"></i> Мои курсы
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

<div class="my-courses-container">
    <div class="page-header">
        <div class="header-left">
            <h1 class="page-title">Мои курсы</h1>
            <p class="page-subtitle">Управляйте своими курсами и отслеживайте прогресс обучения</p>
        </div>
        <c:if test="${user.role == 'teacher'}">
            <div class="header-right">
                <a href="/addCourse" class="btn-create-course">
                    <i class="fas fa-plus"></i>
                    Создать курс
                </a>
            </div>
        </c:if>
    </div>

    <c:choose>
        <c:when test="${user.role == 'student'}">
            <c:if test="${empty enrolledCourses}">
                <div class="empty-state">
                    <i class="fas fa-book-reader empty-state-icon"></i>
                    <h3>У вас пока нет курсов</h3>
                    <p class="empty-state-text">Начните свое обучение прямо сейчас! Выберите курс из каталога.</p>
                    <a href="/home" class="btn btn-primary">
                        <i class="fas fa-search"></i>
                        Найти курсы
                    </a>
                </div>
            </c:if>

            <div class="courses-grid">
                <c:forEach var="course" items="${enrolledCourses}">
                    <div class="course-card animate-fade-in">
                        <div class="course-content">
                            <h3 class="course-title">${course.name}</h3>
                            <p class="course-description">${course.description}</p>
                            <div class="course-actions">
                                <a href="/course?id=${course.id}" class="btn btn-primary">
                                    <i class="fas fa-play"></i>
                                    Продолжить обучение
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>

        <c:when test="${user.role == 'teacher'}">

            <c:if test="${empty createdCourses}">
                <div class="empty-state">
                    <i class="fas fa-chalkboard-teacher empty-state-icon"></i>
                    <h3>У вас пока нет созданных курсов</h3>
                    <p class="empty-state-text">Создайте свой первый курс и начните делиться знаниями!</p>
                    <a href="/addCourse" class="btn btn-primary">
                        <i class="fas fa-plus"></i>
                        Создать курс
                    </a>
                </div>
            </c:if>

            <div class="courses-grid">
                <c:forEach var="course" items="${createdCourses}">
                    <div class="course-card animate-fade-in">
                        <div class="course-content">
                            <h3 class="course-title">${course.name}</h3>
                            <p class="course-description">${course.description}</p>
                            <div class="course-actions">
                                <a href="/manageCourse?id=${course.id}" class="btn btn-primary">
                                    <i class="fas fa-cog"></i>
                                    Управление
                                </a>
                                <a href="/course?id=${course.id}" class="btn btn-outline">
                                    <i class="fas fa-eye"></i>
                                    Просмотр
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
    </c:choose>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>