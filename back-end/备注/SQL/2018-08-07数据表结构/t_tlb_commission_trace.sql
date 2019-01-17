/*
Navicat MySQL Data Transfer

Source Server         : java
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2018-08-07 17:50:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_tlb_commission_trace`
-- ----------------------------
DROP TABLE IF EXISTS `t_tlb_commission_trace`;
CREATE TABLE `t_tlb_commission_trace` (
  `c_id` varchar(32) NOT NULL,
  `t_commission_id` varchar(32) DEFAULT NULL,
  `i_order_no` bigint(32) DEFAULT NULL,
  `c_money` double DEFAULT NULL,
  `d_create_at` datetime DEFAULT NULL,
  `i_lot_amount` double DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

