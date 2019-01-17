/* backup
create table t_tlb_trade_update20180731
select * from t_tlb_trade a
where exists(select null from t_tlb_trade_back_second as b where a.i_order_no = b.i_order_no)
and ((a.i_close_price = a.i_sl and a.c_gain = 'Y') or (a.i_close_price = a.i_tp and a.c_gain = 'N'))
order by a.d_close_time desc;

create table t_tlb_account_update20180731
select * from t_tlb_account as a
where exists(select null from t_tlb_trade_update20180731 as b where a.c_account = b.c_account);
*/

start TRANSACTION

update t_tlb_account a,
(select a.c_account, sum(a.c_gain_amount) as amount
from t_tlb_trade a
where exists(select null from t_tlb_trade_back_second as b where a.i_order_no = b.i_order_no)
and ((a.i_close_price = a.i_sl and a.c_gain = 'Y') or (a.i_close_price = a.i_tp and a.c_gain = 'N'))
group by a.c_account)b
set a.i_balance = a.i_balance - b.amount, a.i_free_margin = a.i_free_margin - b.amount
where a.c_account = b.c_account 


update t_tlb_trade a SET
a.c_gain = case when a.i_close_price = a.i_sl then 'N' else 'Y' end,
a.c_gain_amount = case when a.i_close_price = a.i_sl then 0 else a.i_lots * 50 end
where exists(select null from t_tlb_trade_back_second as b where a.i_order_no = b.i_order_no)
and ((a.i_close_price = a.i_sl and a.c_gain = 'Y') or (a.i_close_price = a.i_tp and a.c_gain = 'N'))

update t_tlb_account a,
(select a.c_account, sum(a.c_gain_amount) as amount
from t_tlb_trade a
where exists(select null from t_tlb_trade_update20180731 as b where a.i_order_no = b.i_order_no)
group by a.c_account)b
set a.i_balance = a.i_balance + b.amount, a.i_free_margin = a.i_free_margin + b.amount
where a.c_account = b.c_account 

/* check

select * from t_tlb_trade a
where exists(select null from t_tlb_trade_update20180731 b where a.i_order_no = b.i_order_no)

select * from t_tlb_trade a
where exists(select null from t_tlb_trade_back_second as b where a.i_order_no = b.i_order_no)
and ((a.i_close_price = a.i_sl and a.c_gain = 'Y') or (a.i_close_price = a.i_tp and a.c_gain = 'N'))
order by a.d_close_time desc;

select count(1) from t_tlb_account as a
where exists( select null from t_tlb_account_update20180731 as b where a.c_account =  b.c_account)
and a.i_balance != a.i_free_margin + a.i_margin;

select count(1) from t_tlb_account as a join 
( 
select a.c_account, sum(a.i_lots * 10000) as amount
from t_tlb_trade a
where a.c_closed = 'N'
group by a.c_account
)b on a.c_account = b.c_account
where exists( select null from t_tlb_account_update20180731 as b where a.c_account =  b.c_account)
and  a.i_margin != b.amount;

*/


-- commit
ROLLBACK
