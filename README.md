# WebSocket Session Demo (WS-First)

This project demonstrates a **WebSocket-first session model** where:

- Each `/game` load issues a stable `wsSessionId` stored in Redis.
- WS handshake validates `wsSessionId` directly (no HTTP session required).
- Presence is tracked per `wsSessionId` with TTL refresh via heartbeat.
- Bonus messages are published via Redis pub/sub to specific `wsSessionId`.
- No HTTP session overhead—session lifecycle is WebSocket-driven.

## Quick start

1. Start the app

```bash
./gradlew bootRun
```

2. Open game page

- `http://localhost:8080/game`
- Note the `wsSessionId` displayed on the page.
- Click `Connect` to open WebSocket.

3. Trigger a bonus for the same WS session

```bash
WS_SESSION_ID="69e4a836-cff0-47da-b86c-3a907287b5cb"

curl -i -X POST "http://localhost:8080/v1/bonus?wsSessionId=${WS_SESSION_ID}" \
  -H "Content-Type: application/json" \
  -d '{"playerId":"p1","bonusId":"b1"}'
```

5) Run tests

```bash
./gradlew test
```
