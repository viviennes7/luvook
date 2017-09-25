CREATE DATABASE luvook DEFAULT CHARACTER SET utf8mb4;

use luvook;

CREATE TABLE `board` (
  `board_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '게시글 아이디',
  `member_id` int(11) NOT NULL COMMENT '멤버 아이디',
  `contents` longtext NOT NULL COMMENT '내용',
  `grade` int(1) NOT NULL DEFAULT '0' COMMENT '책 점수 (0~5)',
  `is_use` varchar(3) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `dtype` varchar(3) NOT NULL COMMENT '자식테이블 구분자',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  `mod_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=613 DEFAULT CHARSET=utf8mb4 COMMENT='책 게시글';

CREATE TABLE `board_book` (
  `board_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `cover` varchar(255) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `isbn13` varchar(20) NOT NULL,
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='게시글(책)';

CREATE TABLE `board_comment` (
  `board_comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '댓글 아이디',
  `member_id` int(11) NOT NULL COMMENT '멤버 아이디',
  `board_id` int(11) NOT NULL COMMENT '게시글 아이디',
  `contents` varchar(255) NOT NULL COMMENT '댓글 내용',
  `is_use` varchar(3) NOT NULL DEFAULT 'Y' COMMENT '사용 여부',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  `mod_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  PRIMARY KEY (`board_comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COMMENT='게시글의 댓글';

CREATE TABLE `board_heart` (
  `board_heart_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '게시글 하트 아이디',
  `board_id` int(11) NOT NULL COMMENT '게시글 아이디',
  `member_id` int(11) NOT NULL COMMENT '멤버 아이디',
  `is_use` varchar(3) NOT NULL DEFAULT 'Y' COMMENT '사용 여부 ',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일 ',
  `mod_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일 ',
  PRIMARY KEY (`board_heart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COMMENT='게시글에하트';

CREATE TABLE `member_master` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '멤버 아이디(기본키)',
  `nickname` varchar(45) DEFAULT NULL COMMENT '이름',
  `email` varchar(45) NOT NULL COMMENT '이메일 / 아이디',
  `password` varchar(100) NOT NULL COMMENT '암호화  비밀번호',
  `profile_img` varchar(200) DEFAULT NULL COMMENT '프로필이미지',
  `member_type` varchar(10) NOT NULL DEFAULT 'USER' COMMENT '회원등급',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  `mod_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4 COMMENT='유저정보';
