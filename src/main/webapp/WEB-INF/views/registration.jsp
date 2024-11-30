<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Регистрация</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="/css/authetification.css">
  <script>
    function toggleRoleField() {
      var role = document.getElementById("role").value;
      var additionalField = document.getElementById("additionalField");

      if (role === "teacher") {
        additionalField.style.display = "block"; // Показываем дополнительное поле
      } else {
        additionalField.style.display = "none"; // Скрываем дополнительное поле
      }
    }
  </script>
</head>
<body>
<div class="container">
  <!-- Центрируем форму регистрации -->
  <div class="row justify-content-center">
    <div class="col-md-4">
      <div class="card">
        <h3 class="card-title text-center">Создайте аккаунт</h3>
        <c:if test="${not empty error}">
          <div class="alert alert-danger" role="alert">
              ${error}
          </div>
        </c:if>
        <form action="/registration" method="POST">
          <div class="form-group">
            <label for="name">Имя</label>
            <input type="text" class="form-control" id="name" name="name" required>
          </div>
          <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
          </div>
          <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" id="password" name="password" required>
          </div>
          <div class="form-group">
            <label for="confirmPassword">Подтвердите пароль</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
          </div>

          <div class="form-group">
            <label for="role">Что ты хочешь?</label>
            <select class="form-control" id="role" name="role" onchange="toggleRoleField()" required>
              <option value="student">Учиться</option>
              <option value="teacher">Преподавать</option>
            </select>
          </div>


          <button type="submit" class="btn btn-primary btn-block">Зарегистрироваться</button>
          <div class="text-center mt-2">
            <span>Уже есть аккаунт? <a href="/login">Войти</a></span>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
