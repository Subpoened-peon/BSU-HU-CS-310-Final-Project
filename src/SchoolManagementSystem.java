import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This application will keep track of things like what classes are offered by
 * the school, and which students are registered for those classes and provide
 * basic reporting. This application interacts with a database to store and
 * retrieve data.
 */
public class SchoolManagementSystem {

    public static void getAllClassesByInstructor(String first_name, String last_name) {
    	try {	
 	    	Connection connection = Database.getDatabaseConnection();
 	        Statement sqlStatement = connection.createStatement();

 	        try {
 	             String sql = "SELECT first_name, last_name, title, classes.code, classes.name"
 	             		+ ", terms.name FROM instructors LEFT JOIN academic_titles ON"
 	             		+ " instructors.academic_title_id = academic_titles.academic_title_id LEFT JOIN"
 	             		+ " class_sections ON class_sections.instructor_id = instructors.instructor_id LEFT JOIN"
 	             		+ " terms ON terms.term_id = class_sections.term_id LEFT JOIN classes ON"
 	             		+ " classes.class_id = class_sections.class_id WHERE first_name = " + "'" + first_name + "'" +
 	             		" AND last_name = '" + last_name + "' GROUP BY"
 	             		+ " instructors.first_name, title, terms.name, classes.code, classes.name";
 	             
 	             ResultSet rs = sqlStatement.executeQuery(sql);
 	             
 	             System.out.println("First Name | Last Name | Title | Code | Name | Term");
 	             System.out.println("--------------------------------------------------------------------------------");
 	             
 	             while(rs.next()) {
 	             
 	             String firstname = rs.getString("first_name");
 	             String lastname = rs.getString("last_name");
 	             String academic_title = rs.getString("title");
 	             String class_code = rs.getString("classes.code");
 	             String class_name = rs.getString("classes.name");
 	             String term_name = rs.getString("terms.name");
 	             
 	             System.out.println(firstname + " | " + lastname + " | " + academic_title + 
 	            		 " | " + class_code + " | " + class_name + " | " + term_name);
 	             }
 	        } catch (SQLException sqlException) {
 	            System.out.println("Failed to get students");
 	            System.out.println(sqlException.getMessage());

 	        } finally {
 	            try {
 	                if (sqlStatement != null)
 	                    sqlStatement.close();
 	            } catch (SQLException se2) {
 	            }
 	            try {
 	                if (connection != null)
 	                    connection.close();
 	            } catch (SQLException se) {
 	                se.printStackTrace();
 	            }
 	        }
 	    } catch (SQLException e) {
 	    }
 	    }
    
    
    public static void submitGrade(String studentId, String classSectionID, String grade) {
    	try {	
 	    	Connection connection = Database.getDatabaseConnection();
 	        Statement sqlStatement = connection.createStatement();

 	        try {
 	             String sql = "UPDATE class_registrations SET grade_id = convert_to_grade_point('" + grade + "') WHERE"
 	             		+ " student_id = '" + studentId + "' AND class_section_id = '" + classSectionID + "';"; 
 	            		 
 	             
 	             sqlStatement.executeUpdate(sql);
 	             
 	             System.out.println("--------------------------------------------------------------------------------");
 	             
 	             System.out.println("Grade has been submitted!");
 	             
 	        } catch (SQLException sqlException) {
 	            System.out.println("Failed to get students");
 	            System.out.println(sqlException.getMessage());

 	        } finally {
 	            try {
 	                if (sqlStatement != null)
 	                    sqlStatement.close();
 	            } catch (SQLException se2) {
 	            }
 	            try {
 	                if (connection != null)
 	                    connection.close();
 	            } catch (SQLException se) {
 	                se.printStackTrace();
 	            }
 	        }
 	    } catch (SQLException e) {
 	    }
 	    }


