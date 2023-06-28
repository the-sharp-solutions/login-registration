package com.security.loginregistration.appuser;

import com.security.loginregistration.registration.RegistrationService;
import com.security.loginregistration.registration.token.ConfirmationToken;
import com.security.loginregistration.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static String USER_NOT_FOUND_MSG
            = "user with email %s not found";
    private final ConfirmationTokenService confirmationTokenService;

    private static String token;
    private static ConfirmationToken confirmationToken;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }
    public String signUp(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getUsername()).isPresent();

        if (userExists) {
            if (!confirmationToken.getAppUser().getEnabled()) {

                AppUser oldUser = confirmationToken.getAppUser();

                oldUser.setFirstName(appUser.getFirstName());
                oldUser.setLastName(appUser.getLastName());
                oldUser.setEmail(appUser.getEmail());
                oldUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
                oldUser.setAppUserRole(AppUserRole.USER);

                appUserRepository.save(oldUser);

                // generate new token
                String newToken = UUID.randomUUID().toString();

                confirmationToken.setToken(newToken);
                confirmationToken.setCreatedAt(LocalDateTime.now());
                confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
                confirmationToken.setAppUser(oldUser);

                confirmationTokenService.saveConfirmationToken(confirmationToken);

                return newToken;
            } else {
                throw new IllegalStateException("email is already taken");
            }
        }

        // password encoding
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        // save user information's to the database
        appUserRepository.save(appUser);

        token = UUID.randomUUID().toString();

        confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        // save confirmation token details to the database
        confirmationTokenService.saveConfirmationToken(confirmationToken);



        // TODO: SEND MAIL
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
