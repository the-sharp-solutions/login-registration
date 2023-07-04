package com.security.loginregistration.clientIP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ip-check")
public class IPCheckController {
    @Autowired
    private RequestService requestService;

    @GetMapping
    public String getIP(HttpServletRequest request) {
        String clientIP = requestService.getClientIpAddress(request);
        return clientIP;
    }
}
