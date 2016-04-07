
CREATE TABLE db_corporation (
  id varchar(32) NOT NULL,
  name varchar(200) NOT NULL,
  host varchar(200) NOT NULL,
  `schema` varchar(32) NOT NULL,
  create_time datetime NOT NULL,
  update_time datetime NOT NULL,
  PRIMARY KEY (id),
  KEY `schema` (`schema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE db_pool (
  id varchar(50) NOT NULL,
  name varchar(200) NOT NULL,
  host varchar(100) DEFAULT NULL,
  port int(11) DEFAULT NULL,
  url varchar(200) NOT NULL,
  username varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  initial_size int(11) NOT NULL DEFAULT '5',
  min_size int(11) NOT NULL DEFAULT '1',
  max_size int(11) NOT NULL DEFAULT '20',
  create_time datetime DEFAULT NULL,
  update_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE db_schema (
  id varchar(32) NOT NULL,
  name varchar(200) DEFAULT NULL,
  host varchar(200) DEFAULT NULL,
  corporation varchar(50) DEFAULT NULL,
  write_pool varchar(37) DEFAULT NULL,
  read_pool varchar(37) DEFAULT NULL,
  create_time datetime DEFAULT NULL,
  update_time datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY write_pool (write_pool),
  KEY read_pool (read_pool),
  CONSTRAINT db_schema_ibfk_1 FOREIGN KEY (write_pool) REFERENCES db_pool (id),
  CONSTRAINT db_schema_ibfk_2 FOREIGN KEY (read_pool) REFERENCES db_pool (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

