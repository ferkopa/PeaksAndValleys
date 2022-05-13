/*
 * References code from code contributed in class - week 10:
 * 
 * Hood, S. (2021-11-16> PROG32758 Enterprise Java Development - Week 10-1: 
 * 	User registration and Transport Guarantee [Source Code]. 
 * 	Sheridan College. 
 * 	https://slate.sheridancollege.ca/d2l/le/content/880292/viewContent
 * 	/11242183/View
 */

/* function to verify that the user's passwords match, and are not left 
 *blank/null
*/

function verifyUser(){
	var password1 = document.forms['form']['password'].value;
	var password2 = document.forms['form']['verifiedPassword'].value;
	
	if(password1 != password2 || password1 == null || password1 == "") {
		document.getElementById("error").innerHTML = 
		"Invalid credentials. Try again";
		return false;
	}
}