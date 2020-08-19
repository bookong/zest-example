CREATE TABLE `user` (
  `id`          bigint       NOT NULL AUTO_INCREMENT,
  `login_name`  varchar(32)  NOT NULL ,
  `password`    varchar(32)  NOT NULL ,
  `nickname`    varchar(64)  NOT NULL ,
  `ext_info`    json                   DEFAULT NULL ,
  `create_time` datetime     NOT NULL ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_u_1` (`login_name`)
) ;
