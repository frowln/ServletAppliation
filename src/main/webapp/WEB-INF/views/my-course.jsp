<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Мои Курсы</title>
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
          <a class="nav-link active" href="/courses">Мои курсы</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/profile">Профиль</a>
        </li>
        <c:if test="${empty user}">
          <li class="nav-item">
            <a class="nav-link" href="/login">Войти</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/register">Регистрация</a>
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


<div class="content container">
  <h2 class="text-center my-5">Мои Курсы</h2>

  <c:choose>

    <c:when test="${user.role == 'student'}">
      <div class="row">
        <c:forEach var="course" items="${enrolledCourses}">
          <div class="col-md-4 mb-4">
            <div class="card h-100">
              <div class="card-body">
                <h5 class="card-title">${course.name}</h5>
                <p class="card-text">${course.description}</p>
                <a href="/course?id=${course.id}" class="btn btn-primary">Подробнее</a>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:when>


    <c:when test="${user.role == 'teacher'}">
      <h3 class="mb-4">Созданные курсы</h3>
      <div class="row">
        <c:forEach var="course" items="${createdCourses}">
          <div class="col-md-4 mb-4">
            <div class="card h-100">
              <div class="card-body">
                <h5 class="card-title">${course.name}</h5>
                <p class="card-text">${course.description}</p>
                <a href="/manageCourse.jsp?courseId=${course.id}" class="btn btn-secondary">Управление</a>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:when>
  </c:choose>
</div>


<footer class="text-center mt-5 py-3 bg-light">
  <p class="mb-0">Платформа для онлайн-обучения | Контакты | Социальные сети</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
