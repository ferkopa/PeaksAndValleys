CREATE TABLE employee_portal(
	userId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	email VARCHAR(50) NOT NULL UNIQUE,
	encryptedPassword VARCHAR(128) NOT NULL,
	enabled BIT NOT NULL
);

CREATE TABLE roles(
	roleId BIGINT NOT NULL PRIMARY KEY,
	roleName VARCHAR(20) NOT NULL 
);

CREATE TABLE user_roles(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	roleId BIGINT NOT NULL,
	userId BIGINT NOT NULL
);

CREATE TABLE employee_info(
	employeeId  BIGINT PRIMARY KEY AUTO_INCREMENT,
	userId BIGINT,
	roleId BIGINT,
	firstName VARCHAR(20) NOT NULL,
	selectedDay1 VARCHAR(20),
	selectedTime1 VARCHAR(20),
	selectedDay2 VARCHAR(20),
	selectedTime2 VARCHAR(20)
);

ALTER TABLE user_roles
	ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);


ALTER TABLE user_roles
	ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
	REFERENCES employee_portal (userId);

ALTER TABLE user_roles
	ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
	REFERENCES roles (roleId);

ALTER TABLE employee_info
	ADD CONSTRAINT employee_role FOREIGN KEY (roleId)
	REFERENCES roles (roleId);
	
ALTER TABLE employee_info
	ADD CONSTRAINT employee_user FOREIGN KEY (userId)
	REFERENCES employee_portal (userId);
	
