<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Мой Профиль</title>
  <link href="/css/profile.css" rel="stylesheet" type="text/css">
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
          <a class="nav-link" href="#">Профиль</a>
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
<!-- Контейнер для профиля -->
<div class="container mt-5">
  <div class="row">
    <div class="col-md-12">
      <h2>Мой профиль</h2>

      <!-- Сообщение об успешном обновлении -->
      <c:if test="${not empty successMessage}">
        <div class="alert alert-success" role="alert">
            ${successMessage}
        </div>
      </c:if>

      <!-- Сообщение об ошибке -->
      <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
      </c:if>

      <!-- Форма обновления профиля -->
      <form action="/profile" method="post">
        <div class="mb-3">
          <label for="name" class="form-label">Имя</label>
          <input type="text" class="form-control" id="name" name="name" value="${user.name}">
        </div>
        <div class="mb-3">
          <label for="email" class="form-label">Email</label>
          <input type="email" class="form-control" id="email" name="email" value="${user.email}">
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">Новый пароль</label>
          <input type="password" class="form-control" id="password" name="password" placeholder="Введите новый пароль">
        </div>
        <div class="mb-3">
          <label for="confirmPassword" class="form-label">Подтвердите новый пароль</label>
          <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Подтвердите новый пароль">
        </div>
        <button type="submit" class="btn btn-primary">Обновить профиль</button>
      </form>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
