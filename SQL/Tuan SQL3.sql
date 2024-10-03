
CREATE TABLE dept AS SELECT * FROM kotroczo.dept;
CREATE TABLE emp AS SELECT * FROM kotroczo.emp;
 
SELECT * FROM emp;
SELECT * FROM dept;
 
-- 1. List the employees whose salary is greater than 2800
 
SELECT ename
FROM emp
WHERE sal > 2800;
 
-- 2. List the employees working on department 10 or 20
 
SELECT ename
FROM emp
WHERE deptno IN (10, 20);
 
-- 3. List the employees whose commission is greater than 600
 
SELECT ename
FROM emp
WHERE comm > 600;
 
-- 4. List the employees whose comm is not greater than 600
 
SELECT ename
FROM emp
WHERE comm <= 600 OR comm IS NULL;
 
-- 5. List the employees whose comm is not known
 
SELECT ename
FROM emp
WHERE comm IS NULL;
 
-- 6.1 List the jobs of the employees with duplicate
 
SELECT job
FROM emp;
 
-- 6.2 List the jobs of the employees without duplicate
 
SELECT UNIQUE job
FROM emp;
 
SELECT DISTINCT job
FROM emp;
 
-- 7. Give the name and double salary of employees working on the dept 10
 
SELECT ename, sal*2 AS double_salary
FROM emp
WHERE deptno = 10;
 
-- 8. List the ems whose hired date is greater than 1982.01.01
 
SELECT ename
FROM emp
WHERE hiredate > TO_DATE('1982.01.01', 'YYYY.MM.DD');
 
SELECT ename
FROM emp
WHERE hiredate > '01-jan-1982';
 
-- 9. List the ems who doesn't have a manager
 
SELECT ename
FROM emp
WHERE mgr IS NULL;
 
-- 10. List the ems whose name contains a letter 'A'
 
SELECT ename
FROM emp
WHERE ename LIKE '%A%';
 
-- 11. List the ems whose name contains two letter 'L'
 
SELECt ename 
FROM emp
WHERE ename LIKE '%L%L%';
 
-- 13. List the name and sal ord by sal
 
SELECT ename, sal
FROM emp
ORDER BY sal;
 
-- 14. list name and sal ord desc by sal then asc by name
 
SELECT ename, sal
FROM emp
ORDER BY sal DESC, ename ASC;
 
-- 16. List the ems whose manager is KING
 
SELECT * FROM emp;
 
SELECT ename
FROM emp
WHERE mgr IN (
    SELECT empno 
    FROM emp 
    WHERE ename = 'KING'
);
 
-- 17 give the names of ems who are manager of someone but whose job is not manager
SELECT distinct boss.* FROM emp boss, emp subquery
where boss.empno = subquery.mgr
and boss.job <> upper('manager');
-- 18. list the names of emp who has greater sal than his manager.
select employ.*,boss.* from emp employ, emp boss
where (boss.empno=employ.mgr and employ.sal > boss.sal);
--19. list the employees whose mgr's mgr is KING.
select employ.ename from emp employ, emp boss, emp bossking
where bossking.ename = upper('king') and boss.mgr = bossking.empno and employ.mgr = boss.empno;
--20. list the employees whose dep's location is dallas or chicago;
select employ.* from emp employ, dept dept2 
where employ.deptno= dept2.deptno and (dept2.loc = upper('dallas') or dept2.loc = upper('chicago'));