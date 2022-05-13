package ca.sheridancollege.ferkopa.security;

/*
Name: Paulina Ferko
File:  SecurityConfiguration.java
Other Files in this Project:
   Main class: A2PaulinaFerkoApplication.java
   Other files: Employee.java
    			Database.java
    			EmployeePortalController.java
    			ImplUserDetailsService.java
    			LoggingAccessDeniedHandler.java
    			EmployeePortal.java
    			index.html
    			loginStyle.css
    			portalCss.css
    			script.js
    			noAccess.html
    			bookings.html
    			index.html (secure)
    			meetings.html
    			index.html (not secure)
    			login.html
    			register.html
*/

/*
 * References code from code contributed in class - week 10:
 * 
 * Hood, S. (2021-11-16> PROG32758 Enterprise Java Development - Week 10-1: 
 * 	User registration and Transport Guarantee [Source Code]. 
 * 	Sheridan College. 
 * 	https://slate.sheridancollege.ca/d2l/le/content/880292/viewContent
 * 	/11242183/View
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	//autowires already set UserDetailsService
	@Autowired
	UserDetailsService userDetailsService;
	
	//autowires already set LoggingAccessDeniedHandler
	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;
	
	//method to configure restrictions and permissions
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		/*access to the h2-console - commented out so that no one has access 
		 * to the console
		 */
		http
			.csrf()
			.disable();
	
		http
			.headers()
			.frameOptions()
			.disable();
		
		
		http
			.authorizeRequests()
			
			/*sets access to secure pages to users who have the role assigned 
			 * as USER
			 */
			.antMatchers("/secure/**").hasRole("USER")
			
			/*allows everyone access to the register pages both before and 
			 * after registration (GET and POST methods)
			 */
			.antMatchers(HttpMethod.POST, "/register")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/register")
			.permitAll()
			
			/*allows access to the pages meetings and bookings if the user has
			 *  role USER
			 */
			.antMatchers("/secure/meetings").hasRole("USER")
			.antMatchers("/secure/bookings").hasRole("USER")
			
			/*allows access to pages before and after registration if the user 
			 * has role assigned as USER
			 */
			.antMatchers(HttpMethod.POST, "/secure/bookings").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/secure/bookings").hasRole("USER")
			
			/*allows access to the root(index.html), noAccess, and folders js
			 * css, and images to everyone
			 */
			.antMatchers("/", "/js/**", "/css/**", "/images/**", "/noAccess")
			.permitAll()
			
			/*allows access to h2-console to users with assigned USER role
			 * Commented out to avoid access 
			 */
			.antMatchers("/h2-console/**")
			.hasRole("USER")
			
			//denies permission to anything else
			.antMatchers("/**")
			.denyAll()
			.anyRequest().authenticated()
			
			//allows access to the login form upon request
			.and().formLogin()
				.loginPage("/login")
				.permitAll()
				
			//allows accces to logout page upone request to logout
			.and().logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				
				//changes the url after logging out to this URL
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				
				//if error/permission denied, it redirects to the noAccess page
			.and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
				;
		
	}
	
	//authenticates the user using userDetailsService and password encoder
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
			throws Exception{
		
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
