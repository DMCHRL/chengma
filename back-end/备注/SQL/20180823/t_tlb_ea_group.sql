/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50629
Source Host           : localhost:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2018-08-23 13:52:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_tlb_ea_group
-- ----------------------------
DROP TABLE IF EXISTS `t_tlb_ea_group`;
CREATE TABLE `t_tlb_ea_group` (
  `c_id` varchar(32) NOT NULL,
  `c_ea_group` varchar(25) DEFAULT NULL,
  `c_ea_group_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tlb_ea_group
-- ----------------------------
INSERT INTO `t_tlb_ea_group` VALUES ('1', 'A', 'ʵ��A��');
INSERT INTO `t_tlb_ea_group` VALUES ('2', 'B', 'ʵ��B��');
INSERT INTO `t_tlb_ea_group` VALUES ('3', 'C', 'ʵ��C��');
