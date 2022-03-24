package sk.upjs.ics.springbootkeycloakdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

@RestController
public class ApiController {
    public final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/dates")
    public Instant now(@AuthenticationPrincipal Jwt jwt) {
        logger.info("{}", jwt);
        return Instant.now();
    }

    @GetMapping("/hosts")
    @PreAuthorize("hasAnyAuthority('visitor')")
    public String hosts() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }
}
