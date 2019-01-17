CREATE TABLE t_tlb_trade_delete20180816
select * from t_tlb_trade a where 
NOT exists(select null from t_tlb_account as b where a.c_account = b.c_account)
and not exists(select null from t_tlb_trade_back_second as c where a.i_order_no = c.i_order_no)


start TRANSACTION
delete a from t_tlb_trade a
where exists(select * from t_tlb_trade_delete20180816 b where a.c_id = b.c_id);

select count(1) from t_tlb_trade;
COMMIT
ROLLBACK