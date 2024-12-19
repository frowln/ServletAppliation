<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результаты поиска | Chill Study</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/home.css" rel="stylesheet" type="text/css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark sticky-top">
    <div class="container">
        <a class="navbar-brand" href="/home">
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

<main class="main-content">
    <div class="container py-4">
        <h2 class="text-center mb-4">Результаты поиска</h2>
        <p class="text-center subtitle mb-5">По запросу: "${query}"</p>

        <div class="search-bar mb-4">
            <form action="/searchCourses" method="get" class="d-flex">
                <div class="input-group">
                        <span class="input-group-text bg-transparent border-0">
                            <i class="fas fa-search"></i>
                        </span>
                    <input type="text"
                           name="query"
                           class="form-control"
                           placeholder="Поиск курсов..."
                           value="${query}"
                           required>
                    <button type="submit" class="btn btn-primary">
                        Найти
                    </button>
                </div>
            </form>
        </div>

        <c:if test="${empty searchResults}">
            <div class="text-center py-5">
                <i class="fas fa-search fa-3x text-muted mb-4"></i>
                <h3 class="text-muted">Курсы не найдены</h3>
                <p class="text-muted mb-4">Попробуйте изменить параметры поиска</p>
                <a href="/home" class="btn btn-outline-primary">
                    <i class="fas fa-home me-2"></i>Вернуться на главную
                </a>
            </div>
        </c:if>

        <c:if test="${not empty searchResults}">
            <div class="row justify-content-center g-4">
                <c:forEach var="course" items="${searchResults}">
                    <div class="col-12 col-md-6 col-lg-4">
                        <div class="course-card-container">
                            <a href="/course?id=${course.id}" class="text-decoration-none">
                                <div class="course-card">
                                    <div class="card-body">
                                        <h5 class="card-title">
                                            <i class="fas fa-book-open me-2"></i>${course.name}
                                        </h5>
                                        <p class="card-text">${course.description}</p>

                                        <div class="rating mb-3">
                                            <c:forEach var="i" begin="1" end="5">
                                                <i class="fas fa-star ${i <= course.averageRating ? 'active' : ''}"></i>
                                            </c:forEach>
                                            <span class="rating-text">${String.format("%.1f", course.averageRating)}</span>
                                        </div>

                                        <c:if test="${user.role != 'teacher'}">
                                            <div class="course-action">
                                                <c:choose>
                                                    <c:when test="${enrolledCourseIds.contains(course.id)}">
                                                        <button type="button" class="btn btn-enrolled" disabled>
                                                            <i class="fas fa-check me-2"></i>Вы записаны
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form action="/enroll" method="post" class="d-inline">
                                                            <input type="hidden" name="userId" value="${user.id}">
                                                            <input type="hidden" name="courseId" value="${course.id}">
                                                            <button type="submit" class="btn btn-primary">
                                                                <i class="fas fa-graduation-cap me-2"></i>Записаться
                                                            </button>
                                                        </form>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const cards = document.querySelectorAll('.course-card-container');
        cards.forEach((card, index) => {
            card.style.animation = `fadeInUp 0.5s ease forwards ${index * 0.1}s`;
        });
    });
</script>
</body>
</html>
