package com.pedroabtome.secure_quiz_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pedroabtome.secure_quiz_app.service.QuizUserDetailsService;

@Configuration
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(QuizUserDetailsService quizUserDetailsService) {
        return quizUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider authenticationProvider) throws Exception {
        http
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/registration", "/register").permitAll()
                .requestMatchers("/quizList", "/addQuiz", "/editQuiz/**", "/deleteQuiz/**").hasRole("ADMIN")
                .requestMatchers("/quiz", "/submitAnswers", "/result").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/quiz", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}