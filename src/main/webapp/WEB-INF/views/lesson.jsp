<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${lesson.title}</title>
  <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<header>
  <h1>${lesson.title}</h1>
</header>
<main>
  <p>${lesson.content}</p>
</main>
<footer>
  <a href="/course?id=${lesson.courseId}" class="btn">Назад к курсу</a>
</footer>
</body>
</html>

