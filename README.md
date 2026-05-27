# WebSocket Session Demo

This project sends a WebSocket message to a specific browser session.

## 1) Start the app

```bash
./gradlew bootRun
```

App runs on http://localhost:8080.

## 2) Open the game page (creates HTTP session)
   
Open in browser: http://localhost:8080/game

On the page:
- Click Connect (opens STOMP WebSocket connection).
- Keep this tab open.

## 3) Trigger bonus with the same session

Use curl with a cookie jar so the request uses the same JSESSIONID.

```shell
SESSION_ID="4B4C905219308622EAA7A34FC1972BB9"

curl -i -X POST http://localhost:8080/v1/bonus \
  -H "Cookie: JSESSIONID=${SESSION_ID}" \
  -H "Content-Type: application/json" \
  -d '{"playerId":"p1","bonusId":"b1"}'
```

## 4) Trigger bonus with the same session

Check the browser page (/game) log panel.