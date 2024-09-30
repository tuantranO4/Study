CREATE TABLE R(A VARCHAR(10), B INTEGER, C INTEGER); 
INSERT INTO R VALUES('X',1, 2); 
INSERT INTO R VALUES('Y',2, 3); 
INSERT INTO R VALUES('Y',3, 4); 
INSERT INTO R VALUES('X',1, 5); 
INSERT INTO R VALUES('Y',3, 5); 
INSERT INTO R VALUES('X',4, 2); 
INSERT INTO R VALUES('X',4, 4); 
CREATE TABLE S(C INTEGER, D INTEGER); 
INSERT INTO S VALUES(2, 8); 
INSERT INTO S VALUES(2, 15); 
INSERT INTO S VALUES(3, 9); 
INSERT INTO S VALUES(3, 14); 
INSERT INTO S VALUES(4, 11); 
INSERT INTO S VALUES(4, 17); 
INSERT INTO S VALUES(2, 1); 
INSERT INTO S VALUES(6, 20); 

drop table Practice5;
create table Practice5 as
select A, avg(D) av
from R cross join S --R, S
where B>=2
group by A;

select * from Practice5;

--task 2
SELECT A, AVG(D) FROM R, S WHERE R.B >=2 GROUP BY A; 
SELECT A FROM R NATURAL JOIN S GROUP BY A HAVING AVG(S.D)>10; 
SELECT DISTINCT A FROM R, S WHERE R.C = S.C;  
SELECT A, C FROM R WHERE B = 2 ORDER BY A; 
SELECT DISTINCT A, B FROM R WHERE C IN (SELECT C FROM S WHERE D=1); 
SELECT A FROM R WHERE C NOT IN (SELECT C FROM S); 
SELECT A FROM R WHERE NOT EXISTS (SELECT * FROM S WHERE R.C = S.C); 

select A 
from R natural join S
group by A
having avg(D) >10;

select distinct A
from R,S
where R.C=S.C;

(SELECT A FROM R)
minus
(SELECT A FROM R,S WHERE R.C=S.C)


drop view avgsal_by_dept;
create view avgsal_by_dept as
select deptno, round(avg(sal),2) dept_avg
from emp
group by deptno;
select * from avg_sal_by_dept;

drop view gen_avg_sal;
create view gen_avg_sal as
select round(avg(sal),2) gen_avg
from emp;

select dname, dept_avg, gen_avg, dept_avg - gen_avg diff
from dept, avg_sal_by_dept, gen_avg_sal
where dept.deptno=avgsal_by_dept.deptno;
