package com.brand13.authtest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            
        http
            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().fullyAuthenticated()
            )
            .formLogin(Customizer.withDefaults());
        
            return http.build();
    }

    @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userDnPatterns("uid={0},ou=people")
        .groupSearchBase("ou=groups")
        .contextSource()
          .managerDn("cn=admin,dc=kyk,dc=brand13,dc=co,dc=kr")
          .managerPassword("VMware1!")
          .url("ldap://211.45.170.92:389/dc=kyk,dc=brand13,dc=co,dc=kr");
        //   .and()           //ldap 서버에서 암호화는 sha1로 설정!!
        // .passwordCompare()
        //   .passwordEncoder(new BCryptPasswordEncoder())
        //   .passwordAttribute("userPassword"); 
  }
}
