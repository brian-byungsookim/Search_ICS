CREATE DATABASE IF NOT EXISTS crawler;
USE crawler;
CREATE TABLE `documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`,`url`),
  UNIQUE KEY `url_UNIQUE` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
CREATE TABLE `terms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `term` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`term`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
CREATE TABLE `terms_documents` (
  `term_id` int(11) DEFAULT NULL,
  `document_id` int(11) DEFAULT NULL,
  `termCount` int(11) DEFAULT '1',
  KEY `term_idx` (`term_id`),
  KEY `documents_fkey_idx` (`document_id`),
  CONSTRAINT `documents_fkey` FOREIGN KEY (`document_id`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `terms_fkey` FOREIGN KEY (`term_id`) REFERENCES `terms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


