/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 127.0.0.1:3306
 Source Schema         : note

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 09/07/2021 09:59:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_note
-- ----------------------------
DROP TABLE IF EXISTS `tb_note`;
CREATE TABLE `tb_note`  (
  `noteId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `typeId` int(11) NULL DEFAULT NULL COMMENT '外键，从属tb_note_type',
  `pubTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `lon` float NULL DEFAULT NULL COMMENT '经度',
  `lat` float NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`noteId`) USING BTREE,
  INDEX `fk_note_ref_type`(`typeId`) USING BTREE,
  CONSTRAINT `fk_note_ref_type` FOREIGN KEY (`typeId`) REFERENCES `tb_note_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_note
-- ----------------------------
INSERT INTO `tb_note` VALUES (1, '上海一日游', '<p><strong>这是测试内容</strong></p>', 2, '2020-08-23 05:10:35', 121.395, 31.2536);
INSERT INTO `tb_note` VALUES (2, '第一次测试', '<p>测试</p>', 4, '2020-08-23 05:05:54', 121.395, 31.2536);
INSERT INTO `tb_note` VALUES (4, '第二次测试123', '<p style=\"text-align: center;\">测试123</p>', 1, '2020-09-23 05:05:50', 121.364, 31.0662);
INSERT INTO `tb_note` VALUES (5, '第三次测试', '<p>测试</p>', 1, '2020-09-23 05:05:47', 120.075, 30.2741);
INSERT INTO `tb_note` VALUES (6, '第四次测试', '<p>测试</p>', 1, '2020-10-23 05:02:21', 120.105, 30.1777);
INSERT INTO `tb_note` VALUES (7, '出行计划', '<p>出行计划</p>', 2, '2020-11-23 05:02:24', 119.989, 30.1587);
INSERT INTO `tb_note` VALUES (8, '测试001', '<p>测试001</p>', 4, '2020-11-23 05:02:26', 120.629, 28.0021);
INSERT INTO `tb_note` VALUES (9, '测试002', '<p>测试002123</p>', 4, '2020-12-23 05:02:28', 120.753, 27.8908);
INSERT INTO `tb_note` VALUES (11, '重复', '<p>重复123</p>', 3, '2020-12-25 02:22:49', 120.681, 28.0628);
INSERT INTO `tb_note` VALUES (12, '测试地址', '<p>测试</p>', 1, '2020-12-25 03:42:18', 121.488, 31.2492);
INSERT INTO `tb_note` VALUES (16, '测试京味府', '<p>2021-07-08测试京味府</p>', 1, '2021-07-08 18:04:08', 118.6, 24.9017);
INSERT INTO `tb_note` VALUES (17, '123', '<p style=\"text-align: center;\">123</p>', 3, '2021-07-09 09:52:17', 118.6, 24.9017);

-- ----------------------------
-- Table structure for tb_note_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_note_type`;
CREATE TABLE `tb_note_type`  (
  `typeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `typeName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名，在同一个用户下唯一',
  `userId` int(11) NULL DEFAULT NULL COMMENT '从属用户',
  PRIMARY KEY (`typeId`) USING BTREE,
  INDEX `fk_type_ref_user`(`userId`) USING BTREE,
  CONSTRAINT `fk_type_ref_user` FOREIGN KEY (`userId`) REFERENCES `tb_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_note_type
-- ----------------------------
INSERT INTO `tb_note_type` VALUES (1, '私人', 1);
INSERT INTO `tb_note_type` VALUES (2, '旅游', 1);
INSERT INTO `tb_note_type` VALUES (3, '美食', 1);
INSERT INTO `tb_note_type` VALUES (4, '测试', 1);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `uname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `upwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `head` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `mood` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '心情',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', '微信图片_20210705141243.jpg', 'Hello ');
INSERT INTO `tb_user` VALUES (2, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', 'zhangsan', 'jay.jpg', 'Hello');
INSERT INTO `tb_user` VALUES (3, 'xx', 'e10adc3949ba59abbe56e057f20f883e', 'xx', '404.jsp', '哈喽');
INSERT INTO `tb_user` VALUES (4, 'xx', 'e10adc3949ba59abbe56e057f20f883e', 'xx', '404.jsp', '哈喽');
INSERT INTO `tb_user` VALUES (5, 'xx', 'e10adc3949ba59abbe56e057f20f883e', 'xx', '404.jsp', '哈喽');
INSERT INTO `tb_user` VALUES (6, 'xx', 'e10adc3949ba59abbe56e057f20f883e', 'xx', '404.jsp', '哈喽');
INSERT INTO `tb_user` VALUES (7, 'xx', 'e10adc3949ba59abbe56e057f20f883e', 'xx', '404.jsp', '哈喽');

SET FOREIGN_KEY_CHECKS = 1;
