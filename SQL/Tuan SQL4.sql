create table prac4 as select * from nikovits.emp;
select * from prac4;
--1. List the employees whose salary is divisible by 15 (mod func)
select ename from prac4 where 
mod(sal,15) = 0;
--2.  List the employees, whose hiredate is greater than 1982.01.01. (use to_date function)
select ename from prac4 where hiredate > TO_DATE('1982-01-01', 'YYYY-MM-DD');
--3.  List the employees where the second character of his name is 'A'. (use substr function)
select ename from prac4 where substr(ename,2,1)='A'; --substr() extracting letter
--4.  List the employees whose name contains two 'L'-s. (use instr function)
select ename from prac4 WHERE INSTR(ename, 'L') <> 0 
AND INSTR(substr(ename, instr(ename,'L') + 1), 'L') <> 0;
--5.  List the last 3 characters of the employees' names. (use substr function)
select ename,substr(ename,-3) as substringed from prac4; 
--6.  List the emloyees whose name has a 'T' in the last but one position (position before the last). (use substr function)
select ename from prac4 where substr(ename,-1) like 'T';
--7.  List the square root of the salaries rounded to 2 decimals and the integer part of it. (sqrt, round, trunc function)
    select sal , round(sqrt(sal),2), trunc(sqrt(sal),2) from prac4;
--8.  In which month was the hiredate of ADAMS? (give the name of the month) (date functions)
SELECT to_char(hiredate, 'mm-dd-yyyy') AS hire_year_adams from prac4 where ename='ADAMS'; 
--to_date: Purpose: Converts a string representation of a date into a date data type.
--to_char: Purpose: Converts a date or number to a string (character) format.

--9.  Give the number of days since ADAMS's hiredate. (date arithmetic)
select trunc(Sysdate - hiredate) as dayhire from prac4 where ename='ADAMS';
--10. List the employees whose hiredate was Tuesday. (Take care of the length of name_day string!) (to_char function)
SELECT ename from prac4 where to_char(hiredate, 'Day') like 'Tuesday%';
--11. Give the manager-employee name pairs where the length of the two names are equal. (length function)
SELECT e1.ename, e1.mgr AS employee, e2.ename, e2.empno AS manager
FROM prac4 e1
JOIN prac4 e2 ON e1.mgr = e2.empno 
WHERE LENGTH(e1.ename) = LENGTH(e2.ename);
--12. List the employees whose salary is in category 1. (see Sal_cat table)
create table sal4 as select * from nikovits.sal_cat;
select * from sal4;
select * from prac4;
SELECT e.empno, e.ename, e.sal
FROM prac4 e
JOIN sal4 s ON e.sal BETWEEN s.lowest_sal AND s.highest_sal
WHERE s.category = 1;
--13. List the employees whose salary category is an even number. (mod function)
select e.ename, e.empno, e.sal, s.category
from prac4 e 
join sal4 s on e.sal BETWEEN s.lowest_sal AND s.highest_sal 
where mod(s.category,2)=0;
--14. Give the number of days between the hiredate of KING and the hiredate of JONES.

--15. Give the name of the day (e.g. Monday) which was the last day of the month in which KING's hiredate was. (last_day function)
--16. Give the name of the day (e.g. Monday) which was the first day of the month in which KING's hiredate was. (trunc function)
--17. Give the names of employees whose department name contains a letter 'C' and whose salary category is >= 4.

