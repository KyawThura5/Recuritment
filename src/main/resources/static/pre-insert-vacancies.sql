INSERT INTO `vacancy`
(`id`, `code`, `due_date`, `created_date_time`, `status`, `created_user_id`, `department_id`, `comment`) VALUES
(1, 'DAT_VACANCY_20221201_0001', '2022-12-31', '2022-12-01 10:12:02', 'CLOSED', 6, 1, 'Lorem ipsum dolor, sit amet consectetur adipisicing elit. Ratione vel earum aspernatur saepe consectetur maiores libero quam nisi. Corrupti, eos voluptatem hic non facilis assumenda aperiam voluptates repellendus laboriosam nemo.'),
(2, 'DAT_VACANCY_20221230_0002', '2023-01-31', '2022-12-30 14:30:05', 'CLOSED', 6, 1, null),
(3, 'DAT_VACANCY_20230305_0003', '2023-03-20', '2023-03-05 15:00:03', 'OPENING', 6, 2, null),
(4, 'DAT_VACANCY_20230314_0004', '2023-04-30', '2023-03-14 13:24:09', 'OPENING', 3, 3, 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Facere odit quae quam dolores necessitatibus neque laboriosam asperiores sunt error dolor, alias nihil. Illum deserunt magni velit, consectetur iusto architecto explicabo?');


INSERT INTO `require_position`
(`id`, `vacancy_id`, `position_id`, `is_foc`, `team_id`, `count`) VALUES
(1, 1, 1, true, 1, 3),
(2, 1, 3, false, 1, 1),
(3, 2, 1, false, 2, 2),
(4, 2, 4, false, 1, 2),
(5, 2, 2, false, 1, 1),
(6, 3, 10, false, 5, 1),
(7, 4, 6, false, 6, 1),
(8, 4, 9, false, 7, 2);