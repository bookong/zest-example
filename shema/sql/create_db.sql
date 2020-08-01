set names utf8;

CREATE SCHEMA `zestdemo` DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `login_name` VARCHAR(32) NOT NULL COMMENT '登录名',
  `password` VARCHAR(32) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(64) NOT NULL COMMENT '昵称',
  `create_time` DATETIME NOT NULL COMMENT '账号创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '用户表';

CREATE TABLE `xkcd` (
  `id` BIGINT NOT NULL COMMENT 'XKCD 漫画索引号',
  `title` VARCHAR(1024) NOT NULL COMMENT '漫画标题',
  `alt` VARCHAR(1024) NOT NULL COMMENT '漫画图片的替代文本',
  `img` VARCHAR(512) NOT NULL COMMENT '漫画图片地址',
  `pub_date` DATETIME NOT NULL COMMENT '漫画发布时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = 'XKCD 漫画';

CREATE TABLE `remark` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID user.id',
  `xkcd_id` BIGINT NOT NULL COMMENT '漫画ID xkcd.id',
  `content` VARCHAR(512) NOT NULL COMMENT '评论内容',
  `create_time` DATETIME NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '漫画评论';
