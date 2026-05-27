<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Game WS</title>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.1.1/bundles/stomp.umd.min.js"></script>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        #log { border: 1px solid #ccc; padding: 10px; min-height: 120px; white-space: pre-wrap; }
    </style>
</head>
<body>
<h2>Game WebSocket Test</h2>
<p>Session ID: <code id="sid">${sessionId! "N/A"}</code></p>
<button id="connectBtn">Connect</button>
<button id="disconnectBtn">Disconnect</button>

<h3>Messages</h3>
<div id="log"></div>

<script>
    const sessionId = "${(sessionId!'')?js_string}";
    const logEl = document.getElementById("log");
    const append = (msg) => logEl.textContent += msg + "\n";

    const wsScheme = location.protocol === "https:" ? "wss" : "ws";
    const brokerURL = wsScheme + "://" + location.host + "/ws-endpoint?sessionId=" + encodeURIComponent(sessionId);

    let client = null;

    document.getElementById("connectBtn").addEventListener("click", () => {
        if (client && client.active) return;

        client = new StompJs.Client({
            brokerURL,
            reconnectDelay: 5000,
            debug: (s) => append("[debug] " + s),
            onConnect: () => {
                append("Connected.");
                client.subscribe("/user/bonus", (message) => {
                    append("Bonus message: " + message.body);
                });
            },
            onStompError: (frame) => append("Broker error: " + frame.headers["message"]),
            onWebSocketClose: () => append("Disconnected.")
        });

        client.activate();
    });

    document.getElementById("disconnectBtn").addEventListener("click", () => {
        if (client) client.deactivate();
    });
</script>
</body>
</html>
