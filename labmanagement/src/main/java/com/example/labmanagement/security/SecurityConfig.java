package com.example.labmanagement.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/", "login", "/register/**", "/dashboard", "/logout").permitAll()
                                .requestMatchers("/projects/list", "/members/list", "/publications/list").permitAll() 
                                .requestMatchers("/resources/list", "/resources/add", "/resources/add/**", "/resources/book/**", "/resources/unbook/**",  "/resources/delete/**").hasRole("ADMIN")
                                .requestMatchers("/projects/add", "/projects/edit/**", "/projects/delete/**").hasRole("ADMIN")
                                .requestMatchers("/members/add", "/members/edit/**", "/members/delete/**", "/members/add/**").hasRole("ADMIN")
                                .requestMatchers( "/publications/edit/**", "/publications/delete/**").hasRole("ADMIN")
                                .requestMatchers("/publications/add", "/publications/add/**").hasAnyRole("ADMIN", "TEACHER")
                )
                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll());
            return http.build();
    }



}
