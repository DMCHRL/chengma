//+------------------------------------------------------------------+
//|                                                         txfx.mq4 |
//|                                                            totti |
//|                                           https://www.txasfx.com |
//+------------------------------------------------------------------+
#include <stdlib.mqh>
#include <MQLMySQL.mqh>
#property copyright "totti"
#property link      "https://www.txasfx.com"
#property version   "1.00"
#property strict
//+------------------------------------------------------------------+
//| Expert initialization function                                   |
//+------------------------------------------------------------------+

string ip="127.0.0.1";string db_user="root";string db_pass="123";
//string ip="47.52.199.109";string db_user="dev";string db_pass="dev123";

//string db_pass="123456qianL";

string db_name="crm";

extern double EurusdStopLose=420;//欧美止损点数
extern double EurusdStopEarn=610;//欧美止盈点数

extern double XauusdStopLose=420;//黄金止损点数

extern double XauusdStopEarn=670;//黄金止盈点数




int OnInit()
  {
  
 
  
  
  
  
  
//--- create timer
   EventSetTimer(2);
      
//---
   return(INIT_SUCCEEDED);
  }
//+------------------------------------------------------------------+
//| Expert deinitialization function                                 |
//+------------------------------------------------------------------+
void OnDeinit(const int reason)
  {
//--- destroy timer
   EventKillTimer();
      
  }
//+------------------------------------------------------------------+
//| Expert tick function                                             |
//+------------------------------------------------------------------+
void OnTick()
  {
//---
   
   
   
   
   
   
   
   
   
  }
//+------------------------------------------------------------------+
//| Timer function                                                   |
//+------------------------------------------------------------------+
void OnTimer()
  {
//---
     int DB = MySqlConnect(ip, db_user, db_pass, db_name, 3306, 0, "");
   if (DB == -1) { 
   
       Print ("连接失败!错误信息: "+MySqlErrorDescription);
       
   } else { 
   
        Print ("连接成功#",DB);
        
        Print("OrdersTotal：================", OrdersTotal());
        string symbol = Symbol();
        
        //string sql="select id,lots,type from ry_bbb_orderdetail where is_real='0'";
        string sql="select a.c_id, a.i_lots, a.c_order_type, b.c_comment  from t_tlb_trade a, t_tlb_account b where a.c_ordered = 'N' and a.c_symbol = '" + symbol + "' and a.c_account = b.c_account and b.c_group = 'TXA3' and b.c_ea_group = 'A' ";
       // Print ("insql:"+sql);
        int cursor = MySqlCursorOpen(DB, sql);
       // Print ("游标:"+cursor);
        
        if (cursor >= 0){
            int rows = MySqlCursorRows(cursor);
            
            Print (rows, " row(s) selected.");
            
           for (int i=0; i<rows; i++){
           
               if (MySqlCursorFetchRow(cursor)){
               
                   string id = MySqlGetFieldAsString(cursor, 0); // 编号
                   double lots = MySqlGetFieldAsDouble(cursor, 1); // 手数
                   string type = MySqlGetFieldAsString(cursor, 2); // 交易类型
                   string comment = MySqlGetFieldAsString(cursor, 3); // 交易类型
                   
                   Print ("id:"+id + " lots ======== " + lots + " type ========" + type + " comment =========" + comment); 
                   
                   int ticket;
                   double openprice;
                   double rstopwin;
                   double rstoplost;
                   datetime rtime;
                   
                  
                  if((type=="BUY" && "Y" != comment)
                        || (type=="SELL" && "Y" == comment)){
                         //执行卖单交易
                         openprice=Ask;
						 if(symbol == "EURUSD"){
							rstopwin=Ask+EurusdStopEarn*Point;
							rstoplost=Ask-EurusdStopLose*Point;
						 }else if(symbol == "XAUUSD"){
							rstopwin=Ask+XauusdStopEarn*Point;
							rstoplost=Ask-XauusdStopLose*Point;
						 }
                         ticket=OrderSend(Symbol(), OP_BUY, lots, openprice, 3, rstoplost , rstopwin, comment , comment, 0, Green);
   
                   }else{
                        openprice=Bid;
						 if(symbol == "EURUSD"){
							rstopwin=Bid-EurusdStopEarn*Point;
							rstoplost=Bid+EurusdStopLose*Point;
						 }else if(symbol == "XAUUSD"){
							rstopwin=Bid-XauusdStopEarn*Point;
							rstoplost=Bid+XauusdStopLose*Point;
						 }
                        
                        ticket=OrderSend(Symbol(), OP_SELL, lots, openprice, 3, rstoplost, rstopwin, comment , comment, 0, Red);
                   }
                   
                   
                   Print ("ticket:"+ticket); 
                   Print ("TimeCurrent:"+TimeCurrent());
                   rtime = TimeCurrent();
                  
                  if(OrderSelect(ticket, SELECT_BY_TICKET)==true){
                          openprice= OrderOpenPrice();
                          rtime=OrderOpenTime();
                          rstopwin=OrderTakeProfit();
                          rstoplost=OrderStopLoss();
                          if("Y" == comment){
                             rstopwin=OrderStopLoss();
                             rstoplost=OrderTakeProfit();
                          }
                   }
                   
                   if(ticket!=-1){
                      string upsql="update t_tlb_trade set c_ordered='Y', i_order_no = " + ticket + ", i_open_price = " + openprice 
                                 + ", d_open_time = '" + rtime + "', i_sl = " + rstoplost + ", i_tp = " + rstopwin + " where c_id='"+id+"'";
                      Print ("upsql:"+upsql);
                                  
                      bool result = MySqlExecute(DB, upsql);
                      
                      Print ("bool:"+result);
                   }
                   
                   
               }
               
            }
            MySqlCursorClose(cursor); // NEVER FORGET TO CLOSE CURSOR !!!
    
    
       }else{
       
            Print ("Cursor opening failed. Error: ", MySqlErrorDescription);
        
        
       }
     
     
       MySqlDisconnect(DB);
    
   
  
  
    }
  
  }
//+------------------------------------------------------------------+
