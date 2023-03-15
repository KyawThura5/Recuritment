INSERT INTO `recruitment_resource` (`code`, `name`, `entity_type`) VALUES
('DAT_RESOURCE_001', 'ACE Inspiration', 'DirectRecruitmentResource'),
('DAT_RESOURCE_002', 'Direct', 'DirectRecruitmentResource'),
('DAT_RESOURCE_003', 'Internship', 'DirectRecruitmentResource'),
('DAT_RESOURCE_004', 'Job.com', 'ExternalRecruitmentResource'),
('DAT_RESOURCE_005', 'Connect Job Myanmar', 'ExternalRecruitmentResource'),
('DAT_RESOURCE_006', 'JobNet Myanmar', 'ExternalRecruitmentResource'),
('DAT_RESOURCE_007', 'VAC Jobsearch', 'ExternalRecruitmentResource'),
('DAT_RESOURCE_008', 'UCSY', 'ExternalRecruitmentResource'),
('DAT_RESOURCE_009', 'Neo Tech', 'ExternalRecruitmentResource');

INSERT INTO `direct_recruitment_resource` (`id`) VALUES
(1), (2), (3);

INSERT INTO `external_recruitment_resource` 
(`id`, `contact_person`, `pic`, `email`, `phone`, `website_link`, `type`) VALUES
(4,'U Han Bo', 'U Han Bo', 'jobcom@gmail.com', '09989898989', 'job.com', 'AGENCY'),
(5, 'U Win Ko', 'U Thiha', 'connectjob@gmail.com', '09878787626', 'connectiu.com', 'AGENCY'),
(6, 'Daw Sabel', 'U Aung Ko', 'jobnet@gmail.com', '09987373632', 'jobnet.com.mm', 'AGENCY'),
(7, 'U Tun Naing', 'U Kyaw Swar', 'jobsearch@gmail.com', '09986677522', 'vacjobsearch.com', 'AGENCY'),
(8, 'U Mya Han', 'Daw Kyi Kyi', 'uscy@gmail.com', '09997377363', 'ucsy.edu.mm', 'UNIVERSITY'),
(9, 'U Si Thu', 'U Si Thu', 'neotech@gmail.com', '09787876543', null, 'TRAINING_CENTER');