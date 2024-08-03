package com.example.oauth2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SocialApplicationConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/login", "/error", "/webjars/**").permitAll() // Разрешаем доступ к этим маршрутам всем
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Только для администраторов
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Обработка ошибок аутентификации
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/") // Указываем страницу для входа
                        .defaultSuccessUrl("/user")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для выхода
                        .logoutSuccessHandler(oidcLogoutSuccessHandler()) // Обработчик для логаута
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler successHandler = new SimpleUrlLogoutSuccessHandler();
        successHandler.setDefaultTargetUrl("/");
        return successHandler;
    }

}