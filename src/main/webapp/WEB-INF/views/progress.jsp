<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Прогресс по курсу</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Прогресс по курсу</h2>
    <div class="progress" style="height: 30px;">
        <div class="progress-bar bg-success" role="progressbar"
             style="width: ${progress.progressPercentage}%"
             aria-valuenow="${progress.progressPercentage}"
             aria-valuemin="0" aria-valuemax="100">
            ${progress.progressPercentage}%
        </div>
    </div>
    <p class="mt-3">
        Завершено уроков: ${progress.completedLessons} из ${progress.totalLessons}
    </p>
</div>
</body>
</html>
