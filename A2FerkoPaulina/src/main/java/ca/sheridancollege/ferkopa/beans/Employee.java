package ca.sheridancollege.ferkopa.beans;

/*
Name: Paulina Ferko
File:  Employee.java
Other Files in this Project:
   Main class: A2PaulinaFerkoApplication.java
   Other files: EmployeePortal.java
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


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Employee {
	
	//assigns employeeID to all users that fill out form
	private Long employeeId;
	//Test comment
	//non null firstname ensure that the firstname field needs to be filled out
	@NonNull
	private String firstName;
	
	//variables to store the user's choices
	private String selectedDay1;
	private String selectedTime1;
	private String selectedDay2;
	private String selectedTime2;
	
	
	//final string to cycle through the day options in meetings.html page
	private final String[] DAYS = {
			"Monday", 
			"Tuesday", 
			"Wednesday", 
			"Thursday", 
			"Friday"
	};
	
	//final string to cycle through the time options in meetings.html page
	private final String[] TIMES = {
			"Morning",
			"Lunchtime",
			"Afternoon"
	};
	
}
