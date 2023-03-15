INSERT INTO `vacancy`
(`code`, `due_date`, `created_date_time`, `status`, `created_user_id`, `department_id`, `comment`) VALUES
('DAT_VACANCY_2022_12_01_0001', '2022-12-31', '2022-12-01 10:12:02', 'CLOSED', 3, 1, 'Lorem ipsum dolor, sit amet consectetur adipisicing elit. Ratione vel earum aspernatur saepe consectetur maiores libero quam nisi. Corrupti, eos voluptatem hic non facilis assumenda aperiam voluptates repellendus laboriosam nemo.'),
('DAT_VACANCY_2022_12_30_0002', '2023-01-31', '2022-12-30 14:30:05', 'CLOSED', 3, 1, null),
('DAT_VACANCY_2023_03_05_0003', '2023-03-20', '2023-03-05 15:00:03', 'OPENING', 4, 2, null),
('DAT_VACANCY_2023_03_14_0004', '2023-04-30', '2023-03-14 13:24:09', 'OPENING', 9, 3, 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Facere odit quae quam dolores necessitatibus neque laboriosam asperiores sunt error dolor, alias nihil. Illum deserunt magni velit, consectetur iusto architecto explicabo?');


INSERT INTO `require_position`
(`vacancy_id`, `position_id`, `is_foc`, `team_id`, `count`) VALUES
(1, 1, true, 1, 3),
(1, 3, false, 1, 1),
(2, 1, false, 2, 2),
(2, 4, false, 1, 2),
(2, 2, false, 1, 1),
(3, 7, false, 5, 1),
(4, 6, false, 5, 1),
(4, 9, false, 6, 2);