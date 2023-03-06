package springsecurityoauth2.sociallogin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springsecurityoauth2.sociallogin.model.ProviderUser;
import springsecurityoauth2.sociallogin.model.User;
import springsecurityoauth2.sociallogin.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(String registrationId, ProviderUser providerUser) {
        User user = User.builder().registrationId(registrationId)
                .username(providerUser.getUsername())
                .provider(providerUser.getProvider())
                .email(providerUser.getEmail())
                .authorities(providerUser.getAuthorities())
                .build();

        userRepository.register(user);
    }
}
