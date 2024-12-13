<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${course.name}</title>
  <link href="/css/home.css" rel="stylesheet" type="text/css">
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
  <h1>${course.name}</h1>
  <p>${course.description}</p>

  <div class="row">
    <c:forEach var="lesson" items="${lessons}">
      <div class="col-md-4 mb-4">
        <div class="card h-100">
          <div class="card-body">
            <h5 class="card-title">${lesson.name}</h5>
            <a href="/lesson?id=${lesson.id}" class="btn btn-primary">Подробнее</a>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</div>

<footer class="text-center mt-5 py-3 bg-light">
  <p class="mb-0">Платформа для онлайн-обучения | Контакты | Социальные сети</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
