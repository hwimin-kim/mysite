-- UserRepository

desc user;

-- select
select * from user;

-- join
insert
 into user
 values(null, '관리자', 'admin@mysite.com', '1234', 'male', now());
 

-- login
select no, name
 from user
 where email = 'dooly@gmail.com'
 and password = '1234';
 
 -- findByno
 select no, name, email, gender
 from user
 where no = 3;
 
 -- update
 -- name password gender authUser.getNo();
 update user set name = '', password = '', gender = '' where no = '';
 update user set name = '', gender = '' where no = '';
-- redirect updateform