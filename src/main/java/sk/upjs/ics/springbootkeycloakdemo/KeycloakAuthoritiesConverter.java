package sk.upjs.ics.springbootkeycloakdemo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Extracts Keycloak roles to authorities.
 * <p>
 *     Source:
 *     <pre>
 *         "realm_access : { "roles":["visitor"] }
 *     </pre>
 * </p>
 */
public class KeycloakAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String REALM_ACCESS_CLAIM = "realm_access";

    public static final String ROLES_CLAIM = "roles";

    @SuppressWarnings("unchecked")
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        if (source.hasClaim(REALM_ACCESS_CLAIM)) {
            Object claim = source.getClaim(REALM_ACCESS_CLAIM);
            if (claim instanceof Map) {
                Map<String, Object> realmAccess = (Map<String, Object>) claim;
                if (realmAccess.containsKey(ROLES_CLAIM)) {
                    Collection<String> roleStrings = (Collection<String>) realmAccess.get(ROLES_CLAIM);
                    return roleStrings.stream()
                                      .map(SimpleGrantedAuthority::new)
                                      .collect(Collectors.toList());
                }
            }

        }
        return Collections.emptyList();
    }
}
