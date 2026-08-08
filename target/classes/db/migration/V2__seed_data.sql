-- V2__seed_data.sql: Classroom Digital Board Management System Initial Seed Data

-- 1. SEED USERS (BCrypt Hash for 'password123' is $2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xD00DMxs.AQ8H9a.)
INSERT INTO users (id, username, password, email, first_name, last_name, phone, role, enabled) VALUES
(1, 'principal_admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xD00DMxs.AQ8H9a.', 'principal@school.edu', 'Arthur', 'Pendelton', '+19876543210', 'ROLE_PRINCIPAL', true),
(2, 'teacher_john', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xD00DMxs.AQ8H9a.', 'john.doe@school.edu', 'John', 'Doe', '+19876543211', 'ROLE_TEACHER', true),
(3, 'teacher_sarah', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xD00DMxs.AQ8H9a.', 'sarah.connor@school.edu', 'Sarah', 'Connor', '+19876543212', 'ROLE_TEACHER', true);

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));

-- 2. SEED PRINCIPALS & TEACHERS
INSERT INTO principals (id, user_id, qualification, office_room) VALUES
(1, 1, 'Ph.D. in Educational Leadership', 'Admin-101');

INSERT INTO teachers (id, user_id, employee_id, designation, specialization, joining_date) VALUES
(1, 2, 'EMP-T-001', 'Senior Mathematics Teacher', 'Algebra & Calculus', '2020-08-15'),
(2, 3, 'EMP-T-002', 'Lead Physics Teacher', 'Quantum & Mechanics', '2021-01-10');

SELECT setval('principals_id_seq', (SELECT MAX(id) FROM principals));
SELECT setval('teachers_id_seq', (SELECT MAX(id) FROM teachers));

-- 3. SEED CLASSES
INSERT INTO classes (id, grade, section, academic_year, class_teacher_id) VALUES
(1, '10', 'A', '2025-2026', 1),
(2, '10', 'B', '2025-2026', 2);

SELECT setval('classes_id_seq', (SELECT MAX(id) FROM classes));

-- 4. SEED CLASSROOMS (Physical Board Devices)
INSERT INTO classrooms (id, room_number, building, digital_board_device_id, ip_address, is_active, current_class_id) VALUES
(1, 'Room-101', 'Science Block A', 'BOARD-ROOM-101-WIN11', '192.168.1.101', true, 1),
(2, 'Room-102', 'Science Block A', 'BOARD-ROOM-102-WIN11', '192.168.1.102', true, 2);

SELECT setval('classrooms_id_seq', (SELECT MAX(id) FROM classrooms));

-- 5. SEED SUBJECTS
INSERT INTO subjects (id, code, name, description) VALUES
(1, 'MATH-10', 'Mathematics Grade 10', 'Algebra, Geometry, Trigonometry and Statistics'),
(2, 'PHYS-10', 'Physics Grade 10', 'Motion, Energy, Waves and Electricity');

SELECT setval('subjects_id_seq', (SELECT MAX(id) FROM subjects));

-- 6. SEED TEACHER ASSIGNMENTS
INSERT INTO teacher_assignments (id, teacher_id, class_id, subject_id, academic_year) VALUES
(1, 1, 1, 1, '2025-2026'),
(2, 2, 1, 2, '2025-2026'),
(3, 1, 2, 1, '2025-2026');

SELECT setval('teacher_assignments_id_seq', (SELECT MAX(id) FROM teacher_assignments));

-- 7. SEED STUDENTS (Class 10A Hostellers)
INSERT INTO students (id, roll_number, first_name, last_name, admission_number, gender, hostel_block, room_number, class_id) VALUES
(1, '10A-01', 'Alexander', 'Hamilton', 'ADM-2024-001', 'Male', 'Block Alpha', 'H-101', 1),
(2, '10A-02', 'Beatrix', 'Kiddo', 'ADM-2024-002', 'Female', 'Block Beta', 'H-204', 1),
(3, '10A-03', 'Charles', 'Xavier', 'ADM-2024-003', 'Male', 'Block Alpha', 'H-105', 1),
(4, '10A-04', 'Diana', 'Prince', 'ADM-2024-004', 'Female', 'Block Beta', 'H-208', 1);

SELECT setval('students_id_seq', (SELECT MAX(id) FROM students));

-- 8. SEED TIMETABLE
INSERT INTO timetable (id, class_id, subject_id, teacher_id, classroom_id, day_of_week, start_time, end_time, period_number, academic_year) VALUES
(1, 1, 1, 1, 1, 'MONDAY', '08:30:00', '09:30:00', 1, '2025-2026'),
(2, 1, 2, 2, 1, 'MONDAY', '09:35:00', '10:35:00', 2, '2025-2026'),
(3, 1, 1, 1, 1, 'TUESDAY', '08:30:00', '09:30:00', 1, '2025-2026'),
(4, 1, 2, 2, 1, 'WEDNESDAY', '10:40:00', '11:40:00', 3, '2025-2026');

SELECT setval('timetable_id_seq', (SELECT MAX(id) FROM timetable));

-- 9. SEED CHAPTERS & TOPICS
INSERT INTO chapters (id, subject_id, class_id, chapter_number, title, description) VALUES
(1, 1, 1, 1, 'Quadratic Equations', 'Standard form, factorization, quadratic formula'),
(2, 2, 1, 1, 'Laws of Motion', 'Newton first, second and third laws of motion');

SELECT setval('chapters_id_seq', (SELECT MAX(id) FROM chapters));

INSERT INTO topics (id, chapter_id, topic_number, title, description, estimated_hours) VALUES
(1, 1, 1, 'Introduction to Quadratic Form', 'Ax^2 + Bx + C = 0 properties', 1.0),
(2, 1, 2, 'Solving by Completing the Square', 'Method and step by step derivations', 1.5),
(3, 2, 1, 'Inertia and Newton First Law', 'Concept of inertia and momentum', 1.0);

SELECT setval('topics_id_seq', (SELECT MAX(id) FROM topics));
