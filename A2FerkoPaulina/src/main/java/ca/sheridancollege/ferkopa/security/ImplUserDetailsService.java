package ca.sheridancollege.ferkopa.security;

/*
Name: Paulina Ferko
File:  ImplUserDetailsService.java
Other Files in this Project:
   Main class: A2PaulinaFerkoApplication.java
   Other files: Employee.java
    			Database.java
    			EmployeePortalController.java
    			LoggingAccessDeniedHandler.java
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
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.ferkopa.database.Database;

@Service
public class ImplUserDetailsService implements UserDetailsService {
 
	@Autowired
	@Lazy
	private Database dbas;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		/*uses the EmployeePortal class and finds the username using Database
		 *  method to find the user account by email
		 */
		ca.sheridancollege.ferkopa.beans.EmployeePortal employee = 
				dbas.findAccountByEmail(username);
		
		
		//throws exception if no account is found
		if(employee == null) {
			throw new UsernameNotFoundException("Username not found. "
					+ "Try again");
		}
		
		//gets the roles of the users that registers/are logged into the system 
		List<String> roleList = dbas.getRolesById(employee.getUserId());
		
		
		//ArrayList to be used with in another statement
		List<GrantedAuthority> grantedAccess = new 
				ArrayList<GrantedAuthority>();
		
		/*adds simple granted authority to a role if there are values 
		 * in the roleList
		 */
		if(roleList != null) {
			for(String role : roleList) {
				grantedAccess.add(new SimpleGrantedAuthority(role));
			}
			
		}
		
		//sets UserDetails to be used in SecurityConfiguration class
		UserDetails userDetails = (UserDetails) new 
				org.springframework.security.core.userdetails.User(
						employee.getEmail(),
						employee.getEncryptedPassword(),
						grantedAccess);
		
		return userDetails;
	}

}
