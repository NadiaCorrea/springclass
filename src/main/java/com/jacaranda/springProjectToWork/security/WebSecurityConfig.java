package com.jacaranda.springProjectToWork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jacaranda.springProjecToWork.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserService myUserDetailService;
//	// Indicamos que la configuración se hará a travéx del servicio.

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService);
	}

	// Método que usaremos más abajo
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}

	// Método que nos suministrará la codificación
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Método que autentifica
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

    // Aquí es donde podemos especificar qué es lo que hace y lo que no
    // según el rol del usuario
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/home").permitAll()
                    .requestMatchers("/listStudent").hasAnyAuthority("USER", "ADMIN")
                    .requestMatchers("/elements/**").hasAnyAuthority("USER", "ADMIN")
                    .requestMatchers("/addStudent").hasAuthority("ADMIN")
                    .requestMatchers("/editStudent/**").hasAuthority("ADMIN")
                    .requestMatchers("/delStudent/**").hasAuthority("ADMIN")
                    .requestMatchers("/register").permitAll()
                    .requestMatchers("/process_register").permitAll()
                    .anyRequest().permitAll();
        }).formLogin((form) -> form.permitAll()).logout((logout) -> logout.permitAll());
        return http.build();
    }

}

