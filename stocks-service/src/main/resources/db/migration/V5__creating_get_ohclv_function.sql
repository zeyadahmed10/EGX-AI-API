drop function if exists get_ohclv_proc(p_period varchar(255),
									 p_reuters varchar(255),
									 p_interval varchar(255));
create or replace function get_ohclv_proc(p_period varchar(255),
									 p_reuters varchar(255),
									 p_interval varchar(255))
returns SETOF historical_stock
language plpgsql as
$func$
begin
	return query
	select last(id, res.time) id, last(curr_price, res.time) curr_price, last(highest, res.time) highest,
	last(lowest, res.time) lowest, last(open, res.time) open, last(percentage_of_change, res.time) percentage_of_change,
	last(prev_close, res.time) prev_close, last(rate_of_change, res.time) rate_of_change, last(value, res.time) value,
	last(volume, res.time) volume, cast(time_bucket(cast(p_period as interval), res.time) as timestamp(6)) time,last(equity_id, res.time) equity_id
	from (

		select s.* from historical_stock s
		join equity e on e.id=s.equity_id
		where e.reuters_code=p_reuters
		) as res

	where res.time> NOW() - cast(p_interval as interval)
	group by time;
end;
$func$

