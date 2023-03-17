INSERT INTO `department` (`id`, `name`, `is_deleted`) VALUES
(1, 'Offshore Department', false),
(2, 'Networking Department', false),
(3, 'Department 3', false);

INSERT INTO `team` (`id`, `name`, `department_id`, `is_deleted`) VALUES
(1, 'Team Marvel', 1, false),
(2, 'Team DC', 1, false),
(3, 'Team Alpha', 2, false),
(4, 'Team Beta', 2, false),
(5, 'Team Penta', 2, false),
(6, 'Team Falcon', 3, false),
(7, 'Team Shark', 3, false);