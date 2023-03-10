package springsecurityoauth2.sociallogin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.Collection;
import java.util.HashSet;

public class CustomAuthorityMapper implements GrantedAuthoritiesMapper {

    private final String prefix = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        HashSet<GrantedAuthority> mapped = new HashSet<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            mapped.add(mapAuthority(authority.getAuthority()));
        }
        return mapped;
    }

    private GrantedAuthority mapAuthority(String name) {
        if (name.lastIndexOf(".") > 0) {
            int idx = name.lastIndexOf(".");
            name = "SCOPE_" + name.substring(idx + 1);
        }

        if (!name.startsWith(prefix)) {
            name = prefix + name;
        }
        return new SimpleGrantedAuthority(name);
    }
}
