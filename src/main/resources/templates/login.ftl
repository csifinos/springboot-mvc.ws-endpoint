<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
<h2>Sign In</h2>

<#if RequestParameters.error??>
    <p style="color: red;">Invalid username or password.</p>
</#if>

<form action="/login" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required />
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required />
    </div>
    <button type="submit">Sign In</button>
</form>
</body>
</html>