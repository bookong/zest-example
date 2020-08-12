CREATE TABLE `xkcd` (
  `id` BIGINT NOT NULL COMMENT 'XKCD 漫画索引号',
  `title` VARCHAR(1024) NOT NULL COMMENT '漫画标题',
  `alt` VARCHAR(1024) NOT NULL COMMENT '漫画图片的替代文本',
  `img` VARCHAR(512) NOT NULL COMMENT '漫画图片地址',
  `pub_date` DATETIME NOT NULL COMMENT '漫画发布时间',
  PRIMARY KEY (`id`)
) ;