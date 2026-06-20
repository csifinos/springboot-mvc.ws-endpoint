package com.github.csifinos.wsendpoint.session;

import com.github.csifinos.wsendpoint.geolocation.GeoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SessionDetailsFilter extends OncePerRequestFilter {

    private static final String USER_AGENT = "User-Agent";
    private static final String FORWARD_FOR = "X-FORWARDED-FOR";
    private static final String LOCATION_FORMAT = "%s - %s";
    private static final String UNKNOWN = "Unknown";

    private final GeoService geoService;

    public SessionDetailsFilter(GeoService geoService) {
        this.geoService = geoService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);

        Optional.ofNullable(request.getSession(false))
                .ifPresent(session -> {
                    String remoteAddr = getRemoteAddress(request);
                    String geoLocation = getGeoLocation(remoteAddr);

                    SessionDetails details = new SessionDetails();
                    details.setAccessType(request.getHeader(USER_AGENT));
                    details.setLocation(String.format(LOCATION_FORMAT, remoteAddr, geoLocation));

                    session.setAttribute(SessionConstants.SESSION_DETAILS, details);
                });
    }

    String getGeoLocation(String remoteAddr) {
        try {
            return geoService.getCityByIp(remoteAddr)
                    .orElse(UNKNOWN);
        } catch (Exception ex) {
            return UNKNOWN;
        }
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String remoteAddr = request.getHeader(FORWARD_FOR);
        if (remoteAddr == null) {
            remoteAddr = request.getRemoteAddr();
        } else if (remoteAddr.contains(",")) {
            remoteAddr = remoteAddr.split(",")[0];
        }
        return remoteAddr;
    }

}
