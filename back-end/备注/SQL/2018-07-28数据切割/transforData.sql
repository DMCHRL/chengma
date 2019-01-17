-- ------------准备处理数据-------------------------------------------

/* 创建交易插入表
create table t_tlb_trade_insert20180728
select `c_id`, `i_order_no`, `c_order_type`, `c_ordered`, `c_symbol`, `i_lots`, `i_sl`, `i_tp`, `i_open_price`, `d_open_time`, `i_close_price`, `d_close_time`, `c_closed`, `c_gain`, `c_gain_amount`, `c_calc_sum`, `c_account`, `d_create_at`
from t_tlb_trade_back_second as a
where exists(select null from t_tlb_account as b where a.c_account = b.c_account)
and not exists(select null from t_tlb_trade as c where a.i_order_no = c.i_order_no)
*/

/* 创建更新表
create table t_tlb_trade_update20180728
select `c_id`, `i_order_no`, `c_order_type`, `c_ordered`, `c_symbol`, `i_lots`, `i_sl`, `i_tp`, `i_open_price`, `d_open_time`, `i_close_price`, `d_close_time`, `c_closed`, `c_gain`, `c_gain_amount`, `c_calc_sum`, `c_account`, `d_create_at`
from t_tlb_trade_back_second as a
where exists(select null from t_tlb_account as b where a.c_account = b.c_account)
and exists(select null from t_tlb_trade as c where a.i_order_no = c.i_order_no and a.c_closed != c.c_closed)
*/

/* 创建帐号更新表表
create table t_tlb_account_update20180728
select a.* 
from t_tlb_account_back a join t_tlb_account b on a.c_account = b.c_account
*/


/* 创建交易记录备份表
create table t_tlb_account_back20180728
select a.* 
from t_tlb_account a
*/

/* 创建帐号更新表表
create table t_tlb_trade_back20180728
select a.* 
from t_tlb_trade a 
*/

-- ------------开始处理数据-------------------------------------------
start TRANSACTION

insert into t_tlb_trade
select * from t_tlb_trade_insert20180728;

/* 检查数据
select a.c_account, a.c_gain, a.c_gain_amount, a.i_close_price, a.d_close_time,
b.c_gain, b.c_gain_amount, b.i_close_price, b.d_close_time
from t_tlb_trade a, t_tlb_trade_update20180728 b 
where a.i_order_no = b.i_order_no ;
*/

update t_tlb_trade a, t_tlb_trade_update20180728 b 
set a.c_gain = b.c_gain, a.c_gain_amount = b.c_gain_amount, a.i_close_price =b.i_close_price, a.d_close_time = b.d_close_time
, a.c_closed = b.c_closed
where a.i_order_no = b.i_order_no ;

/* 检查数据
select a.c_account, a.c_account_name, a.i_balance, a.i_profit, b.i_balance, b.i_profit
from t_tlb_account a, t_tlb_account_update20180728 b
where a.c_account = b.c_account
and a.c_account not in( '18900668', '1890038')
*/

update t_tlb_account a, t_tlb_account_update20180728 b
set a.i_balance = b.i_balance, a.i_profit = b.i_profit
where a.c_account = b.c_account
and a.c_account not in( '18900668');


select a.c_account, a.i_balance, ifnull( b.amount, 0) as i_margin, a.i_balance - ifnull( b.amount, 0) as i_free_margin
from t_tlb_account a left join 
(select a.c_account, sum(a.i_lots * 10000) as amount
from t_tlb_trade a
where exists(select null from t_tlb_account_update20180728 as b where a.c_account = b.c_account)
and a.c_closed != 'Y'
group by a.c_account) b on a.c_account = b.c_account
order by a.c_account



update t_tlb_account a left join 
(select a.c_account, sum(a.i_lots * 10000) as amount
from t_tlb_trade a
where exists(select null from t_tlb_account_update20180728 as b where a.c_account = b.c_account)
and a.c_closed != 'Y'
group by a.c_account) b on a.c_account = b.c_account
set i_margin = ifnull( b.amount, 0) , a.i_free_margin = a.i_balance - ifnull( b.amount, 0)
where a.c_account not in( '18900668');



commit
rollback;










