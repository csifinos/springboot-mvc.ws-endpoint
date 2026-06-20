<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>User Sessions</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-4">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Active Sessions</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead class="table-light">
                    <tr>
                        <th>User ID</th>
                        <th>Session ID</th>
                        <th>Location</th>
                        <th>Access Type</th>
                        <th>Created</th>
                        <th>Last Accessed</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list sessions as session>
                        <tr>
                            <td>${session.userId?string}</td>
                            <td><code>${session.sessionId?string}</code></td>
                            <td>${session.location?string}</td>
                            <td><span class="badge bg-info text-dark">${session.accessType?string}</span></td>
                            <td>${session.creationTime?string}</td>
                            <td>${session.lastAccessedTime?string}</td>
                        </tr>
                    <#else>
                        <tr>
                            <td colspan="6" class="text-center">No active sessions found.</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <footer class="text-center mt-4 text-muted small">
        <p>Uses the <a href="https://lite.ip2location.com" class="text-decoration-none">IP2Location LITE database</a>.</p>
    </footer>
</div>
</body>
</html>