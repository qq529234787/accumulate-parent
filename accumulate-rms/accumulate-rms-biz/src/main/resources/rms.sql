/*
Navicat MySQL Data Transfer

Source Server         : test-edit-173
Source Server Version : 50625
Source Host           : 10.168.66.173:3306
Source Database       : adminmall

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-03-29 11:48:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_department
-- ----------------------------
DROP TABLE IF EXISTS `admin_department`;
CREATE TABLE `admin_department` (
  `department_id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `department_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称' ,
  `status`  int(4) NOT NULL COMMENT '1、有效 -1、无效' ,
  `created`  datetime NULL DEFAULT NULL ,
  `modifyed`  datetime NULL DEFAULT NULL ,
  PRIMARY KEY (`department_id`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=17

;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
  `menu_id`  bigint(18) NOT NULL AUTO_INCREMENT ,
  `parent_id`  bigint(18) NOT NULL COMMENT '一级菜单PID为0' ,
  `menu_link`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '访问系统的链接' ,
  `code`  varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统权限码 暂时无用' ,
  `menu_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称' ,
  `seq`  int(10) NULL DEFAULT NULL COMMENT '排序号' ,
  `menu_level`  int(4) NOT NULL COMMENT '1、一级菜单 2、二级菜单 3、三级菜单' ,
  `status`  int(4) NOT NULL COMMENT '1、有效   -1、无效' ,
  `created`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
  `modifyed`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
  PRIMARY KEY (`menu_id`),
  INDEX `admin_menu_pid` (`parent_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=142

;

-- ----------------------------
-- Table structure for admin_menu_property
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu_property`;
CREATE TABLE `admin_menu_property` (
  `menu_property_id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `property_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功能名称' ,
  `menu_id`  bigint(20) NOT NULL COMMENT '菜单ID' ,
  `status`  int(4) NOT NULL COMMENT '1、有效 2、无效' ,
  `created`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
  `modifyed`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
  PRIMARY KEY (`menu_property_id`),
  INDEX `menu_id` (`menu_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=32

;

-- ----------------------------
-- Table structure for admin_property_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_property_role`;
CREATE TABLE `admin_property_role` (
  `menu_role_id`  bigint(18) NOT NULL AUTO_INCREMENT ,
  `property_id`  bigint(18) NOT NULL COMMENT '功能编号' ,
  `role_id`  bigint(18) NOT NULL COMMENT '角色编号' ,
  `status`  int(4) NULL DEFAULT NULL COMMENT '1、有效   -1、无效' ,
  `created`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
  `modifyed`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
  PRIMARY KEY (`menu_role_id`),
  INDEX `admin_menu_id` (`property_id`) USING BTREE ,
  INDEX `admin_role_id` (`role_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=6953

;

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `role_id`  bigint(18) NOT NULL AUTO_INCREMENT ,
  `role_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称' ,
  `status`  int(4) NOT NULL COMMENT '1、有效   -1、无效' ,
  `created`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
  `modifyed`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
  PRIMARY KEY (`role_id`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=20

;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `user_id`  bigint(18) NOT NULL AUTO_INCREMENT ,
  `login_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆用户名' ,
  `status`  int(4) NOT NULL COMMENT '1、启用 2、禁用 -1、删除' ,
  `department_id`  bigint(18) NULL DEFAULT NULL COMMENT '部门ID' ,
  `created`  datetime NOT NULL COMMENT '创建时间' ,
  `modifyed`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `admin_username` (`login_name`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=111

;

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role` (
  `user_role_id`  bigint(18) NOT NULL AUTO_INCREMENT ,
  `user_id`  bigint(18) NOT NULL COMMENT '用户ID' ,
  `role_id`  bigint(18) NOT NULL COMMENT '角色ID' ,
  `status`  int(4) NOT NULL COMMENT '1、有效 -1、无效' ,
  `created`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
  `modifyed`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
  PRIMARY KEY (`user_role_id`),
  INDEX `admin_user_id` (`user_id`) USING BTREE ,
  INDEX `admin_role_id` (`role_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=123

;

-- ----------------------------
-- Table structure for collect_url
-- ----------------------------
DROP TABLE IF EXISTS `collect_url`;
CREATE TABLE `collect_url` (
  `collect_id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `login_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `url`  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问的链接' ,
  `param`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '访问的参数' ,
  `param_json`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '访问的参数 json格式' ,
  `local_addr`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务器IP' ,
  `remote_addr`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端IP' ,
  `created`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
  PRIMARY KEY (`collect_id`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=90378

;

-- ----------------------------
-- Auto increment value for admin_department
-- ----------------------------
ALTER TABLE `admin_department` AUTO_INCREMENT=17;

-- ----------------------------
-- Auto increment value for admin_menu
-- ----------------------------
ALTER TABLE `admin_menu` AUTO_INCREMENT=142;

-- ----------------------------
-- Auto increment value for admin_menu_property
-- ----------------------------
ALTER TABLE `admin_menu_property` AUTO_INCREMENT=32;

-- ----------------------------
-- Auto increment value for admin_property_role
-- ----------------------------
ALTER TABLE `admin_property_role` AUTO_INCREMENT=6953;

-- ----------------------------
-- Auto increment value for admin_role
-- ----------------------------
ALTER TABLE `admin_role` AUTO_INCREMENT=20;

-- ----------------------------
-- Auto increment value for admin_user
-- ----------------------------
ALTER TABLE `admin_user` AUTO_INCREMENT=111;

-- ----------------------------
-- Auto increment value for admin_user_role
-- ----------------------------
ALTER TABLE `admin_user_role` AUTO_INCREMENT=123;

-- ----------------------------
-- Auto increment value for collect_url
-- ----------------------------
ALTER TABLE `collect_url` AUTO_INCREMENT=100;
