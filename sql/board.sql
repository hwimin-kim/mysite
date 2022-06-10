-- board
desc board;

-- select
select * from board;
select count(*) from board;
select g_no, o_no, depth from board where no = 1;
-- update


-- insert
select IF(ISNULL(g_no), 1, MAX(g_no) +1) AS g_no from board;
insert into board values(null, 'tt', 'test', 1, now(), (select IF(ISNULL(g_no), 1, MAX(g_no) +1) AS g_no from board), 1, 1, 2);
insert into board values(null, 'tt', 'test', 1, now(), (select IF(ISNULL(g_no), 1, MAX(g_no) +1) from board t), 1, 1, 2);

-- select findAll
select a.no, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y/%m/%d %H:%i:%s') as reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name from board a, user b where a.user_no = b.no order by g_no desc, o_no asc;
select a.no, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y/%m/%d %H:%i:%s') as reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name from board a, user b where a.user_no = b.no order by g_no desc, o_no asc limit 0, 5;
-- delete

-- count
select count(*) from board where title like '%ttttt%';