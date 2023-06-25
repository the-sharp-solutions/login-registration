package com.security.loginregistration.registration;

import com.security.loginregistration.appuser.AppUser;
import com.security.loginregistration.appuser.AppUserRole;
import com.security.loginregistration.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email is not valid");
        }
        return appUserService.signUp(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
