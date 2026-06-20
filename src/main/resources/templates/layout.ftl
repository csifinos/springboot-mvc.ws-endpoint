<#macro base_layout>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Game Portal</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="/">Home</a>
            <div class="navbar-nav me-auto">
                <a class="nav-link" href="/game">Game</a>
                <a class="nav-link" href="/sessions">Sessions</a>
            </div>
            <form action="/logout" method="post" class="d-flex">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-outline-danger btn-sm">Logout</button>
            </form>
        </div>
    </nav>
    <div class="container">
        <#nested>
    </div>
    <footer class="text-center mt-5 text-muted small">
        <p>Uses the <a href="https://lite.ip2location.com" class="text-decoration-none">IP2Location LITE database</a>.
        </p>
    </footer>
    </body>
    </html>
</#macro>