    public static void registerStudent(String studentId, String classSectionID) {
    	try {	
 	    	Connection connection = Database.getDatabaseConnection();
 	        Statement sqlStatement = connection.createStatement();

 	        try {
 	             String sql = "INSERT INTO class_registrations (student_id, class_section_id) VALUES ('" + 
 	            		 studentId + "' , '" + classSectionID + "');";
 	             
 	             sqlStatement.executeUpdate(sql);
 	             
 	             System.out.println("Registration ID | Student ID | Class Section ID");
 	             System.out.println("--------------------------------------------------------------------------------");
 	             
 	          ResultSet rs = sqlStatement.executeQuery("SELECT class_registration_id, student_id, class_section_id FROM"
 	             		+ " class_registrations WHERE student_id = '" + studentId + "' AND class_section_id = '" + 
 	            		 classSectionID + "'");
 	             while(rs.next()) {
 	            	 String registration_id = rs.getString("class_registration_id");
 	            	 String student_id = rs.getString("student_id");
 	            	 String class_section_id = rs.getString("class_section_id");
 	            	 
 	            	 System.out.println(registration_id + " | " + student_id + " | " + class_section_id);
 	             }
 	             
 	        } catch (SQLException sqlException) {
 	            System.out.println("Failed to get students");
 	            System.out.println(sqlException.getMessage());

 	        } finally {
 	            try {
 	                if (sqlStatement != null)
 	                    sqlStatement.close();
 	            } catch (SQLException se2) {
 	            }
 	            try {
 	                if (connection != null)
 	                    connection.close();
 	            } catch (SQLException se) {
 	                se.printStackTrace();
 	            }
 	        }
 	    } catch (SQLException e) {
 	    }
 	    }

    public static void deleteStudent(String studentId) {
    	
    	try {	
 	    	Connection connection = Database.getDatabaseConnection();
 	        Statement sqlStatement = connection.createStatement();

 	        try {
 	             String sql = "DELETE FROM students WHERE student_id = '" + studentId + "'";
 	             
 	             sqlStatement.executeUpdate(sql);
 	             
 	             System.out.println("--------------------------------------------------------------------------------");
 	             
 	             System.out.println("Student with id: " + studentId + " was deleted");
 	             
 	             
 	        } catch (SQLException sqlException) {
 	            System.out.println("Failed to get students");
 	            System.out.println(sqlException.getMessage());

 	        } finally {
 	            try {
 	                if (sqlStatement != null)
 	                    sqlStatement.close();
 	            } catch (SQLException se2) {
 	            }
 	            try {
 	                if (connection != null)
 	                    connection.close();
 	            } catch (SQLException se) {
 	                se.printStackTrace();
 	            }
 	        }
 	    } catch (SQLException e) {
 	    }
 	    }


    public static void createNewStudent(String firstName, String lastName, String birthdate) {
    	 try {	
 	    	Connection connection = Database.getDatabaseConnection();
 	        Statement sqlStatement = connection.createStatement();

 	        try {
 	             String sql = "INSERT INTO students (first_name, last_name, birthdate) VALUES ('" + 
 	            		 firstName + "' , '" + lastName + "' , '" + birthdate + "');";
 	             
 	             sqlStatement.executeUpdate(sql);
 	             
 	             System.out.println("First Name | Last Name | Birthdate");
 	             System.out.println("--------------------------------------------------------------------------------");
 	            System.out.println(firstName + " | " + lastName + " | " + birthdate);
 	             
 	             
 	        } catch (SQLException sqlException) {
 	            System.out.println("Failed to get students");
 	            System.out.println(sqlException.getMessage());

 	        } finally {
 	            try {
 	                if (sqlStatement != null)
 	                    sqlStatement.close();
 	            } catch (SQLException se2) {
 	            }
 	            try {
 	                if (connection != null)
 	                    connection.close();
 	            } catch (SQLException se) {
 	                se.printStackTrace();
 	            }
 	        }
 	    } catch (SQLException e) {
 	    }
 	    }

