package com.security.loginregistration.registration;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Controller
@RequestMapping(path = "/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    @PostMapping
    public String registration(@RequestBody RegistrationRequest request, HttpServletRequest httpServletRequest) throws IOException, GeoIp2Exception {
        return registrationService.register(request, httpServletRequest);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        //registrationService.confirmToken(token);
        return registrationService.confirmToken(token);
    }

    @GetMapping(path = "/try")
    public String check() {
        return "test";
    }

    @PostMapping(path = "/sign-up")
    public String add(
                      @RequestParam("firstname") String firstname,
                      @RequestParam("lastname") String lastname,
                      @RequestParam("email") String email,
                      @RequestParam("password") String password,
                      HttpServletRequest httpServletRequest
                      ) {
        RegistrationRequest registrationRequest = new RegistrationRequest(firstname, lastname, email, password);


        return registrationService.register(registrationRequest, httpServletRequest);
    }
}
