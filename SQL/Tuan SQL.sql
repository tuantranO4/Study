create table emp
as select * from nikovits.emp;


select * from emp e, emp m 
where e.mgr=m.empno --join 2 table, e and m can be consider as instance of the table(?)
