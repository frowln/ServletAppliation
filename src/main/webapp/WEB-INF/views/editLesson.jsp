<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Редактировать урок</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/home.css" rel="stylesheet" type="text/css">
    <link href="/css/editLesson.css" rel="stylesheet" type="text/css">
    <script src="https://cdn.tiny.cloud/1/fpvjnk02z3y6s4auzdm383yaqykj7wezhbke2l7wgt9y5pux/tinymce/6/tinymce.min.js"
            referrerpolicy="origin"></script>
    <script>
        tinymce.init({
            selector: '#body',
            plugins: 'advlist autolink lists link image charmap print preview anchor searchreplace visualblocks code fullscreen insertdatetime media table paste code help wordcount',
            toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help',
            height: 500,
            menubar: false,
            branding: false,
            skin: 'oxide',
            content_css: 'default',
            init_instance_callback: function (editor) {
                console.log("Editor is initialized");
            },
            setup: function (editor) {
                editor.on('change', function () {
                    editor.save();
                });
            }
        });
    </script>
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
                    <a class="nav-link" href="/home">
                        <i class="fas fa-home me-1"></i> Главная
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="/courses">
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

<div class="edit-lesson-container">
    <div class="page-header">
        <div class="header-content">
            <h1 class="page-title">Редактирование урока</h1>
            <p class="page-subtitle">Внесите необходимые изменения в урок</p>
        </div>
    </div>

    <div class="edit-content">
        <div class="edit-card">
            <div class="card-header">
                <i class="fas fa-edit"></i>
                <h3>Форма редактирования</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        <i class="fas fa-exclamation-circle me-2"></i>
                            ${error}
                    </div>
                </c:if>

                <form action="/editLesson" method="post" onsubmit="tinymce.triggerSave();" class="edit-form">
                    <input type="hidden" name="id" value="${lesson.id}">

                    <div class="form-group">
                        <label for="name" class="form-label">
                            <i class="fas fa-heading"></i>
                            Название урока
                        </label>
                        <input type="text" class="form-control" id="name" name="name" value="${lesson.name}" required>
                    </div>

                    <div class="form-group">
                        <label for="body" class="form-label">
                            <i class="fas fa-book-open"></i>
                            Содержание урока
                        </label>
                        <textarea class="form-control" id="body" name="body" required>${lesson.body}</textarea>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-save">
                            <i class="fas fa-save"></i>
                            Сохранить изменения
                        </button>
                        <a href="/manageCourse?id=${lesson.courseId}" class="btn btn-cancel">
                            <i class="fas fa-times"></i>
                            Отмена
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
