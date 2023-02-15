create table demodb.countries
(
    country_code varchar(2)   null,
    country_name varchar(100) null,
    capital      varchar(200) null,
    area         varchar(200) null,
    continent    varchar(20)  null
);

create index countries_country_code_index
    on demodb.countries (country_code);

create table demodb.destinations
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

create table demodb.feature_codes
(
    code                  varchar(10)  not null,
    meaning               varchar(50)  not null,
    description           varchar(150) null,
    feature_class         varchar(5)   not null,
    feature_class_meaning varchar(50)  not null,
    id                    int auto_increment
        primary key
);

create index feature_codes__index
    on demodb.feature_codes (code);

create index feature_codes_feature_class_index
    on demodb.feature_codes (feature_class);

create index feature_codes_id_index
    on demodb.feature_codes (id);

create table demodb.geonames
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
        foreign key (country_code) references demodb.countries (country_code)
);

alter table demodb.countries
    add constraint countries_country_code_fk
        foreign key (country_code) references demodb.geonames (country_code);

create index geonames_asciiname_index
    on demodb.geonames (asciiname);

create index geonames_country_code_index
    on demodb.geonames (country_code);

create index geonames_feature_code_index
    on demodb.geonames (feature_code);

create index geonames_name_index
    on demodb.geonames (name);

create table demodb.user_details
(
    id                     int auto_increment
        primary key,
    biography              varchar(1000) null,
    gender                 varchar(10)   null,
    residence              bigint        null,
    registration_timestamp timestamp     null,
    profile_privacy        varchar(10)   null,
    birthdate              date          null,
    constraint FKhbiig354pqo2uptnlnu57ukhw
        foreign key (residence) references demodb.geonames (geonameid)
);

create table demodb.users
(
    id                int auto_increment
        primary key,
    username          varchar(100)  not null,
    email             varchar(100)  not null,
    password          varchar(2000) not null,
    first_name        varchar(100)  null,
    last_name         varchar(100)  null,
    registration_date datetime      null,
    birthdate         date          null,
    biography         varchar(500)  null,
    gender            varchar(25)   null,
    details_id        int           not null,
    constraint email
        unique (email),
    constraint id
        unique (id),
    constraint username
        unique (username),
    constraint users_details_fk
        foreign key (details_id) references demodb.user_details (id)
);

create table demodb.follow
(
    id          int auto_increment
        primary key,
    follow_date datetime not null,
    followed_id int      not null,
    follower_id int      not null,
    constraint id
        unique (id),
    constraint FK9o03ye7x353hojmg9iaaybem2
        foreign key (followed_id) references demodb.users (id),
    constraint FKjikg34txcxnhcky26w14fvfcc
        foreign key (follower_id) references demodb.users (id)
);

create table demodb.follow_requests
(
    id           int auto_increment
        primary key,
    request_date datetime not null,
    requester_id int      not null,
    receiver_id  int      not null,
    constraint id
        unique (id),
    constraint FKbgsle7d734bxmsqlfyd393oht
        foreign key (receiver_id) references demodb.users (id),
    constraint FKdavofo5pfcbxtcbtvbo0tq4v9
        foreign key (requester_id) references demodb.users (id)
);

create table demodb.journeys
(
    id             int auto_increment
        primary key,
    user_id        int          not null,
    start_date     datetime     null,
    end_date       datetime     null,
    destination_id bigint       null,
    description    varchar(500) null,
    visibility     varchar(25)  null,
    posted_on      datetime     not null,
    constraint id
        unique (id),
    constraint FK1um7r8cm6rnn4dinfmarjjmqv
        foreign key (user_id) references demodb.users (id),
    constraint FK5on53fhiark530qq6365v8aum
        foreign key (destination_id) references demodb.geonames (geonameid)
);

create table demodb.activities
(
    id          int auto_increment
        primary key,
    journey_id  int          not null,
    date        date         null,
    title       varchar(100) null,
    description varchar(500) null,
    location    varchar(100) null,
    type        varchar(100) null,
    constraint id
        unique (id),
    constraint FK8tlrdbtqfvfa951dup5e3uo0t
        foreign key (journey_id) references demodb.journeys (id)
);

create table demodb.comments
(
    id         int auto_increment
        primary key,
    journey_id int          not null,
    user_id    int          not null,
    posted_on  datetime     not null,
    content    varchar(500) not null,
    constraint id
        unique (id),
    constraint FK8omq0tc18jd43bu5tjh6jvraq
        foreign key (user_id) references demodb.users (id),
    constraint FKcl4dmg7gkwc1wwwqypkrcad9m
        foreign key (journey_id) references demodb.journeys (id)
);

create table demodb.interests
(
    id              bigint auto_increment
        primary key,
    interest_key    varchar(50)  not null,
    value           double       not null,
    user_id         int          not null,
    count           int          null,
    last_comment_id int          null,
    entity          varchar(200) null,
    constraint interests_last_comment_fk
        foreign key (last_comment_id) references demodb.comments (id),
    constraint interests_user_fk
        foreign key (user_id) references demodb.users (id)
);

create table demodb.notifications
(
    id                      bigint auto_increment
        primary key,
    content                 varchar(255) null,
    object_id               bigint       null,
    object_type             varchar(255) null,
    preposition_object_id   bigint       null,
    preposition_object_type varchar(255) null,
    timestamp               datetime     not null,
    receiver_id             int          not null,
    subject_id              int          not null,
    constraint notifications_receiver_id_fk
        foreign key (receiver_id) references demodb.users (id),
    constraint notifications_subject_id_fk
        foreign key (subject_id) references demodb.users (id)
);

create index notifications_object_id_index
    on demodb.notifications (object_type);

create index notifications_object_type_index
    on demodb.notifications (object_id);

create table demodb.reviews
(
    id             int auto_increment
        primary key,
    user_id        int          not null,
    destination_id bigint       not null,
    posted_on      datetime     not null,
    star_rating    int          not null,
    title          varchar(100) null,
    content        varchar(500) null,
    constraint id
        unique (id),
    constraint FKcgy7qjc1r99dp117y9en6lxye
        foreign key (user_id) references demodb.users (id),
    constraint reviews_ibfk_1
        foreign key (destination_id) references demodb.geonames (geonameid)
);

create index FKo07xgps8spbcjhqpj559t835x
    on demodb.reviews (destination_id);

create index index_user_details_id
    on demodb.users (details_id);

