package com.snupa.config.security

import com.snupa.config.security.jwt.JwtConfig
import com.snupa.config.security.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
        @Autowired
        private val jwtTokenProvider: JwtTokenProvider
) : WebSecurityConfigurerAdapter()
{
    override fun configure(http: HttpSecurity) {
        http
                .cors().disable()
                .csrf().disable()
                .antMatcher("/api/**").authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/v1/oauth/login").anonymous()
                .anyRequest().authenticated()
                .and()
                .apply(JwtConfig(jwtTokenProvider))
    }
}