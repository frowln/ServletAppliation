<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Образовательная Платформа</title>
  <link href="/css/home.css" rel="stylesheet" type="text/css"> <!-- Подключаем внешний CSS файл -->
  <link href="https://fonts.googleapis.com/css2?family=Segoe+UI&display=swap" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- Навигационная панель -->
<nav class="navbar navbar-expand-lg navbar-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Образовательная Платформа</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Переключить навигацию">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item">
          <a class="nav-link active" href="/home">Главная</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/courses">Мои курсы</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/profile">Профиль</a>
        </li>
        <c:if test="${empty user}">
          <li class="nav-item">
            <a class="nav-link" href="/login">Войти</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/registration">Регистрация</a>
          </li>
        </c:if>
        <c:if test="${not empty user}">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                ${user.name}
            </a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="/profile">Мой профиль</a></li>
              <li><a class="dropdown-item" href="/logout">Выйти</a></li>
            </ul>
          </li>
        </c:if>
      </ul>
    </div>
  </div>
</nav>

<!-- Блок Все курсы -->
<div class="content container">
  <h2 class="text-center mb-5">Все курсы</h2>

  <!-- Поисковая строка -->
  <div class="search-bar mb-4">
    <input type="text" class="form-control" placeholder="Поиск курсов...">
  </div>

  <!-- Кнопка добавления курса для преподавателя -->
  <div class="search-bar mb-4">
    <c:if test="${user.role == 'teacher'}">
      <div class="text-end mb-4">
        <a href="/addCourse" class="btn btn-add-course">Добавить курс</a>
      </div>
    </c:if>
  </div>

  <!-- Карточки курсов -->
  <div class="row justify-content-center">
    <c:forEach var="course" items="${courses}">
      <div class="course-card-container mb-4">
        <a href="/course?id=${course.id}" class="card course-card text-decoration-none text-dark">
        <div class="card course-card">
          <div class="card-body">
            <h5 class="card-title">${course.name}</h5>
            <p class="card-text">${course.description}</p>
            <c:if test="${user.role != 'teacher'}">
              <c:if test="${user == null}">
                <form action="/login" method="get">
                  <input type="hidden" name="error" value="not_authenticated">
                  <button type="submit" class="btn btn-primary">Записаться</button>
                </form>
              </c:if>

              <c:if test="${user != null}">
                <c:choose>
                  <c:when test="${enrolledCourseIds.contains(course.id)}">
                    <button type="button" class="btn btn-light" disabled>Вы записаны</button>
                  </c:when>
                  <c:otherwise>
                    <form action="/enroll" method="post" class="d-inline">
                      <input type="hidden" name="userId" value="${user.id}">
                      <input type="hidden" name="courseId" value="${course.id}">
                      <button type="submit" class="btn btn-primary">Записаться</button>
                    </form>
                  </c:otherwise>
                </c:choose>
              </c:if>
            </c:if>
          </div>
        </div>
        </a>
      </div>
    </c:forEach>
  </div>
</div>

<!-- Информация о сайте -->
<footer class="text-center mt-5">
  <p>Платформа для онлайн-обучения | Контакты | Социальные сети</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
