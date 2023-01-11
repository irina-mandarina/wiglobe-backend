insert into countries (country_code, country_name, capital, area, continent)
values ('AN', 'Netherlands Antilles', 'Willemstad', '800', 'North America');

delete from geonames where geonameid in (8505031, 8505033, 7500737);

delete from geonames where country_code = '';
commit;
alter table geonames
    add constraint geonames_country_code_fk
        foreign key (country_code) references countries (country_code);

