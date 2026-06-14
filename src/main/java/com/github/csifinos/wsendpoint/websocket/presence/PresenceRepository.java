package com.github.csifinos.wsendpoint.websocket.presence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresenceRepository extends CrudRepository<Presence, String> {
}

