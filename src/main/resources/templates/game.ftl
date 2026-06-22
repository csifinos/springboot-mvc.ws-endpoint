<#import "layout.ftl" as l>
<@l.base_layout>
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Game WebSocket Test</h5>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <button id="connectBtn" class="btn btn-success">Connect</button>
                <button id="disconnectBtn" class="btn btn-danger">Disconnect</button>
            </div>
            <h6>Messages</h6>
            <div id="log" class="p-3 rounded border"
                 style="height: 300px; overflow-y: auto; background-color: #f8f9fa;"></div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.1.1/bundles/stomp.umd.min.js"></script>
    <script>
        const brokerURL = "${(brokerUrl!'')?js_string}";
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
                        client.subscribe(`/user/queue/bonus`, (message) => {
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
</@l.base_layout>