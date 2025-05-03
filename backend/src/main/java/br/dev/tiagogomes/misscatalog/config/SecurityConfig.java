package br.dev.tiagogomes.misscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
/*	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
		http.csrf (csrf -> csrf.disable ());
		http.authorizeHttpRequests (auth -> auth
				.anyRequest ().permitAll ());
		return http.build ();
	}*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable()) // Desativar CSRF para APIs REST
				.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilitar CORS
				.authorizeHttpRequests(auth -> auth
						// Liberar endpoints do Springdoc OpenAPI
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
						// Liberar console H2
						.requestMatchers("/h2-console/**").permitAll()
						// Liberar endpoints públicos
						.requestMatchers("/api/public/**").permitAll()
						// Proteger endpoints de usuários
						.requestMatchers("/api/users/**").hasRole("ADMIN")
						// Exigir autenticação para outros endpoints
						//.anyRequest().authenticated()
						.anyRequest().permitAll () // Acessar os endpoints sem autenticação
				)
				// Desativar frameOptions para H2 Console e Swagger UI
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
				// Configurar autenticação Basic
				.httpBasic(withDefaults());
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		// Configurar usuário ADMIN em memória para testes
		UserDetails admin = User.withUsername("admin")
				.password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmZjyKrgOxQ5PYJhj1z8fH2o4Qz2Z6Qz2Z6Qz2Z6")
				.roles("ADMIN")
				.build();
		
		UserDetails user = User.withUsername("user")
				.password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmZjyKrgOxQ5PYJhj1z8fH2o4Qz2Z6Qz2Z6Qz2Z6")
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager (admin, user);
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration ();
		configuration.addAllowedOrigin("*"); // Permitir todas as origens (ajuste para produção)
		configuration.addAllowedMethod("*"); // Permitir todos os métodos (GET, POST, PUT, etc.)
		configuration.addAllowedHeader("*"); // Permitir todos os cabeçalhos
		configuration.setAllowCredentials(true); // Permitir credenciais (para Basic Auth)
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource ();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
