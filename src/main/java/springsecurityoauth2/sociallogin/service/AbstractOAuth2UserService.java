package springsecurityoauth2.sociallogin.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import springsecurityoauth2.sociallogin.model.*;
import springsecurityoauth2.sociallogin.repository.UserRepository;

@Service
@Slf4j
@Getter
@RequiredArgsConstructor
public abstract class AbstractOAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;

    protected ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        String registrationId = clientRegistration.getRegistrationId();
        switch (registrationId) {
            case "keycloak":
                return new KeycloakUser(oAuth2User, clientRegistration);
            case "google":
                return new GoogleUser(oAuth2User, clientRegistration);
            default:
                throw new IllegalArgumentException("No registration");
        }
    }

    protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {
        User user = userRepository.findByUsername(providerUser.getUsername());

        if (user == null) {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            userService.register(registrationId, providerUser);
        } else {
            log.info("user = {}", user);
        }
    }
}
