select * from emp;
select * from dept;
CREATE OR REPLACE PROCEDURE day_avg(d varchar2) IS
    var_count_emp number;
    var_avg_sal number;
begin
    select count(*), avg(sal) 
    into var_count_emp, var_avg_sal
    from emp
    where to_char(hiredate, 'Day', 'nls_date_language=english') like d || '%';
    dbms_output.put_line('Number of emps: ' || var_count_emp || ', Average sal: ' || var_avg_sal);
end;

execute day_avg('Thursday');

set serveroutput on; --important to see the result
drop table practice9;
create table practice9 as select * from emp;
CREATE OR REPLACE PROCEDURE upd_cat(p integer) IS
    cursor emp_curs is
        select * from practice9 join sal_cat on sal
        between lowest_sal and highest_sal where category=p
        for update of sal;
begin
    for rec in emp_curs loop
    update practice9 p_tab
    set sal=sal+(select min(sal) from practice9 where deptno=p_tab.deptno)
    where current of emp_curs;
    end loop;
    select avg(sal)
    into var_arg_sal from practice9;
end;
execute upd_cat(2);select * from practice9;
commit;