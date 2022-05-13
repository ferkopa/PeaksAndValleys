package ca.sheridancollege.ferkopa.beans;

/*
Name: Paulina Ferko
File:  EmployeePortal.java
Other Files in this Project:
   Main class: A2PaulinaFerkoApplication.java
   Other files: Employee.java
    			Database.java
    			ImplUserDetailsService.java
    			LoggingAccessDeniedHandler.java
    			SecurityConfiguration.java
    			EmployeePortalController.java
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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EmployeePortal {
	
	/*stores userId in database - not @NonNull because automatically 
	 * incremented when entered into the system
	 */
	
	private Long userId;
	
	/*@NonNull stored variable to ensure that no value is null when entering 
	 * database
	 */
	@NonNull
	private String email;
	@NonNull
	private String encryptedPassword;
	@NonNull
	private Boolean enabled;
}
