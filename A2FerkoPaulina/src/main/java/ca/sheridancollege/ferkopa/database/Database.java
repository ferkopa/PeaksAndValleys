package ca.sheridancollege.ferkopa.database;

/*
Name: Paulina Ferko
File:  Database.java
Other Files in this Project:
   Main class: A2PaulinaFerkoApplication.java
   Other files: Employee.java
    			EmployeePortalController.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.ferkopa.beans.Employee;
import ca.sheridancollege.ferkopa.beans.EmployeePortal;

@Repository
public class Database {
	
	//autowires jdbc template to be used in later methods
	@Autowired
	private NamedParameterJdbcTemplate temp;
	
	// declares passEncoder to salt passwords that the user enters
	private BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	
	/*adds employee to employee_portal database including their email and the 
	 * salted encrypted password
	 */
	public void addEmployee(String email, String encryptedPassword) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO employee_portal "
				+ "(email, encryptedPassword, enabled) "
				+ "VALUES (:email, :encryptedPassword, 1)";
		parameters.addValue("email", email);
		
		//encodes the password entered by user
		parameters.addValue("encryptedPassword", 
				passEncoder.encode(encryptedPassword));
		temp.update(query, parameters);
		
	}
	
	// method used to find user email upon logging into the portal
	public EmployeePortal findAccountByEmail(String email) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		//selects email from database if found
		String query = "SELECT * FROM employee_portal WHERE email = :email";
		parameters.addValue("email", email);
		
		/*adds the query results to employeeList to verify that it is in 
		 * the system
		 */
		ArrayList<EmployeePortal> employeeList = (ArrayList<EmployeePortal>)
				temp.query(query, parameters, 
						new BeanPropertyRowMapper<EmployeePortal>
				(EmployeePortal.class));
		
		// checks empployeeList for size and returns null if contains no values
		if(employeeList.size() > 0)
			return employeeList.get(0);
		else
			return null;

	}
	
	//method to be used in UserDetailsService implementation
	public List<String> getRolesById(Long userId){
		ArrayList<String> roleList = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		//selects userId and roleName wehre the userID is equal to roleId
		String query = "SELECT user_roles.userId, roles.roleName "
				+ "FROM user_roles, roles "
				+ "WHERE user_roles.roleId = roles.roleId "
				+ "AND userId = :userId";
		
		//adds userId to parameters
		parameters.addValue("userId", userId);
		
		//adds role to roleList
		List<Map<String, Object>> rows = temp.queryForList(query, parameters);
		for(Map<String, Object> r : rows) {
			roleList.add((String)r.get("roleName"));
		}
		return roleList;
		
	}
	
	//method to add role to user_roles database table
	public void addRole(Long userId, Long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		/*inserts userId and roleId into user_roles and adds to 
		 * MapSqlParameterSource
		 */
		String query = "INSERT INTO user_roles(userId, roleId) "
				+ "VALUES(:userId, :roleId)";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		//updates the table with the query results and the parameters values
		temp.update(query, parameters);
	}
	
	//method to add day and time choices to employee_portal table
	public void addEmployeeBooking(Employee employee) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO employee_info "
				+ "(firstName, selectedDay1, selectedTime1, selectedDay2, "
				+ "selectedTime2) VALUES (:firstName, :selectedDay1, :selectedTime1, "
				+ ":selectedDay2, :selectedTime2)";
		
		/*adds values to parameters using the get methods to determine 
		 * those values
		 */
		parameters.addValue("firstName", employee.getFirstName());
		parameters.addValue("selectedDay1", employee.getSelectedDay1());
		parameters.addValue("selectedTime1", employee.getSelectedTime1());
		parameters.addValue("selectedDay2", employee.getSelectedDay2());
		parameters.addValue("selectedTime2", employee.getSelectedTime2());
		
		//returns message to console to inform which user has added choices
		int addedValues = temp.update(query, parameters);
		if(addedValues > 0) {
			System.out.println(employee.getFirstName() + 
					" booked their dates.");
		}
		
	}
	
	/*retrieves add queries from the employee_info table to display in a 
	 * table in the bookings page
	 */
	public List<Employee> getBookingList(){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM employee_info";
		return temp.query(query, parameters, 
				new BeanPropertyRowMapper<Employee>(Employee.class));
	}
	
	
}
