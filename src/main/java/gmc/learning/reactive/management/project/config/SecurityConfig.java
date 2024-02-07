package gmc.learning.reactive.management.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.stereotype.Component;

import gmc.learning.reactive.management.project.services.AuthService;
import gmc.learning.reactive.management.project.utils.JwtTokenAuthenticationFilter;
import gmc.learning.reactive.management.project.utils.JwtTokenProvider;

@Component
@Configuration
public class SecurityConfig {

	@Autowired
	private AuthConfig authConfig;

	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http,
	        JwtTokenProvider tokenProvider,
	        ReactiveAuthenticationManager reactiveAuthenticationManager) {
	    final String SWAGGER="/webjars/swagger-ui/**";
	    
	    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
	        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
	        .authenticationManager(reactiveAuthenticationManager)
	        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
	        .authorizeExchange(it->it
	            .pathMatchers(HttpMethod.GET, SWAGGER).permitAll()
	            .pathMatchers(HttpMethod.POST, "/auth").permitAll()
	            .pathMatchers(HttpMethod.POST, authConfig.getAuthUrl()).permitAll()
	            .pathMatchers("/**").authenticated()
	            .anyExchange().permitAll())
	        .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider),SecurityWebFiltersOrder.HTTP_BASIC)
	        .build();
	}
	
	@Bean
	ReactiveAuthenticationManager reactiveAuthenticationManager(AuthService authService,
	        BCryptPasswordEncoder bCryptPasswordEncoder) {
	        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(authService);
	        authenticationManager.setPasswordEncoder(bCryptPasswordEncoder);
	        return authenticationManager;
	}

}
