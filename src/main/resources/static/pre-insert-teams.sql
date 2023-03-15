INSERT INTO `department` (`name`, `is_deleted`) VALUES
('Offshore Department', false), ('Department 2', false), ('Department 3', false);

INSERT INTO `team` (`name`, `department_id`, `is_deleted`) VALUES
('Team Marvel', 1, false), ('Team DC', 1, false), ('Team Alpha', 2, false),
('Team Beta', 2, false), ('Team Penta', 2, false), ('Team Falcon', 3, false),
('Team Shark', 3, false);