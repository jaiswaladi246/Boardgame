package com.javaproject.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public void setAccessDeniedHandler(LoggingAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    /**
     * Creates a bean of type JdbcUserDetailsManager that will be used in
     * HomeController
     * 
     * @return an instance configured to use our datasource
     * @throws Exception
     */
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        // provides crud operations for users
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

        // Link up with our datasource
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("USER", "MANAGER") // sets up authorization
                .antMatchers("/secured/**").hasAnyRole("USER", "MANAGER")
                .antMatchers("/manager/**").hasRole("MANAGER")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/", "/**").permitAll() // allows access to index in templates
                .and() // allows us to chain
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/secured")
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .passwordEncoder(passwordEncoder)
                .withUser("bugs")
                .password(passwordEncoder.encode("bunny")).roles("USER")
                .and()
                .withUser("daffy")
                .password(passwordEncoder.encode("duck")).roles("USER", "MANAGER");
    }
}