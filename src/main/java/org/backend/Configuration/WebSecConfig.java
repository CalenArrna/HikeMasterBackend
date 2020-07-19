package org.backend.Configuration;

import org.backend.DTOs.HikeMasterUserErrorDTO;
import org.backend.DTOs.ResponseDTO;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.passay.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        http.csrf().disable()
//                .formLogin()
//                .loginPage("/login_page")
//                .defaultSuccessUrl("/messages")
//                .permitAll()
//                .and()
                .authorizeRequests()
                .antMatchers("/csrf","/hike_route","/registration").permitAll()
                .anyRequest()
                .authenticated();
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutSuccessUrl("/login_page")
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//                .and()
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(ResponseDTO.class, HikeMasterUserErrorDTO.class, TypeMappingOptions.mapNull(false));
            }
        });
        return dozer;
    }

    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator(new WhitespaceRule(), new UsernameRule(), new LengthRule(8, 16));
    }


}
