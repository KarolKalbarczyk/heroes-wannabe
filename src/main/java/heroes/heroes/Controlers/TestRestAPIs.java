package heroes.heroes.Controlers;

import heroes.heroes.Security.UserPrinciple;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestAPIs {


    @GetMapping("/api/test/user")
    @PreAuthorize("hasAuthority('USER')")
    public String userAccess(SecurityContextHolder holder) {
        return "forward:/aaaa";
    }

    @GetMapping("/api/test/pm")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String projectManagementAccess() {
        return ">>> Project Management Board";
    }

    @GetMapping("/api/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return ">>> Admin Contents";
    }
}