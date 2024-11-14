package seg3x02.tempconverterapi.controller

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    // Define the user details in a separate function for clarity
    @Bean
    fun userDetailsService(): UserDetailsService {
        val user1 = createUser("user1", "pass1")
        val user2 = createUser("user2", "pass2")
        return InMemoryUserDetailsManager(user1, user2)
    }

    // Helper function to create a user
    private fun createUser(username: String, password: String) =
        User.withUsername(username)
            .password("{noop}$password") // Using {noop} for plaintext password
            .roles("USER")
            .build()

    // Configure the security filter chain
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests { auth -> auth.anyRequest().authenticated() }  // Secures all requests
            .httpBasic()  // Enables HTTP Basic Authentication
        return http.build()
    }
}
