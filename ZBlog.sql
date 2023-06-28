DROP DATABASE IF EXISTS zblog;
CREATE DATABASE zblog;
USE zblog;

CREATE TABLE t_user(
	id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
	nickname VARCHAR(32) NOT NULL DEFAULT '' COMMENT '昵称',
	`username` VARCHAR(32) UNIQUE NOT NULL DEFAULT '' COMMENT '用户名',
	`password` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '密码',
	email VARCHAR(128) NOT NULL DEFAULT '' COMMENT '邮箱',
	`type` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户类型',
	avatar VARCHAR(200) NOT NULL DEFAULT '' COMMENT '头像url',
	create_time DATETIME COMMENT '创建时间',
	update_time DATETIME COMMENT '修改时间'
);
INSERT INTO t_user VALUES(NULL, 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@163.com', 'admin', 'admin', '2023-4-14', '2023-4-14');


CREATE TABLE t_type(
	id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
	`name` VARCHAR(32) NOT NULL UNIQUE DEFAULT '' COMMENT '类型名称'
);  


CREATE TABLE t_blog(
	id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
	title VARCHAR(64) NOT NULL DEFAULT '' COMMENT '博客标题',
	content VARCHAR(10000) NOT NULL DEFAULT '' COMMENT '博客内容',
	first_picture VARCHAR(500) NOT NULL DEFAULT '' COMMENT '博客首图url',
	flag VARCHAR(32) NOT NULL DEFAULT '' COMMENT '标记: 原创 转载 翻译',
	view_cnt INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
	appreciation BOOL NOT NULL DEFAULT FALSE COMMENT '是否开启赞赏',
	share_statement BOOL NOT NULL DEFAULT FALSE COMMENT '是否开启显示版权信息',
	reviewable BOOL NOT NULL DEFAULT FALSE COMMENT '是否开启评论',
	recommend BOOL NOT NULL DEFAULT FALSE COMMENT '是否推荐',
	create_time DATETIME COMMENT '创建时间',
	update_time DATETIME COMMENT '修改时间',
	type_id INT NOT NULL COMMENT '所属类型id',
	user_id INT NOT NULL COMMENT '所属用户id',
	description VARCHAR(200) NOT NULL DEFAULT '' COMMENT '博客描述',
	FOREIGN KEY(type_id) REFERENCES t_type(id),
	FOREIGN KEY(user_id) REFERENCES t_user(id),
	INDEX `index_blog_type_id`(`type_id`) USING BTREE,
	INDEX `index_blog_user_id`(`user_id`) USING BTREE,
	published BOOL NOT NULL DEFAULT TRUE COMMENT 'true为已经发布 false 为保存'
);

 
CREATE TABLE t_tag(
	id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
	`name` VARCHAR(32) NOT NULL UNIQUE DEFAULT '' COMMENT '标签名称'
);

CREATE TABLE t_blog_tag(
	`id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
	blog_id INT NOT NULL COMMENT '博客id',
	tag_id INT NOT NULL COMMENT '标签id'
);

CREATE TABLE t_comment(
	id INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
	nickname VARCHAR(32) NOT NULL DEFAULT '' COMMENT '昵称',
	email VARCHAR(128) NOT NULL DEFAULT '' COMMENT '邮箱',
	avatar VARCHAR(200) NOT NULL DEFAULT '' COMMENT '头像url',
	content VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '评论内容',
	create_time DATETIME COMMENT '创建时间',
	blog_id INT NOT NULL COMMENT '所属博客id',
	admin_comment BOOL NOT NULL DEFAULT TRUE COMMENT '是否为一级评论',
	parent_comment_id INT COMMENT '上级评论id',
	FOREIGN KEY(blog_id) REFERENCES t_blog(id) 
);


