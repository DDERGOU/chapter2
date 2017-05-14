/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2017-05-13 17:13:00
*/

/*---------------------------------
---------------新建并且插入数据----
-----------------------------------
 */
/*
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_customer
-- ----------------------------
DROP TABLE IF EXISTS `Customer`;
CREATE TABLE `Customer` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID 自增主键',
  `name` varchar(255) NOT NULL COMMENT '客户名称',
  `contact` varchar(255) NOT NULL COMMENT '联系人',
  `telephone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_customer
-- ----------------------------
INSERT INTO `Customer` VALUES ('1', 'customer1', 'jack', '13512345678', 'jack@gmail.com', null);
INSERT INTO `Customer` VALUES ('2', 'customer2', 'rose', '13623456789', 'rose@gmail.com', null);
*/

/*---------------------------------
---------------初始化表格数据------
-----------------------------------
 */

/*
TRUNCATE Customer;
INSERT INTO `Customer` VALUES ('1', 'customer1', 'jack', '13512345678', 'jack@gmail.com', null);
INSERT INTO `Customer` VALUES ('2', 'customer2', 'rose', '13623456789', 'rose@gmail.com', null);

*/

/*
-----------------------标点符号-----------错误例子-------------------------------
CREATE TABLE 'tbl_customer' (
'id' bigint(20) NOT NULL AUTO_INCREMENT,
'name' varchar(255) DEFAULT null,
'contact' varchar(255) DEFAULT null,
'telephone' varchar(255)  DEFAULT null,
'email' varchar(255) DEFAULT null,
'remark' text,
PRIMARY KEY('id')
)ENGINE=InnoDB DEFAULT CHARACTER=utf8;

INSERT INTO 'tbl_customer' VALUES('1', 'customer1', 'Jack', '13512345678', 'jack@gmail.com', null);
INSERT INTO 'tbl_customer' VALUES('2', 'customer2', 'Rose', '13612345678', 'rose@gmail.com', null);

 */
