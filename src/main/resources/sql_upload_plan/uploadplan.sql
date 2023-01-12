insert into countries (country_code, country_name, capital, area, continent)
values ('AN', 'Netherlands Antilles', 'Willemstad', '800', 'North America');

delete from geonames where geonameid in (8505031, 8505033, 7500737);

delete from geonames where country_code = '';
commit;
alter table geonames
    add constraint geonames_country_code_fk
        foreign key (country_code) references countries (country_code);

alter table geonames
    modify name varchar(200) null;

alter table geonames
    modify asciiname varchar(200) null;

alter table geonames
    modify country_code varchar(2) null;

create index geonames_name_index
    on geonames (name);

create index geonames_asciiname_index
    on geonames (asciiname);

create index geonames_feature_code_index
    on geonames (feature_code);

alter table feature_codes
    add constraint feature_codes_fk
        foreign key (code) references geonames (feature_code);
