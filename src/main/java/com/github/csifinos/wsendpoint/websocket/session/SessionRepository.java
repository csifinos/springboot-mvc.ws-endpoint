package com.github.csifinos.wsendpoint.websocket.session;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("WsSessionRepository")
public interface SessionRepository extends CrudRepository<Session, String> {
}