    public static void listAllClassRegistrations() {
    	try {	
 	    	Connection connection = Database.getDatabaseConnection();
 	        Statement sqlStatement = connection.createStatement();

 	        try {
 	             String sql = "SELECT students.student_id, class_sections.class_section_id,"
 	             		+ " first_name, last_name, classes.code, classes.name"
 	             		+ ", terms.name, letter_grade FROM class_sections LEFT JOIN classes ON"
 	             		+ " classes.class_id = class_sections.class_id LEFT JOIN"
 	             		+ " terms ON terms.term_id = class_sections.term_id LEFT JOIN"
 	             		+ " class_registrations ON class_registrations.class_section_id = class_sections"
 	             		+ ".class_section_id LEFT JOIN grades ON grades.grade_id = class_registrations.grade_id"
 	             		+ " LEFT JOIN students ON students.student_id = class_registrations.student_id WHERE"
 	             		+ " students.student_id IS NOT NULL GROUP BY"
 	             		+ " students.student_id, terms.name, class_sections.class_section_id, letter_grade";
 	             
 	             ResultSet rs = sqlStatement.executeQuery(sql);
 	             
 	             System.out.println("Student ID | Class Section ID | First Name | "
 	             		+ "Last Name | Code | Name | Term | Letter Grade");
 	             System.out.println("--------------------------------------------------------------------------------");
 	             
 	             while(rs.next()) {
 	            	 
 	             String student_id = rs.getString("students.student_id");
 	             String class_section_id = rs.getString("class_section_id");
 	             String first_name = rs.getString("first_name");
 	             String last_name = rs.getString("last_name");
 	             String class_code = rs.getString("classes.code");
 	             String class_name = rs.getString("classes.name");
 	             String term_name = rs.getString("terms.name");
 	             String letter_grade = rs.getString("letter_grade");
 	             
 	             System.out.println(student_id + " | " + class_section_id + " | " + 
 	             first_name + " | " + last_name + " | " + class_code +
 	            		 " | " + class_name + " | " + term_name + " | " + letter_grade);
 	             }
 	        } catch (SQLException sqlException) {
 	            System.out.println("Failed to get students");
 	            System.out.println(sqlException.getMessage());

 	        } finally {
 	            try {
 	                if (sqlStatement != null)
 	                    sqlStatement.close();
 	            } catch (SQLException se2) {
 	            }
 	            try {
 	                if (connection != null)
 	                    connection.close();
 	            } catch (SQLException se) {
 	                se.printStackTrace();
 	            }
 	        }
 	    } catch (SQLException e) {
 	    }
 	    }

    public static void listAllClassSections() {
    	try {	
 	    	Connection connection = Database.getDatabaseConnection();
 	        Statement sqlStatement = connection.createStatement();

 	        try {
 	             String sql = "SELECT class_section_id, classes.code, classes.name"
 	             		+ ", terms.name FROM class_sections LEFT JOIN classes ON"
 	             		+ " classes.class_id = class_sections.class_id LEFT JOIN"
 	             		+ " terms ON terms.term_id = class_sections.term_id GROUP BY"
 	             		+ " class_sections.class_section_id, terms.name";
 	             
 	             ResultSet rs = sqlStatement.executeQuery(sql);
 	             
 	             System.out.println("Class Section ID | Code | Name | Term");
 	             System.out.println("--------------------------------------------------------------------------------");
 	             
 	             while(rs.next()) {
 	             
 	             String class_section_id = rs.getString("class_section_id");
 	             String class_code = rs.getString("classes.code");
 	             String class_name = rs.getString("classes.name");
 	             String term_name = rs.getString("terms.name");
 	             
 	             System.out.println(class_section_id + " | " + class_code +
 	            		 " | " + class_name + " | " + term_name);
 	             }
 	        } catch (SQLException sqlException) {
 	            System.out.println("Failed to get students");
 	            System.out.println(sqlException.getMessage());

 	        } finally {
 	            try {
 	                if (sqlStatement != null)
 	                    sqlStatement.close();
 	            } catch (SQLException se2) {
 	            }
 	            try {
 	                if (connection != null)
 	                    connection.close();
 	            } catch (SQLException se) {
 	                se.printStackTrace();
 	            }
 	        }
 	    } catch (SQLException e) {
 	    }
 	    }

    public static void listAllClasses() {
    	 try {	
 	    	Connection connection = Database.getDatabaseConnection();
 	        Statement sqlStatement = connection.createStatement();

 	        try {
 	             String sql = "SELECT * FROM classes";
 	             
 	             ResultSet rs = sqlStatement.executeQuery(sql);
 	             
 	             System.out.println("Class ID | Code | Name | Description");
 	             System.out.println("--------------------------------------------------------------------------------");
 	             
 	             while(rs.next()) {
 	             
 	             String class_id = rs.getString("class_id");
 	             String class_code = rs.getString("code");
 	             String class_name = rs.getString("name");
 	             String description = rs.getString("description");
 	             
 	             System.out.println(class_id + " | " + class_code +
 	            		 " | " + class_name + " | " + description);
 	             }
 	        } catch (SQLException sqlException) {
 	            System.out.println("Failed to get students");
 	            System.out.println(sqlException.getMessage());

 	        } finally {
 	            try {
 	                if (sqlStatement != null)
 	                    sqlStatement.close();
 	            } catch (SQLException se2) {
 	            }
 	            try {
 	                if (connection != null)
 	                    connection.close();
 	            } catch (SQLException se) {
 	                se.printStackTrace();
 	            }
 	        }
 	    } catch (SQLException e) {
 	    }
 	    }

