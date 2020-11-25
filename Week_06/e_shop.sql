/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : e_shop

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 25/11/2020 22:46:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods_info（商品信息表）
-- ----------------------------
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE `goods_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `type_id` int DEFAULT NULL COMMENT '商品类别id',
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品标题',
  `discount_price` decimal(10,8) DEFAULT NULL COMMENT '折后价',
  `normal_price` decimal(10,8) DEFAULT NULL COMMENT '原始价',
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简介',
  `weight` decimal(5,2) DEFAULT NULL COMMENT '重量',
  `size_description` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '尺寸描述',
  `stock` int DEFAULT NULL COMMENT '当前库存数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for goods_type（商品类别表）
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int DEFAULT NULL COMMENT '类别ID',
  `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类别名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for order_detail（订单详情表）
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单ID',
  `goods_id` int DEFAULT NULL COMMENT '商品ID',
  `goods_num` int DEFAULT NULL COMMENT '商品数量',
  `price` decimal(10,8) DEFAULT NULL COMMENT '购买时价格',
  `total_price` decimal(10,8) DEFAULT NULL COMMENT '总价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for order_main_info（订单主表）
-- ----------------------------
DROP TABLE IF EXISTS `order_main_info`;
CREATE TABLE `order_main_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单唯一ID',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `order_status` int DEFAULT NULL COMMENT '订单状态：0-提交未付钱、1-已付钱未发货、2-已发货、3-买家已拿货、4-买家已最终确认收货',
  `total_price` decimal(10,4) DEFAULT NULL COMMENT '商品总价（元）',
  `leave_message` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '买家留言',
  `transport_price` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `receiver_info_id` int DEFAULT NULL COMMENT '收件人详情ID',
  `buyer_id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for receiver_info（收货人信息表）
-- ----------------------------
DROP TABLE IF EXISTS `receiver_info`;
CREATE TABLE `receiver_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收件人',
  `phone_number` int DEFAULT NULL COMMENT '电话号码',
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收件地址',
  `postalcode` int DEFAULT NULL COMMENT '邮政编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for user_info（用户表）
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户唯一ID',
  `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户姓名',
  `account` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录账号',
  `phone_number` int DEFAULT NULL COMMENT '手机号码',
  `password` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录密码',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `birthday` date DEFAULT NULL COMMENT '出生年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
