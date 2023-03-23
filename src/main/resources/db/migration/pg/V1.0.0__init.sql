create table api_key
(
    id     BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    apikey varchar(255)  null,
    status int default 0 null
);

create table sys_config
(
    id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    config_key   varchar(255) null,
    config_name  varchar(255) null,
    config_type  int          null,
    config_value varchar(255) null
);

create table users
(
    id       BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    password varchar(255) null,
    role     varchar(255) null,
    username varchar(255) null
);



INSERT INTO sys_config (config_key, config_name, config_type, config_value)
VALUES ('weichat_name', '小程序名称', 1, 'knows everything');
INSERT INTO sys_config (config_key, config_name, config_type, config_value)
VALUES ('weichat_adv', '小程序广告', 1, '需要问答接口,以及聊天接口请联系开发者');
INSERT INTO sys_config (config_key, config_name, config_type, config_value)
VALUES ('weichat_notice', '小程序公告', 1, '这两天官网有点慢,所以回答的很慢,请大家耐心一点');


--  admin/admin  使用BcryptUtil.bcryptHash(password);加密 更新数据库可修改密码
INSERT INTO users (password, role, username)
VALUES ('$2a$10$vq5tH3FVde0yegfFIeua3eW6xJeiHNL3MSAJ77Wtr/Jc4PYFW3f/q', 'admin', 'admin');
