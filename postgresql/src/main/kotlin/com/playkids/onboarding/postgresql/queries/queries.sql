--money spent by each region
select p.region, sum(p.money_spent) as money_spent from profiles p
group by p.region

--percentage of paying users
select (count(*) * 100/(select count(*) from profiles)) as paying_users from profiles p
where p.money_spent > 0

--avg value spent by paying users
select avg(money_spent) as avg_value from profiles p
where p.money_spent > 0

--order regions by avg spent by paying users
select p.region, avg(p.money_spent) as avg_money_spent from profiles p
where p.money_spent > 0
group by p.region
order by avg_money_spent desc