package ca.sheridancollege.ferkopa.security;

/*
Name: Paulina Ferko
File:  LoggingAccessDeniedHandler.java
Other Files in this Project:
   Main class: A2PaulinaFerkoApplication.java
   Other files: Employee.java
    			Database.java
    			EmployeePortalController.java
    			ImplUserDetailsService.java
    			SecurityConfiguration.java
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


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler{
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) 
					throws IOException, ServletException {
		
		/*sets variable auth to be used later to verify if a user has access 
		 * to a specific page
		 */
		Authentication auth = SecurityContextHolder
							  .getContext()
							  .getAuthentication();
		
		/*checks of the auth if not equal to null - rejects the user if not null
		 * and redirects to the /noAccess page
		 */
		if(auth != null) {
			System.out.println(auth.getName() + " was trying to access a "
					+ "protected site called: " + 
					request.getRequestURI());
		}
		response.sendRedirect(request.getContextPath() + "/noAccess");
	}

}
