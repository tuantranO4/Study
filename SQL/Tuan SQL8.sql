--PL/SQL
--Procedures and function
Create or replace procedure hello as
begin 
    dbms_output.put_line('Hello World!');
end;

/
Call hello()
execute hello;

begin
    hello;
end;
/

--1
Create or replace procedure my_sum(a integer, b integer) as result integer;
begin
    result:=a+b;
    dbms_output.put_line('Result: ' || result);
end;
/

Call my_sum(5,6); --this call... for procedure
/


--2
Create or replace function even_num(n integer) return integer as e_sum integer:=0;
begin
    for i in 1..n loop
        if mod(i,2) = 0 then
            e_sum:=e_sum+i;
        end if;
    end loop;
    return e_sum;
end;

/
select even_num(5) from dual; 


begin 
    dbms_output.put_line(even_num(5));
end;    


--3
/*
Write a function which decides if the parameter number is prime or not. 
   In case of yes (no) the function returns 1 (0).
*/


CREATE OR REPLACE FUNCTION prim(n integer) RETURN number as outbool integer:=1;
begin
    for i in 2..(n-1) loop
        if mod(n,i)=0 then
        outbool:=0;
        end if;
    end loop;
    return outbool;
end;
/

select prim(7) from dual; --select... from dual: call function
/


--4
/* Write a function which prints out the n-th Fibonacchi number. 
   fib1 = 0, fib2 = 1, fib3 = 1, fib4 = 2 ... etc.
*/
Create or replace FUNCTION fib_rec(x IN INTEGER) RETURN INTEGER IS
    BEGIN
        IF x= 0 then return 0;
        ELSIF x <= 2 THEN
            RETURN 1; 
        ELSE
            RETURN fib_rec(x - 1) + fib_rec(x - 2);  -- Recursion
        END IF;
    END;
/
select fib_rec(10) from dual;

---extra
create or replace procedure print_emp as 
    cursor emp_t is select * from emp;
    emp_row emp%rowtype;
    begin
        for emp_row in emp_t loop
            dbms_output.put_line(emp_row.ename);
        end loop;
    end;
/
call print_emp();
select * from emp;

/*
write a procedure what list the emps whose working on department 20 and salary is greater than 1200.
*/
create or replace procedure find_emp as cursor emp_t is --cursor: whole board
select * from emp;
emp_row emp % rowtype; --emp_row: take row
begin
    for emp_row in emp_t loop
        if emp_row.deptno = 20 and emp_row.sal>1200 then
            dbms_output.put_line(emp_row.ename);
            end if;
        end loop;
    end;
/
call find_emp();
select * from emp;

--5
/* Write a function which returns the greatest common divisor of two integers */
CREATE OR REPLACE FUNCTION gcd(p1 INTEGER, p2 INTEGER) RETURN INTEGER IS
    temp INTEGER;
BEGIN
    IF p2 = 0 THEN 
        RETURN p1; 
    ELSIF p1 = 0 THEN 
        RETURN p2; 
    ELSE
        WHILE MOD(p1, p2) <> 0 LOOP
            temp := p2;
            p2 := MOD(p1, p2);
            p1 := temp;
        END LOOP;
        RETURN p2;
    END IF;
END;
/

