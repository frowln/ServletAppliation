<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${course.name}</title>
  <link rel="stylesheet" href="/css/course.css">
</head>
<body>
<header>
  <h1>${course.name}</h1>
  <p>${course.description}</p>
</header>

<main class="lessons-container">
  <c:forEach var="lesson" items="${lessons}">
    <div class="lesson-card">
      <h2>${lesson.title}</h2>
      <p>${lesson.content.substring(0, Math.min(100, lesson.content.length()))}...</p>
      <a href="/lesson?id=${lesson.id}" class="btn">Подробнее</a>
    </div>
  </c:forEach>
</main>
</body>
</html>

