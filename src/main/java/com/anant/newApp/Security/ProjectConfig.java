package com.anant.newApp.Security;


//import com.anant.newApp.Service.AuthenticationProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AuthenticationProviderService authenticationProvider;

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();
        var user1 = User.withUsername("john")
                .password("12345")
                .roles("ADMIN")
                .build();
        var user2 = User.withUsername("jane")
                .password("12345")
                .roles("MANAGER")
                .build();
        manager.createUser(user1);
        manager.createUser(user2);
        return manager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SCryptPasswordEncoder sCryptPasswordEncoder() {
//        return new SCryptPasswordEncoder();
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        //auth.authenticationProvider(authenticationProvider);
////        auth.authenticationProvider();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/", true);
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.cors().disable();
//        http.httpBasic();
//        http.authorizeRequests()
//                .mvcMatchers("/").hasRole("ADMIN")
//                .mvcMatchers("/topHeadLines").hasRole("MANAGER");
    }
}
