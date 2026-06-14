package com.github.csifinos.wsendpoint.websocket.handshake;

import com.github.csifinos.wsendpoint.websocket.config.SessionPrincipal;
import org.apache.logging.log4j.util.Strings;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
public class WsHandshakeHandler extends DefaultHandshakeHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WsHandshakeHandler.class);

    @Override
    protected @Nullable Principal determineUser(ServerHttpRequest request,
                                                WebSocketHandler wsHandler,
                                                Map<String, Object> attributes) {
        String sessionId = (String) attributes.get(WsHandshakeProperties.WS_SESSION_ATTR);
        if (Strings.isBlank(sessionId)) {
            return null;
        }
        LOG.info("WebSocket connection accepted for sessionId {}", sessionId);
        return new SessionPrincipal(sessionId);
    }
}