    public static void listAllStudents() {
    	 try {	
    	    	Connection connection = Database.getDatabaseConnection();
    	        Statement sqlStatement = connection.createStatement();

    	        try {
    	             String sql = "SELECT * FROM students";
    	             
    	             ResultSet rs = sqlStatement.executeQuery(sql);
    	             
    	             System.out.println("Student ID | First Name | Last Name | Birthdate");
    	             System.out.println("--------------------------------------------------------------------------------");
    	             
    	             while(rs.next()) {
    	             
    	             String student_id = rs.getString("student_id");
    	             String first_name = rs.getString("first_name");
    	             String last_name = rs.getString("last_name");
    	             String birthdate = rs.getString("birthdate");
    	             
    	             System.out.println(student_id + " | " + first_name +
    	            		 " | " + last_name + " | " + birthdate);
    	             }
    	        } catch (SQLException sqlException) {
    	            System.out.println("Failed to get students");
    	            System.out.println(sqlException.getMessage());

    	        } finally {
    	            try {
    	                if (sqlStatement != null)
    	                    sqlStatement.close();
    	            } catch (SQLException se2) {
    	            }
    	            try {
    	                if (connection != null)
    	                    connection.close();
    	            } catch (SQLException se) {
    	                se.printStackTrace();
    	            }
    	        }
    	    } catch (SQLException e) {
    	    }
    	    }
    /***
     * Splits a string up by spaces. Spaces are ignored when wrapped in quotes.
     *
     * @param command - School Management System cli command
     * @return splits a string by spaces.
     */
    public static List<String> parseArguments(String command) {
        List<String> commandArguments = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
        while (m.find()) commandArguments.add(m.group(1).replace("\"", ""));
        return commandArguments;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the School Management System");
        System.out.println("-".repeat(80));

        Scanner scan = new Scanner(System.in);
        String command = "";

        do {
            System.out.print("Command: ");
            command = scan.nextLine();
            ;
            List<String> commandArguments = parseArguments(command);
            command = commandArguments.get(0);
            commandArguments.remove(0);

            if (command.equals("help")) {
                System.out.println("-".repeat(38) + "Help" + "-".repeat(38));
                System.out.println("test connection \n\tTests the database connection");

                System.out.println("list students \n\tlists all the students");
                System.out.println("list classes \n\tlists all the classes");
                System.out.println("list class_sections \n\tlists all the class_sections");
                System.out.println("list class_registrations \n\tlists all the class_registrations");
                System.out.println("list instructor <first_name> <last_name>\n\tlists all the classes taught by that instructor");


                System.out.println("delete student <studentId> \n\tdeletes the student");
                System.out.println("create student <first_name> <last_name> <birthdate> \n\tcreates a student");
                System.out.println("register student <student_id> <class_section_id>\n\tregisters the student to the class section");

                System.out.println("submit grade <studentId> <class_section_id> <letter_grade> \n\tcreates a student");
                System.out.println("help \n\tlists help information");
                System.out.println("quit \n\tExits the program");
            } else if (command.equals("test") && commandArguments.get(0).equals("connection")) {
                Database.testConnection();
            } else if (command.equals("list")) {
                if (commandArguments.get(0).equals("students")) listAllStudents();
                if (commandArguments.get(0).equals("classes")) listAllClasses();
                if (commandArguments.get(0).equals("class_sections")) listAllClassSections();
                if (commandArguments.get(0).equals("class_registrations")) listAllClassRegistrations();

                if (commandArguments.get(0).equals("instructor")) {
                    getAllClassesByInstructor(commandArguments.get(1), commandArguments.get(2));
                }
            } else if (command.equals("create")) {
                if (commandArguments.get(0).equals("student")) {
                    createNewStudent(commandArguments.get(1), commandArguments.get(2), commandArguments.get(3));
                }
            } else if (command.equals("register")) {
                if (commandArguments.get(0).equals("student")) {
                    registerStudent(commandArguments.get(1), commandArguments.get(2));
                }
            } else if (command.equals("submit")) {
                if (commandArguments.get(0).equals("grade")) {
                    submitGrade(commandArguments.get(1), commandArguments.get(2), commandArguments.get(3));
                }
            } else if (command.equals("delete")) {
                if (commandArguments.get(0).equals("student")) {
                    deleteStudent(commandArguments.get(1));
                }
            } else if (!(command.equals("quit") || command.equals("exit"))) {
                System.out.println(command);
                System.out.println("Command not found. Enter 'help' for list of commands");
            }
            System.out.println("-".repeat(80));
        } while (!(command.equals("quit") || command.equals("exit")));
        System.out.println("Bye!");
    }
}

