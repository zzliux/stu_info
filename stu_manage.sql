SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `stu_manage`
--
CREATE DATABASE IF NOT EXISTS `stu_manage` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `stu_manage`;

-- --------------------------------------------------------

--
-- 表的结构 `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(12) NOT NULL,
  `sex` int(11) NOT NULL DEFAULT '0' COMMENT '1 - 男, 2 - 女',
  `age` int(11) NOT NULL DEFAULT '18',
  `class_name` varchar(32) NOT NULL DEFAULT '',
  `phone_number` char(11) NOT NULL,
  `location` varchar(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=140920052 ;

--
-- 转存表中的数据 `student`
--

INSERT INTO `student` (`id`, `name`, `sex`, `age`, `class_name`, `phone_number`, `location`) VALUES
(111, '123', 1, 17, '软件1333', '13333333333', '123312'),
(123, '我的学号屌爆', 2, 21, '软件1403', '13333333333', '我的学号就是屌'),
(155, '孙6', 1, 18, '软件1403', '13333333333', '长沙'),
(233, '这个学号是233', 2, 20, '软件1333', '13333333333', '233'),
(345, '123', 2, 20, '软件14055', '13666666666', '112123123'),
(1234, '添加测试测试', 2, 23, '软件1', '13323333333', '嗯？？'),
(1321, '张三', 1, 19, '软件1403', '13012342234', '长沙'),
(2333, '哈哈', 1, 21, '软件1333', '13333333333', '23333'),
(13521, '李四', 1, 21, '软件14055', '13012342255', '株洲'),
(23214, '张四', 2, 19, '软件1403', '13012342234', '长沙'),
(123321, '123231213', 2, 17, '软件1', '13333333333', '4444'),
(134567, 'rdseaew', 2, 18, '软件1', '15133334444', 'fdfds'),
(213123, '234', 2, 18, '软件14055', '17622222222', '123'),
(140920049, '刘潇', 1, 19, '计科1402', '17608471299', '长沙'),
(140920051, '付利东', 1, 24, '计科1402', '13333333333', '湖南');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_type` int(11) NOT NULL DEFAULT '0' COMMENT '用户类型( 0 - 普通用户 ， 1 - 管理员)',
  `name` varchar(64) NOT NULL,
  `password` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=31 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `user_type`, `name`, `password`) VALUES
(3, 1, 'zzliux', '9d2707adf6d5ff3d7052f52a732b2549'),
(5, 0, '13521', 'ae5d31dd4cd1798a35fff447bb42b1d0'),
(6, 0, '1321', '999268507e44861d3d950c4c9adda2c7'),
(8, 0, '23214', '82becb01272ce8dcc31dcf5566ceb6b4'),
(13, 0, '1234', '509e87d912367c64d3be8829883d96c2'),
(15, 0, '123', 'e6510c21021c920b3a9b10ba280adcba'),
(18, 0, '233', '57b259039e10ba54abcb8b3d7b5d663b'),
(19, 0, '345', '3c41d288cf5ec71a296743da692d417d'),
(23, 0, '2333', '79a1a4329e43762b0687a813f0bde351'),
(24, 0, '140920049', '9a92725f4da208f1faa95fb30c9f75b0'),
(25, 0, '155', 'bc10050f90ae435f9f92283570bda6d8'),
(26, 0, '134567', '29c7d1d5c50ba9a567d32d75e009a898'),
(27, 0, '111', '0173d00fd8793934e8b3719677e2a6d6'),
(28, 0, '123321', '89bcb747d1f58d6289ab71932065ec71'),
(29, 0, '213123', '8e6ed0b32b827f76ce4c31455b058ed5'),
(30, 0, '140920051', 'cea34865696c6232bf139f7d6e98f45c');
