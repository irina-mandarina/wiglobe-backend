create table countries
(
    country_code varchar(2)   null,
    country_name varchar(100) null,
    capital      varchar(200) null,
    area         varchar(200) null,
    continent    varchar(20)  null
);

create index countries_country_code_index
    on countries (country_code);

create table destinations
(
    id            int auto_increment
        primary key,
    latitude      double       not null,
    longitude     double       not null,
    feature_class char         null,
    feature_code  varchar(10)  null,
    country_code  varchar(2)   null,
    name          varchar(200) null,
    x_cord        double       not null,
    y_cord        double       not null,
    constraint id
        unique (id)
);

create table feature_codes
(
    code                  varchar(10)  not null,
    id                    int auto_increment
        primary key,
    meaning               varchar(50)  not null,
    description           varchar(150) null,
    feature_class         varchar(5)   not null,
    feature_class_meaning varchar(50)  not null
);

create table geonames
(
    geonameid           bigint         not null
        primary key,
    name                varchar(200)   null,
    asciiname           varchar(200)   null,
    alternatenames      varchar(13000) null,
    latitude            double         null,
    longitude           double         null,
    feature_class       varchar(1)     null,
    feature_code        varchar(5)     null,
    country_code        varchar(2)     null,
    cc2                 text           null,
    `admin1 code`       text           null,
    `admin2 code`       text           null,
    `admin3 code`       text           null,
    `admin4 code`       text           null,
    population          bigint         null,
    elevation           text           null,
    dem                 int            null,
    timezone            text           null,
    `modification date` text           null,
    constraint geonames_country_code_fk
        foreign key (country_code) references countries (country_code)
);

alter table countries
    add constraint countries_country_code_fk
        foreign key (country_code) references geonames (country_code);

create index geonames_asciiname_index
    on geonames (asciiname);

create index geonames_country_code_index
    on geonames (country_code);

create index geonames_name_index
    on geonames (name);

create table users
(
    id                int auto_increment
        primary key,
    username          varchar(100) null,
    email             varchar(100) null,
    password          varchar(100) null,
    first_name        varchar(100) null,
    last_name         varchar(100) null,
    registration_date datetime     null,
    birthdate         date         null,
    biography         varchar(500) null,
    gender            varchar(25)  null,
    constraint email
        unique (email),
    constraint id
        unique (id),
    constraint username
        unique (username)
);

create table follow
(
    id          int auto_increment
        primary key,
    follow_date datetime not null,
    followed_id int      not null,
    follower_id int      not null,
    constraint id
        unique (id),
    constraint FK9o03ye7x353hojmg9iaaybem2
        foreign key (followed_id) references users (id),
    constraint FKjikg34txcxnhcky26w14fvfcc
        foreign key (follower_id) references users (id)
);

create table follow_requests
(
    id           int auto_increment
        primary key,
    request_date datetime not null,
    requester_id int      not null,
    receiver_id  int      not null,
    constraint id
        unique (id),
    constraint FKbgsle7d734bxmsqlfyd393oht
        foreign key (receiver_id) references users (id),
    constraint FKdavofo5pfcbxtcbtvbo0tq4v9
        foreign key (requester_id) references users (id)
);

create table journeys
(
    id             int auto_increment
        primary key,
    user_id        int          not null,
    start_date     datetime     not null,
    end_date       datetime     null,
    destination_id bigint       not null,
    description    varchar(500) null,
    visibility     varchar(25)  null,
    constraint id
        unique (id),
    constraint FK1um7r8cm6rnn4dinfmarjjmqv
        foreign key (user_id) references users (id),
    constraint FK5on53fhiark530qq6365v8aum
        foreign key (destination_id) references geonames (geonameid)
);

create table activities
(
    id          int auto_increment
        primary key,
    journey_id  int          not null,
    date        date         null,
    title       varchar(100) null,
    description varchar(500) null,
    location    varchar(100) null,
    type        int          not null,
    constraint id
        unique (id),
    constraint FK8tlrdbtqfvfa951dup5e3uo0t
        foreign key (journey_id) references journeys (id)
);

create table comments
(
    id          int auto_increment
        primary key,
    journey_id  int          not null,
    user_id     int          not null,
    date_posted datetime     not null,
    content     varchar(500) not null,
    constraint id
        unique (id),
    constraint FK8omq0tc18jd43bu5tjh6jvraq
        foreign key (user_id) references users (id),
    constraint FKcl4dmg7gkwc1wwwqypkrcad9m
        foreign key (journey_id) references journeys (id)
);

create table reviews
(
    id             int auto_increment
        primary key,
    user_id        int          not null,
    destination_id bigint       not null,
    reviewed_date  datetime     not null,
    star_rating    int          not null,
    title          varchar(100) null,
    content        varchar(500) null,
    constraint id
        unique (id),
    constraint FKcgy7qjc1r99dp117y9en6lxye
        foreign key (user_id) references users (id),
    constraint reviews_ibfk_1
        foreign key (destination_id) references geonames (geonameid)
);

create index FKo07xgps8spbcjhqpj559t835x
    on reviews (destination_id);

