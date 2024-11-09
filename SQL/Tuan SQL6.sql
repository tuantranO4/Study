--Compulsory exercise for practice 6
--===========================================================================================
--Create a table in both databases (ullman, aramis) whose name is PRACTICE6, and it contains 
--the same data as NIKOVITS.DEPT (table with departments). Then delete the departments 
--from this table which have an employee with salary category 2. Don't forget to run
--a COMMIT statement after deletion.
--
--You can check whether you completed the compulsory exercises with the following SQL query:
--
--SELECT object_name "TABLE", created FROM user_objects 
--WHERE object_type='TABLE' AND object_name LIKE 'PRACTICE%'
--ORDER BY object_name;
--*******************************************************************************************
--
--Exercises for DELETE, INSERT, UPDATE
--------------------------------------
--
--NIKOVITS.EMP (empno, ename, job, mgr, hiredate, sal, comm, deptno)
--NIKOVITS.DEPT(deptno, dname, loc)
--NIKOVITS.SAL_CAT(category, lowest_sal, highest_sal)
--
--/* You should create a copy from the tables before modifying the data

CREATE TABLE copy_emp AS SELECT * FROM nikovits.emp;
CREATE TABLE dept AS SELECT * FROM nikovits.dept;
--UPDATE emp2 ...
--Check the data after modification: SELECT * FROM emp2; SELECT * FROM dept2;         
--Drop the modified tables: DROP TABLE emp2; DROP TABLE dept2;
--
--*/
--
--DELETE
--------
--1. Delete the employees whose commission is null.
delete from copy_emp
where comm is null;
rollback; --It lets a user undo those transactions that aren't saved yet in the database

--2. Delete the employees whose hiredate was before 1982.01.01.
delete from copy_emp
where copy_emp.hiredate<to_date('1982.01.01','YYYY.MM.DD');
rollback;

create table pr8(num integer); --attendance
--3. Delete the employees whose department's location is DALLAS.
delete
from copy_emp
where copy_emp.empno in (
select e.empno
from copy_emp e, dept d where e.deptno=d.deptno and d.loc='DALLAS');--delete doesn't work as it's only for 1 table, we need returned subquery.
rollback;
--4. Delete the employees whose salary is less than the average salary.
delete
from copy_emp
where sal < (select avg(sal) from copy_emp);
rollback;
--5. Delete the employees whose salary is less than the average salary on his department.

--6. Delete the employee (employees) whose salary is the greatest.

--7. Delete the departments which have an employee with salary category 2.

--8. Delete the departments which have at least two employees with salary category 2.

--
--INSERT
--------
--9. Insert a new employee with the following values:
--   empno=1, ename='Smith', deptno=10, hiredate=sysdate, salary=average salary in department 10.
--   All the other columns should be NULL.
--a) Insert the row with the 'VALUES' keyword
--b) Insert the row with a SELECT query without 'VALUES' keyword.
insert into copy_emp(empno, ename, deptno, hiredate,sal)
values(1, 'Smith', 10, sysdate,
(select avg(sal) from emp2 group by deptno having deptno=10));
commit;
rollback; --doesnt work after commit
select * from copy_emp;

--UPDATE
--------
--10. Increase the salary of the employees in department 20 with 20%.
update emp2 --only 1 table like delete
set sal = 1.2*sal
where deptno = 20;
rollback;
--11. Increase the salary with 500 for the employees whose commission is NULL or whose 
--    salary is less than the average.
update copy_emp
set sal = sal+500
where comm is null or sal <(select avg(sal) from copy_emp);
rollback;

--12. Increase the commission of all employees with the maximal commission.
--    If an employee has NULL commission, treat it as 0.
select NVL(comm,0) from copy_emp;
update copy_emp
set
comm = nvl(comm,0) + (select max(comm) from copy_emp);
rollback;
--13. Modify the name of the employee with the lowest salary to 'Poor'.
update copy_emp
set ename='Poor'
where sal = (select min(sal) from copy_emp);
--14. Increase the commission of the employees with 3000, who has at least 2 direct subordinates.
--    If an employee has NULL commission, treat it as 0.

--15. Increase the salary of those employees who has a subordinate. The increment is the minimal salary.

--16. Increase the salary of the employees who don't have a subordinate. The increment is
--    the average salary of their own department.
