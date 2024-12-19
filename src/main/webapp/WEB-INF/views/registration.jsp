<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация | Образовательная платформа</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="/css/authetification.css">
</head>
<body>
<div class="background-animation"></div>

<div class="container">
    <div class="card">
        <div class="logo-container floating">
            <div class="logo-bg"></div>
            <div class="logo-icon">
                <i class="fas fa-user-graduate"></i>
            </div>
        </div>

        <h1 class="card-title">Создайте аккаунт</h1>
        <p class="subtitle">Присоединяйтесь к нашей образовательной платформе</p>

        <c:if test="${not empty error}">
            <div class="alert">
                <i class="fas fa-exclamation-triangle"></i>
                <span>${error}</span>
            </div>
        </c:if>

        <form action="/registration" method="POST">
            <div class="form-group">
                <div class="input-wrapper">
                    <input type="text"
                           class="form-control"
                           id="name"
                           name="name"
                           placeholder="Введите ваше имя"
                           required>
                    <i class="fas fa-user input-icon"></i>
                </div>
            </div>

            <div class="form-group">
                <div class="input-wrapper">
                    <input type="email"
                           class="form-control"
                           id="email"
                           name="email"
                           placeholder="Введите email"
                           required>
                    <i class="fas fa-envelope input-icon"></i>
                </div>
            </div>

            <div class="form-group">
                <div class="input-wrapper">
                    <input type="password"
                           class="form-control"
                           id="password"
                           name="password"
                           placeholder="Придумайте пароль"
                           required>
                    <i class="fas fa-lock input-icon"></i>
                </div>
            </div>

            <div class="form-group">
                <div class="input-wrapper">
                    <input type="password"
                           class="form-control"
                           id="confirmPassword"
                           name="confirmPassword"
                           placeholder="Подтвердите пароль"
                           required>
                    <i class="fas fa-shield-alt input-icon"></i>
                </div>
            </div>

            <div class="form-group">
                <div class="input-wrapper">
                    <select class="form-control"
                            id="role"
                            name="role"
                            onchange="toggleRoleField()"
                            required>
                        <option value="" disabled selected>Выберите роль</option>
                        <option value="student">Хочу учиться</option>
                        <option value="teacher">Хочу преподавать</option>
                    </select>
                    <i class="fas fa-graduation-cap input-icon"></i>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">
                Создать аккаунт
                <i class="fas fa-arrow-right ml-2"></i>
            </button>

            <div class="register-link">
                Уже есть аккаунт?
                <a href="/login">Войти</a>
            </div>
        </form>
    </div>
</div>

<script>
    document.querySelectorAll('.form-control').forEach(input => {
        input.addEventListener('focus', function () {
            this.parentElement.classList.add('focused');
        });

        input.addEventListener('blur', function () {
            if (!this.value) {
                this.parentElement.classList.remove('focused');
            }
        });
    });

    function toggleRoleField() {
        const role = document.getElementById("role").value;
        const form = document.querySelector('form');

        if (role === "teacher") {
            form.classList.add('teacher-mode');
        } else {
            form.classList.remove('teacher-mode');
        }
    }

    document.getElementById('confirmPassword').addEventListener('input', function () {
        const password = document.getElementById('password').value;
        const confirmPassword = this.value;

        if (password !== confirmPassword) {
            this.setCustomValidity('Пароли не совпадают');
        } else {
            this.setCustomValidity('');
        }
    });
</script>
</body>
</html>
