package com.security.loginregistration.clientIP.clientLocation;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;

public interface LocationDataFromIP {
    String locationData(String ip) throws IOException, GeoIp2Exception;
}
