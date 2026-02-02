package com.pedroabtome.secure_quiz_app.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pedroabtome.secure_quiz_app.model.User;

@Service
public class QuizUserDetailsService implements UserDetailsService {

    // Stores users in memory, keyed by username
    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();

    // Used to hash passwords (e.g., BCrypt). We'll configure the bean later in SecurityConfig.
    private final PasswordEncoder passwordEncoder;

    public QuizUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Spring Security calls this during authentication.
     * It MUST take only "username" (password is checked elsewhere by Spring Security).
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersByUsername.get(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Converts a "role" string into a Spring Security authority
        // Example: "ADMIN" -> "ROLE_ADMIN"
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        // Returns a Spring Security UserDetails object (username + hashed password + roles)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    /**
     * Registers a new user and stores it in memory.
     * Password is saved hashed (encoded), not in plain text.
     */
    public void registerUser(String username, String password, String email, String role) {
        if (usersByUsername.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, encodedPassword, role);
        usersByUsername.put(username, user);
    }
}
