CREATE TABLE `t_tlb_mt_price` (
  `c_id` varchar(64) NOT NULL COMMENT 'id',
  `i_sell` DECIMAL(18,8) DEFAULT 0 COMMENT '卖出价格',
  `i_buy` DECIMAL(18,8) DEFAULT 0 COMMENT '买入价格',
  `c_time` varchar(30) DEFAULT NULL COMMENT '时间',
  `c_symbol` varchar(20) DEFAULT NULL COMMENT '货币类型',
  `i_high1440` varchar(225) DEFAULT NULL,
  `i_high30` varchar(225) DEFAULT NULL,
  `i_high60` varchar(225) DEFAULT NULL,
  `i_low1440` varchar(225) DEFAULT NULL,
  `i_low30` varchar(225) DEFAULT NULL,
  `i_low60` varchar(225) DEFAULT NULL,
  `i_open1440` varchar(225) DEFAULT NULL,
  `i_open30` varchar(225) DEFAULT NULL,
  `i_open60` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_tlb_history_k` (
  `c_id` varchar(35) NOT NULL COMMENT 'id',
  `c_cycle` varchar(20) NOT NULL COMMENT '周期',
  `i_open` DECIMAL(18,8) DEFAULT 0 COMMENT '开盘价',
  `i_close` DECIMAL(18,8) DEFAULT 0 COMMENT '收盘价',
  `i_high` DECIMAL(18,8) DEFAULT 0 COMMENT '最高价',
  `i_low` DECIMAL(18,8) DEFAULT 0 COMMENT '最低价',
  `c_macd` varchar(225) DEFAULT NULL COMMENT 'macd',
  `c_dif` varchar(225) DEFAULT NULL COMMENT 'DIF',
  `d_time` datetime NOT NULL COMMENT '时间',
  `c_symbol` varchar(50) NOT NULL COMMENT '货币对',
  PRIMARY KEY (`c_cycle`,`d_time`,`c_symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;