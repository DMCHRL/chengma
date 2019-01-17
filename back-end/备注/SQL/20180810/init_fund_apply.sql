-- init 出入金
create table t_tlb_fund_apply_init
select replace(uuid(), '-', '') c_id, 3 c_user_id, CONCAT('system-', a.c_account) c_account, 'IN' c_fund_type, a.i_in i_amount, 'PASSED'  c_status, '2018-07-28' d_create_at, 
'2018-07-28' d_approved_at, 3 c_approved_id, 'data init from source system' c_remark, '' c_transaction_id, 0 i_cny_amount
from t_tlb_account_back a
where exists(select null from t_tlb_account as b where a.c_account = b.c_account)
and i_in > 0
union ALL
select replace(uuid(), '-', '') c_id, 3 c_user_id, CONCAT('system-', a.c_account) c_account, 'OUT' c_fund_type, a.i_out i_amount, 'PASSED'  c_status, '2018-07-28' d_create_at, 
'2018-07-28' d_approved_at, 3 c_approved_id, 'data init from source system' c_remark, '' c_transaction_id, 0 i_cny_amount
from t_tlb_account_back a
where exists(select null from t_tlb_account as b where a.c_account = b.c_account)
and i_out > 0
;

INSERT INTO `crm`.`t_tlb_fund_apply` (`c_id`, `c_user_id`, `c_account`, `c_fund_type`, `i_amount`, `c_status`, 
`d_create_at`, `d_approved_at`, `c_approved_id`, `c_remark`, 
`c_transaction_id`, `i_cny_amount`) 
select * from t_tlb_fund_apply_init;