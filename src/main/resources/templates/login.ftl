<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-4">Sign In</h2>

                    <#if RequestParameters.error??>
                        <div class="alert alert-danger">Invalid username or password.</div>
                    </#if>

                    <form action="/login" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="mb-3">
                            <label for="username" class="form-label">Username:</label>
                            <input type="text" id="username" name="username" class="form-control" required />
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password:</label>
                            <input type="password" id="password" name="password" class="form-control" required />
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Sign In</button>
                    </form>
                </div>
            </div>
            <footer class="text-center mt-4 text-muted small">
                <p>Uses the <a href="https://lite.ip2location.com" class="text-decoration-none">IP2Location LITE database</a>.</p>
            </footer>
        </div>
    </div>
</div>
</body>
</html>