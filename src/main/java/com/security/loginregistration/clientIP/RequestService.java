package com.security.loginregistration.clientIP;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface RequestService {
    String getClientIpAddress(HttpServletRequest request);
}
