create table t_tlb_accountback20180802
select * from t_tlb_account a
where exists( 
		select null from t_tlb_tradeback20180802 as b where a.c_account = b.c_account
);

start TRANSACTION
update t_tlb_trade set c_closed = 'N'
where d_close_time >= '2018-08-01 15:17:58' and d_close_time <='2018-08-02 04:10:10' and c_account not in('18900669','1890047') order by d_close_time desc;

select * from t_tlb_account a
where exists( 
		select null from t_tlb_tradeback20180802 as b where a.c_account = b.c_account
);
select * from t_tlb_trade where d_close_time >= '2018-08-01 15:17:58'  and d_close_time <='2018-08-02 04:10:10' and c_account not in('18900669','1890047') order by d_close_time desc;
select * from t_tlb_accountback20180802 a
COMMIT