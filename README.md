# WebSocket Session Demo (WS-First)

```mermaid
sequenceDiagram
    participant User as Browser
    participant App as App Instance (A or B)
    participant Redis
    participant PubSub as Redis Pub/Sub
    
    Note over User, Redis: 1. Login & WS Preparation
    User->>App: POST /login
    App->>Redis: Save HttpSession
    App-->>User: Set-Cookie (SessionID)
    User->>App: GET /load-game
    App-->>User: Returns WS Session ID

    Note over User, Redis: 2. WebSocket Handshake
    User->>App: WS Handshake (with HTTP Session ID)
    
    App ->>App: Check if the http session belongs to the user.
    App ->>App: Check if the user has any other WS active.
    App->>Redis: Create a SimpSession ID joined with the User and the HTTP Session (TTL 120s)
    App->>User: WS Connection Established

    Note over User, PubSub: 3. Async Bonus Delivery
    Note right of App: Bonus Triggered
    App->>App: A new bonus assigned to the user (though another flow)
    App->>Redis: Find the presence of the user
    App->>PubSub: Publish "BONUS" to topic along with the simpSessionID
    PubSub-->>App: Received by Instance holding which holding the simpSessionID
    App->>User: Send "Bonus Awarded" message via WebSocket

    Note over User, Redis: 4. Heartbeat Flow
    User->>App: WS Message: "HEARTBEAT"
    App->>Redis: Refresh TTL(SimpleSessionID)
```

This project demonstrates a **WebSocket-first session model** where:

- Each `/game` uses a HttpSession to communicate with the server.
- WS handshake validates this `HttpSession` and the User that belongs it.
- Presence is tracked per `simpSessionId` with TTL refresh via heartbeat.
- Bonus messages are published via Redis pub/sub to specific `simplSessionId`.

## Quick start

1. Start the app

```bash
./gradlew bootRun
```

2. Open game page

- `http://localhost:8080/game`
- Click `Connect` to open WebSocket.

3. Trigger a bonus for the same user

```bash
curl -i -X POST "http://localhost:8080/v1/bonus" \
  -H "Content-Type: application/json" \
  -d '{"user":"user0","bonusId":"b1"}'
```

