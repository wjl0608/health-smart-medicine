-- 创建库
create database if not exists health_smart_medicine;

-- 切换库
use health_smart_medicine;

-- 用户表
create table if not exists user
(
    id bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userName     varchar(256)                           not null comment '名字',
    userPassword varchar(512)                           not null comment '密码',
    userAge      int  default null                      null comment '年龄',
    userSex      varchar(1)   default null              null comment '性别：0男 1女',
    userEmail    varchar(256)                           null comment '邮箱',
    userTel      varchar(50)                            null comment '手机号',
    userRole     varchar(50) default 'user'             not null comment '用户角色：1管理员，0普通用户',
    imgPath      varchar(1024)                          not null comment '头像',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    UNIQUE KEY uk_userAccount (userAccount),
    INDEX idx_userName (userName)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 药品表
create table if not exists medicine
(
    id             bigint auto_increment comment 'id' primary key,
    medicineName   varchar(256)                           not null comment '药的名字',
    keyword        varchar(256) default null              null comment '关键字搜索',
    medicineEffect mediumtext                             not null comment '药的功效',
    medicineBrand  varchar(256)                           not null comment '药的品牌',
    interaction    mediumtext                             null comment '药的相互作用',
    taboo          mediumtext                             null comment '禁忌',
    useAge         mediumtext                             null comment '用法用量',
    medicineType   varchar(1)   default null              null comment '药的类型：0西药，1中药，2中成药',
    imgPath        varchar(1024)                          not null comment '相关图片路径',
    medicinePrice  decimal(10, 2)                         not null comment '药的价格',
    editTime       datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint      default 0                 not null comment '是否删除',
    INDEX idx_medicineName (medicineName)
)comment '药品' collate = utf8mb4_unicode_ci;
