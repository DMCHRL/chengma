/*
Navicat MySQL Data Transfer

Source Server         : java
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2018-07-31 14:34:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_tlb_account_control`
-- ----------------------------
DROP TABLE IF EXISTS `t_tlb_account_control`;
CREATE TABLE `t_tlb_account_control` (
  `c_id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `c_account` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tlb_account_control
-- ----------------------------

