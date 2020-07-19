package org.backend.Configuration;

import org.dozer.DozerBeanMapper;
import org.passay.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/hike_route")
                .hasAuthority("SCOPE_read")
                .antMatchers(HttpMethod.POST, "registration")
                .hasAuthority("SCOPE_write")
                .antMatchers("/").permitAll()
                .and().formLogin();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper () {
        return new DozerBeanMapper();
    }

    @Bean
    public PasswordValidator passwordValidator () {
        return new PasswordValidator(new WhitespaceRule(), new UsernameRule(), new LengthRule(8,16));
    }

}
