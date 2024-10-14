--
--Compulsory exercise for practice 4
--===========================================================================================
--Create a table in both databases (ullman, aramis) whose name is PRACTICE4, and it contains 
--the deptno and location of the departments and the average salary on the department. (Depto, Loc, AvgSal).
--Deadline: 23:59 on the day of the practice.
--
--You can check whether you completed the compulsory exercises with the following SQL query:
--
--SELECT object_name "TABLE", created FROM user_objects 
--WHERE object_type='TABLE' AND object_name LIKE 'PRACTICE%'
--ORDER BY object_name;
--*******************************************************************************************
--
--Aggregation, Grouping
-----------------------
--Study the following queries and their results.
--You can see the behaviour of aggregation functions for empty sets and NULL values. 
--
--SELECT ename, sal, comm FROM emp WHERE ename LIKE '%O%' AND deptno < 50;
--ename   sal   comm
---------------------
--JONES  2975   null
--SCOTT  3000   null
--FORD   3000   700
--
--SELECT count(comm), count(*), count(sal), count(distinct sal), sum(sal), sum(distinct sal)
--FROM emp WHERE ename LIKE '%O%' AND deptno < 50;
----------------------------------------------
--1   3   3   2   8975   5975
--
---- Behaviour of Aggregators for empty set.
--SELECT MIN(sal), COUNT(sal), COUNT(*) FROM emp WHERE empno = 0;
-----------------------------------------------
--null   0   0
--
--Queries for the following tables (see description of the columns in previous exercises)
----------------------------------
--NIKOVITS.EMP (empno, ename, job, mgr, hiredate, sal, comm, deptno)
--NIKOVITS.DEPT(deptno, dname, loc)
--NIKOVITS.SAL_CAT(category, lowest_sal, highest_sal)
-----------------------------------------------------
--
--Give the following queries in SQL and extended relational algebra (see Relax_4Tables.txt)
--
--1.  Give the maximal salary. [max_sal]
create table pr5 as select * from nikovits.emp;
create table sal5 as select * from nikovits.sal_cat;
select Max(sal) max_sal from pr5;
--2.  Give the sum of all salaries. [sum_sal]
select sum(sal) sumsal from pr5;
--3.  Give the summarized salary and average salary on department 20. [sum_sal, avg_sal]
select sum(sal), avg(sal) from pr5 where deptno = 20;
--4.  How many different jobs do we have in the emp table? [num_jobs]
select count(distinct job) as distinct_jobs from emp;
--5.  Give the number of employees whose salary is greater than 2000. [num_emps]
select count(ename) as numemp from emp where sal>2000;
--6.  Give the average salary by departments. [deptno, avg_sal]
select deptno, avg(sal) as salavgdep from emp group by deptno;
--7.  Give the location and average salary by departments. [deptno, loc, avg_sal]
select dep.deptno, dep.loc, avg(sal) avgsal from emp e, dept dep group by dep.deptno, dep.loc;
--8.  Give the number of employees by departments. [deptno, num_emps]
    select deptno, count(*) numpemp from emp group by deptno;
--9.  Give the average salary by departments where this average is greater than 2000. [deptno, avg_sal]
    select deptno, avg(sal) avg2k from emp group by deptno having avg(sal) >2000;
--10. Give the average salary by departments where the department has at least 4 employees. [deptno, avg_sal]
    select avg(sal) avgsal4, deptno from emp group by deptno having count(distinct ename)>=4;
--11. Give the average salary and location by departments where the department has at least 4 employees. [deptno, loc, avg_sal]
    select avg(sal) avgsal4, dept.loc from emp, dept group by dept.loc having count(ename)>=4;
--12. Give the name and location of departments where the average salary is greater than 2000. [dname, loc]
  SELECT d.dname, d.loc
FROM dept d
JOIN emp e ON d.deptno = e.deptno
GROUP BY d.dname, d.loc
HAVING AVG(e.sal) > 2000;
--13. Give the salary categories where we can find exactly 3 employees. [category]
    select s.sal_cat from sal5 s join emp e on s.sal_cat = e.sal_cat 
    group by s.sal_cat having count(*)=3;
--14. Give the salary categories where the employees in this category work on the same department. [category]

--15. List the department number, department name and location for the departments having an employee with salary category 1. 
--    (deptno, dname, loc)

--16. List the department number, department name and location for the departments having at least two employees with salary category 1. 
--    (deptno, dname, loc)

--17. List the number of employees whose empno is an even number and list the number whose empno is odd. [parity, num_of_emps]

--18. List the number of employees and average salary by jobs. Print the average salary with a character string 
--    containing '#'-s where one '#' denotes 200. So if the average is 600, print '###'. [job, num_emps, avg_sal, char_str]

--19. List the department number, department name, number of employees and summarized salaries for all departments
--    even for those having no employees (for these the summarized salary should be 0). List the previous only for the departments
--    where the summarized salary is less than 10000. (Deptno, Dname, Num_of_emp, Sum_sal)
