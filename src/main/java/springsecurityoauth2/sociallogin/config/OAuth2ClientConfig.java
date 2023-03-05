package springsecurityoauth2.sociallogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class OAuth2ClientConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(
                "static/js/**", "static/css/**"
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authRequest -> authRequest
                .antMatchers("/").permitAll()
                .anyRequest().authenticated());
        http
                .oauth2Login(Customizer.withDefaults());
        http.logout()
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true);
        return http.build();
    }
}
