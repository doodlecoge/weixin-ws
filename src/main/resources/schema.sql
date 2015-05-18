CREATE DATABASE IF NOT EXISTS wxdb
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;


USE wxdb;


CREATE TABLE IF NOT EXISTS users
(
  wxid         VARCHAR(128) NOT NULL PRIMARY KEY,
  wbx_password VARCHAR(32)  NOT NULL,
  wbx_username VARCHAR(128) NOT NULL,
  wbx_site_url VARCHAR(128) NOT NULL,
  fullname     VARCHAR(32)  NULL,
  created_at   DATETIME     NOT NULL,
  updated_at   DATETIME     NOT NULL
);