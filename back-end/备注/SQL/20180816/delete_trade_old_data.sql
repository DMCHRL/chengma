create table t_tlb_trade20180816delete_from_old_data
select * from t_tlb_trade a where 
NOT exists(select null from t_tlb_account as b where a.c_account = b.c_account)
and exists(select null from t_tlb_trade_back_second as c where a.i_order_no = c.i_order_no)

start TRANSACTION
delete a from t_tlb_trade a
where exists(select * from t_tlb_trade20180816delete_from_old_data b where a.c_id = b.c_id);

select count(1) from t_tlb_trade;
-- COMMIT
ROLLBACK;