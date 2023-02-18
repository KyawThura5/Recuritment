INSERT INTO `department` (`name`, `is_deleted`) VALUES
('Department 1', false), ('Department 2', false), ('Department 3', false);

INSERT INTO `team` (`name`, `department_id`, `is_deleted`) VALUES
('Team ABC', 1, false), ('Team BBC', 1, false), ('Team ACC', 2, false),
('Team BCD', 1, false), ('Team CDE', 1, false), ('Team DEF', 2, false),
('Team OPQ', 2, false);