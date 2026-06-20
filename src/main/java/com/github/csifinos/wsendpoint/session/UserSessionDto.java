package com.github.csifinos.wsendpoint.session;

import org.springframework.format.datetime.standard.InstantFormatter;

import java.time.Instant;
import java.util.Locale;

public class UserSessionDto {

    private static final InstantFormatter INSTANT_FORMATTER = new InstantFormatter();

    private String sessionId;
    private String userId;
    private String location;
    private String accessType;
    private String creationTime;
    private String lastAccessedTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime, Locale locale) {
        this.creationTime = INSTANT_FORMATTER.print(creationTime, locale);
    }

    public String getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(Instant lastAccessedTime, Locale locale) {
        this.lastAccessedTime = INSTANT_FORMATTER.print(lastAccessedTime, locale);;
    }
}
