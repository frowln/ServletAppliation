<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добавить Курс</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="/css/home.css" rel="stylesheet">
    <link href="/css/addCourse.css" rel="stylesheet">
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
                    <a class="nav-link active" href="/home">
                        <i class="fas fa-home me-1"></i> Главная
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/courses">
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

<div class="add-course-container">
    <div class="form-card">
        <div class="form-header">
            <h1 class="form-title">Создать новый курс</h1>
            <p class="form-subtitle">Создайте увлекательный курс для ваших студентов. Добавьте подробное описание и
                структурируйте материал.</p>
        </div>

        <form action="/addCourse" method="post" class="course-form">
            <div class="form-group">
                <label for="name" class="form-label">Название курса</label>
                <input type="text" class="form-control" id="name" name="name" required
                       placeholder="Введите название курса">
            </div>

            <div class="form-group">
                <label for="description" class="form-label">Описание курса</label>
                <textarea class="form-control" id="description" name="description" rows="5" required
                          placeholder="Опишите, чему научатся студенты в этом курсе"></textarea>
            </div>

            <div class="feature-list">
                <div class="feature-item">
                    <i class="fas fa-video feature-icon"></i>
                    <div class="feature-text">
                        Добавляйте видео-уроки и интерактивные материалы
                    </div>
                </div>
                <div class="feature-item">
                    <i class="fas fa-tasks feature-icon"></i>
                    <div class="feature-text">
                        Создавайте задания и тесты для проверки знаний
                    </div>
                </div>
                <div class="feature-item">
                    <i class="fas fa-users feature-icon"></i>
                    <div class="feature-text">
                        Взаимодействуйте со студентами через комментарии
                    </div>
                </div>
            </div>

            <div class="btn-container">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-plus-circle"></i>
                    Создать курс
                </button>
                <a href="/home" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i>
                    Назад
                </a>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>