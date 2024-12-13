<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Добавить урок</title>
  <link href="/css/home.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css2?family=Segoe+UI&display=swap" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.tiny.cloud/1/fpvjnk02z3y6s4auzdm383yaqykj7wezhbke2l7wgt9y5pux/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
  <script>
    tinymce.init({
      selector: '#body',
      plugins: 'advlist autolink lists link image charmap print preview anchor searchreplace visualblocks code fullscreen insertdatetime media table paste code help wordcount',
      toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help',
      height: 300,
      menubar: false,
      branding: false,
      init_instance_callback: function (editor) {
        console.log("Editor is initialized");
      },
      setup: function (editor) {
        // Добавляем обработчик на форму
        editor.on('change', function () {
          editor.save();
        });
      }
    });
  </script>
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
  <h2 class="mb-4">Добавить урок</h2>

  <form action="/addLesson" method="post" onsubmit="tinymce.triggerSave();">
    <input type="hidden" name="courseId" value="${courseId}">
    <div class="mb-3">
      <label for="name" class="form-label">Название урока</label>
      <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="mb-3">
      <label for="body" class="form-label">Содержание урока</label>
      <textarea class="form-control" id="body" name="body" rows="5" required></textarea>
    </div>
    <button type="submit" class="btn btn-primary">Добавить</button>
    <a href="/manageCourse?id=${courseId}" class="btn btn-secondary">Отмена</a>
  </form>

</div>

<footer class="text-center mt-5 py-3 bg-light">
  <p class="mb-0">Платформа для онлайн-обучения | Контакты | Социальные сети</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
