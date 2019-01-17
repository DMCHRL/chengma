/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.1.49-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;
CREATE TABLE `t_tlb_commission` (
  `c_id` varchar(32) NOT NULL,
  `i_balance` double DEFAULT NULL,
  `c_user_id` varchar(32) DEFAULT NULL,
  `i_lot_amount` double DEFAULT NULL,
  `i_lot_percent` double DEFAULT NULL,
  `i_total` double DEFAULT NULL,
  `i_withdraw` double DEFAULT NULL,
  `d_create_at` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
