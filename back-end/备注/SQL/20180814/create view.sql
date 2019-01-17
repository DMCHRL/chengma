CREATE or REPLACE VIEW t_tlb_commission_trace_view AS 
SELECT i_order_no, c_money as company_money , 0 proxy_money
from t_tlb_commission_trace a 
join t_tlb_commission b on a.t_commission_id = b.c_id
join jhi_user c on b.c_user_id = c.id
where c.department = 'company'
union ALL
SELECT i_order_no, 0 company_money ,c_money as proxy_money
from t_tlb_commission_trace a 
join t_tlb_commission b on a.t_commission_id = b.c_id
join jhi_user c on b.c_user_id = c.id
where c.department = 'proxy'


CREATE OR REPLACE VIEW t_tlb_commission_trace_group_view AS 
SELECT a.i_order_no, sum(a.company_money) company_money, sum(a.proxy_money) proxy_money
from t_tlb_commission_trace_view a
group by a.i_order_no
