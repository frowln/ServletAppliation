<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход | Образовательная платформа</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="/css/authetification.css">
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card">
                <div class="logo-container">
                    <div class="logo-bg"></div>
                    <div class="logo-icon">
                        <i class="fas fa-graduation-cap fa-2x"></i>
                    </div>
                </div>

                <h1 class="card-title">Добро пожаловать!</h1>
                <p class="subtitle">Войдите в свой аккаунт для продолжения</p>

                <c:if test="${not empty error}">
                    <div class="alert">
                        <i class="fas fa-exclamation-circle"></i>
                        <span>${error}</span>
                    </div>
                </c:if>

                <form action="/login" method="POST">
                    <div class="form-group form-floating">
                        <i class="fas fa-envelope input-icon"></i>
                        <input type="email"
                               class="form-control input-with-icon"
                               id="email"
                               name="email"
                               placeholder=" "
                               required>
                        <label for="email">Email адрес</label>
                    </div>

                    <div class="form-group form-floating">
                        <i class="fas fa-lock input-icon"></i>
                        <input type="password"
                               class="form-control input-with-icon"
                               id="password"
                               name="password"
                               placeholder=" "
                               required>
                        <label for="password">Пароль</label>
                    </div>

                    <button type="submit" class="btn btn-primary ripple">
                        <span>Войти</span>
                        <i class="fas fa-arrow-right ml-2"></i>
                    </button>

                    <div class="register-link">
                        <p>
                            Нет аккаунта?
                            <a href="/registration">Зарегистрироваться</a>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    document.querySelectorAll('.ripple').forEach(button => {
        button.addEventListener('click', function (e) {
            let x = e.clientX - e.target.offsetLeft;
            let y = e.clientY - e.target.offsetTop;

            let ripples = document.createElement('span');
            ripples.style.left = x + 'px';
            ripples.style.top = y + 'px';
            this.appendChild(ripples);

            setTimeout(() => {
                ripples.remove();
            }, 1000);
        });
    });

    document.querySelectorAll('.form-floating input').forEach(input => {
        input.addEventListener('focus', function () {
            this.parentElement.classList.add('focused');
        });
        input.addEventListener('blur', function () {
            if (!this.value) {
                this.parentElement.classList.remove('focused');
            }
        });
    });
</script>
</body>
</html>
