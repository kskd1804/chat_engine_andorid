-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 24, 2017 at 04:22 PM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cheatengine`
--

-- --------------------------------------------------------

--
-- Table structure for table `kskd1804@gmail.com`
--

CREATE TABLE `kskd1804@gmail.com` (
  `id` int(11) NOT NULL,
  `from_name` varchar(200) NOT NULL,
  `to_name` varchar(200) NOT NULL,
  `msg` text NOT NULL,
  `sent_recd` varchar(2) NOT NULL,
  `datetime` varchar(40) NOT NULL,
  `imgURL` varchar(250) NOT NULL,
  `status` varchar(15) NOT NULL DEFAULT 'SENT',
  `msg_type` varchar(20) NOT NULL DEFAULT 'img'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kskd1804@gmail.com`
--

INSERT INTO `kskd1804@gmail.com` (`id`, `from_name`, `to_name`, `msg`, `sent_recd`, `datetime`, `imgURL`, `status`, `msg_type`) VALUES
(1, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', 'Hello\n', 's', '2016-04-06 12:21:02', 'UserContent/kskd.jpg', 'READ', 'img'),
(2, 'muddamarchithreddy@gmail.com', 'kskd1804@gmail.com', 'Hi!\n', 'r', '2016-04-06 12:21:23', 'UserContent/profile_00.jpg', 'READ', 'img'),
(3, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', 'Test!\n', 's', '2016-08-16 22:15:33', 'UserContent/kskd.jpg', 'READ', 'img'),
(4, 'vardhanharsha56@gmail.com', 'kskd1804@gmail.com', 'hello\n', 'r', '2017-01-10 22:33:48', 'UserContent/profile_00.jpg', 'READ', 'img'),
(5, 'vardhanharsha56@gmail.com', 'kskd1804@gmail.com', 'hi\n', 'r', '2017-01-11 00:26:19', 'UserContent/profile_00.jpg', 'READ', 'img'),
(7, 'vardhanharsha56@gmail.com', 'kskd1804@gmail.com', '\n', 'r', '2017-01-28 14:20:05', 'UserContent/profile_00.jpg', 'READ', 'img'),
(9, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', '<script><?php echo ''Hello''?></script>\n', 's', '2017-01-28 17:52:45', 'UserContent/profile_01.jpg', 'SENT', 'img'),
(10, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', '<?php echo ''Hello'';?>\n', 's', '2017-01-28 17:53:12', 'UserContent/profile_01.jpg', 'SENT', 'img');

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `from_name` varchar(200) NOT NULL,
  `to_name` varchar(200) NOT NULL,
  `msg` text NOT NULL,
  `sent_recd` varchar(2) NOT NULL,
  `datetime` varchar(40) NOT NULL,
  `imgURL` varchar(250) NOT NULL,
  `status` varchar(15) NOT NULL DEFAULT 'SENT',
  `msg_type` varchar(20) NOT NULL DEFAULT 'img'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`id`, `from_name`, `to_name`, `msg`, `sent_recd`, `datetime`, `imgURL`, `status`, `msg_type`) VALUES
(1, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', 'Hi!!!\n', 's', '2016-11-03 18:37:42', 'UserContent/profile_01.jpg', 'SENT', 'img');

-- --------------------------------------------------------

--
-- Table structure for table `muddamarchithreddy@gmail.com`
--

CREATE TABLE `muddamarchithreddy@gmail.com` (
  `id` int(11) NOT NULL,
  `from_name` varchar(200) NOT NULL,
  `to_name` varchar(200) NOT NULL,
  `msg` text NOT NULL,
  `sent_recd` varchar(2) NOT NULL,
  `datetime` varchar(40) NOT NULL,
  `imgURL` varchar(250) NOT NULL,
  `status` varchar(15) NOT NULL DEFAULT 'SENT',
  `msg_type` varchar(20) NOT NULL DEFAULT 'img'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `muddamarchithreddy@gmail.com`
--

INSERT INTO `muddamarchithreddy@gmail.com` (`id`, `from_name`, `to_name`, `msg`, `sent_recd`, `datetime`, `imgURL`, `status`, `msg_type`) VALUES
(1, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', 'Hello\n', 'r', '2016-04-06 12:21:02', 'UserContent/kskd.jpg', 'READ', 'img'),
(2, 'muddamarchithreddy@gmail.com', 'kskd1804@gmail.com', 'Hi!\n', 's', '2016-04-06 12:21:23', 'UserContent/profile_00.jpg', 'READ', 'img'),
(3, 'sirishareddyaleti@gmail.com', 'muddamarchithreddy@gmail.com', 'hey\n', 'r', '2016-04-07 15:40:32', 'UserContent/profile_00.jpg', 'READ', 'img'),
(4, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', 'Test!\n', 'r', '2016-08-16 22:15:34', 'UserContent/kskd.jpg', 'READ', 'img'),
(5, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', '<script><?php echo ''Hello''?></script>\n', 'r', '2017-01-28 17:52:45', 'UserContent/profile_01.jpg', 'SENT', 'img'),
(6, 'kskd1804@gmail.com', 'muddamarchithreddy@gmail.com', '<?php echo ''Hello'';?>\n', 'r', '2017-01-28 17:53:12', 'UserContent/profile_01.jpg', 'SENT', 'img');

-- --------------------------------------------------------

--
-- Table structure for table `sharmapranav307@gmail.com`
--

CREATE TABLE `sharmapranav307@gmail.com` (
  `id` int(11) NOT NULL,
  `from_name` varchar(200) NOT NULL,
  `to_name` varchar(200) NOT NULL,
  `msg` text NOT NULL,
  `sent_recd` varchar(2) NOT NULL,
  `datetime` varchar(40) NOT NULL,
  `imgURL` varchar(250) NOT NULL,
  `status` varchar(15) NOT NULL DEFAULT 'SENT',
  `msg_type` varchar(20) NOT NULL DEFAULT 'img'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sirishareddyaleti@gmail.com`
--

CREATE TABLE `sirishareddyaleti@gmail.com` (
  `id` int(11) NOT NULL,
  `from_name` varchar(200) NOT NULL,
  `to_name` varchar(200) NOT NULL,
  `msg` text NOT NULL,
  `sent_recd` varchar(2) NOT NULL,
  `datetime` varchar(40) NOT NULL,
  `status` varchar(15) NOT NULL DEFAULT 'SENT',
  `msg_type` varchar(20) NOT NULL DEFAULT 'img',
  `imgURL` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sirishareddyaleti@gmail.com`
--

INSERT INTO `sirishareddyaleti@gmail.com` (`id`, `from_name`, `to_name`, `msg`, `sent_recd`, `datetime`, `status`, `msg_type`, `imgURL`) VALUES
(1, 'sirishareddyaleti@gmail.com', 'muddamarchithreddy@gmail.com', 'hey\n', 's', '2016-04-07 15:40:32', 'READ', 'img', 'UserContent/profile_00.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `t.premkumar@gmail.com`
--

CREATE TABLE `t.premkumar@gmail.com` (
  `id` int(11) NOT NULL,
  `from_name` varchar(200) NOT NULL,
  `to_name` varchar(200) NOT NULL,
  `msg` text NOT NULL,
  `sent_recd` varchar(2) NOT NULL,
  `datetime` varchar(40) NOT NULL,
  `status` varchar(15) NOT NULL DEFAULT 'SENT',
  `imgURL` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t.premkumar@gmail.com`
--

INSERT INTO `t.premkumar@gmail.com` (`id`, `from_name`, `to_name`, `msg`, `sent_recd`, `datetime`, `status`, `imgURL`) VALUES
(1, 't.premkumar@gmail.com', 'vardhanharsha56@gmail.com', 'hi\n', 's', '2017-01-10 22:45:08', 'READ', 'UserContent/profile_00.jpg'),
(2, 't.premkumar@gmail.com', 'vardhanharsha56@gmail.com', '\n', 's', '2017-01-10 22:45:08', 'READ', 'UserContent/profile_00.jpg'),
(3, 't.premkumar@gmail.com', 'vardhanharsha56@gmail.com', '\n', 's', '2017-01-10 22:45:09', 'READ', 'UserContent/profile_00.jpg'),
(4, 't.premkumar@gmail.com', 'vardhanharsha56@gmail.com', '\n', 's', '2017-01-10 22:45:09', 'READ', 'UserContent/profile_00.jpg'),
(5, 'vardhanharsha56@gmail.com', 't.premkumar@gmail.com', 'hello prem\n', 'r', '2017-01-10 22:45:26', 'READ', 'UserContent/profile_00.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `firstname` varchar(150) NOT NULL,
  `lastname` varchar(150) NOT NULL,
  `email` varchar(200) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `dob` date NOT NULL,
  `password` varchar(50) NOT NULL,
  `gender` text NOT NULL,
  `joineddate` date NOT NULL,
  `msgtable` varchar(200) NOT NULL,
  `status` varchar(7) NOT NULL DEFAULT 'offline',
  `imgURL` varchar(150) NOT NULL DEFAULT 'UserContent/profile_00.jpg',
  `user_desc` text NOT NULL,
  `last_online` varchar(50) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `firstname`, `lastname`, `email`, `mobile`, `dob`, `password`, `gender`, `joineddate`, `msgtable`, `status`, `imgURL`, `user_desc`, `last_online`) VALUES
(1, 'Kousthubha', 'Kalvakolanu', 'kskd1804@gmail.com', '7675038333', '1997-04-18', '25f9e794323b453885f5181f1b624d0b', 'Male', '2016-02-03', 'kskd1804@gmail.com', 'offline', 'UserContent/profile_01.jpg', 'College: MVSR Engineering College<br>\r\nBranch: Information Technolgy<br>\r\nYear: II/IV<br>\r\nSemester: II/II\r\n', '2017-04-24 19:51:14'),
(6, 'Archith', 'Reddy', 'muddamarchithreddy@gmail.com', '9666742456', '1996-08-17', 'feb416a0920abac89dd5c616a92e7228', 'Male', '2016-02-06', 'muddamarchithreddy@gmail.com', 'offline', 'UserContent/profile_00.jpg', '', '2016-08-16 22:18:31'),
(7, 'Surampudi', 'Pranav', 'sharmapranav307@gmail.com', '8374151700', '1996-12-07', '057d6777de6b972b19a87c5ecee20651', 'Male', '2016-02-06', 'sharmapranav307@gmail.com', 'offline', 'UserContent/profile_00.jpg', '', '0'),
(8, 'Sirisha', 'Reddy', 'sirishareddyaleti@gmail.com', '8019524521', '1996-12-10', '07be494d3989258e7f956cfd1c66d732', 'Female', '2016-04-07', 'sirishareddyaleti@gmail.com', 'offline', 'UserContent/profile_00.jpg', '', '2016-04-07 15:41:26'),
(10, 'Meghana', 'Sreepada', 'sreepadameghana@gmail.com', '9666641996', '1996-11-07', 'maggi', 'female', '2016-09-25', 'sreepadameghana@gmail.com', 'offline', 'UserContent/profile_00.jpg', '', '0'),
(11, 'Harsha', 'reddy', 'vardhanharsha56@gmail.com', '9848012345', '1995-12-04', 'de7c982c874801a8dc690327a54db9f5', 'Male', '2017-01-10', 'vardhanharsha56@gmail.com', 'offline', 'UserContent/profile_00.jpg', 'Description', '2017-01-28 17:37:38'),
(15, 'prem', 'kumar', 't.premkumar@gmail.com', '9494342414', '1995-11-11', '38c44c4d897ee251dad714385b573c6e', 'Male', '2017-01-10', 't.premkumar@gmail.com', 'offline', 'UserContent/profile_00.jpg', '', '2017-01-10 22:49:09');

-- --------------------------------------------------------

--
-- Table structure for table `vardhanharsha56@gmail.com`
--

CREATE TABLE `vardhanharsha56@gmail.com` (
  `id` int(11) NOT NULL,
  `from_name` varchar(200) NOT NULL,
  `to_name` varchar(200) NOT NULL,
  `msg` text NOT NULL,
  `sent_recd` varchar(2) NOT NULL,
  `datetime` varchar(40) NOT NULL,
  `status` varchar(15) NOT NULL DEFAULT 'SENT',
  `imgURL` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vardhanharsha56@gmail.com`
--

INSERT INTO `vardhanharsha56@gmail.com` (`id`, `from_name`, `to_name`, `msg`, `sent_recd`, `datetime`, `status`, `imgURL`) VALUES
(1, 'vardhanharsha56@gmail.com', 'kskd1804@gmail.com', 'hello\n', 's', '2017-01-10 22:33:48', 'READ', 'UserContent/profile_00.jpg'),
(2, 't.premkumar@gmail.com', 'vardhanharsha56@gmail.com', 'hi\n', 'r', '2017-01-10 22:45:08', 'READ', 'UserContent/profile_00.jpg'),
(3, 't.premkumar@gmail.com', 'vardhanharsha56@gmail.com', '\n', 'r', '2017-01-10 22:45:08', 'READ', 'UserContent/profile_00.jpg'),
(4, 't.premkumar@gmail.com', 'vardhanharsha56@gmail.com', '\n', 'r', '2017-01-10 22:45:09', 'READ', 'UserContent/profile_00.jpg'),
(5, 't.premkumar@gmail.com', 'vardhanharsha56@gmail.com', '\n', 'r', '2017-01-10 22:45:09', 'READ', 'UserContent/profile_00.jpg'),
(6, 'vardhanharsha56@gmail.com', 't.premkumar@gmail.com', 'hello prem\n', 's', '2017-01-10 22:45:25', 'READ', 'UserContent/profile_00.jpg'),
(7, 'vardhanharsha56@gmail.com', 'kskd1804@gmail.com', 'hi\n', 's', '2017-01-11 00:26:19', 'READ', 'UserContent/profile_00.jpg'),
(8, 'vardhanharsha56@gmail.com', 'kskd1804@gmail.com', '<script>\n</script>', 's', '2017-01-28 14:20:05', 'READ', 'UserContent/profile_00.jpg'),
(9, 'vardhanharsha56@gmail.com', 'kskd1804@gmail.com', '\n', 's', '2017-01-28 14:20:05', 'READ', 'UserContent/profile_00.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kskd1804@gmail.com`
--
ALTER TABLE `kskd1804@gmail.com`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `muddamarchithreddy@gmail.com`
--
ALTER TABLE `muddamarchithreddy@gmail.com`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sharmapranav307@gmail.com`
--
ALTER TABLE `sharmapranav307@gmail.com`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sirishareddyaleti@gmail.com`
--
ALTER TABLE `sirishareddyaleti@gmail.com`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t.premkumar@gmail.com`
--
ALTER TABLE `t.premkumar@gmail.com`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `msgtable` (`msgtable`);

--
-- Indexes for table `vardhanharsha56@gmail.com`
--
ALTER TABLE `vardhanharsha56@gmail.com`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kskd1804@gmail.com`
--
ALTER TABLE `kskd1804@gmail.com`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `muddamarchithreddy@gmail.com`
--
ALTER TABLE `muddamarchithreddy@gmail.com`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `sharmapranav307@gmail.com`
--
ALTER TABLE `sharmapranav307@gmail.com`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `sirishareddyaleti@gmail.com`
--
ALTER TABLE `sirishareddyaleti@gmail.com`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `t.premkumar@gmail.com`
--
ALTER TABLE `t.premkumar@gmail.com`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `vardhanharsha56@gmail.com`
--
ALTER TABLE `vardhanharsha56@gmail.com`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
