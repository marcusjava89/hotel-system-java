Respostas da avaliação de banco de dados

1) select count(*) from customers;

2) select count(*) from rooms;

3) select count(*) from reservations where status in ('scheduled','in_use');

4) select count(*) from rooms r where r.id not in (select room_id from reservations where status = 'in_use');

5) select count(*) from reservations where status = 'in_use';

6) select count(*) from reservations where checkin > current_date;

7) select * from rooms order by price desc limit 1;

8) select r.id, r.room_number, count(*) as cancel_count from reservations res join rooms r 
on res.room_id = r.id where res.status = 'canceled' group by r.id, r.room_number order by cancel_count desc limit 1;

9) select r.id, r.room_number, count(distinct res.customer_id) as total_customers
from rooms r left join reservations res on r.id = res.room_id group by r.id, r.room_number;

10) select r.id, r.room_number, count(*) as total_occupations from reservations res join rooms r 
on res.room_id = r.id group by r.id, r.room_number order by total_occupations desc limit 3;

11) select c.id, c.name, count(*) as total_reservations from reservations res join customers c 
on res.customer_id = c.id group by c.id, c.name order by total_reservations desc limit 10;

12) select avg(r.price) as average_revenue from reservations res join rooms r on res.room_id = r.id 
where res.status in ('finished','in_use');
