alter table comments
    add status varchar(20) null;
UPDATE demodb.comments t SET t.status = 'POSTED' where id > 0