set names utf8;

CREATE SCHEMA `zestdemo` DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nickname` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '昵称',
  `ext_info` json DEFAULT NULL COMMENT '扩展信息',
  `create_time` datetime NOT NULL COMMENT '账号创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

