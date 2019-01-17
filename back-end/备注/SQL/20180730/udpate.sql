create table t_tlb_trade_update20180730
select * from t_tlb_trade where c_closed = 'Y' and ifnull(c_gain, '')  = ''; 


update t_tlb_trade set c_gain = 'N', c_gain_amount = 0
where c_closed = 'Y' and ifnull(c_gain, '')  = ''; 


start TRANSACTION
update t_tlb_trade a , t_tlb_trade_back_second as b set a.i_sl = b.i_tp, a.i_tp = b.i_sl
where a.i_order_no = b.i_order_no

select count(1)
from  t_tlb_trade a , t_tlb_trade_back_second as b 
where a.i_order_no = b.i_order_no
and ((a.c_order_type ='BUY' AND a.i_sl > a.i_tp) or (a.c_order_type ='SELL' AND a.i_sl < a.i_tp))
-- COMMIT
ROLLBACK