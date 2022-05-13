package ca.sheridancollege.ferkopa.controllers;

/*
Name: Paulina Ferko
File:  EmployeePortalController.java
Other Files in this Project:
   Main class: A2PaulinaFerkoApplication.java
   Other files: Employee.java
    			Database.java
    			ImplUserDetailsService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.ferkopa.beans.Employee;
import ca.sheridancollege.ferkopa.database.Database;

@Controller
public class EmployeePortalController {
	
	
	/* Autowired database class into controller. @Lazy annotation tells 
	 * program to load the as lazy and only call on component when told to do so
	 */
	@Autowired 
	@Lazy
	private Database dbas;
	
	//mapping method for unsecure index
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	//mapping method for secure index
	@GetMapping("/secure")
	public String portal(Model model, Authentication authentication) {
		
		/*calls on email to be used to display username to user upon 
		 * entering the secure index
		 */
		String email = authentication.getName();
		
		model.addAttribute("email", email);
		return "/secure/index";
	}
	
	//mapping method for login page
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	//mapping method for no access page - restricted page for non users
	@GetMapping("/noAccess")
	public String noAccess() {
		return "/accessDenied/noAccess";
	}
	
	//mapping method for meetings page within secure index page
	@GetMapping("/secure/meetings")
	public String meetings(Model model) {
		
		
		model.addAttribute("bookingList", dbas.getBookingList());
		
		//adds new Employee object to database 
		model.addAttribute("employee", new Employee());
			
		return "/secure/meetings";
	}
	//mapping method for bookings page within secure index page
	@GetMapping("/secure/bookings")
	public String bookings(Model model) {
		
		//displays employee results in the booking page
		model.addAttribute("bookingList", dbas.getBookingList());
		
		return "/secure/bookings";
	}
	
	//maps the register page
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	/*post mapping of register page - method accepts email and password to 
	 * add to database upon clicking submit
	 */
	@PostMapping("/register")
	public String postRegister(@RequestParam String email, 
			@RequestParam String password) {
		
		//adds entered values of email and password to the database
		dbas.addEmployee(email, password);
		
		//adds retrieved userId to the the roles database
		Long userId = dbas.findAccountByEmail(email).getUserId();
		dbas.addRole(userId, Long.valueOf(1));
		
		//redirects from registration page to unsecure index page
		return "redirect:/";
	}
	
	/*post mapping for meetings page to add to database and be retrieved in 
	 * bookings page
	 */
	@PostMapping("/secure/bookings")
	public String booking(Model model, @ModelAttribute Employee employee) {
		
		//adds employee choices to the employee_info database
		dbas.addEmployeeBooking(employee);
		
		// method to display employee results in the booking page
		model.addAttribute("bookingList", dbas.getBookingList());
		
		//ready to add the next employee on the meetings page
		model.addAttribute("employee", new Employee());
		
		return "/secure/bookings";
	}
	
	
	
}
