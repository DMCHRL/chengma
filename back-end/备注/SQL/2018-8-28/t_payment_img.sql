/*
Navicat MySQL Data Transfer

Source Server         : bili
Source Server Version : 50629
Source Host           : 192.168.1.139:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2018-08-28 14:15:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_payment_img`
-- ----------------------------
DROP TABLE IF EXISTS `t_payment_img`;
CREATE TABLE `t_payment_img` (
  `c_id` varchar(32) NOT NULL DEFAULT '',
  `c_img` varchar(200) DEFAULT NULL,
  `d_create_at` datetime DEFAULT NULL,
  `d_update_at` datetime DEFAULT NULL,
  `t_text` varchar(200) DEFAULT NULL,
  `c_shop_name` varchar(50) DEFAULT NULL,
  `c_flag` varchar(10) DEFAULT NULL COMMENT '标记选中true or false',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

