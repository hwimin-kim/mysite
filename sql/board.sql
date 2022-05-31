-- board

desc board;
select * from user;

-- insert
select IF(ISNULL(g_no), 1, MAX(g_no) +1) AS g_no from board; 
insert into board values(null, 'tt', 'test', 1, now(), (select MAX(g_no) from board), 1, 1, 2);