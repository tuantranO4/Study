--1
create or replace procedure examp1 as
e_avg number;
begin
select avg(sal) into e_avg from emp;
dbms_output.put_line(e_avg);
end;
/

call exampl();
accept ename char prompt 'give employee';
begin
dbms_output.put_line('&ename');
end;
/

--2
accept age number prompt 'YOUR AGE: ';
create or replace procedure examp as wrong_age exception;
begin
if '&age' <0 or '&age' >100 then 
raise wrong_age;
end if;
dbms_output.put_line('your age: & age');

exception 
when wrong_age THEN
dbms_output.put_line('your age: &age is wrong!');
end;
/
call examp();

--3
select * from dept;

ACCEPT department_name CHAR PROMPT 'Department: ';
CREATE OR REPLACE PROCEDURE print_jobs(e_job VARCHAR2) AS
    CURSOR cs IS -- perform selection
        SELECT DISTINCT job
            FROM emp e, dept d
        WHERE 
            e.deptno = d.deptno AND
            d.dname = e_job
        ORDER BY 1;
    result VARCHAR2(100); -- declared variable will be used
BEGIN
    FOR i IN cs LOOP
        result := result || i.job || '-';
    END LOOP;
    DBMS_OUTPUT.PUT_LINE(SUBSTR(result, 0, LENGTH(result) - 1));
END;
/
 
CALL print_jobs('RESEARCH');

select * from emp2;
--4
--write a procedure what add 1000 to the salary those emp whose working on dep 20.
--in the procedure update the rows one by one.
create or replace procedure addsal as
cursor t is select * from emp2 where deptno=20;
begin
for i in t loop
update emp2
set sal=100+sal
where empno=i.empno;
end loop;
end;
/
call addsal();
select * from emp2 where deptno =20;;