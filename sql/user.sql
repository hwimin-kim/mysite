-- UserRepository

desc user;

-- insert
insert
 into user
 values(null, '관리자', 'admin@mysite.com', '1234', 'male', now());
-- select
select * from user;