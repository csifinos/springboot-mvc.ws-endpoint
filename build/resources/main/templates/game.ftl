<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Game WS</title>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.1.1/bundles/stomp.umd.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        #log {
            border: 1px solid #ccc;
            padding: 10px;
            min-height: 120px;
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
<h2>Game WebSocket Test</h2>
<p>WS Session ID: <code id="wsSessionId">${wsSessionId! "N/A"}</code></p>
<button id="connectBtn">Connect</button>
<button id="disconnectBtn">Disconnect</button>

<h3>Messages</h3>
<div id="log"></div>

<script>
    const namespace = "${(namespace!'')?js_string}";
    const instance = "${(instance!'')?js_string}";
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
                    client.subscribe(`/${namespace}/${instance}/${wsSessionId}/bonus`, (message) => {
                        const parsed = JSON.parse(message.body);
                        append("Bonus #" + parsed.sequence + " message: " + JSON.stringify(parsed.payload));
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
