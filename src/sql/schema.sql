CREATE DATABASE IF NOT EXISTS cs_hu_310_final_project; 
USE cs_hu_310_final_project; 
DROP TABLE IF EXISTS class_registrations; 
DROP TABLE IF EXISTS grades; 
DROP TABLE IF EXISTS class_sections; 
DROP TABLE IF EXISTS instructors; 
DROP TABLE IF EXISTS academic_titles; 
DROP TABLE IF EXISTS students; 
DROP TABLE IF EXISTS classes;
DROP FUNCTION IF EXISTS convert_to_grade_point; 
 
CREATE TABLE IF NOT EXISTS classes( 
    class_id INT AUTO_INCREMENT, 
    name VARCHAR(50) NOT NULL, 
    description VARCHAR(1000), 
    code VARCHAR(10) UNIQUE, 
    maximum_students INT DEFAULT 10, 
    PRIMARY KEY(class_id) 
); 
 
CREATE TABLE IF NOT EXISTS students( 
    student_id INT AUTO_INCREMENT, 
    first_name VARCHAR(30) NOT NULL, 
    last_name VARCHAR(50) NOT NULL, 
    birthdate DATE, 
    PRIMARY KEY (student_id) 
); 

CREATE TABLE IF NOT EXISTS academic_titles(
	academic_title_id INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
    PRIMARY KEY(academic_title)
    );
    
CREATE TABLE IF NOT EXISTS instructors(
	instructor_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    academic_title_id INT,
    PRIMARY KEY(instructor_id),
    FOREIGN KEY(academic_title_id) REFERENCES academic_titles(academic_title_id)
  );  
  
  CREATE TABLE IF NOT EXISTS terms(
	term_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    PRIMARY KEY(term_id)
    );
    
    CREATE TABLE IF NOT EXISTS class_sections(
    class_section_id INT NOT NULL AUTO_INCREMENT,
    class_id INT NOT NULL,
    instructor_id INT NOT NULL,
    term_id INT NOT NULL,
    PRIMARY KEY(class_section_id),
    FOREIGN KEY(class_id) REFERENCES classes(class_id),
    FOREIGN KEY(instructor_id) REFERENCES instructors(instructor_id),
    FOREIGN KEY(term_id) REFERENCES terms(term_id)
    );
    
    CREATE TABLE IF NOT EXISTS grades(
    grade_id INT NOT NULL AUTO_INCREMENT,
    letter_grade CHAR(2) NOT NULL,
    PRIMARY KEY(grade_id)
    );
    
    DELIMITER $$

CREATE FUNCTION convert_to_grade_point(letter_grade char(2))
	RETURNS int
	DETERMINISTIC
BEGIN
	DECLARE credit INT;
	IF letter_grade = "A" THEN
		SET credit = 4;
	ELSEIF letter_grade = "B" THEN
		SET credit = 3;
	ELSEIF letter_grade = "C" THEN
		SET credit = 2;
	ELSEIF letter_grade = "D" THEN
		SET credit = 1;
	ELSEIF letter_grade = "F" THEN 
		SET credit = 0;
	ELSEIF letter_grade = NULL THEN 
		SET credit = NULL;
	END IF;
	RETURN credit;
END $$
