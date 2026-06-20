package com.github.csifinos.wsendpoint.websocket.handshake;

import com.github.csifinos.wsendpoint.websocket.session.WsSessionService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WsHandshakeInterceptor implements HandshakeInterceptor {


    private static final Logger LOG = LoggerFactory.getLogger(WsHandshakeInterceptor.class);
    private final HttpSession httpSession;
    private final WsSessionService wsSessionService;

    public WsHandshakeInterceptor(HttpSession httpSession, WsSessionService wsSessionService) {
        this.httpSession = httpSession;
        this.wsSessionService = wsSessionService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        if (!(request instanceof ServletServerHttpRequest servletRequest)) {
            return false;
        }

        String sessionId = servletRequest.getServletRequest().getParameter(WsHandshakeProperties.SESSION_PARAM);
//        httpSession
//        if (!wsSessionService.isSessionValid(sessionId)) {
//            LOG.warn("Rejected websocket handshake: invalid sessionId {}", sessionId);
//            return false;
//        }

        attributes.put(WsHandshakeProperties.WS_SESSION_ATTR, sessionId);
        LOG.info("WebSocket handshake accepted for sessionId {}", sessionId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // no-op
    }
}
