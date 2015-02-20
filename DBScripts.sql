CREATE TABLE `usertable` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(15) NOT NULL,
  `Password` varchar(15) NOT NULL,
  `MobileUID` varchar(40) NOT NULL,
  `Current_Status` varchar(10) NOT NULL DEFAULT 'ON',
  `MobileIP` varchar(20) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `MobileUID` (`MobileUID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `location` (
  `UserId` int(11) NOT NULL,
  `XCord` varchar(20) NOT NULL,
  `YCord` varchar(20) NOT NULL,
  `Updated_Time` datetime NOT NULL,
  KEY `location_ibfk_1` (`UserId`),
  CONSTRAINT `location_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `usertable` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `filetable` (
  `FileID` int(11) NOT NULL AUTO_INCREMENT,
  `FileName` varchar(30) NOT NULL,
  `FileSize` float NOT NULL,
  `SyncStatus` varchar(10) NOT NULL,
  `Type` varchar(10) NOT NULL,
  `Source` int(11) NOT NULL,
  `SourceType` varchar(6) NOT NULL,
  `LastUpdatedTime` datetime NOT NULL,
  `FileMIMEType` varchar(10) NOT NULL,
  PRIMARY KEY (`FileID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `userfilemapping` (
  `UserId` int(11) NOT NULL,
  `FileId` int(11) NOT NULL,
  PRIMARY KEY (`UserId`,`FileId`),
  KEY `fk_map_fileid` (`FileId`),
  CONSTRAINT `fk_map_fileid` FOREIGN KEY (`FileId`) REFERENCES `filetable` (`FileID`),
  CONSTRAINT `fk_map_userid` FOREIGN KEY (`UserId`) REFERENCES `usertable` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `vmdetail` (
  `VMID` int(11) NOT NULL AUTO_INCREMENT,
  `IPAddress` varchar(20) NOT NULL,
  `MACAddress` varchar(25) NOT NULL,
  `VMLocation` varchar(25) NOT NULL,
  `isAllocated` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`VMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_vm` (
  `UserID` int(11) NOT NULL,
  `VMID` int(11) NOT NULL,
  KEY `user_vm_ibfk_1` (`UserID`),
  KEY `user_vm_ibfk_2` (`VMID`),
  CONSTRAINT `user_vm_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `usertable` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_vm_ibfk_2` FOREIGN KEY (`VMID`) REFERENCES `vmdetail` (`VMID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;