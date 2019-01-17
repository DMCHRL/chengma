create table t_tlb_commission_trace_addAmount
select b.d_close_time, b.c_account, b.i_lots * 5 addAmount,  a.*
from t_tlb_commission_trace a, t_tlb_trade b 
where a.t_commission_id = '2c9f707364cbf5cf0164cf6a74c7004f'
and a.i_lot_amount = 10
and a.i_order_no = b.i_order_no
and b.d_close_time > '2018-07-28'
order by b.d_close_time
;

select sum(addAmount) from t_tlb_commission_trace_addAmount;

select * FROM t_tlb_commission_trace WHERE t_commission_id = '2c9f707364cbf5cf0164cf6a74c7004f';

select SUM(c_money) FROM t_tlb_commission_trace WHERE t_commission_id = '2c9f707364cbf5cf0164cf6a74c7004f';


start TRANSACTION
create table t_tlb_commission_traceback20180814
select  a.*
from t_tlb_commission_trace a, t_tlb_trade b 
where a.t_commission_id = '2c9f707364cbf5cf0164cf6a74c7004f'
and a.i_lot_amount = 10
and a.i_order_no = b.i_order_no
and b.d_close_time > '2018-07-28'
order by b.d_close_time


update t_tlb_commission_trace a, t_tlb_commission_trace_addAmount b
set a.c_money = a.c_money + b.addAmount, a.i_lot_amount = 15
where a.c_id = b.c_id

select * FROM t_tlb_commission WHERE c_id = '2c9f707364cbf5cf0164cf6a74c7004f';

select SUM(c_money) FROM t_tlb_commission_trace WHERE t_commission_id = '2c9f707364cbf5cf0164cf6a74c7004f';

COMMIT;