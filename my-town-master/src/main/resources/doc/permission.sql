/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020-03-14 15:01:15                          */
/*==============================================================*/


drop table if exists category;

drop table if exists permission;

drop table if exists products;

drop table if exists user_extend;

drop table if exists userinfo;

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
create table category
(
   ID                   bigint(18) not null,
   NAME                 varchar(255) not null default ' ',
   PARENTID             bigint(18) not null default -1,
   LEVEL                int(2) not null default 0,
   QUEUE                int(10) not null default 0,
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   ENABLE               char(2) not null default '1',
   MODIFY_ID            varchar(255) not null default ' ',
   CREATE_ID            varchar(255) not null default ' ',
   DESCRIPTION          varchar(255) not null default ' ',
   primary key (ID)
);

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   ID                   bigint(18) not null,
   LEVEL                int(2) not null default 1,
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   ENABLE               char(2) not null default '1',
   MODIFY_ID            varchar(255) not null default ' ',
   CREATE_ID            varchar(255) not null default ' ',
   QUEUE                int(10) not null default 0,
   DESCRIPTION          varchar(255) not null default ' ',
   ROLE                 varchar(255) not null default ' ',
   primary key (ID)
);

/*==============================================================*/
/* Table: products                                              */
/*==============================================================*/
create table products
(
   ID                   bigint(18) not null,
   USERID               bigint(18) not null,
   CATEGORY_ID          bigint(18) not null default 0,
   VIEW_COUNT           int(10) not null default 0,
   PRICE                decimal(16,4) not null default 0,
   DESCRIPTION          varchar(255) not null default ' ',
   IMG3                 varchar(500) not null default ' ',
   IMG2                 varchar(500) not null default ' ',
   IMG1                 varchar(500) not null default ' ',
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   ENABLE               char(2) not null default '1',
   MODIFY_ID            varchar(255) not null default ' ',
   CREATE_ID            varchar(255) not null default ' ',
   QUEUE                int(10) not null default 0,
   primary key (ID)
);

/*==============================================================*/
/* Table: user_extend                                           */
/*==============================================================*/
create table user_extend
(
   ID                   bigint(18) not null,
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   ENABLE               char(2) not null default '1',
   MODIFY_ID            varchar(255) not null default ' ',
   CREATE_ID            varchar(255) not null default ' ',
   QUEUE                int(10) not null default 0,
   DESCRIPTION          varchar(255) not null default ' ',
   USERID               bigint(18) not null,
   NAME                 varchar(255) not null default ' ',
   PHONE                varchar(255) not null default ' ',
   WX_CHAT              varchar(255) not null default ' ',
   SHOP_NAME            varchar(255) not null default ' ',
   EMAIL                varchar(255) not null default ' ',
   EXPIRE_TIME          datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: userinfo                                              */
/*==============================================================*/
create table userinfo
(
   ID                   bigint(18) not null,
   USER_NAME            varchar(255) not null,
   PASSWORD             varchar(255) not null,
   FIRST_LOGINIP        varchar(255) not null default ' ',
   SALT                 varchar(255) not null default ' ',
   PERMISSION_ID        bigint(18) not null default -1,
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   ENABLE               char(2) not null default '1',
   MODIFY_ID            varchar(255) not null default ' ',
   CREATE_ID            varchar(255) not null default ' ',
   QUEUE                int(10) not null default 0,
   DESCRIPTION          varchar(255) not null default ' ',
   primary key (ID)
);
