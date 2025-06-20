/*
 Navicat Premium Dump SQL

 Source Server         : server01
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : 54.95.189.69:3306
 Source Schema         : lease

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 18/04/2025 14:31:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apartment_facility
-- ----------------------------
DROP TABLE IF EXISTS `apartment_facility`;
CREATE TABLE `apartment_facility`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apartment_id` bigint NULL DEFAULT NULL COMMENT '公寓id',
  `facility_id` bigint NULL DEFAULT NULL COMMENT '设施id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公寓&配套关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apartment_facility
-- ----------------------------
INSERT INTO `apartment_facility` VALUES (1, 1, 24, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (2, 1, 25, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (3, 1, 26, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (4, 1, 40, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (5, 1, 41, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (6, 1, 44, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (7, 1, 45, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (8, 1, 47, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (9, 1, 57, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (10, 1, 46, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_facility` VALUES (11, 1, 24, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (12, 1, 25, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (13, 1, 26, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (14, 1, 40, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (15, 1, 41, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (16, 1, 44, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (17, 1, 45, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (18, 1, 47, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (19, 1, 57, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_facility` VALUES (20, 1, 46, '2025-04-11 13:40:03', NULL, 0);

-- ----------------------------
-- Table structure for apartment_fee_value
-- ----------------------------
DROP TABLE IF EXISTS `apartment_fee_value`;
CREATE TABLE `apartment_fee_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apartment_id` bigint NULL DEFAULT NULL COMMENT '公寓id',
  `fee_value_id` bigint NULL DEFAULT NULL COMMENT '收费项value_id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公寓&杂费关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apartment_fee_value
-- ----------------------------
INSERT INTO `apartment_fee_value` VALUES (1, 1, 4, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_fee_value` VALUES (2, 1, 14, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_fee_value` VALUES (3, 1, 21, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_fee_value` VALUES (4, 1, 4, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_fee_value` VALUES (5, 1, 14, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_fee_value` VALUES (6, 1, 21, '2025-04-11 13:40:03', NULL, 0);

-- ----------------------------
-- Table structure for apartment_info
-- ----------------------------
DROP TABLE IF EXISTS `apartment_info`;
CREATE TABLE `apartment_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公寓id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公寓名称',
  `introduction_cn` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公寓介绍(中文)',
  `introduction_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公寓介绍(英文)',
  `introduction_ja` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公寓介绍(日文)',
  `district_id` bigint NULL DEFAULT NULL COMMENT '所处区域id',
  `district_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域名称',
  `city_id` bigint NULL DEFAULT NULL COMMENT '所处城市id',
  `city_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市名称',
  `province_id` bigint NULL DEFAULT NULL COMMENT '所处省份id',
  `province_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份名称',
  `address_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `latitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `longitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公寓前台电话',
  `is_release` tinyint NULL DEFAULT NULL COMMENT '是否发布（1:发布，0:未发布）',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公寓信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apartment_info
-- ----------------------------
INSERT INTO `apartment_info` VALUES (1, 'U＋東京足立', '东京 U+ 公寓是为年轻人提供的经济实惠的合租公寓。 每个房间都是私人的，共用厨房、浴室和洗衣房。 大楼提供免费无线网络连接、公共工作区、自习室和健身区。 步行 7 分钟即可到达最近的火车站，附近还有便利店、超市和餐馆。 这里还定期举办社区活动，深受上班族和学生的欢迎。 这里有安全的安保系统和 24 小时支持服务。', 'U+ Apartment Tokyo is an affordable shared house-style apartment for young people. Each room is private, with shared kitchen, bathroom, and laundry facilities. The building features free Wi-Fi, co-working space, study room, and fitness area. It is a 7-minute walk from the nearest station, with convenience stores, supermarkets, and restaurants nearby. Popular among working professionals and students, regular community events are held. Comes with secure security system and 24-hour support.', 'U＋アパートメント東京は、若者向けの手頃な価格のシェアハウス型アパートです。各部屋は個室で、キッチン、バスルーム、ランドリールームは共有です。館内には無料Wi-Fi、共同ワークスペース、自習室、フィットネスエリアを完備。最寄り駅から徒歩7分、周辺にはコンビニ、スーパー、飲食店が揃っています。社会人や学生に人気で、定期的なコミュニティイベントも開催されています。安心のセキュリティシステムと24時間サポート付き。', 3, '足立区', 1, '東京', 1, '東京都', '西新井栄町, 足立区, 東京都, 123-0843, 日本', '35.77513045', '139.7879780505055', '03-1234-5678', 1, '2025-04-11 13:21:54', '2025-04-11 13:40:03', 0);

-- ----------------------------
-- Table structure for apartment_label
-- ----------------------------
DROP TABLE IF EXISTS `apartment_label`;
CREATE TABLE `apartment_label`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apartment_id` bigint NULL DEFAULT NULL COMMENT '公寓id',
  `label_id` bigint NULL DEFAULT NULL COMMENT '标签id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公寓标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apartment_label
-- ----------------------------
INSERT INTO `apartment_label` VALUES (1, 1, 1, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_label` VALUES (2, 1, 2, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_label` VALUES (3, 1, 3, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_label` VALUES (4, 1, 4, '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `apartment_label` VALUES (5, 1, 1, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_label` VALUES (6, 1, 2, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_label` VALUES (7, 1, 3, '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `apartment_label` VALUES (8, 1, 4, '2025-04-11 13:40:03', NULL, 0);

-- ----------------------------
-- Table structure for attr_key
-- ----------------------------
DROP TABLE IF EXISTS `attr_key`;
CREATE TABLE `attr_key`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性key',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '房间基本属性表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of attr_key
-- ----------------------------
INSERT INTO `attr_key` VALUES (1, '面積', '2023-06-19 01:43:37', '2023-06-19 02:20:01', 1);
INSERT INTO `attr_key` VALUES (2, '向き', '2023-06-19 02:06:12', '2023-06-21 10:10:57', 1);
INSERT INTO `attr_key` VALUES (3, '間取り', '2023-06-19 02:20:53', '2023-06-19 02:31:14', 1);
INSERT INTO `attr_key` VALUES (4, '間取り', '2023-06-19 02:36:20', '2023-06-19 02:36:40', 1);
INSERT INTO `attr_key` VALUES (5, '間取り', '2023-06-21 10:09:18', NULL, 0);
INSERT INTO `attr_key` VALUES (6, '面積', '2023-07-22 11:55:41', '2023-07-22 11:58:31', 1);
INSERT INTO `attr_key` VALUES (7, '面積', '2023-07-22 11:58:50', NULL, 0);
INSERT INTO `attr_key` VALUES (8, '向き', '2023-08-10 15:21:50', '2023-08-10 15:22:04', 0);
INSERT INTO `attr_key` VALUES (9, '採光', '2023-08-10 18:46:45', NULL, 0);
INSERT INTO `attr_key` VALUES (10, 'トイレ', '2023-08-10 18:47:36', '2023-08-14 00:11:57', 0);

-- ----------------------------
-- Table structure for attr_value
-- ----------------------------
DROP TABLE IF EXISTS `attr_value`;
CREATE TABLE `attr_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性value',
  `attr_key_id` bigint NULL DEFAULT NULL COMMENT '对应的属性key_id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '房间基本属性值表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of attr_value
-- ----------------------------
INSERT INTO `attr_value` VALUES (1, '20㎡', 1, '2023-06-19 01:44:17', '2023-06-19 02:20:01', 1);
INSERT INTO `attr_value` VALUES (2, '25㎡', 1, '2023-06-19 01:44:23', '2023-06-19 02:20:01', 1);
INSERT INTO `attr_value` VALUES (3, '30㎡', 1, '2023-06-19 01:44:28', '2023-06-19 02:20:01', 1);
INSERT INTO `attr_value` VALUES (4, '南向き', 2, '2023-06-19 02:06:42', NULL, 1);
INSERT INTO `attr_value` VALUES (5, '北向き', 2, '2023-06-19 02:06:48', NULL, 1);
INSERT INTO `attr_value` VALUES (6, '西向き', 2, '2023-06-19 02:06:53', '2023-06-21 10:10:46', 1);
INSERT INTO `attr_value` VALUES (7, '東向き', 2, '2023-06-19 02:06:58', '2023-06-19 02:14:26', 1);
INSERT INTO `attr_value` VALUES (8, '1K', 5, '2023-06-21 10:09:50', NULL, 0);
INSERT INTO `attr_value` VALUES (9, '2K', 5, '2023-06-21 10:09:56', NULL, 0);
INSERT INTO `attr_value` VALUES (10, '3K', 5, '2023-06-21 10:10:02', NULL, 0);
INSERT INTO `attr_value` VALUES (11, '25㎡', 6, '2023-07-22 11:55:58', NULL, 1);
INSERT INTO `attr_value` VALUES (12, '30㎡', 6, '2023-07-22 11:56:05', NULL, 1);
INSERT INTO `attr_value` VALUES (13, '40㎡', 6, '2023-07-22 11:56:11', NULL, 1);
INSERT INTO `attr_value` VALUES (14, '20㎡', 7, '2023-07-22 11:56:19', NULL, 1);
INSERT INTO `attr_value` VALUES (15, '25㎡', 7, '2023-07-22 11:58:58', NULL, 0);
INSERT INTO `attr_value` VALUES (16, '35㎡', 7, '2023-07-22 11:59:04', NULL, 0);
INSERT INTO `attr_value` VALUES (17, '60㎡', 7, '2023-07-22 11:59:11', NULL, 0);
INSERT INTO `attr_value` VALUES (18, '80㎡', 7, '2023-07-22 11:59:31', NULL, 0);
INSERT INTO `attr_value` VALUES (19, '東', 8, '2023-08-10 15:22:10', NULL, 0);
INSERT INTO `attr_value` VALUES (20, '南', 8, '2023-08-10 15:22:20', NULL, 0);
INSERT INTO `attr_value` VALUES (21, '西', 8, '2023-08-10 15:22:27', NULL, 0);
INSERT INTO `attr_value` VALUES (22, '北', 8, '2023-08-10 15:22:34', NULL, 0);
INSERT INTO `attr_value` VALUES (23, '優れている', 9, '2023-08-10 18:46:55', NULL, 0);
INSERT INTO `attr_value` VALUES (24, '良い', 9, '2023-08-10 18:47:04', NULL, 0);
INSERT INTO `attr_value` VALUES (25, '普通', 9, '2023-08-10 18:47:14', NULL, 0);
INSERT INTO `attr_value` VALUES (26, 'なし', 9, '2023-08-10 18:47:19', NULL, 0);
INSERT INTO `attr_value` VALUES (27, '専用', 10, '2023-08-10 18:47:46', NULL, 0);
INSERT INTO `attr_value` VALUES (28, '共用', 10, '2023-08-10 18:47:51', NULL, 0);

-- ----------------------------
-- Table structure for blog_comment_info
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment_info`;
CREATE TABLE `blog_comment_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `blog_id` bigint NULL DEFAULT NULL COMMENT '博客ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父评论ID',
  `reply_user_id` bigint NULL DEFAULT NULL COMMENT '回复用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_blog_id`(`blog_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_comment_info
-- ----------------------------
INSERT INTO `blog_comment_info` VALUES (1, 1, 12, '私も同じく新卒で入居しました！交通の便の良さは本当に助かりますよね。特に終電を逃しても歩いて帰れるのが魅力です。学割についてもっと早く知りたかったです…', NULL, NULL, '2025-04-14 09:28:39', '2025-04-14 10:28:38', 0);
INSERT INTO `blog_comment_info` VALUES (2, 1, 10, '共用エリアのビリヤード台は使用料金かかりますか？また、混雑状況はどうですか？', NULL, NULL, '2025-04-14 10:15:41', '2025-04-14 11:15:40', 0);
INSERT INTO `blog_comment_info` VALUES (3, 1, 11, 'ビリヤード台は完全無料です！混雑状況は平日夜と週末に少し混みますが、予約制ではないので、空いていれば自由に使えます。個人的には平日の昼間や早朝がおすすめですよ。', 2, 10, '2025-04-14 10:16:17', '2025-04-14 11:16:16', 0);
INSERT INTO `blog_comment_info` VALUES (4, 2, 10, 'ペットのお世話サービスについてもっと詳しく知りたいです！出張が多い私にとって、猫の世話は大きな悩みなので…', NULL, NULL, '2025-04-14 15:03:14', '2025-04-14 16:03:14', 0);
INSERT INTO `blog_comment_info` VALUES (5, 3, 11, '実際に住んでみて3ヶ月になりますが、本当にその通りです！特にセキュリティ面が心配だった私にとって、24時間体制は大きな安心感です。おすすめです！', NULL, NULL, '2025-04-14 15:16:22', '2025-04-14 16:16:22', 0);

-- ----------------------------
-- Table structure for blog_follow
-- ----------------------------
DROP TABLE IF EXISTS `blog_follow`;
CREATE TABLE `blog_follow`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `follow_user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '关联的用户id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关注' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of blog_follow
-- ----------------------------
INSERT INTO `blog_follow` VALUES (1, 12, 11, '2025-04-14 09:28:44');
INSERT INTO `blog_follow` VALUES (2, 11, 12, '2025-04-14 10:48:05');
INSERT INTO `blog_follow` VALUES (4, 13, 11, '2025-04-14 15:02:12');
INSERT INTO `blog_follow` VALUES (5, 10, 13, '2025-04-14 15:03:02');
INSERT INTO `blog_follow` VALUES (6, 11, 10, '2025-04-14 16:03:36');
INSERT INTO `blog_follow` VALUES (7, 10, 11, '2025-04-16 08:16:45');

-- ----------------------------
-- Table structure for blog_graph_info
-- ----------------------------
DROP TABLE IF EXISTS `blog_graph_info`;
CREATE TABLE `blog_graph_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `blog_id` bigint NOT NULL COMMENT '博客ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_blog_id`(`blog_id` ASC) USING BTREE COMMENT '博客ID索引'
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客图片信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_graph_info54.95.189.69
-- ----------------------------
INSERT INTO `blog_graph_info` VALUES (1, 1, '博客3.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/996cbd4f-882a-4864-a020-7fe2b92e2200-博客3.jpg', '2025-04-14 09:26:00', NULL, 0);
INSERT INTO `blog_graph_info` VALUES (2, 2, '博客1-2.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/970fc4b1-18d3-4f38-ac78-73ad5ab625e7-博客1-2.jpg', '2025-04-14 15:01:30', NULL, 0);
INSERT INTO `blog_graph_info` VALUES (3, 2, '博客2.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/e0098226-8e7a-46ce-abee-0df2e77fb460-博客2.jpg', '2025-04-14 15:01:30', NULL, 0);
INSERT INTO `blog_graph_info` VALUES (4, 3, '博客1-1.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/ea392aaf-5dd4-4e3f-a6af-f4d34b1eb665-博客1-1.jpg', '2025-04-14 15:02:58', NULL, 0);

-- ----------------------------
-- Table structure for blog_info
-- ----------------------------
DROP TABLE IF EXISTS `blog_info`;
CREATE TABLE `blog_info`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '博客的文字描述',
  `liked` int UNSIGNED NULL DEFAULT 0 COMMENT '点赞数量',
  `comments` int UNSIGNED NULL DEFAULT NULL COMMENT '评论数量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of blog_info
-- ----------------------------
INSERT INTO `blog_info` VALUES (1, 11, 'U＋アパートメントに住んで365日目！', '気づけばU＋アパートメントに住んで1年が経ちました。1年前、私も一人で東京に飛び込んだ会社員で、土地勘もなく、住まい探しが最初の難関でした。幸運にも多くのアパートの中からU＋アパートメントを選びました。新卒の皆さんに1年間の感想をシェアします：\n\n基本情報：卒業後2年以内の学生認証で98%オフ、卒業2年以内の大学生は敷金1ヶ月・家賃1ヶ月で入居可能。\n\n1. 🏠入居が簡単、直接契約で24時間スタッフ常駐、安全面も保証され、敷金の心配もなく、仲介手数料もかからないので時間とコストの節約になります。\n2. 🚍交通の便が良く、建物の下には地下鉄の駅があります。周辺には多くのレストランやスーパーがあり、生活がとても便利です。\n3. ☀️採光が良く、16階（エレベーター付き）に住んでいて日当たり良好、洗濯物はベランダや屋上で干せます。基本的に朝洗濯して部屋で干しても夜にはほぼ乾いています。\n4. 🧰アパートの防音性は良く、住人もほとんど若い人で、コミュニケーションが取りやすいです。隣人が小猫🐱を飼っていて、時々出てくるのを見かけると撫でさせてもらっています。騒音があっても報告すればすぐに解決してくれます。\n5. 🏃アパートの18階には共用エリアがあり、ランニングマシン、ビリヤード台、自習室が無料で利用できます（私のような気まぐれにジムに行く人間にとって、年会費や月会費を払っても数回しか行かないジムよりも、アパート内のジムは本当に便利です！）\n6. 🛠️最後に、部屋の設備修理も無料で、アプリで修理依頼すると技術者がすぐに対応してくれ、基本的に1〜2時間で解決します。', 2, NULL, '2025-04-14 09:26:00', '2025-04-15 09:16:46', 0);
INSERT INTO `blog_info` VALUES (2, 13, '賃貸派必見！U＋アパートメントがなぜ第一選択になるのか', 'みなさん、お部屋探しで色々なアパートを比較して、たくさん下調べした結果、U＋アパートメントを選びました。体験は最高です👍 比較感想をシェアします！\n\n🏠物件レイアウト：周辺はとても賑やか、多様な間取りが揃っていて、一人暮らしからカップル、小さなファミリーまで誰でも合う部屋が見つかります。\n\n💰家賃コスパ：家賃はとても良心的で、契約方法も柔軟、敷金1ヶ月・家賃1ヶ月払いで入居できるのが嬉しい。\n\n🛋️設備：共有スペースにはジム、オーディオルーム、共同キッチンがあり、週末はゆっくりくつろげます。部屋にはスマート家電が完備され、ハイテク感満載。\n\n💁‍♀️サービス：ハウスキーパーのサービスは高品質、24時間セキュリティでとても安心、コミュニティイベントも充実。\n\n総合的な体験は本当に素晴らしい！お部屋を探している方はぜひ検討してみてください💗', 1, NULL, '2025-04-14 15:01:30', '2025-04-14 16:03:19', 0);
INSERT INTO `blog_info` VALUES (3, 10, '*賃貸派必見！U＋アパートメントがなぜ第一選択になるのか', 'みなさん、お部屋探しで色々なアパートを比較して、たくさん下調べした結果、U＋アパートメントを選びました。体験は最高です👍 比較感想をシェアします！\n\n🏠物件レイアウト：周辺はとても賑やか、多様な間取りが揃っていて、一人暮らしからカップル、小さなファミリーまで誰でも合う部屋が見つかります。\n\n💰家賃コスパ：家賃はとても良心的で、契約方法も柔軟、敷金1ヶ月・家賃1ヶ月払いで入居できるのが嬉しい。\n\n🛋️設備：共有スペースにはジム、オーディオルーム、共同キッチンがあり、週末はゆっくりくつろげます。部屋にはスマート家電が完備され、ハイテク感満載。\n\n💁‍♀️サービス：ハウスキーパーのサービスは高品質、24時間セキュリティでとても安心、コミュニティイベントも充実。\n\n総合的な体験は本当に素晴らしい！お部屋を探している方はぜひ検討してみてください💗', 1, NULL, '2025-04-14 15:02:58', '2025-04-15 09:16:33', 0);
INSERT INTO `blog_info` VALUES (4, 9, 'U＋アパートメントは想像以上に良かった！', '東京で4つのチェーンアパートに住んだ経験がありますが、U＋アパートメントが一番良かったです。\n\n良い点:\n1. ハウスキーパーが非常に頼りになる。何か修理依頼をすると、必ず2日以内に修理に来てくれます。どんな問題も積極的に解決してくれますし、特に店長さんの対応が素晴らしく、4つのチェーンアパートの中で最も迅速な対応でした。\n2. 月に2回の害虫駆除があり、3階に住んでいますが、一度もゴキブリを見たことがありません。\n3. ペットのお世話サービスがあり、動物病院に預ける必要がないのは本当に助かります。\n4. セキュリティがしっかりしていて、出入りにはカードが必要で、安全性が高いです。\n\n欠点:エアコンの電力消費が大きいこと。', 1, NULL, '2025-04-14 15:08:00', '2025-04-15 09:15:40', 0);

-- ----------------------------
-- Table structure for browsing_history
-- ----------------------------
DROP TABLE IF EXISTS `browsing_history`;
CREATE TABLE `browsing_history`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `room_id` bigint NULL DEFAULT NULL COMMENT '浏览房间id',
  `browse_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '浏览历史' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of browsing_history
-- ----------------------------
INSERT INTO `browsing_history` VALUES (1, 11, 2, '2025-04-15 10:19:13', '2025-04-11 13:56:21', '2025-04-14 08:09:10', 0);
INSERT INTO `browsing_history` VALUES (2, 11, 1, '2025-04-18 13:23:55', '2025-04-11 13:58:08', '2025-04-11 13:58:17', 0);

-- ----------------------------
-- Table structure for city_info
-- ----------------------------
DROP TABLE IF EXISTS `city_info`;
CREATE TABLE `city_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '城市id',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市名称',
  `province_id` int NULL DEFAULT NULL COMMENT '所属省份id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of city_info
-- ----------------------------
INSERT INTO `city_info` VALUES (1, '東京', 1, '2025-04-10 13:43:49', '2025-04-10 13:43:49', 0);
INSERT INTO `city_info` VALUES (2, '名古屋', 2, '2025-04-10 13:43:49', '2025-04-10 13:43:49', 0);
INSERT INTO `city_info` VALUES (3, '大阪', 3, '2025-04-10 13:43:49', '2025-04-10 13:43:49', 0);
INSERT INTO `city_info` VALUES (4, '京都', 4, '2025-04-10 13:43:49', '2025-04-10 13:43:49', 0);

-- ----------------------------
-- Table structure for district_info
-- ----------------------------
DROP TABLE IF EXISTS `district_info`;
CREATE TABLE `district_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '区域id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域名称',
  `city_id` int NULL DEFAULT NULL COMMENT '所属城市id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of district_info
-- ----------------------------
INSERT INTO `district_info` VALUES (1, '新宿区', 1, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (2, '渋谷区', 1, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (3, '足立区', 1, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (4, '千代田区', 1, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (5, '中央区', 1, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (6, '中区', 2, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (7, '東区', 2, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (8, '北区', 2, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (9, '昭和区', 2, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (10, '瑞穂区', 2, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (11, '北区', 3, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (12, '中央区', 3, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (13, '天王寺区', 3, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (14, '浪速区', 3, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (15, '淀川区', 3, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (16, '東山区', 4, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (17, '中京区', 4, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (18, '上京区', 4, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (19, '下京区', 4, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);
INSERT INTO `district_info` VALUES (20, '右京区', 4, '2025-04-10 13:44:09', '2025-04-10 13:44:09', 0);

-- ----------------------------
-- Table structure for facility_info
-- ----------------------------
DROP TABLE IF EXISTS `facility_info`;
CREATE TABLE `facility_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增逐渐',
  `type` tinyint NULL DEFAULT NULL COMMENT '类型（1:公寓图片,2:房间图片）',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '配套信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of facility_info
-- ----------------------------
INSERT INTO `facility_info` VALUES (24, 1, 'ジム', 'gym', '2023-06-17 06:21:24', '2023-07-15 09:01:38', 0);
INSERT INTO `facility_info` VALUES (25, 1, '駐車場', 'parking', '2023-06-17 06:21:42', '2023-07-18 11:22:21', 0);
INSERT INTO `facility_info` VALUES (26, 1, 'エレベーター', 'elevator', '2023-06-17 06:21:47', '2023-07-18 11:22:33', 0);
INSERT INTO `facility_info` VALUES (27, 1, 'カフェ', 'cafe', '2023-06-17 06:21:56', '2023-06-21 10:01:01', 1);
INSERT INTO `facility_info` VALUES (28, 2, 'エアコン', 'aircondition', '2023-06-17 06:22:06', '2023-07-18 11:22:44', 0);
INSERT INTO `facility_info` VALUES (29, 2, '洗濯機', 'washer', '2023-06-17 06:22:11', '2023-07-18 11:22:51', 0);
INSERT INTO `facility_info` VALUES (30, 2, '冷蔵庫', 'refrigerator', '2023-06-17 06:22:15', '2023-07-18 11:23:01', 0);
INSERT INTO `facility_info` VALUES (38, 2, 'ベッド', NULL, '2023-06-19 06:11:22', '2023-06-19 14:15:03', 1);
INSERT INTO `facility_info` VALUES (39, 1, '213', '24時間警備', '2023-07-18 14:12:24', '2023-07-18 14:12:29', 1);
INSERT INTO `facility_info` VALUES (40, 1, 'ビリヤード', 'billiards', '2023-07-22 11:52:46', NULL, 0);
INSERT INTO `facility_info` VALUES (41, 1, 'セキュリティ', 'security', '2023-08-01 09:01:31', NULL, 0);
INSERT INTO `facility_info` VALUES (42, 1, 'コミュニティ活動', 'community', '2023-08-10 18:39:41', '2023-08-10 18:40:04', 0);
INSERT INTO `facility_info` VALUES (43, 1, 'ブックカフェ', 'bookcafe', '2023-08-10 18:40:18', NULL, 0);
INSERT INTO `facility_info` VALUES (44, 1, '休憩室', 'restroom', '2023-08-10 18:40:30', NULL, 0);
INSERT INTO `facility_info` VALUES (45, 1, 'コンビニ', 'store', '2023-08-10 18:40:41', NULL, 0);
INSERT INTO `facility_info` VALUES (46, 1, 'レクリエーションエリア', 'recreation', '2023-08-10 18:40:51', NULL, 0);
INSERT INTO `facility_info` VALUES (47, 1, '監視カメラ', 'camera', '2023-08-10 18:41:05', NULL, 0);
INSERT INTO `facility_info` VALUES (48, 2, 'デスク', 'desk', '2023-08-10 18:41:27', NULL, 0);
INSERT INTO `facility_info` VALUES (49, 2, 'Wi-Fi', 'wifi', '2023-08-10 18:42:24', NULL, 0);
INSERT INTO `facility_info` VALUES (50, 2, 'ベッド', 'bed', '2023-08-10 18:42:37', NULL, 0);
INSERT INTO `facility_info` VALUES (51, 2, 'ソファ', 'sofa', '2023-08-10 18:42:47', NULL, 0);
INSERT INTO `facility_info` VALUES (52, 2, '電子レンジ', 'microwave', '2023-08-10 18:43:02', NULL, 0);
INSERT INTO `facility_info` VALUES (53, 2, '換気扇', 'ventilator', '2023-08-10 18:43:23', NULL, 0);
INSERT INTO `facility_info` VALUES (54, 2, '給湯器', 'waterheater', '2023-08-10 18:43:49', NULL, 0);
INSERT INTO `facility_info` VALUES (55, 2, 'クローゼット', 'closet', '2023-08-10 18:44:07', NULL, 0);
INSERT INTO `facility_info` VALUES (56, 2, 'テレビ', 'tv', '2023-08-10 18:44:23', NULL, 0);
INSERT INTO `facility_info` VALUES (57, 1, 'スマートロック', 'smartlock', '2023-08-10 18:44:49', NULL, 0);

-- ----------------------------
-- Table structure for fee_key
-- ----------------------------
DROP TABLE IF EXISTS `fee_key`;
CREATE TABLE `fee_key`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用key',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '杂项费用表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of fee_key
-- ----------------------------
INSERT INTO `fee_key` VALUES (1, '駐車場料金', '2023-06-19 03:03:55', NULL, 0);
INSERT INTO `fee_key` VALUES (2, 'インターネット料金', '2023-06-19 03:06:49', NULL, 0);
INSERT INTO `fee_key` VALUES (3, '光熱費', '2023-06-21 10:03:36', '2023-06-21 10:07:59', 1);
INSERT INTO `fee_key` VALUES (4, '光熱費', '2023-06-21 10:05:19', '2023-06-21 10:08:37', 1);
INSERT INTO `fee_key` VALUES (5, '暖房費', '2023-07-18 15:55:13', '2023-07-18 16:32:17', 1);
INSERT INTO `fee_key` VALUES (6, '電気代', '2023-08-10 18:45:12', '2023-08-10 18:49:16', 0);
INSERT INTO `fee_key` VALUES (7, '水道代', '2023-08-10 18:50:00', '2023-08-10 18:50:17', 0);
INSERT INTO `fee_key` VALUES (8, '暖房費', '2023-08-10 18:51:08', NULL, 0);

-- ----------------------------
-- Table structure for fee_value
-- ----------------------------
DROP TABLE IF EXISTS `fee_value`;
CREATE TABLE `fee_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用value',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费单位',
  `fee_key_id` bigint NULL DEFAULT NULL COMMENT '费用所对的fee_key',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '杂项费用值表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of fee_value
-- ----------------------------
INSERT INTO `fee_value` VALUES (1, '4200', '円/月', 1, '2023-06-19 03:04:35', NULL, 0);
INSERT INTO `fee_value` VALUES (2, '6300', '円/月', 1, '2023-06-19 03:04:40', NULL, 0);
INSERT INTO `fee_value` VALUES (3, '8400', '円/月', 1, '2023-06-19 03:04:44', NULL, 0);
INSERT INTO `fee_value` VALUES (4, '1050', '円/月', 2, '2023-06-19 03:07:00', NULL, 0);
INSERT INTO `fee_value` VALUES (5, '1260', '円/月', 2, '2023-06-19 03:07:07', NULL, 0);
INSERT INTO `fee_value` VALUES (6, '21000', '円/年', 2, '2023-06-19 03:07:20', NULL, 0);
INSERT INTO `fee_value` VALUES (7, '10500', '円/年', 2, '2023-06-19 03:07:27', NULL, 0);
INSERT INTO `fee_value` VALUES (8, '2100', '円/月', 4, '2023-06-21 10:04:53', '2023-06-21 10:08:22', 1);
INSERT INTO `fee_value` VALUES (9, '4200', '円/月', 4, '2023-06-21 10:04:55', NULL, 1);
INSERT INTO `fee_value` VALUES (10, '8400', '円/月', 4, '2023-06-21 10:05:00', NULL, 1);
INSERT INTO `fee_value` VALUES (11, '31500', '円/年', 5, '2023-07-18 15:55:37', '2023-07-18 15:56:03', 1);
INSERT INTO `fee_value` VALUES (12, '42000', '円/年', 5, '2023-07-18 15:55:53', NULL, 1);
INSERT INTO `fee_value` VALUES (13, '31.5', '円/kWh', 6, '2023-08-10 18:49:01', '2023-08-10 18:49:25', 0);
INSERT INTO `fee_value` VALUES (14, '21', '円/kWh', 6, '2023-08-10 18:49:34', NULL, 0);
INSERT INTO `fee_value` VALUES (15, '10.5', '円/kWh', 6, '2023-08-10 18:49:43', NULL, 0);
INSERT INTO `fee_value` VALUES (16, '210', '円/m³', 7, '2023-08-10 18:50:35', NULL, 0);
INSERT INTO `fee_value` VALUES (17, '189', '円/m³', 7, '2023-08-10 18:50:41', NULL, 0);
INSERT INTO `fee_value` VALUES (18, '168', '円/m³', 7, '2023-08-10 18:50:47', NULL, 0);
INSERT INTO `fee_value` VALUES (19, '147', '円/m³', 7, '2023-08-10 18:50:53', NULL, 0);
INSERT INTO `fee_value` VALUES (20, '21000', '円/年', 8, '2023-08-10 18:51:23', NULL, 0);
INSERT INTO `fee_value` VALUES (21, '25200', '円/年', 8, '2023-08-10 18:51:38', NULL, 0);
INSERT INTO `fee_value` VALUES (22, '31500', '円/年', 8, '2023-08-10 18:51:46', NULL, 0);

-- ----------------------------
-- Table structure for file_management
-- ----------------------------
DROP TABLE IF EXISTS `file_management`;
CREATE TABLE `file_management`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `original_filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原始文件名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '存储路径',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问URL',
  `status` tinyint NULL DEFAULT 0 COMMENT '文件状态：0-临时，1-确认使用',
  `business_type` int NULL DEFAULT NULL COMMENT '业务类型 1-公寓 2-房间 3-博客 4-用户头像',
  `business_id` bigint NULL DEFAULT NULL COMMENT '关联的业务ID',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_management
-- ----------------------------
INSERT INTO `file_management` VALUES (1, '公寓-外观.jpg', 'temp/20250411/ca162990-729e-4855-ba68-13bb558c7df0-公寓-外观.jpg', 'http://54.95.189.69:9000/lease/temp/20250411/ca162990-729e-4855-ba68-13bb558c7df0-公寓-外观.jpg', 0, NULL, NULL, '2025-04-11 13:17:42', NULL, 0);
INSERT INTO `file_management` VALUES (2, '公寓-健身房.png', 'permanent/20250411/76182070-a217-478e-bab7-21493db737c9-公寓-健身房.png', 'http://54.95.189.69:9000/lease/permanent/20250411/76182070-a217-478e-bab7-21493db737c9-公寓-健身房.png', 1, 1, 1, '2025-04-11 13:17:59', '2025-04-11 14:21:54', 0);
INSERT INTO `file_management` VALUES (3, '公寓-自习室.png', 'permanent/20250411/c0297f06-b34f-4008-8116-8594207239fb-公寓-自习室.png', 'http://54.95.189.69:9000/lease/permanent/20250411/c0297f06-b34f-4008-8116-8594207239fb-公寓-自习室.png', 1, 1, 1, '2025-04-11 13:18:07', '2025-04-11 14:21:54', 0);
INSERT INTO `file_management` VALUES (4, '公寓-厨房.png', 'permanent/20250411/44f0e265-0463-41c7-ad48-15f1bb1a5fad-公寓-厨房.png', 'http://54.95.189.69:9000/lease/permanent/20250411/44f0e265-0463-41c7-ad48-15f1bb1a5fad-公寓-厨房.png', 1, 1, 1, '2025-04-11 13:18:10', '2025-04-11 14:21:54', 0);
INSERT INTO `file_management` VALUES (5, '公寓-娱乐场所.png', 'permanent/20250411/f8f013ce-470c-4014-96c7-8e0dfd600ea1-公寓-娱乐场所.png', 'http://54.95.189.69:9000/lease/permanent/20250411/f8f013ce-470c-4014-96c7-8e0dfd600ea1-公寓-娱乐场所.png', 1, 1, 1, '2025-04-11 13:18:16', '2025-04-11 14:21:54', 0);
INSERT INTO `file_management` VALUES (6, '房间1-客厅.jpg', 'temp/20250411/bfa6d44f-d6ec-4429-ac26-8c409281e856-房间1-客厅.jpg', 'http://54.95.189.69:9000/lease/temp/20250411/bfa6d44f-d6ec-4429-ac26-8c409281e856-房间1-客厅.jpg', 0, NULL, NULL, '2025-04-11 13:52:27', NULL, 0);
INSERT INTO `file_management` VALUES (7, '房间1.png', 'permanent/20250411/3861893b-560d-4578-b5cf-5db2e85ab6be-房间1.png', 'http://54.95.189.69:9000/lease/permanent/20250411/3861893b-560d-4578-b5cf-5db2e85ab6be-房间1.png', 1, 2, 1, '2025-04-11 13:52:43', '2025-04-11 14:52:48', 0);
INSERT INTO `file_management` VALUES (8, '房间1-1.png', 'permanent/20250411/f34fa09a-175c-48e6-baff-efb108d10160-房间1-1.png', 'http://54.95.189.69:9000/lease/permanent/20250411/f34fa09a-175c-48e6-baff-efb108d10160-房间1-1.png', 1, 2, 1, '2025-04-11 13:52:46', '2025-04-11 14:52:48', 0);
INSERT INTO `file_management` VALUES (9, '房间2.png', 'permanent/20250411/9d550404-29b6-4485-a075-cc076241476d-房间2.png', 'http://54.95.189.69:9000/lease/permanent/20250411/9d550404-29b6-4485-a075-cc076241476d-房间2.png', 1, 2, 2, '2025-04-11 13:53:58', '2025-04-11 14:54:03', 0);
INSERT INTO `file_management` VALUES (10, '房间3.png', 'permanent/20250411/daf1b496-b65b-403d-a9e8-28bbaf745fd8-房间3.png', 'http://54.95.189.69:9000/lease/permanent/20250411/daf1b496-b65b-403d-a9e8-28bbaf745fd8-房间3.png', 1, 2, 2, '2025-04-11 13:54:01', '2025-04-11 14:54:03', 0);
INSERT INTO `file_management` VALUES (11, '用户3.jpg', 'permanent/20250411/b1f980d0-516f-4a5c-9776-301e2f3988f0-用户3.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250411/b1f980d0-516f-4a5c-9776-301e2f3988f0-用户3.jpg', 1, 4, 11, '2025-04-11 13:57:56', '2025-04-11 14:57:58', 0);
INSERT INTO `file_management` VALUES (12, '博客3.jpg', 'permanent/20250414/996cbd4f-882a-4864-a020-7fe2b92e2200-博客3.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/996cbd4f-882a-4864-a020-7fe2b92e2200-博客3.jpg', 1, 3, 1, '2025-04-14 09:25:59', '2025-04-14 10:26:00', 0);
INSERT INTO `file_management` VALUES (13, '用户5.jpg', 'permanent/20250414/9aca4523-527b-45a5-85fd-ef81f8068918-用户5.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/9aca4523-527b-45a5-85fd-ef81f8068918-用户5.jpg', 1, 4, 12, '2025-04-14 09:28:58', '2025-04-14 10:29:05', 0);
INSERT INTO `file_management` VALUES (14, '用户4.jpg', 'permanent/20250414/cb898caf-538f-400a-9d21-1227f831d169-用户4.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/cb898caf-538f-400a-9d21-1227f831d169-用户4.jpg', 1, 4, 10, '2025-04-14 10:15:18', '2025-04-14 11:15:26', 0);
INSERT INTO `file_management` VALUES (15, '用户2.jpg', 'permanent/20250414/ee4e31ef-8cdf-40d9-a97e-8de9a72cbc2c-用户2.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/ee4e31ef-8cdf-40d9-a97e-8de9a72cbc2c-用户2.jpg', 1, 4, 13, '2025-04-14 15:00:18', '2025-04-14 16:00:19', 0);
INSERT INTO `file_management` VALUES (16, '博客1-2.jpg', 'permanent/20250414/970fc4b1-18d3-4f38-ac78-73ad5ab625e7-博客1-2.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/970fc4b1-18d3-4f38-ac78-73ad5ab625e7-博客1-2.jpg', 1, 3, 2, '2025-04-14 15:01:22', '2025-04-14 16:01:30', 0);
INSERT INTO `file_management` VALUES (17, '博客2.jpg', 'permanent/20250414/e0098226-8e7a-46ce-abee-0df2e77fb460-博客2.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/e0098226-8e7a-46ce-abee-0df2e77fb460-博客2.jpg', 1, 3, 2, '2025-04-14 15:01:22', '2025-04-14 16:01:30', 0);
INSERT INTO `file_management` VALUES (18, '博客1-1.jpg', 'permanent/20250414/ea392aaf-5dd4-4e3f-a6af-f4d34b1eb665-博客1-1.jpg', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/ea392aaf-5dd4-4e3f-a6af-f4d34b1eb665-博客1-1.jpg', 1, 3, 3, '2025-04-14 15:02:43', '2025-04-14 16:02:57', 0);
INSERT INTO `file_management` VALUES (19, '用户1.png', 'permanent/20250414/09b914ce-9182-4cc6-b986-ad39ad34830e-用户1.png', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/09b914ce-9182-4cc6-b986-ad39ad34830e-用户1.png', 1, 4, 9, '2025-04-14 15:07:37', '2025-04-14 16:07:38', 0);

-- ----------------------------
-- Table structure for graph_info
-- ----------------------------
DROP TABLE IF EXISTS `graph_info`;
CREATE TABLE `graph_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片名称',
  `item_type` tinyint NULL DEFAULT NULL COMMENT '图片所属对象类型（1:apartment,2:room）',
  `item_id` bigint NULL DEFAULT NULL COMMENT '图片所有对象id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图片信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of graph_info
-- ----------------------------
INSERT INTO `graph_info` VALUES (1, '公寓-健身房.png', 1, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/76182070-a217-478e-bab7-21493db737c9-公寓-健身房.png', '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `graph_info` VALUES (2, '公寓-自习室.png', 1, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/c0297f06-b34f-4008-8116-8594207239fb-公寓-自习室.png', '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `graph_info` VALUES (3, '公寓-厨房.png', 1, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/44f0e265-0463-41c7-ad48-15f1bb1a5fad-公寓-厨房.png', '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `graph_info` VALUES (4, '公寓-娱乐场所.png', 1, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/f8f013ce-470c-4014-96c7-8e0dfd600ea1-公寓-娱乐场所.png', '2025-04-11 13:21:54', NULL, 1);
INSERT INTO `graph_info` VALUES (5, '公寓-健身房.png', 1, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/76182070-a217-478e-bab7-21493db737c9-公寓-健身房.png', '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `graph_info` VALUES (6, '公寓-自习室.png', 1, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/c0297f06-b34f-4008-8116-8594207239fb-公寓-自习室.png', '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `graph_info` VALUES (7, '公寓-厨房.png', 1, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/44f0e265-0463-41c7-ad48-15f1bb1a5fad-公寓-厨房.png', '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `graph_info` VALUES (8, '公寓-娱乐场所.png', 1, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/f8f013ce-470c-4014-96c7-8e0dfd600ea1-公寓-娱乐场所.png', '2025-04-11 13:40:03', NULL, 0);
INSERT INTO `graph_info` VALUES (9, '房间1.png', 2, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/3861893b-560d-4578-b5cf-5db2e85ab6be-房间1.png', '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `graph_info` VALUES (10, '房间1-1.png', 2, 1, 'http://54.95.189.69:9000/lease/permanent/20250411/f34fa09a-175c-48e6-baff-efb108d10160-房间1-1.png', '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `graph_info` VALUES (11, '房间2.png', 2, 2, 'http://54.95.189.69:9000/lease/permanent/20250411/9d550404-29b6-4485-a075-cc076241476d-房间2.png', '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `graph_info` VALUES (12, '房间3.png', 2, 2, 'http://54.95.189.69:9000/lease/permanent/20250411/daf1b496-b65b-403d-a9e8-28bbaf745fd8-房间3.png', '2025-04-11 13:54:02', NULL, 0);

-- ----------------------------
-- Table structure for label_info
-- ----------------------------
DROP TABLE IF EXISTS `label_info`;
CREATE TABLE `label_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` tinyint NULL DEFAULT NULL COMMENT '类型（1:公寓标签,2:房间标签）',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of label_info
-- ----------------------------
INSERT INTO `label_info` VALUES (1, 1, '地下鉄近く', '2023-06-19 00:49:17', NULL, 0);
INSERT INTO `label_info` VALUES (2, 1, 'バス停近く', '2023-06-19 00:49:23', NULL, 0);
INSERT INTO `label_info` VALUES (3, 1, 'エレベーター付き', '2023-06-19 00:49:28', NULL, 0);
INSERT INTO `label_info` VALUES (4, 1, '駐車場あり', '2023-06-19 00:49:38', '2023-06-21 09:43:51', 0);
INSERT INTO `label_info` VALUES (5, 2, '南向き', '2023-06-19 00:50:24', NULL, 0);
INSERT INTO `label_info` VALUES (6, 2, '北向き', '2023-06-19 00:50:29', NULL, 0);
INSERT INTO `label_info` VALUES (7, 2, '東向き', '2023-06-19 00:50:34', NULL, 0);
INSERT INTO `label_info` VALUES (10, 2, '西向き', '2023-07-22 12:01:02', NULL, 0);
INSERT INTO `label_info` VALUES (15, 2, '専用トイレ', '2023-08-11 08:40:51', NULL, 0);
INSERT INTO `label_info` VALUES (16, 2, 'バルコニー', '2023-08-11 08:40:58', NULL, 0);

-- ----------------------------
-- Table structure for lease_agreement
-- ----------------------------
DROP TABLE IF EXISTS `lease_agreement`;
CREATE TABLE `lease_agreement`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '租约id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '承租人手机号码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '承租人姓名',
  `identification_number` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '承租人身份证号码',
  `apartment_id` bigint NULL DEFAULT NULL COMMENT '签约公寓id',
  `room_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '签约房间id',
  `lease_start_date` date NULL DEFAULT NULL COMMENT '租约开始日期',
  `lease_end_date` date NULL DEFAULT NULL COMMENT '租约结束日期',
  `lease_term_id` bigint NULL DEFAULT NULL COMMENT '租期id',
  `rent` decimal(16, 2) NULL DEFAULT NULL COMMENT '租金（元/月）',
  `deposit` decimal(16, 2) NULL DEFAULT NULL COMMENT '押金（元）',
  `payment_type_id` bigint NULL DEFAULT NULL COMMENT '支付类型id\r\n',
  `status` tinyint NULL DEFAULT NULL COMMENT '租约状态（1:签约待确认，2:已签约，3:已取消，4:已到期，5:退租待确认，6:已退租，7:续约待确认）',
  `source_type` tinyint NULL DEFAULT NULL COMMENT '租约来源（1:新签，2:续约）',
  `additional_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租约信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lease_agreement
-- ----------------------------
INSERT INTO `lease_agreement` VALUES (1, 11, '13112345678', 'User131', '', 1, 1, '2025-04-29', '2026-04-28', 6, 50000.00, 50000.00, 6, 1, 1, 'User131-test1', '2025-04-11 14:13:34', '2025-04-11 15:48:08', 0);

-- ----------------------------
-- Table structure for lease_term
-- ----------------------------
DROP TABLE IF EXISTS `lease_term`;
CREATE TABLE `lease_term`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `month_count` int NULL DEFAULT NULL COMMENT '租期',
  `unit` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租期单位',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租期' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lease_term
-- ----------------------------
INSERT INTO `lease_term` VALUES (1, 1, 'ヶ月', '2023-06-30 10:58:09', NULL, 0);
INSERT INTO `lease_term` VALUES (2, 2, 'ヶ月', '2023-06-30 10:58:12', '2023-06-30 11:00:02', 1);
INSERT INTO `lease_term` VALUES (3, 3, 'ヶ月', '2023-06-30 10:58:17', NULL, 0);
INSERT INTO `lease_term` VALUES (4, 6, 'ヶ月', '2023-06-30 10:58:21', NULL, 0);
INSERT INTO `lease_term` VALUES (5, 12, 'ヶ月', '2023-06-30 10:58:23', '2023-08-01 18:00:55', 1);
INSERT INTO `lease_term` VALUES (6, 12, 'ヶ月', '2023-08-01 18:01:20', NULL, 0);

-- ----------------------------
-- Table structure for payment_type
-- ----------------------------
DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE `payment_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式名称',
  `pay_month_count` int NULL DEFAULT NULL COMMENT '每次支付租期数',
  `additional_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付费说明',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付方式表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of payment_type
-- ----------------------------
INSERT INTO `payment_type` VALUES (6, '月払い', 1, '敷金1ヶ月、前払い1ヶ月', '2023-06-21 11:26:08', '2023-06-21 11:27:14', 0);
INSERT INTO `payment_type` VALUES (7, '3ヶ月払い', 3, '敷金1ヶ月、前払い3ヶ月', '2023-06-21 11:26:21', '2023-06-21 11:27:33', 0);
INSERT INTO `payment_type` VALUES (8, '6ヶ月払い', 6, '敷金1ヶ月、前払い6ヶ月', '2023-06-21 11:26:35', NULL, 0);
INSERT INTO `payment_type` VALUES (9, '年払い', 12, '敷金1ヶ月、前払い12ヶ月', NULL, NULL, 1);
INSERT INTO `payment_type` VALUES (10, '年払い', 24, '敷金1ヶ月、前払い12ヶ月', '2023-08-01 23:51:40', NULL, 0);
INSERT INTO `payment_type` VALUES (11, '年払い', 24, '敷金1ヶ月、前払い12ヶ月', '2023-08-01 23:52:15', '2023-08-10 14:36:55', 1);

-- ----------------------------
-- Table structure for province_info
-- ----------------------------
DROP TABLE IF EXISTS `province_info`;
CREATE TABLE `province_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '省份id',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of province_info
-- ----------------------------
INSERT INTO `province_info` VALUES (1, '東京都', '2025-04-10 13:43:40', '2025-04-10 13:43:40', 0);
INSERT INTO `province_info` VALUES (2, '愛知県', '2025-04-10 13:43:40', '2025-04-10 13:43:40', 0);
INSERT INTO `province_info` VALUES (3, '大阪府', '2025-04-10 13:43:40', '2025-04-10 13:43:40', 0);
INSERT INTO `province_info` VALUES (4, '京都府', '2025-04-10 13:43:40', '2025-04-10 13:43:40', 0);

-- ----------------------------
-- Table structure for room_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `room_attr_value`;
CREATE TABLE `room_attr_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NULL DEFAULT NULL COMMENT '房间id',
  `attr_value_id` bigint NULL DEFAULT NULL COMMENT '属性值id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '房间&基本属性值关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of room_attr_value
-- ----------------------------
INSERT INTO `room_attr_value` VALUES (1, 1, 9, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_attr_value` VALUES (2, 1, 17, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_attr_value` VALUES (3, 1, 21, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_attr_value` VALUES (4, 1, 23, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_attr_value` VALUES (5, 1, 27, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_attr_value` VALUES (6, 2, 27, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_attr_value` VALUES (7, 2, 23, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_attr_value` VALUES (8, 2, 20, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_attr_value` VALUES (9, 2, 17, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_attr_value` VALUES (10, 2, 9, '2025-04-11 13:54:02', NULL, 0);

-- ----------------------------
-- Table structure for room_facility
-- ----------------------------
DROP TABLE IF EXISTS `room_facility`;
CREATE TABLE `room_facility`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NULL DEFAULT NULL COMMENT '房间id',
  `facility_id` bigint NULL DEFAULT NULL COMMENT '房间设施id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '房间&配套关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of room_facility
-- ----------------------------
INSERT INTO `room_facility` VALUES (1, 1, 28, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_facility` VALUES (2, 1, 29, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_facility` VALUES (3, 1, 30, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_facility` VALUES (4, 1, 48, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_facility` VALUES (5, 1, 49, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_facility` VALUES (6, 1, 50, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_facility` VALUES (7, 2, 28, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_facility` VALUES (8, 2, 29, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_facility` VALUES (9, 2, 30, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_facility` VALUES (10, 2, 48, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_facility` VALUES (11, 2, 49, '2025-04-11 13:54:02', NULL, 0);
INSERT INTO `room_facility` VALUES (12, 2, 50, '2025-04-11 13:54:02', NULL, 0);

-- ----------------------------
-- Table structure for room_info
-- ----------------------------
DROP TABLE IF EXISTS `room_info`;
CREATE TABLE `room_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '房间id',
  `room_number` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房间号',
  `rent` decimal(16, 2) NULL DEFAULT NULL COMMENT '租金（元/月）',
  `apartment_id` bigint NULL DEFAULT NULL COMMENT '所属公寓id',
  `is_release` tinyint NULL DEFAULT NULL COMMENT '是否发布',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '房间信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of room_info
-- ----------------------------
INSERT INTO `room_info` VALUES (1, '101', 50000.00, 1, 1, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_info` VALUES (2, '102', 60000.00, 1, 1, '2025-04-11 13:54:02', NULL, 0);

-- ----------------------------
-- Table structure for room_label
-- ----------------------------
DROP TABLE IF EXISTS `room_label`;
CREATE TABLE `room_label`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NULL DEFAULT NULL COMMENT '房间id',
  `label_id` bigint NULL DEFAULT NULL COMMENT '标签id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '房间&标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of room_label
-- ----------------------------
INSERT INTO `room_label` VALUES (1, 1, 16, '2025-04-11 13:52:47', NULL, 0);

-- ----------------------------
-- Table structure for room_lease_term
-- ----------------------------
DROP TABLE IF EXISTS `room_lease_term`;
CREATE TABLE `room_lease_term`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NULL DEFAULT NULL COMMENT '房间id',
  `lease_term_id` bigint NULL DEFAULT NULL COMMENT '租期id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '房间租期管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of room_lease_term
-- ----------------------------
INSERT INTO `room_lease_term` VALUES (1, 1, 6, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_lease_term` VALUES (2, 2, 6, '2025-04-11 13:54:02', NULL, 0);

-- ----------------------------
-- Table structure for room_payment_type
-- ----------------------------
DROP TABLE IF EXISTS `room_payment_type`;
CREATE TABLE `room_payment_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NULL DEFAULT NULL COMMENT '房间id',
  `payment_type_id` bigint NULL DEFAULT NULL COMMENT '支付类型id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '房间&支付方式关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of room_payment_type
-- ----------------------------
INSERT INTO `room_payment_type` VALUES (1, 1, 6, '2025-04-11 13:52:47', NULL, 0);
INSERT INTO `room_payment_type` VALUES (2, 2, 6, '2025-04-11 13:54:02', NULL, 0);

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS `system_post`;
CREATE TABLE `system_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '岗位编码',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '岗位名称',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态（1正常 0停用）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记（0:可用 1:已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_post
-- ----------------------------
INSERT INTO `system_post` VALUES (1, 'regional manager', '区域经理', '区域经理', 1, '2023-08-10 09:01:56', '2023-08-10 17:01:57', 0);
INSERT INTO `system_post` VALUES (2, 'dsz', '董事长', '1', 1, '2023-08-10 09:02:46', '2023-08-10 17:02:47', 1);
INSERT INTO `system_post` VALUES (6, 'general manager', '总经理', '总经理', 1, '2023-08-10 09:04:00', '2023-08-10 17:04:01', 0);
INSERT INTO `system_post` VALUES (7, 'apartment manager', '店长', '公寓店长', 1, '2023-08-10 09:03:55', '2023-08-10 17:03:56', 0);
INSERT INTO `system_post` VALUES (8, '测试3', '测试3', '测试3', 1, '2023-07-18 02:13:24', '2023-07-18 10:13:25', 1);

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `type` tinyint NULL DEFAULT NULL COMMENT '用户类型',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `additional_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `post_id` bigint NULL DEFAULT NULL COMMENT '岗位id',
  `status` tinyint NULL DEFAULT NULL COMMENT '账号状态',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 0, '18888888888', NULL, NULL, 6, 1, '2023-08-10 17:13:00', '2023-08-19 23:30:48', 0);
INSERT INTO `system_user` VALUES (2, 'user', 'e10adc3949ba59abbe56e057f20f883e', '用户', 1, '13666666666', NULL, NULL, 7, 1, '2023-08-19 16:53:53', NULL, 0);

-- ----------------------------
-- Table structure for talk_info
-- ----------------------------
DROP TABLE IF EXISTS `talk_info`;
CREATE TABLE `talk_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `sender_id` bigint NOT NULL COMMENT '发送用户ID',
  `receiver_id` bigint NOT NULL COMMENT '接收用户ID',
  `is_read` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已读（0-未读，1-已读）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sender_receiver`(`sender_id` ASC, `receiver_id` ASC) USING BTREE,
  INDEX `idx_receiver_read`(`receiver_id` ASC, `is_read` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '私聊消息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of talk_info
-- ----------------------------
INSERT INTO `talk_info` VALUES (1, 'おはよう', 12, 11, 1, '2025-04-14 09:29:27', '2025-04-14 11:48:19', 0);
INSERT INTO `talk_info` VALUES (2, 'hiii', 11, 12, 0, '2025-04-14 10:48:24', '2025-04-14 11:48:23', 0);
INSERT INTO `talk_info` VALUES (3, '0v0', 11, 12, 0, '2025-04-16 08:14:17', '2025-04-16 09:14:16', 0);
INSERT INTO `talk_info` VALUES (4, '早！', 10, 11, 1, '2025-04-16 08:19:03', '2025-04-16 09:19:06', 0);
INSERT INTO `talk_info` VALUES (5, '1', 10, 11, 1, '2025-04-16 08:19:11', '2025-04-16 09:19:11', 0);
INSERT INTO `talk_info` VALUES (6, 'Hiii', 10, 11, 1, '2025-04-16 08:24:17', '2025-04-16 09:29:46', 0);
INSERT INTO `talk_info` VALUES (7, '早早早', 11, 10, 1, '2025-04-16 08:29:58', '2025-04-16 09:30:36', 0);
INSERT INTO `talk_info` VALUES (8, '在？', 11, 10, 1, '2025-04-16 08:30:02', '2025-04-16 09:30:36', 0);
INSERT INTO `talk_info` VALUES (9, 'hiii不在', 10, 11, 0, '2025-04-16 08:30:43', '2025-04-16 09:30:42', 0);
INSERT INTO `talk_info` VALUES (10, 'test', 10, 11, 0, '2025-04-16 08:34:32', '2025-04-16 09:34:31', 0);

-- ----------------------------
-- Table structure for talk_session
-- ----------------------------
DROP TABLE IF EXISTS `talk_session`;
CREATE TABLE `talk_session`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `target_user_id` bigint NOT NULL COMMENT '目标用户ID',
  `last_message` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后一条消息预览',
  `unread_count` int NOT NULL DEFAULT 0 COMMENT '未读消息数',
  `last_time` datetime NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_target`(`user_id` ASC, `target_user_id` ASC) USING BTREE,
  INDEX `idx_last_time`(`last_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '私聊会话表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of talk_session
-- ----------------------------
INSERT INTO `talk_session` VALUES (1, 12, 11, '0v0', 2, '2025-04-16 09:14:16', '2025-04-14 09:29:27', '2025-04-16 09:14:16', 0);
INSERT INTO `talk_session` VALUES (2, 11, 12, '0v0', 0, '2025-04-16 09:14:16', '2025-04-14 09:29:27', '2025-04-16 09:14:16', 0);
INSERT INTO `talk_session` VALUES (3, 10, 11, 'test', 0, '2025-04-16 09:34:31', '2025-04-16 08:19:03', '2025-04-16 09:34:31', 0);
INSERT INTO `talk_session` VALUES (4, 11, 10, 'test', 2, '2025-04-16 09:34:31', '2025-04-16 08:19:03', '2025-04-16 09:34:31', 0);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `status` tinyint NULL DEFAULT 1 COMMENT '账号状态',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '13012345678', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, '用户-888888', 1, '2023-07-01 14:48:17', NULL, 0);
INSERT INTO `user_info` VALUES (9, '13812345678', 'toukinn99@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/09b914ce-9182-4cc6-b986-ad39ad34830e-用户1.png', 'User138', 1, '2025-02-28 13:17:36', '2025-04-14 15:07:38', 0);
INSERT INTO `user_info` VALUES (10, '13312345678', 'test@uplus.com', 'e10adc3949ba59abbe56e057f20f883e', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/cb898caf-538f-400a-9d21-1227f831d169-用户4.jpg', 'User133', 1, '2025-02-28 14:58:54', '2025-04-14 10:15:26', 0);
INSERT INTO `user_info` VALUES (11, '13112345678', NULL, 'e10adc3949ba59abbe56e057f20f883e', 'http://54.95.189.69:9000/mobile-lease/permanent/20250411/b1f980d0-516f-4a5c-9776-301e2f3988f0-用户3.jpg', 'User131', 1, NULL, '2025-04-11 13:57:57', 0);
INSERT INTO `user_info` VALUES (12, '13212345678', NULL, 'e10adc3949ba59abbe56e057f20f883e', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/9aca4523-527b-45a5-85fd-ef81f8068918-用户5.jpg', 'User132', 1, NULL, '2025-04-14 09:29:05', 0);
INSERT INTO `user_info` VALUES (13, '13412345678', NULL, 'e10adc3949ba59abbe56e057f20f883e', 'http://54.95.189.69:9000/mobile-lease/permanent/20250414/ee4e31ef-8cdf-40d9-a97e-8de9a72cbc2c-用户2.jpg', 'User134', 1, NULL, '2025-04-14 15:00:20', 0);

-- ----------------------------
-- Table structure for view_appointment
-- ----------------------------
DROP TABLE IF EXISTS `view_appointment`;
CREATE TABLE `view_appointment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '预约id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户手机号码',
  `apartment_id` int NULL DEFAULT NULL COMMENT '公寓id',
  `appointment_time` timestamp NULL DEFAULT NULL COMMENT '预约时间',
  `additional_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `appointment_status` tinyint NULL DEFAULT NULL COMMENT '预约状态（1:待看房，2:已取消，3已看房）',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约看房信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of view_appointment
-- ----------------------------
INSERT INTO `view_appointment` VALUES (1, 11, 'User131', '13112345678', 1, '2025-04-11 13:58:48', 'test001-user131', 3, '2025-04-11 13:59:37', NULL, 0);
INSERT INTO `view_appointment` VALUES (2, 11, 'User131', '13112345678', 1, '2025-04-15 10:13:17', 'HII USER131 TEST A', 1, '2025-04-15 10:14:02', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
