INSERT INTO employee_portal (email, encryptedPassword, enabled) 
VALUES ('dschrute@peaksandvalleys.com', 
'$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO employee_portal (email, encryptedPassword, enabled) 
VALUES ('mscott@peaksandvalleys.com', 
'$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO employee_portal (email, encryptedPassword, enabled) 
VALUES ('krkapoor@peaksandvalleys.com', 
'$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO employee_portal (email, encryptedPassword, enabled) 
VALUES ('kmalone@peaksandvalleys.com', 
'$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);



INSERT INTO roles (roleId, roleName)
VALUES (1, 'ROLE_USER');

/*INSERT INTO roles (roleName)
VALUES ('ROLE_GUEST');*/

INSERT INTO user_roles (userId, roleId)
VALUES (1, 1);

INSERT INTO user_roles (userId, roleId)
VALUES (2, 1);

INSERT INTO user_roles (userId, roleId)
VALUES (3, 1);

INSERT INTO user_roles (userId, roleId)
VALUES (4, 1);

/*INSERT INTO user_roles (userId, roleId)
VALUES (2, 2);*/


INSERT INTO employee_info (userId, roleId, firstName, selectedDay1, 
selectedTime1, selectedDay2, selectedTime2) 
VALUES (1, 1, 'Jim Halpert', 'Monday', 'Afternoon', 'Friday', 'Morning');

INSERT INTO employee_info (userId, roleId, firstName, selectedDay1, 
selectedTime1, selectedDay2, selectedTime2) 
VALUES (1, 1, 'Michael Scott', 'Monday', 'Morning', 'Tuesday', 'Lunchtime');

INSERT INTO employee_info (userId, roleId, firstName, selectedDay1, 
selectedTime1, selectedDay2, selectedTime2) 
VALUES (1, 1, 'Kelly Kapoor', 'Wednesday', 'Morning', 'Tuesday', 'Afternoon');

INSERT INTO employee_info (userId, roleId, firstName, selectedDay1, 
selectedTime1, selectedDay2, selectedTime2) 
VALUES (1, 1, 'Ryan Howard', 'Wednesday', 'Lunchtime', 'Tuseday', 'Morning');