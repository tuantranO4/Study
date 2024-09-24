select fruits from likes;

drop table likes;

create table likes as select * from kotroczo.likes;
select * from likes;
--list fruits that Winnie likes
select fruits from likes
where name = 'Winnie';
--2. List the fruits that Winnie doesn't like but someone else does

--Select fruits from likes
--where  name != 'Winnie';

Select fruits from likes
minus
select fruits from likes
where name = 'Winnie'; --set theory removal
--3. list who likes apple?
select name from likes
where fruits='apple';

--list who doesn't like pear but sth else
select name from likes
minus
select name from likes where fruits = 'pear';
--5. list who likes raspberry or pear?
select name from likes
where fruits ='raspberry' or fruits = 'pear';
--6.who likes both apple and pear
select name from likes
where fruits ='apple' 
intersect
select name from likes 
where fruits = 'pear';