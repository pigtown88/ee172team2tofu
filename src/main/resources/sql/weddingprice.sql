CREATE TABLE `weddingprice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '方案類型',
  `price` double NOT NULL,
  `tablecount` double NOT NULL COMMENT '桌数',
  `includephotographer` tinyint(1) NOT NULL COMMENT '是否包含摄影师',
  `includebackboard` tinyint(1) DEFAULT NULL COMMENT '是否包含背板',
  `photographernote` varchar(100) DEFAULT NULL COMMENT '攝影師類別',
  `backboardnote` varchar(100) DEFAULT NULL COMMENT '背板類別',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci



INSERT INTO g2.weddingprice (name,price,tablecount,includephotographer,includebackboard,photographernote,backboardnote) VALUES 
('普通A方案',500,10,0,1,'攝影師','基礎背板')
,('上等B方案',1000,20,1,1,'靜態攝影師+動態攝影師','大理石背板')
,('高級C方案',2000,50,1,1,'5人專業電影+寫真團隊','鮮花背板')
;