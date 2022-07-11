/* Put your final project reporting queries here */
USE cs_hu_310_final_project;

SELECT
	students.first_name,
    students.last_name,
    COUNT(class_registrations.class_registration_id) as number_of_classes,
	SUM(convert_to_grade_point(letter_grade)) as total_grade_points_earned,
    avg(convert_to_grade_point(letter_grade)) as GPA
FROM class_registrations 
JOIN students ON students.student_id = class_registrations.student_id
JOIN grades ON grades.grade_id = class_registrations.grade_id 
WHERE students.student_id = 1;



SELECT
	first_name,
    last_name,
    COUNT(class_registrations.class_registration_id) as number_of_classes,
	SUM(convert_to_grade_point(letter_grade)) as total_grade_points_earned,
    avg(convert_to_grade_point(letter_grade)) as GPA
FROM class_registrations
JOIN students ON students.student_id = class_registrations.student_id
JOIN grades ON grades.grade_id = class_registrations.grade_id 
GROUP BY students.student_id;


SELECT
	code,
    name,
	COUNT(class_registrations.grade_id) as number_of_grades,
    SUM(convert_to_grade_point(letter_grade)) as total_grade_points,
    avg(convert_to_grade_point(letter_grade)) as AVG_GPA
FROM
	classes
JOIN class_sections ON class_sections.class_id = classes.class_id
JOIN class_registrations ON class_registrations.class_section_id = class_sections.class_section_id
JOIN grades ON class_registrations.grade_id = grades.grade_id
GROUP BY classes.class_id;

SELECT
	code,
    classes.name,
    terms.name as term,
	COUNT(class_registrations.grade_id) as number_of_grades,
    SUM(convert_to_grade_point(letter_grade)) as total_grade_points,
    avg(convert_to_grade_point(letter_grade)) as AVG_GPA
FROM
	classes
LEFT JOIN class_sections ON class_sections.class_id = classes.class_id
LEFT JOIN terms ON terms.term_id = class_sections.term_id
LEFT JOIN class_registrations ON class_registrations.class_section_id = class_sections.class_section_id
LEFT JOIN grades ON class_registrations.grade_id = grades.grade_id
GROUP BY classes.class_id, terms.name;

SELECT
	first_name,
    last_name,
    title,
    classes.code,
    classes.name as class_name,
    terms.name as term
FROM
	classes
LEFT JOIN class_sections on class_sections.class_id = classes.class_id
LEFT JOIN terms on terms.term_id = class_sections.term_id
LEFT JOIN instructors on instructors.instructor_id = class_sections.instructor_id
LEFT JOIN academic_titles on academic_titles.academic_title_id = instructors.academic_title_id
WHERE instructors.instructor_id = 1
GROUP BY instructors.instructor_id, terms.name;

SELECT
    classes.code,
    classes.name as class_name,
    terms.name as term,
    first_name,
    last_name
FROM
	classes
LEFT JOIN class_sections on class_sections.class_id = classes.class_id
LEFT JOIN terms on terms.term_id = class_sections.term_id
LEFT JOIN instructors on instructors.instructor_id = class_sections.instructor_id
LEFT JOIN academic_titles on academic_titles.academic_title_id = instructors.academic_title_id
GROUP BY instructors.instructor_id, terms.name;

SELECT
	classes.code,
    classes.name,
    terms.name as term,
    COUNT(class_registrations.student_id) as enrolled_students,
    (classes.maximum_students - COUNT(class_registrations.student_id)) as space_remaining
FROM
	classes
LEFT JOIN class_sections ON classes.class_id = class_sections.class_id
LEFT JOIN terms ON terms.term_id = class_sections.term_id
LEFT JOIN class_registrations ON class_registrations.class_section_id = class_sections.class_section_id
GROUP BY classes.code, terms.name;
