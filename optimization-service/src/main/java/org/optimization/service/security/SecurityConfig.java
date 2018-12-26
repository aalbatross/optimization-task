package org.optimization.service.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String ADMIN = "ADMIN";

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("admin").password("{noop}secret").roles(ADMIN);
  }

  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/knapsack/tasks")
        .permitAll()
        .antMatchers("/knapsack/admin/tasks")
        .hasRole(ADMIN)
        .antMatchers("/knapsack/admin/shutdown")
        .hasRole(ADMIN)
        .antMatchers("/knapsack/admin/tasks/**")
        .hasRole(ADMIN)
        .anyRequest()
        .permitAll()
        .and()
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable();
  }
}
