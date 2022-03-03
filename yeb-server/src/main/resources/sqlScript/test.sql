delete from test;
delete from test_1;

INSERT INTO `ggz`.`test_1` (`id`, `name`) VALUES (1, 'ggz1');
INSERT INTO `ggz`.`test_1` (`id`, `name`) VALUES (2, 'ggz2');

INSERT INTO `ggz`.`test` (`id`, `NAME`) VALUES (1, 'ggz');
INSERT INTO `ggz`.`test` (`id`, `NAME`) VALUES (2, 'ggz');

commit;