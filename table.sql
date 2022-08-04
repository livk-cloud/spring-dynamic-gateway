-- auto-generated definition
create table if not exists dynamic_route
(
    id          varchar(32)       not null comment '路由id'
        primary key,
    uri         varchar(64)       not null comment 'url地址',
    predicates  json              null comment '断言',
    filters     json              null comment '过滤器',
    metadata    json              null comment '元数据',
    `order`     int               not null comment '排序',
    description varchar(128)      null comment '描述',
    status      tinyint default 0 not null comment '状态值:0-禁用 1-启用',
    insert_time datetime          null comment '插入时间',
    update_time datetime          null comment '更新时间'
);
