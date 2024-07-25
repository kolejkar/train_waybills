package karol.train_waybill.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	  @Override
	    protected void configure(HttpSecurity http) throws Exception { 
		  http
	        .cors().and()
	        .csrf().disable()
	        .authorizeRequests()
	        .antMatchers("**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .formLogin();
	    }
	  
	  /**
	   * Allows access to static resources, bypassing Spring security.
	   */
	  @Override
	  public void configure(WebSecurity web) throws Exception {
	      web.ignoring().antMatchers(
	              // Vaadin Flow static resources // (1)
	              "/VAADIN/**",

	              // the standard favicon URI
	              "/favicon.ico",

	              // the robots exclusion standard
	              "/robots.txt",

	              // web application manifest // (2)
	              "/manifest.webmanifest",
	              "/sw.js",
	              "/offline-page.html",

	              // (development mode) static resources // (3)
	              "/frontend/**",

	              // (development mode) webjars // (3)
	              "/webjars/**",

	              // (production mode) static resources // (4)
	              "/frontend-es5/**", "/frontend-es6/**");
	  }
}
