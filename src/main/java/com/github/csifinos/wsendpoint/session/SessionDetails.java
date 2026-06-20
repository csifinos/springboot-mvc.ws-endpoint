package com.github.csifinos.wsendpoint.session;

import java.io.Serial;
import java.io.Serializable;

public class SessionDetails implements Serializable {

    private String location;

    private String accessType;

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccessType() {
        return this.accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "SessionDetails{" +
                "location='" + location + '\'' +
                ", accessType='" + accessType + '\'' +
                '}';
    }

    @Serial
    private static final long serialVersionUID = 8850489178248613501L;
}