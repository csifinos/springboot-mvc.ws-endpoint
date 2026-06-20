<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Game WS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.1.1/bundles/stomp.umd.min.js"></script>
    <style>
        #log {
            height: 300px;
            overflow-y: auto;
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            font-family: monospace;
            white-space: pre-wrap;
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-4">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Game WebSocket Test</h5>
        </div>
        <div class="card-body">
            <p>WS Session ID: <span class="badge bg-secondary" id="wsSessionId">${wsSessionId! "N/A"}</span></p>

            <div class="mb-3">
                <button id="connectBtn" class="btn btn-success">Connect</button>
                <button id="disconnectBtn" class="btn btn-danger">Disconnect</button>
            </div>

            <h6 class="mt-4">Messages</h6>
            <div id="log" class="p-3 rounded border"></div>
        </div>
    </div>

    <footer class="text-center mt-4 text-muted small">
        <p>Uses the <a href="https://lite.ip2location.com" class="text-decoration-none">IP2Location LITE database</a>.</p>
    </footer>
</div>

<script>
    const wsSessionId = "${(wsSessionId!'')?js_string}";
    const brokerURL = "${(brokerUrl!'')?js_string}" + encodeURIComponent(wsSessionId);
    const reconnectDelay = "${(reconnectDelay!5000)?c}";
    const heartbeatIncoming = "${(heartbeatIncoming!10000)?c}";
    const heartbeatOutgoing = "${(heartbeatOutgoing!10000)?c}";
    const refreshInterval = "${(refreshInterval!12000)?c}";

    const logEl = document.getElementById("log");
    const append = (msg) => logEl.textContent += msg + "\n";

    let client = null;
    let refreshTimer = null;

    document
        .getElementById("connectBtn")
        .addEventListener("click", () => {
            if (client && client.active) return;

            client = new StompJs.Client({
                brokerURL,
                reconnectDelay,
                heartbeatIncoming,
                heartbeatOutgoing,
                debug: (s) => append("[debug] " + s),
                onConnect: () => {
                    append("Connected.");
                    client.subscribe(`/user/bonus`, (message) => {
                        const parsed = JSON.parse(message.body);
                        append("Bonus: message: " + JSON.stringify(parsed.payload));
                    });
                    refreshTimer = setInterval(() => {
                        append("Sending heartbeat...");
                        client.publish({destination: "/app/heartbeat", body: "{}"});
                    }, refreshInterval);
                },
                onStompError: (frame) => append("Broker error: " + frame.headers["message"]),
                onWebSocketClose: () => {
                    if (refreshTimer) {
                        clearInterval(refreshTimer);
                        refreshTimer = null;
                    }
                    append("Disconnected.");
                }
            });

            client.activate();
        });

    document
        .getElementById("disconnectBtn")
        .addEventListener("click", () => {
            if (refreshTimer) {
                clearInterval(refreshTimer);
                refreshTimer = null;
            }
            if (client) client.deactivate();
        });
</script>
</body>
</html>
