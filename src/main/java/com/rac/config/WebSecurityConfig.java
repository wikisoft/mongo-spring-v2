package com.rac.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

	// http.httpBasic().and().authorizeRequests()
	// .antMatchers("/index.html", "/views/home.html", "/views/post.html",
	// "/views/login.html", "/css/*",
	// "/user", "/js/*", "/lib/*", "/fonts/*", "/*")
	// .permitAll().anyRequest().authenticated().and().csrf().csrfTokenRepository(csrfTokenRepository()).and()
	// .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);

	// .and().formLogin().loginPage("/login").permitAll()
	// antMatchers("/**").permitAll().

	http.httpBasic();
	http.authorizeRequests().antMatchers("/static/**").permitAll()
		.antMatchers("/front/**", "/rest/**").authenticated().and()
		.formLogin().loginPage("/#/front/login").permitAll().and()
		.csrf().csrfTokenRepository(csrfTokenRepository()).and()
		.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);

	// http.authorizeRequests().antMatchers("/**").authenticated().and().csrf()
	// .csrfTokenRepository(csrfTokenRepository()).and().addFilterAfter(csrfHeaderFilter(),
	// CsrfFilter.class);
    }

    private Filter csrfHeaderFilter() {
	return new OncePerRequestFilter() {
	    @Override
	    protected void doFilterInternal(HttpServletRequest request,
		    HttpServletResponse response, FilterChain filterChain)
		    throws ServletException, IOException {
		CsrfToken csrf = (CsrfToken) request
			.getAttribute(CsrfToken.class.getName());
		if (csrf != null) {
		    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
		    String token = csrf.getToken();
		    if (cookie == null || token != null
			    && !token.equals(cookie.getValue())) {
			cookie = new Cookie("XSRF-TOKEN", token);
			cookie.setPath("/");
			response.addCookie(cookie);
		    }
		}
		filterChain.doFilter(request, response);
	    }
	};
    }

    private CsrfTokenRepository csrfTokenRepository() {
	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	repository.setHeaderName("X-XSRF-TOKEN");
	return repository;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
	    throws Exception {
	auth.inMemoryAuthentication().withUser("user").password("user")
		.roles("USER").and().withUser("admin").password("admin")
		.roles("ADMIN");
    }
}

// @Configuration
// class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter
// {
//
// @Autowired
// private UserRepository accountRepository;
//
// @Override
// public void init(AuthenticationManagerBuilder auth) throws Exception {
// auth.userDetailsService(userDetailsService());
// }
//
// @Bean
// UserDetailsService userDetailsService() {
// return new UserDetailsService() {
//
// @Override
// public UserDetails loadUserByUsername(String username)
// throws UsernameNotFoundException {
// User account = accountRepository.findByUsername(username);
// if (account != null) {
// return new org.springframework.security.core.userdetails.User(
// account.getUsername(), account.getPassword(), true,
// true, true, true,
// AuthorityUtils.createAuthorityList("USER"));
// } else {
// throw new UsernameNotFoundException(
// "could not find the user '" + username + "'");
// }
// }
//
// };
// }
// }
