/*
Navicat MySQL Data Transfer

Source Server         : java
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2018-07-30 16:27:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_bank_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_bank_info`;
CREATE TABLE `t_bank_info` (
  `c_id` varchar(32) NOT NULL,
  `b_num` varchar(50) DEFAULT NULL COMMENT '銀行卡號碼',
  `bank` varchar(50) DEFAULT NULL COMMENT '開戶行',
  `sub_bank` varchar(70) DEFAULT NULL COMMENT '開戶支行',
  `location` varchar(100) DEFAULT NULL COMMENT '銀行卡所在地',
  `c_user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bank_info
-- ----------------------------
