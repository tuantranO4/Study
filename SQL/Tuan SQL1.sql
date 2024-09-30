create table practice4 as
select deptno, round(avg(sal),2) avg_sal
from emp
group by deptno;

