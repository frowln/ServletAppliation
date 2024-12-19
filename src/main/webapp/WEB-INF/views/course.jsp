<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${course.name} | Образовательная Платформа</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/course.css" rel="stylesheet" type="text/css">
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

<div class="container py-5">
    <div class="course-header animate-fade-in">
        <h1 class="course-title">${course.name}</h1>
        <p class="course-description">${course.description}</p>

        <div class="d-flex align-items-center gap-4 mb-4">
            <div class="rating">
                <c:forEach var="i" begin="1" end="5">
                    <i class="fas fa-star ${i <= course.averageRating ? 'active' : ''}"></i>
                </c:forEach>
                <span class="ms-2 text-white">${course.averageRating}/5</span>
            </div>
            <div class="course-stats">
                <i class="fas fa-users me-2"></i>
                <span>${enrolledStudents} студентов</span>
            </div>
            <div class="course-stats">
                <i class="fas fa-book me-2"></i>
                <span>${lessons.size()} уроков</span>
            </div>
        </div>

        <div class="course-actions">
            <c:if test="${user.role != 'teacher'}">
                <c:choose>
                    <c:when test="${enrolled}">
                        <form action="/leaveCourse" method="post" class="d-inline">
                            <input type="hidden" name="userId" value="${user.id}">
                            <input type="hidden" name="courseId" value="${course.id}">
                            <button type="submit" class="btn btn-danger btn-lg">
                                <i class="fas fa-times me-2"></i>Покинуть курс
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="/enroll" method="post" class="d-inline">
                            <input type="hidden" name="userId" value="${user.id}">
                            <input type="hidden" name="courseId" value="${course.id}">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-sign-in-alt me-2"></i>Записаться на курс
                            </button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>

    <div class="row mt-5">
        <div class="col-lg-8">
            <div class="lesson-list animate-fade-in" style="animation-delay: 0.2s">
                <h2 class="mb-4">
                    <i class="fas fa-book-reader me-2"></i>Содержание курса
                </h2>

                <c:forEach var="lesson" items="${lessons}" varStatus="status">
                    <div class="lesson-item">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h3 class="lesson-title">
                                    <span class="lesson-number me-2">${status.index + 1}.</span>
                                        ${lesson.name}
                                </h3>
                            </div>
                            <div class="lesson-actions">
                                <a href="/lesson?id=${lesson.id}" class="btn btn-primary btn-sm">
                                    <i class="fas fa-play me-1"></i>Начать
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="course-progress-card animate-fade-in" style="animation-delay: 0.4s">
                <h3>Ваш прогресс</h3>
                <c:if test="${not empty progress}">
                    <div class="progress mb-3">
                        <div class="progress-bar"
                             role="progressbar"
                             style="width: ${progress.completedLessons * 100 / progress.totalLessons}%;"
                             aria-valuenow="${progress.completedLessons * 100 / progress.totalLessons}"
                             aria-valuemin="0"
                             aria-valuemax="100">
                                ${progress.completedLessons * 100 / progress.totalLessons}%
                        </div>
                    </div>
                    <p class="text-center">
                        Завершено уроков: ${progress.completedLessons} из ${progress.totalLessons}
                    </p>
                </c:if>
                <c:if test="${empty progress}">
                    <p class="text-center text-muted">Прогресс недоступен.</p>
                </c:if>
            </div>

            <div class="teacher-card animate-fade-in" style="animation-delay: 0.6s">
                <h3>Преподаватель</h3>
                <c:if test="${not empty teacher}">
                    <div class="d-flex align-items-center">
                        <img src="${teacher.avatarUrl != null ? teacher.avatarUrl : '/images/default-avatar.png'}"
                             alt="Преподаватель"
                             class="teacher-avatar"
                             style="width: 80px; height: 80px; border-radius: 50%; object-fit: cover;">
                        <div class="ms-3">
                            <h4 class="teacher-name">${teacher.name}</h4>
                        </div>
                    </div>
                </c:if>
                <c:if test="${empty teacher}">
                    <p class="text-muted">Информация о преподавателе отсутствует.</p>
                </c:if>
            </div>
        </div>
    </div>

    <div class="mt-5">
        <h2>Отзывы</h2>
        <c:forEach var="review" items="${reviews}">
            <div class="review-card">
                <div class="review-header d-flex align-items-center">
                    <img src="${review.avatarUrl != null ? review.avatarUrl : '/images/default-avatar.png'}"
                         alt="Аватар пользователя" class="review-avatar">
                    <div class="ms-3">
                        <h5 class="review-user-name">${review.userName}</h5>
                        <small class="text-muted review-date" data-date="${review.createdAt}"></small>
                    </div>
                </div>
                <div class="rating mt-2">
                    <c:forEach var="i" begin="1" end="5">
                        <i class="fas fa-star ${i <= review.rating ? 'active' : ''}"></i>
                    </c:forEach>
                    <span class="rating-text">${review.rating}</span>
                </div>
                <p class="review-comment mt-3">${review.comment}</p>
            </div>
        </c:forEach>

        <c:if test="${empty reviews}">
            <p class="text-muted">Пока отзывов нет. Будьте первым!</p>
        </c:if>

        <c:if test="${not empty user and enrolled}">
            <form action="/addReview" method="post" class="mt-4">
                <input type="hidden" name="courseId" value="${course.id}">
                <div class="mb-3">
                    <label for="rating" class="form-label">Оценка</label>
                    <select class="form-select" id="rating" name="rating" required>
                        <option value="5">5 - Отлично</option>
                        <option value="4">4 - Хорошо</option>
                        <option value="3">3 - Нормально</option>
                        <option value="2">2 - Плохо</option>
                        <option value="1">1 - Ужасно</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="comment" class="form-label">Ваш отзыв</label>
                    <textarea class="form-control" id="comment" name="comment" rows="3" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Добавить отзыв</button>
            </form>
        </c:if>

        <c:if test="${not empty user and not enrolled}">
            <p class="text-muted mt-4">Чтобы оставить отзыв, необходимо быть записанным на этот курс.</p>
        </c:if>

        <c:if test="${empty user}">
            <div class="text-muted mt-4">
                <i class="fas fa-info-circle me-2"></i>
                Чтобы оставить отзыв, <a href="/login">войдите</a> или <a href="/registration">зарегистрируйтесь</a>.
            </div>
        </c:if>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Обработка отправки формы
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function(e) {
            const submitButton = this.querySelector('button[type="submit"]');
            submitButton.disabled = true;
            submitButton.innerHTML = '<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>Подождите...';
        });
    });
</script>
</body>
</html>
