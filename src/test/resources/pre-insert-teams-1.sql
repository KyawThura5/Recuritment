INSERT INTO `department` (`name`, `is_deleted`) VALUES
('Department 1', false), ('Department 2', false), ('Department 3', true);

INSERT INTO `team` (`name`, `department_id`, `is_deleted`) VALUES
('Team ABC', 1, false), ('Team ABC', 1, true), ('Team ABC', 2, false),
('Team BCD', 1, false), ('Team CDE', 1, true), ('Team DEF', 2, false),
('Team DEF', 2, true);