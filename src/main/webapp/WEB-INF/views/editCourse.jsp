<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Редактировать курс</title>
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
  <h2 class="mb-4">Редактировать курс</h2>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <form action="/editCourse" method="post">
    <input type="hidden" name="id" value="${course.id}">
    <div class="mb-3">
      <label for="name" class="form-label">Название курса</label>
      <input type="text" class="form-control" id="name" name="name" value="${course.name}" required>
    </div>
    <div class="mb-3">
      <label for="description" class="form-label">Описание курса</label>
      <textarea class="form-control" id="description" name="description" rows="5" required>${course.description}</textarea>
    </div>
    <button type="submit" class="btn btn-primary">Сохранить</button>
    <a href="/manageCourse?id=${course.id}" class="btn btn-secondary">Отмена</a>
  </form>
</div>

<footer class="text-center mt-5 py-3 bg-light">
  <p class="mb-0">Платформа для онлайн-обучения | Контакты | Социальные сети</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
