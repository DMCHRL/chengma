
#property copyright ""
#property link""
#import "kernel32.dll"
int CopyFileW(string a0,string a1,int a2);
bool CreateDirectoryW(string a0,int a1);
//========================================================================
input string 跟踪主账号="";
string FILES文件夹路径="";
string 中转路径="";
input string 间隔符="*";
string 中转路径2="";
string FILES文件夹路径2;
input string 货币对名称附加="";
input string comm0="▼跟单功能▼";
input int 跟单允许秒误差=100;
input double 跟单允许点误差=100;
input bool 跟踪止损止盈=true;
input bool 跟踪平仓=true;
input bool 反向跟单=false;
input bool 跟多单=true;
input bool 跟空单=true;
input bool 跟踪挂单=true;
input string comm1="▼单量设置▼";
input double 单量比例=1;
input bool 使用固定单量=false;
input bool 使用净值比例下单=false;
input double 固定单量=0.1;
bool 低于最低单量时使用最低单量=false;
input string comm2="▼持仓设置▼";
input int 最大持单数=10000;
input double 跟主账户单量大于等于N单子=0;
input double 跟主账户单量小于等于M单子=100;
input string comm3="▼注释添加功能▼";
input string 自定义部分备注="";
input string 接收订单需包含备注="";
input string comm4="▼特殊名称设置/跨货币跟单设置▼";
input string comm5="所跟货币对名称：LLG、GOLD、黄金";
input string comm6="所跟货币对名称：XAUUSD";
input bool 货币对名称强制修正开关=false;
input string 所跟货币对名称1 ="";
input string 下单货币对名称1 ="";
input double 特殊跟单单量比例1=1;
input string 所跟货币对名称2 ="";
input string 下单货币对名称2 ="";
input double 特殊跟单单量比例2=1;
input string 所跟货币对名称3 ="";
input string 下单货币对名称3 ="";
input double 特殊跟单单量比例3=1;
input bool 进行尾部价格修饰=false;
input string comm8="▼货币限制功能（一）▼";
input bool 限定可做的货币对=false;
input string 限制可做货币对1 ="";
input string 限制可做货币对2 ="";
input string 限制可做货币对3 ="";
input string 限制可做货币对4 ="";
input string 限制可做货币对5 ="";
input string 限制可做货币对6 ="";
input string 限制可做货币对7 ="";
input string 限制可做货币对8 ="";
input string 限制可做货币对9 ="";
input string 限制可做货币对10="";
string 限制可做货币对[100];
input string comm9="▼货币限制功能（二）▼";
input bool 限定不可做的货币对=false;
input string 限制不可做货币对1="";
input string 限制不可做货币对2="";
input string 限制不可做货币对3="";
input string 限制不可做货币对4="";
input string 限制不可做货币对5="";
input string 限制不可做货币对6="";
input string 限制不可做货币对7="";
input string 限制不可做货币对8="";
input string 限制不可做货币对9="";
input string 限制不可做货币对10="";
string 限制不可做货币对[100];
input string comm10="▼窗口文字显示开关▼";
input bool 是否显示文字标签=true;
input int magic=0;
input string comm11="---------------------";
int X=20;
int Y=20;
int Y间隔=15;
color 标签颜色=Yellow;
int 标签字体大小=10;
ENUM_BASE_CORNER 固定角=0;

int OrderTicketX[200];
string OrderSymbolX[200];
int OrderTypeX[200];
double OrderLotsX[200];
double OrderStopLossX[200];
double OrderTakeProfitX[200];
string OrderCommentX[200];
int OrderMagicNumberX[200];
datetime OrderOpenTimeX[200];
double OrderOpenPriceX[200];

int OrderTicketXH[200];
string OrderSymbolXH[200];
int OrderTypeXH[200];
double OrderLotsXH[200];
double OrderStopLossXH[200];
double OrderTakeProfitXH[200];
string OrderCommentXH[200];
int OrderMagicNumberXH[200];
datetime OrderOpenTimeXH[200];
double OrderOpenPriceXH[200];
double AccountEquityXH[200]; // 增加接收发送方的净值

double 每点价值[200];
double 滑点=5;
int check;
//=========================================================================== 
int OnInit()
  {
   限制可做货币对 [1]=限制可做货币对1;
   限制可做货币对 [2]=限制可做货币对2;
   限制可做货币对 [3]=限制可做货币对3;
   限制可做货币对 [4]=限制可做货币对4;
   限制可做货币对 [5]=限制可做货币对5;
   限制可做货币对 [6]=限制可做货币对6;
   限制可做货币对 [7]=限制可做货币对7;
   限制可做货币对 [8]=限制可做货币对8;
   限制可做货币对 [9]=限制可做货币对9;
   限制可做货币对[10]=限制可做货币对10;

   限制不可做货币对[1]=限制不可做货币对1;
   限制不可做货币对[2]=限制不可做货币对2;
   限制不可做货币对[3]=限制不可做货币对3;
   限制不可做货币对[4]=限制不可做货币对4;
   限制不可做货币对[5]=限制不可做货币对5;
   限制不可做货币对[6]=限制不可做货币对6;
   限制不可做货币对[7]=限制不可做货币对7;
   限制不可做货币对[8]=限制不可做货币对8;
   限制不可做货币对[9]=限制不可做货币对9;
   限制不可做货币对[10]=限制不可做货币对10;

   for(int ix=0;ix<200;ix++)
     {
      OrderSymbolX[ix]="";
      OrderCommentX[ix]="";
      OrderSymbolXH[ix]="";
      OrderCommentXH[ix]="";
      AccountEquityXH[ix]=0;
     }
   if(IsDllsAllowed()==false)
      Alert("请允许调用动态链接库");
   OnTick();
   return(INIT_SUCCEEDED);
  }
//===========================================================================
void OnDeinit(const int reason)
  {
   for(int i=ObjectsTotal();i>=0;i--)
     {
      if(StringFind(ObjectName(i),"标签",0)==0)
        {
         ObjectDelete(ObjectName(i));
         i=ObjectsTotal();
        }
     }
   ObjectsDeleteAll();
   Comment("");
  }
//===========================================================================
void OnTick()
  {

//if(TimeCurrent()>StringToTime("2115.07.11")){Alert("到期了");return(0);}
   if(IsDllsAllowed()==false)
      return;

   if(中转路径=="")
     {
      CreateDirectoryW("C:\\编程跟单软件中转路径",0);
      中转路径2="C:\\编程跟单软件中转路径";
     }
   else
      中转路径2=中转路径;

   while(true)
     {
      RefreshRates();
      ChartRedraw();

      按钮("强制补充已平和未跟单据",180,30,160,20,CORNER_RIGHT_LOWER,clrOrangeRed,clrBlack);

      if(ObjectGetInteger(0,"强制补充已平和未跟单据",OBJPROP_STATE)==1)
         强制补充已平和未跟单据=true;
      else
         强制补充已平和未跟单据=false;

      提取信号();
      提取历史信号();

      ObjectSetInteger(0,"强制补充已平和未跟单据",OBJPROP_STATE,0);

      按钮("获利达到全平 大于",180,90,100,20,CORNER_RIGHT_LOWER,clrSpringGreen,clrBlack);
      EDIT标签("获利达到全平 大于-2","100000",80,90,60,20,CORNER_RIGHT_LOWER,clrWhite,clrBlack);

      if(ObjectGetInteger(0,"获利达到全平 大于",OBJPROP_STATE)==1)
         if(AccountProfit()>StrToDouble(ObjectGetString(0,"获利达到全平 大于-2",OBJPROP_TEXT)))
           {
            deleteorder(-100,-1,"");
            ObjectSetInteger(0,"获利达到全平 大于",OBJPROP_STATE,0);
           }

      按钮("亏损达到全平 小于",180,60,100,20,CORNER_RIGHT_LOWER,clrRed,clrBlack);
      EDIT标签("亏损达到全平 小于-2","-100000",80,60,60,20,CORNER_RIGHT_LOWER,clrWhite,clrBlack);

      if(ObjectGetInteger(0,"亏损达到全平 小于",OBJPROP_STATE)==1)
         if(AccountProfit()<StrToDouble(ObjectGetString(0,"亏损达到全平 小于-2",OBJPROP_TEXT)))
           {
            deleteorder(-100,-1,"");
            ObjectSetInteger(0,"亏损达到全平 小于",OBJPROP_STATE,0);
           }

      for(int ix=GlobalVariablesTotal();ix>=0;ix--)
        {
         int 订单号监测=GlobalVariableGet(GlobalVariableName(ix));
         if(OrderSelect(订单号监测,SELECT_BY_TICKET,MODE_HISTORY))
            if(OrderCloseTime()!=0)
               if(TimeCurrent()-OrderCloseTime()>=24*60*60)
                  GlobalVariableDel(GlobalVariableName(ix));
        }

      string 内容[100];
      内容[0]="--------------跟单系统--------------";
      内容[1]="----------编程跟单软件接收端------------";
      内容[2]="--------------已开启跟单接收端--------------";
      内容[3]="历史0:"+OrderTicketXH[0]+" "+OrderSymbolXH[0]+" "+OrderTypeXH[0]+" "+OrderLotsXH[0];
      内容[4]="历史1:"+OrderTicketXH[1]+" "+OrderSymbolXH[1]+" "+OrderTypeXH[1]+" "+OrderLotsXH[1];
      内容[5]="历史2:"+OrderTicketXH[2]+" "+OrderSymbolXH[2]+" "+OrderTypeXH[2]+" "+OrderLotsXH[2];
      内容[6]="历史3:"+OrderTicketXH[3]+" "+OrderSymbolXH[3]+" "+OrderTypeXH[3]+" "+OrderLotsXH[3];
      内容[7]="历史4:"+OrderTicketXH[4]+" "+OrderSymbolXH[4]+" "+OrderTypeXH[4]+" "+OrderLotsXH[4];
      内容[8]="历史5:"+OrderTicketXH[5]+" "+OrderSymbolXH[5]+" "+OrderTypeXH[5]+" "+OrderLotsXH[5];
      内容[9]="历史6:"+OrderTicketXH[6]+" "+OrderSymbolXH[6]+" "+OrderTypeXH[6]+" "+OrderLotsXH[6];
      内容[10]="";
      内容[11]="持有0:"+OrderTicketX[0]+" "+OrderSymbolX[0]+" "+OrderTypeX[0]+" "+OrderLotsX[0]+" 对应"+已跟单对应记录(OrderTicketX[0])+" 时间"+TimeToStr(OrderOpenTimeX[0]);
      内容[12]="持有1:"+OrderTicketX[1]+" "+OrderSymbolX[1]+" "+OrderTypeX[1]+" "+OrderLotsX[1]+" 对应"+已跟单对应记录(OrderTicketX[1])+" 时间"+TimeToStr(OrderOpenTimeX[1]);
      内容[13]="持有2:"+OrderTicketX[2]+" "+OrderSymbolX[2]+" "+OrderTypeX[2]+" "+OrderLotsX[2]+" 对应"+已跟单对应记录(OrderTicketX[2])+" 时间"+TimeToStr(OrderOpenTimeX[2]);
      内容[14]="持有3:"+OrderTicketX[3]+" "+OrderSymbolX[3]+" "+OrderTypeX[3]+" "+OrderLotsX[3]+" 对应"+已跟单对应记录(OrderTicketX[3])+" 时间"+TimeToStr(OrderOpenTimeX[3]);
      内容[15]="持有4:"+OrderTicketX[4]+" "+OrderSymbolX[4]+" "+OrderTypeX[4]+" "+OrderLotsX[4]+" 对应"+已跟单对应记录(OrderTicketX[4])+" 时间"+TimeToStr(OrderOpenTimeX[4]);
      内容[16]="持有5:"+OrderTicketX[5]+" "+OrderSymbolX[5]+" "+OrderTypeX[5]+" "+OrderLotsX[5]+" 对应"+已跟单对应记录(OrderTicketX[5])+" 时间"+TimeToStr(OrderOpenTimeX[5]);
      内容[17]="持有6:"+OrderTicketX[6]+" "+OrderSymbolX[6]+" "+OrderTypeX[6]+" "+OrderLotsX[6]+" 对应"+已跟单对应记录(OrderTicketX[6])+" 时间"+TimeToStr(OrderOpenTimeX[6]);

      for(int ixx=0;ixx<=16;ixx++)
         固定位置标签("标签"+ixx,内容[ixx],X,Y+Y间隔*ixx,标签颜色,标签字体大小,固定角);

      ChartRedraw();
      if(!(!IsStopped() && IsExpertEnabled() && IsTesting()==false && IsOptimization()==false))
         return;
      Sleep(300);
     }
   return;
  }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
void 固定位置标签(string 名称,string 内容,int XX,int YX,color C,int 字体大小,int 固定角内)
  {
   if(内容==EMPTY)
      return;
   if(ObjectFind(名称)==-1)
     {
      ObjectDelete(名称);
      ObjectCreate(名称,OBJ_LABEL,0,0,0);
     }
   ObjectSet(名称,OBJPROP_XDISTANCE,XX);
   ObjectSet(名称,OBJPROP_YDISTANCE,YX);
   ObjectSetText(名称,内容,字体大小,"宋体",C);
   ObjectSet(名称,OBJPROP_CORNER,固定角内);
   ObjectSetInteger(0,名称,OBJPROP_ANCHOR,ANCHOR_LEFT);
  }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
void OnChartEvent(const int id,
                  const long &lparam,
                  const double &dparam,
                  const string &sparam)
  {
//---
   OnTick();
  }
//+------------------------------------------------------------------+
void EDIT标签(string name,string 内容,int XX,int YX,int XL,int YL,int WZ,color A,color B)
  {
   if(ObjectFind(0,name)==-1)
     {
      ObjectCreate(0,name,OBJ_EDIT,0,0,0);
      ObjectSetString(0,name,OBJPROP_TEXT,内容);
      ObjectSetInteger(0,name,OBJPROP_XDISTANCE,XX);
      ObjectSetInteger(0,name,OBJPROP_YDISTANCE,YX);
      ObjectSetInteger(0,name,OBJPROP_XSIZE,XL);
      ObjectSetInteger(0,name,OBJPROP_YSIZE,YL);
      ObjectSetString(0,name,OBJPROP_FONT,"微软雅黑");
      ObjectSetInteger(0,name,OBJPROP_FONTSIZE,8);
      ObjectSetInteger(0,name,OBJPROP_CORNER,WZ);
      ObjectSetInteger(0,name,OBJPROP_BGCOLOR,A);
      ObjectSetInteger(0,name,OBJPROP_COLOR,B);
     }
  } void wenzi(string name,string neirong,int x,int y,int daxiao,color yanse)
  {
    if(ObjectFind(name)<0)
     {
        ObjectCreate(name,OBJ_LABEL,0,0,0);
        ObjectSetText(name,neirong,daxiao,"宋体",yanse);
        ObjectSet(name,OBJPROP_XDISTANCE,x);
        ObjectSet(name,OBJPROP_YDISTANCE,y);
        ObjectSet(name,OBJPROP_CORNER,0);
     }
    else
     {
        ObjectSetText(name,neirong,daxiao,"宋体",yanse);
        WindowRedraw();
     }
  }

//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
void 按钮(string name,int XX,int YX,int XL,int YL,int WZ,color A,color B)
  {
   if(ObjectFind(0,name)==-1)
     {
      ObjectCreate(0,name,OBJ_BUTTON,0,0,0);
      ObjectSetInteger(0,name,OBJPROP_XDISTANCE,XX);
      ObjectSetInteger(0,name,OBJPROP_YDISTANCE,YX);
      ObjectSetInteger(0,name,OBJPROP_XSIZE,XL);
      ObjectSetInteger(0,name,OBJPROP_YSIZE,YL);
      ObjectSetString(0,name,OBJPROP_TEXT,name);
      ObjectSetString(0,name,OBJPROP_FONT,"微软雅黑");
      ObjectSetInteger(0,name,OBJPROP_FONTSIZE,8);
      ObjectSetInteger(0,name,OBJPROP_CORNER,WZ);
     }

   if(ObjectGetInteger(0,name,OBJPROP_STATE)==1)
     {
      ObjectSetInteger(0,name,OBJPROP_COLOR,A);
      ObjectSetInteger(0,name,OBJPROP_BGCOLOR,B);
     }
   else
     {
      ObjectSetInteger(0,name,OBJPROP_COLOR,B);
      ObjectSetInteger(0,name,OBJPROP_BGCOLOR,A);
     }
  }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
void 平单(int i2)
  {
   if(OrderTicketXH[i2]!=0)
      if(StringFind(OrderCommentXH[i2],"to #",0)==-1 || 开启部分平仓==false)
         if(OrderSelect(已跟单对应记录(OrderTicketXH[i2]),SELECT_BY_TICKET))
            if(OrderCloseTime()==0)
               if(OrderComment()==自定义部分备注+"{"+DoubleToStr(OrderTicketXH[i2],0)+"}"
                  || (StringFind(OrderComment(),"from #",0)!=-1)
                  || OrderComment()==自定义部分备注+"{"+DoubleToStr(OrderTicketXH[i2],0)+"}(劣转挂)"
                  || 隐藏备注
                  )
                  if(跟踪平仓 || OrderType()>1)
                     if(OrderCloseTime()==0)
                       {
                        if(OrderType()>1)
                           check=OrderDelete(OrderTicket());
                        else
                           check=OrderClose(OrderTicket(),OrderLots(),OrderClosePrice(),滑点);
                        报错组件("1");
                        if(GlobalVariableCheck(OrderTicketXH[i2]))
                           GlobalVariableDel(OrderTicketXH[i2]);
                       }
  }
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
int 已跟单对应记录(int ti)
  {
   if(ti==0)
      return(-1);

   if(隐藏备注==false)
      if(findlassorder(-100,-1,"后","现在",ti)!=-1)
         return(findlassorder(-100,-1,"后" ,"现在",ti));

   if(隐藏备注==false)
      if(findlassorder(-100,-1,"后","历史",ti)!=-1)
         return(findlassorder(-100,-1,"后" ,"历史",ti));

   if(GlobalVariableCheck(ti))
     {
      return(GlobalVariableGet(ti));
     }
   return(-1);
  }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
int 已持有对应记录(int ti)
  {
   if(ti==0)
      return(-1);

   if(GlobalVariableCheck(ti))
      if(OrderSelect(GlobalVariableGet(ti),SELECT_BY_TICKET))
         if(OrderCloseTime()==0)
           {
            return(GlobalVariableGet(ti));
           }

   if(findlassorder(-100,-1,"后","现在",ti)!=-1)
      return(findlassorder(-100,-1,"后" ,"现在",ti));

   return(-1);
  }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
double 单量比例调节;
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
void 跟踪市价单(int i)
  {
   if(分项单据统计(-100,-1,"")<最大持单数)
      if(OrderSymbolX[i]!="" && OrderTicketX[i]!=0)
         if(OrderTypeX[i]<2 || 跟踪挂单)
            if(TimeLocal()-OrderOpenTimeX[i]<=跟单允许秒误差 || StringFind(OrderCommentX[i],"from",0)!=-1 || 强制补充已平和未跟单据)
               if(StringFind(OrderCommentX[i],接收订单需包含备注,0)!=-1 || 接收订单需包含备注=="")
                  if((OrderTypeX[i]==0 && MathAbs(MarketInfo(OrderSymbolX[i],MODE_ASK)-OrderOpenPriceX[i])<=跟单允许点误差 *MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))
                     || (OrderTypeX[i]==1 && MathAbs(MarketInfo(OrderSymbolX[i],MODE_BID)-OrderOpenPriceX[i])<=跟单允许点误差 *MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))
                     || 跟踪挂单
                     || StringFind(OrderCommentX[i],"from",0)!=-1
                     || 强制补充已平和未跟单据)
                     if(跟踪部分平仓单据 || StringFind(OrderCommentX[i],"from",0)==-1)
                        if((已跟单对应记录(OrderTicketX[i])==-1 && 强制补充已平和未跟单据==false) || (已持有对应记录(OrderTicketX[i])==-1 && 强制补充已平和未跟单据))
                           if(OrderLotsX[i]>=跟主账户单量大于等于N单子)
                              if(OrderLotsX[i]<=跟主账户单量小于等于M单子)
                                 if(MarketInfo(OrderSymbolX[i],MODE_ASK)!=0)
                                   {
                                    //==================================================================================================
                                    //==================================================================================================
                                    //==================================================================================================

                                    bool 部分平仓直接做单=false;
                                    if(开启部分平仓 && StringFind(OrderCommentX[i],"from",0)!=-1)
                                      {
                                       int 部分平仓追溯订单号=StrToInteger(StringSubstr(OrderCommentX[i],StringFind(OrderCommentX[i],"#",0)+1,StringLen(OrderCommentX[i])-StringFind(OrderCommentX[i],"#",0)-1));

                                       if(OrderSelect(findlassorder(-100,-1,"后","现在",OrderTicketX[i]),SELECT_BY_TICKET)==false)
                                          if(OrderSelect(findlassorder(-100,-1,"后","现在",部分平仓追溯订单号),SELECT_BY_TICKET)==false)
                                             if(GlobalVariableCheck(部分平仓追溯订单号)==false)
                                                if(GlobalVariableCheck(OrderTicketX[i])==false)
                                                  {
                                                   部分平仓直接做单=true;
                                                  }

                                       if(OrderSelect(findlassorder(-100,-1,"后","现在",部分平仓追溯订单号),SELECT_BY_TICKET))
                                         {
                                          if(MarketInfo(OrderSymbol(),MODE_LOTSTEP)<10)int 单量小数保留=0;
                                          if(MarketInfo(OrderSymbol(),MODE_LOTSTEP)<1)单量小数保留=1;
                                          if(MarketInfo(OrderSymbol(),MODE_LOTSTEP)<0.1)单量小数保留=2;

                                          int ti=OrderTicket();

                                          if(OrderLots()-OrderLotsX[i]*单量比例*单量比例调节>0 && MathMax(NormalizeDouble(OrderLots()-OrderLotsX[i]*单量比例*单量比例调节,单量小数保留),MarketInfo(OrderSymbolX[i],MODE_LOTSTEP))!=OrderLots())
                                            {
                                             if(OrderClose(OrderTicket(),MathMax(NormalizeDouble(OrderLots()-OrderLotsX[i]*单量比例*单量比例调节,单量小数保留),MarketInfo(OrderSymbolX[i],MODE_LOTSTEP)),OrderClosePrice(),100))
                                                if(OrderSelect(findlassorder(-100,-1,"后","现在",ti),SELECT_BY_TICKET))
                                                   GlobalVariableSet(OrderTicketX[i],OrderTicket());
                                            }
                                          else
                                             GlobalVariableSet(OrderTicketX[i],ti);

                                          部分平仓直接做单=false;
                                         }
                                       else
                                       if(OrderSelect(GlobalVariableGet(部分平仓追溯订单号),SELECT_BY_TICKET))
                                         {
                                          if(MarketInfo(OrderSymbol(),MODE_LOTSTEP)<10)单量小数保留=0;
                                          if(MarketInfo(OrderSymbol(),MODE_LOTSTEP)<1)单量小数保留=1;
                                          if(MarketInfo(OrderSymbol(),MODE_LOTSTEP)<0.1)单量小数保留=2;

                                          ti=OrderTicket();
                                          GlobalVariableDel(部分平仓追溯订单号);

                                          if(OrderLots()-OrderLotsX[i]*单量比例*单量比例调节>0 && MathMax(NormalizeDouble(OrderLots()-OrderLotsX[i]*单量比例*单量比例调节,单量小数保留),MarketInfo(OrderSymbolX[i],MODE_LOTSTEP))!=OrderLots())
                                            {
                                             if(OrderClose(OrderTicket(),MathMax(NormalizeDouble(OrderLots()-OrderLotsX[i]*单量比例*单量比例调节,单量小数保留),MarketInfo(OrderSymbolX[i],MODE_LOTSTEP)),OrderClosePrice(),100))
                                                if(OrderSelect(findlassorder(-100,-1,"后","现在",ti),SELECT_BY_TICKET))
                                                   GlobalVariableSet(OrderTicketX[i],OrderTicket());
                                            }
                                          else
                                             GlobalVariableSet(OrderTicketX[i],ti);

                                          部分平仓直接做单=false;
                                         }
                                      }

                                    if(开启部分平仓==false || 部分平仓直接做单 || StringFind(OrderCommentX[i],"from",0)==-1)
                                       //================================================================================================== 
                                       //==================================================================================================
                                       //==================================================================================================  

                                       if(OrderTypeX[i]<2)
                                         {
                                          if(反向跟单==false)
                                            {
                                             if(
                                                价格劣势转为挂单==false
                                                ||(OrderTypeX[i]==OP_BUY&&MarketInfo(OrderSymbolX[i],MODE_ASK)<=OrderOpenPriceX[i]+跟单允许点误差 *MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))
                                                ||(OrderTypeX[i]==OP_SELL&&MarketInfo(OrderSymbolX[i],MODE_BID)>=OrderOpenPriceX[i]-跟单允许点误差 *MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))
                                                )
                                               {
                                                if(
                                                   做单需优价开关==false || 
                                                   (OrderTypeX[i]==OP_BUY&&MarketInfo(OrderSymbolX[i],MODE_ASK)<=OrderOpenPriceX[i]-做单需优于点数*MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))||
                                                   (OrderTypeX[i]==OP_SELL&&MarketInfo(OrderSymbolX[i],MODE_BID)>=OrderOpenPriceX[i]+做单需优于点数*MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))
                                                   )
                                                   int t=建立单据(OrderSymbolX[i],OrderTypeX[i],OrderLotsX[i]*单量比例*单量比例调节,0,0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                               }
                                             else
                                             if(OrderTypeX[i]==OP_BUY)
                                                t=建立单据(OrderSymbolX[i],OP_BUYLIMIT,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}"+"(劣转挂)",magic,0, AccountEquityXH[i]);
                                             else
                                             if(OrderTypeX[i]==OP_SELL)
                                                t=建立单据(OrderSymbolX[i],OP_SELLLIMIT,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}"+"(劣转挂)",magic,0, AccountEquityXH[i]);
                                            }
                                          else
                                            {
                                             if(
                                                价格劣势转为挂单==false
                                                ||(OrderTypeX[i]==OP_SELL&&MarketInfo(OrderSymbolX[i],MODE_ASK)<=OrderOpenPriceX[i]+跟单允许点误差 *MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))
                                                ||(OrderTypeX[i]==OP_BUY&&MarketInfo(OrderSymbolX[i],MODE_BID)>=OrderOpenPriceX[i]-跟单允许点误差 *MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))
                                                )
                                               {
                                                if(
                                                   做单需优价开关==false || 
                                                   (MathAbs(OrderTypeX[i]-1)==OP_BUY&&MarketInfo(OrderSymbolX[i],MODE_ASK)<=OrderOpenPriceX[i]-做单需优于点数*MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))||
                                                   (MathAbs(OrderTypeX[i]-1)==OP_SELL&&MarketInfo(OrderSymbolX[i],MODE_BID)>=OrderOpenPriceX[i]+做单需优于点数*MarketInfo(OrderSymbolX[i],MODE_POINT)*系数(OrderSymbolX[i]))
                                                   )
                                                   t=建立单据(OrderSymbolX[i],MathAbs(OrderTypeX[i]-1),OrderLotsX[i]*单量比例*单量比例调节,0,0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                               }
                                             else
                                             if(OrderTypeX[i]==OP_SELL)
                                                t=建立单据(OrderSymbolX[i],OP_BUYLIMIT,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}"+"(劣转挂)",magic,0, AccountEquityXH[i]);
                                             else
                                             if(OrderTypeX[i]==OP_BUY)
                                                t=建立单据(OrderSymbolX[i],OP_SELLLIMIT,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}"+"(劣转挂)",magic,0, AccountEquityXH[i]);
                                            }
                                         }

                                    if(跟踪挂单)
                                      {
                                       if(OrderTypeX[i]==OP_BUYLIMIT)
                                         {
                                          if(反向跟单==false)
                                             t=建立单据(OrderSymbolX[i],OP_BUYLIMIT,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                          else
                                             t=建立单据(OrderSymbolX[i],OP_SELLSTOP,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                         }
                                       if(OrderTypeX[i]==OP_BUYSTOP)
                                         {
                                          if(反向跟单==false)
                                             t=建立单据(OrderSymbolX[i],OP_BUYSTOP,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                          else
                                             t=建立单据(OrderSymbolX[i],OP_SELLLIMIT,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                         }
                                       if(OrderTypeX[i]==OP_SELLLIMIT)
                                         {
                                          if(反向跟单==false)
                                             t=建立单据(OrderSymbolX[i],OP_SELLLIMIT,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                          else
                                             t=建立单据(OrderSymbolX[i],OP_BUYSTOP,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                         }
                                       if(OrderTypeX[i]==OP_SELLSTOP)
                                         {
                                          if(反向跟单==false)
                                             t=建立单据(OrderSymbolX[i],OP_SELLSTOP,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                          else
                                             t=建立单据(OrderSymbolX[i],OP_BUYLIMIT,OrderLotsX[i]*单量比例*单量比例调节,OrderOpenPriceX[i],0,0,0,自定义部分备注+"{"+OrderTicketX[i]+"}",magic,OrderTicketX[i], AccountEquityXH[i]);
                                         }
                                      }
                                   }
  }
//===========================================================================
void 提取历史信号()
  {

   if(FILES文件夹路径!="")
      FILES文件夹路径2=FILES文件夹路径;
   else
      FILES文件夹路径2=TerminalInfoString(TERMINAL_DATA_PATH)+"\\MQL4\\Files";

   int t=CopyFileW(中转路径2+"\\"+跟踪主账号+"2.csv",FILES文件夹路径2+"\\"+跟踪主账号+"2.csv",0);

   int handle;
   handle=FileOpen(跟踪主账号+"2.csv",FILE_CSV|FILE_READ|FILE_SHARE_WRITE|FILE_SHARE_READ,间隔符);

   if(handle>0)
     {
      for(int i=0;i<200;i++)
        {
         OrderTicketXH[i]=StrToInteger(FileReadString(handle));
         string namexx=FileReadString(handle);

         string name休整=StringSubstr(namexx,0,6);

         if(StringFind(namexx,"GOLD",0)==0)
            name休整="GOLD";

         OrderSymbolXH[i]=name休整+货币对名称附加;

         if(货币对名称强制修正开关)
           {
            if(namexx==所跟货币对名称1)
               OrderSymbolXH[i]=下单货币对名称1;
            if(namexx==所跟货币对名称2)
               OrderSymbolXH[i]=下单货币对名称2;
            if(namexx==所跟货币对名称3)
               OrderSymbolXH[i]=下单货币对名称3;
           }

         OrderTypeXH[i]= StrToInteger(FileReadString(handle));
         OrderLotsXH[i]= StrToDouble(FileReadString(handle));
         if(OrderTicketXH[i]!=0)
            if(OrderTypeXH[i]<6)
              {
               OrderStopLossXH[i]=NormalizeDouble(StrToDouble(FileReadString(handle)),MarketInfo(OrderSymbolXH[i],MODE_DIGITS));
               OrderTakeProfitXH[i]=NormalizeDouble(StrToDouble(FileReadString(handle)),MarketInfo(OrderSymbolXH[i],MODE_DIGITS));
              }

         OrderCommentXH[i]=FileReadString(handle);
         OrderMagicNumberXH[i]=StrToInteger(FileReadString(handle));
         OrderOpenTimeXH[i]=StrToInteger(FileReadString(handle));
         OrderOpenPriceXH[i]=NormalizeDouble(StrToDouble(FileReadString(handle)),MarketInfo(OrderSymbolXH[i],MODE_DIGITS));
         AccountEquityXH[i]=StrToDouble(FileReadString(handle));//增加接收发送方净值

         平单(i);

         if(FileIsEnding(handle))
            break;
        }
      FileClose(handle);
     }
  }
//===========================================================================
void 提取信号()
  {

   if(FILES文件夹路径!="")
      FILES文件夹路径2=FILES文件夹路径;
   else
      FILES文件夹路径2=TerminalInfoString(TERMINAL_DATA_PATH)+"\\MQL4\\Files";

   int t=CopyFileW(中转路径2+"\\"+跟踪主账号+".csv",FILES文件夹路径2+"\\"+跟踪主账号+".csv",0);

   int handle;
   handle=FileOpen(跟踪主账号+".csv",FILE_CSV|FILE_READ|FILE_SHARE_WRITE|FILE_SHARE_READ,间隔符);

   if(handle>0)
     {
      ArrayInitialize(OrderTicketX,0);
      ArrayInitialize(OrderTypeX,0);
      ArrayInitialize(OrderLotsX,0);
      ArrayInitializeX(OrderSymbolX,"",200);
      ArrayInitialize(每点价值,0);

      for(int i=0;i<200;i++)
        {
         OrderTicketX[i]=StrToInteger(FileReadString(handle));
         string namexx=FileReadString(handle);

         string name休整=StringSubstr(namexx,0,6);

         if(StringFind(namexx,"GOLD",0)==0)
            name休整="GOLD";

         OrderSymbolX[i]=name休整+货币对名称附加;

         单量比例调节=1;

         if(货币对名称强制修正开关)
            if(单量比例!=0)
              {
               if(namexx==所跟货币对名称1)
                 {
                  OrderSymbolX[i]=下单货币对名称1;
                  单量比例调节=特殊跟单单量比例1/单量比例;
                 }
               if(namexx==所跟货币对名称2)
                 {
                  OrderSymbolX[i]=下单货币对名称2;
                  单量比例调节=特殊跟单单量比例2/单量比例;
                 }
               if(namexx==所跟货币对名称3)
                 {
                  OrderSymbolX[i]=下单货币对名称3;
                  单量比例调节=特殊跟单单量比例3/单量比例;
                 }
              }

         OrderTypeX[i]= StrToInteger(FileReadString(handle));
         OrderLotsX[i]= StrToDouble(FileReadString(handle));

         if(OrderTicketX[i]!=0)
            if(OrderTypeX[i]<6)
              {
               OrderStopLossX[i]=NormalizeDouble(StrToDouble(FileReadString(handle)),MarketInfo(OrderSymbolX[i],MODE_DIGITS));
               OrderTakeProfitX[i]=NormalizeDouble(StrToDouble(FileReadString(handle)),MarketInfo(OrderSymbolX[i],MODE_DIGITS));
              }

         OrderCommentX[i]=FileReadString(handle);
         OrderMagicNumberX[i]=StrToInteger(FileReadString(handle));
         OrderOpenTimeX[i]=StrToInteger(FileReadString(handle));
         OrderOpenPriceX[i]=NormalizeDouble(StrToDouble(FileReadString(handle)),MarketInfo(OrderSymbolX[i],MODE_DIGITS));
         每点价值[i]=StrToDouble(FileReadString(handle));
         AccountEquityXH[i]=StrToDouble(FileReadString(handle));//增加接收发送方净值

         if(货币对名称强制修正开关)
            if(进行尾部价格修饰)
               if(namexx==所跟货币对名称3)
                  if(MarketInfo(下单货币对名称3,MODE_POINT)!=0)
                    {
                     double price=OrderOpenPriceX[i];
                     double price2=MathFloor(price/MarketInfo(下单货币对名称3,MODE_POINT)/10)*10*MarketInfo(下单货币对名称3,MODE_POINT);

                     if(price>price2+5*MarketInfo(下单货币对名称3,MODE_POINT))
                        OrderOpenPriceX[i]=price2+5*MarketInfo(下单货币对名称3,MODE_POINT);
                     else
                        OrderOpenPriceX[i]=price2;

                     price=OrderStopLossX[i];
                     price2=MathFloor(price/MarketInfo(下单货币对名称3,MODE_POINT)/10)*10*MarketInfo(下单货币对名称3,MODE_POINT);

                     if(price>price2+5*MarketInfo(下单货币对名称3,MODE_POINT))
                        OrderStopLossX[i]=price2+5*MarketInfo(下单货币对名称3,MODE_POINT);
                     else
                        OrderStopLossX[i]=price2;

                     price=OrderTakeProfitX[i];
                     price2=MathFloor(price/MarketInfo(下单货币对名称3,MODE_POINT)/10)*10*MarketInfo(下单货币对名称3,MODE_POINT);

                     if(price>price2+5*MarketInfo(下单货币对名称3,MODE_POINT))
                        OrderTakeProfitX[i]=price2+5*MarketInfo(下单货币对名称3,MODE_POINT);
                     else
                        OrderTakeProfitX[i]=price2;
                    }

         跟踪市价单(i);
         修改订单止损止盈(i);

         if(FileIsEnding(handle))
            break;
        }
      FileClose(handle);
     }
  }
//=========================================================================== 
void ArrayInitializeX(string &A[],string b,int c)
  {
   for(int i=0;i<c;i++)
      A[i]=b;
  }
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
void deleteorder(int type,int magicX,string comm)
  {
   for(int i=OrdersTotal()-1;i>=0;i--)
     {
      if(OrderSelect(i,SELECT_BY_POS))
         //if(Symbol()==OrderSymbol())
         if(OrderMagicNumber()==magicX || magicX==-1)
            if(OrderType()==type || type==-100)
               if(StringFind(OrderComment(),comm,0)!=-1 || comm=="")
                 {
                  if(OrderType()>=2)
                    {
                     if(OrderDelete(OrderTicket())==false)
                        报错组件("");
                     i=OrdersTotal();
                    }
                  else
                    {
                     if(OrderClose(OrderTicket(),OrderLots(),OrderClosePrice(),滑点*系数(Symbol()))==false)
                        报错组件("");
                     i=OrdersTotal();
                    }
                 }
     }
  }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
double 系数(string symbol)
  {
   int 系数=1;
   if(
      MarketInfo(symbol,MODE_DIGITS)==3
      || MarketInfo(symbol,MODE_DIGITS)==5
      || (StringFind(symbol,"XAU",0)==0 && MarketInfo(symbol,MODE_DIGITS)==2)
      ||(StringFind(symbol,"GOLD",0)==0&&MarketInfo(symbol,MODE_DIGITS)==2)
      ||(StringFind(symbol,"Gold",0)==0&&MarketInfo(symbol,MODE_DIGITS)==2)
      || (StringFind(symbol,"USD_GLD",0)==0 && MarketInfo(symbol,MODE_DIGITS)==2)
      )系数=10;

   if(StringFind(symbol,"XAU",0)==0 && MarketInfo(symbol,MODE_DIGITS)==3)系数=100;

   return(系数);
  }
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
void laber(string a,color b)
  {
   if(是否显示文字标签==true)
     {
      ObjectDelete("箭头"+TimeToStr(Time[0],TIME_DATE|TIME_MINUTES)+a);
      ObjectCreate("箭头"+TimeToStr(Time[0],TIME_DATE|TIME_MINUTES)+a,OBJ_TEXT,0,Time[0],Low[0]);
      ObjectSetText("箭头"+TimeToStr(Time[0],TIME_DATE|TIME_MINUTES)+a,a,8,"Times New Roman",b);
     }
  }
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
bool 货币对属于范围(string symbol,string &symbolX[])
  {
   for(int ix=0;ix<11;ix++)
      if(symbol==symbolX[ix])
         return(true);

   return(false);
  }
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
int findlassorder(int type,int magicX,string fx,string 现在与历史,string comm)
  {
   if(现在与历史=="现在")
      if(fx=="后")
         for(int i=OrdersTotal()-1;i>=0;i--)
           {
            if(OrderSelect(i,SELECT_BY_POS))
               //if(Symbol()==OrderSymbol())
               if(OrderMagicNumber()==magicX || magicX==-1)
                  if(OrderType()==type || type==-100)
                     if(StringFind(OrderComment(),comm,0)!=-1 || comm=="")
                        return(OrderTicket());
           }

   if(现在与历史=="现在")
      if(fx=="前")
         for(i=0;i<OrdersTotal();i++)
           {
            if(OrderSelect(i,SELECT_BY_POS))
               //if(Symbol()==OrderSymbol())
               if(OrderMagicNumber()==magicX || magicX==-1)
                  if(OrderType()==type || type==-100)
                     if(StringFind(OrderComment(),comm,0)!=-1 || comm=="")
                        return(OrderTicket());
           }

   if(现在与历史=="历史")
      if(fx=="后")
         for(i=OrdersHistoryTotal()-1;i>=0;i--)
           {
            if(OrderSelect(i,SELECT_BY_POS,MODE_HISTORY))
               //if(Symbol()==OrderSymbol())
               if(OrderMagicNumber()==magicX || magicX==-1)
                  if(OrderType()==type || (type==-100 && OrderType()<=5 && OrderType()>=0))
                     if(StringFind(OrderComment(),comm,0)!=-1 || comm=="")
                        if(OrderCloseTime()!=0)
                           return(OrderTicket());
           }

   if(现在与历史=="历史")
      if(fx=="前")
         for(i=0;i<OrdersHistoryTotal();i++)
           {
            if(OrderSelect(i,SELECT_BY_POS,MODE_HISTORY))
               //if(Symbol()==OrderSymbol())
               if(OrderMagicNumber()==magicX || magicX==-1)
                  if(OrderType()==type || (type==-100 && OrderType()<=5 && OrderType()>=0))
                     if(StringFind(OrderComment(),comm,0)!=-1 || comm=="")
                        if(OrderCloseTime()!=0)
                           return(OrderTicket());
           }
   return(-1);
  }
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
void 修改订单止损止盈(int i)
  {

   if(OrderSelect(已跟单对应记录(OrderTicketX[i]),SELECT_BY_TICKET))
      if((StringFind(OrderComment(),"(劣转挂)",0)!=-1))
         if(OrderType()>=2)
            if(TimeCurrent()-OrderOpenTime()>=劣转挂单存续分钟*60)
               check=OrderDelete(OrderTicket());

   if(跟踪挂单)
      //for(int i=0;i<200;i++)
      if(OrderSelect(已跟单对应记录(OrderTicketX[i]),SELECT_BY_TICKET))
         if(OrderCloseTime()==0)
            if(OrderType()>1)
               if(
                  MathAbs(OrderOpenPrice()-OrderOpenPriceX[i])>=MarketInfo(OrderSymbol(),MODE_POINT)
                  )
                 {
                  check=OrderModify(OrderTicket(),OrderOpenPriceX[i],OrderStopLoss(),OrderTakeProfit(),0);
                  报错组件("2");
                 }

   if(跟踪止损止盈)
      //for(i=0;i<200;i++)
      if(OrderSelect(已跟单对应记录(OrderTicketX[i]),SELECT_BY_TICKET))
         if(StringFind(OrderComment(),"(劣转挂)",0)==-1)
            if(OrderCloseTime()==0)
              {

               if(反向跟单==false)
                  if(
                     MathAbs(OrderStopLoss()-OrderStopLossX[i])>=MarketInfo(OrderSymbol(),MODE_POINT)
                     || 
                     MathAbs(OrderTakeProfit()-OrderTakeProfitX[i])>=MarketInfo(OrderSymbol(),MODE_POINT)
                     )
                    {
                     check=OrderModify(OrderTicket(),OrderOpenPrice(),OrderStopLossX[i],OrderTakeProfitX[i],0);
                     报错组件("3");
                    }

               if(反向跟单)
                  if(
                     MathAbs(OrderStopLoss()-OrderTakeProfitX[i])>=MarketInfo(OrderSymbol(),MODE_POINT)
                     || 
                     MathAbs(OrderTakeProfit()-OrderStopLossX[i])>=MarketInfo(OrderSymbol(),MODE_POINT)
                     )
                    {
                     check=OrderModify(OrderTicket(),OrderOpenPrice(),OrderTakeProfitX[i],OrderStopLossX[i],0);
                     报错组件("4");
                    }

              }
  }
//===========================================================================
int 分项单据统计(int type,int magicX,string comm)
  {
   int 数量=0;
   for(int i=0;i<OrdersTotal();i++)
      if(OrderSelect(i,SELECT_BY_POS))
         //if(OrderSymbol()==Symbol())
         if(OrderMagicNumber()==magicX || magicX==-1)
            if(StringFind(OrderComment(),comm,0)!=-1 || comm=="")
               if(OrderType()==type || type==-100)
                  数量++;
   return(数量);
  }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
int 建立单据(string 货币对,int 类型,double 单量,double 价位,double 间隔,double 止损,double 止盈,string 备注,int magicX,int 所跟订单号, double 发送方净值)
  {
   if(隐藏备注)
      if(所跟订单号!=0)
        {
         备注="";
         if(做单PC验证)
            备注=StringSubstr(TerminalInfoString(TERMINAL_PATH),0,27);
        }
        
   if(使用净值比例下单){
       
       double myAccountEquity = AccountEquity();
       单量 = MathFloor(单量 * (myAccountEquity / 发送方净值) * 100) / 100 ;
   }

   if(使用固定单量)
      单量=固定单量;

   if(限定可做的货币对 && 货币对属于范围(货币对,限制可做货币对)==false)
      return(-1);

   if(限定不可做的货币对 && 货币对属于范围(货币对,限制不可做货币对))
      return(-1);


   if(跟多单==false)
      if(类型==OP_BUY || 类型==OP_BUYSTOP || 类型==OP_BUYLIMIT)
         return(-1);

   if(跟空单==false)
      if(类型==OP_SELL || 类型==OP_SELLSTOP || 类型==OP_SELLLIMIT)
         return(-1);


   if(MarketInfo(货币对,MODE_LOTSTEP)<10)int 单量小数保留X=0;
   if(MarketInfo(货币对,MODE_LOTSTEP)<1)单量小数保留X=1;
   if(MarketInfo(货币对,MODE_LOTSTEP)<0.1)单量小数保留X=2;

   单量=NormalizeDouble(单量,单量小数保留X);

   if(低于最低单量时使用最低单量)
      if(单量<MarketInfo(货币对,MODE_MINLOT))
         单量=MarketInfo(货币对,MODE_MINLOT);

   if(单量<MarketInfo(货币对,MODE_MINLOT))
     {
      //laber("低于最低单量",Yellow);
      return(-1);
     }

   if(单量>MarketInfo(货币对,MODE_MAXLOT))
      单量=MarketInfo(货币对,MODE_MAXLOT);

   int t;
   double POINT=MarketInfo(货币对,MODE_POINT)*系数(货币对);
   int DIGITS=MarketInfo(货币对,MODE_DIGITS);
   int 滑点2=滑点*系数(货币对);

   if(类型==OP_BUY)
     {
      RefreshRates();
      t=OrderSend(货币对,OP_BUY,单量,MarketInfo(货币对,MODE_ASK),滑点2,0,0,备注,magicX,0);
      报错组件("5");
      if(OrderSelect(t,SELECT_BY_TICKET))
        {
         if(止损!=0 && 止盈!=0)
            check=OrderModify(OrderTicket(),OrderOpenPrice(),NormalizeDouble(OrderOpenPrice()-止损*POINT,DIGITS),NormalizeDouble(OrderOpenPrice()+止盈*POINT,DIGITS),0);

         if(止损==0 && 止盈!=0)
            check=OrderModify(OrderTicket(),OrderOpenPrice(),0,NormalizeDouble(OrderOpenPrice()+止盈*POINT,DIGITS),0);

         if(止损!=0 && 止盈==0)
            check=OrderModify(OrderTicket(),OrderOpenPrice(),NormalizeDouble(OrderOpenPrice()-止损*POINT,DIGITS),0,0);

         报错组件("6");
        }
     }

   if(类型==OP_SELL)
     {
      RefreshRates();
      t=OrderSend(货币对,OP_SELL,单量,MarketInfo(货币对,MODE_BID),滑点2,0,0,备注,magicX,0);
      报错组件("7");
      if(OrderSelect(t,SELECT_BY_TICKET))
        {
         if(止损!=0 && 止盈!=0)
            check=OrderModify(OrderTicket(),OrderOpenPrice(),NormalizeDouble(OrderOpenPrice()+止损*POINT,DIGITS),NormalizeDouble(OrderOpenPrice()-止盈*POINT,DIGITS),0);

         if(止损==0 && 止盈!=0)
            check=OrderModify(OrderTicket(),OrderOpenPrice(),0,NormalizeDouble(OrderOpenPrice()-止盈*POINT,DIGITS),0);

         if(止损!=0 && 止盈==0)
            check=OrderModify(OrderTicket(),OrderOpenPrice(),NormalizeDouble(OrderOpenPrice()+止损*POINT,DIGITS),0,0);
        }
      报错组件("8");
     }

   if(类型==OP_BUYLIMIT || 类型==OP_BUYSTOP)
     {
      if(价位==0)
        {
         RefreshRates();
         价位=MarketInfo(货币对,MODE_ASK);
        }

      if(类型==OP_BUYLIMIT)
        {
         if(止损!=0 && 止盈!=0)
            t=OrderSend(货币对,OP_BUYLIMIT,单量,NormalizeDouble(价位-间隔*POINT,DIGITS),滑点2,NormalizeDouble(价位-间隔*POINT-止损*POINT,DIGITS),NormalizeDouble(价位-间隔*POINT+止盈*POINT,DIGITS),备注,magicX,0);
         if(止损==0 && 止盈!=0)
            t=OrderSend(货币对,OP_BUYLIMIT,单量,NormalizeDouble(价位-间隔*POINT,DIGITS),滑点2,0,NormalizeDouble(价位-间隔*POINT+止盈*POINT,DIGITS),备注,magicX,0);
         if(止损!=0 && 止盈==0)
            t=OrderSend(货币对,OP_BUYLIMIT,单量,NormalizeDouble(价位-间隔*POINT,DIGITS),滑点2,NormalizeDouble(价位-间隔*POINT-止损*POINT,DIGITS),0,备注,magicX,0);
         if(止损==0 && 止盈==0)
            t=OrderSend(货币对,OP_BUYLIMIT,单量,NormalizeDouble(价位-间隔*POINT,DIGITS),滑点2,0,0,备注,magicX,0);
        }

      if(类型==OP_BUYSTOP)
        {
         if(止损!=0 && 止盈!=0)
            t=OrderSend(货币对,OP_BUYSTOP,单量,NormalizeDouble(价位+间隔*POINT,DIGITS),滑点2,NormalizeDouble(价位+间隔*POINT-止损*POINT,DIGITS),NormalizeDouble(价位+间隔*POINT+止盈*POINT,DIGITS),备注,magicX,0);
         if(止损==0 && 止盈!=0)
            t=OrderSend(货币对,OP_BUYSTOP,单量,NormalizeDouble(价位+间隔*POINT,DIGITS),滑点2,0,NormalizeDouble(价位+间隔*POINT+止盈*POINT,DIGITS),备注,magicX,0);
         if(止损!=0 && 止盈==0)
            t=OrderSend(货币对,OP_BUYSTOP,单量,NormalizeDouble(价位+间隔*POINT,DIGITS),滑点2,NormalizeDouble(价位+间隔*POINT-止损*POINT,DIGITS),0,备注,magicX,0);
         if(止损==0 && 止盈==0)
            t=OrderSend(货币对,OP_BUYSTOP,单量,NormalizeDouble(价位+间隔*POINT,DIGITS),滑点2,0,0,备注,magicX,0);
        }
      报错组件("9");
     }

   if(类型==OP_SELLLIMIT || 类型==OP_SELLSTOP)
     {
      if(价位==0)
        {
         RefreshRates();
         价位=MarketInfo(货币对,MODE_BID);
        }

      if(类型==OP_SELLSTOP)
        {
         if(止损!=0 && 止盈!=0)
            t=OrderSend(货币对,OP_SELLSTOP,单量,NormalizeDouble(价位-间隔*POINT,DIGITS),滑点2,NormalizeDouble(价位-间隔*POINT+止损*POINT,DIGITS),NormalizeDouble(价位-间隔*POINT-止盈*POINT,DIGITS),备注,magicX,0);
         if(止损==0 && 止盈!=0)
            t=OrderSend(货币对,OP_SELLSTOP,单量,NormalizeDouble(价位-间隔*POINT,DIGITS),滑点2,0,NormalizeDouble(价位-间隔*POINT-止盈*POINT,DIGITS),备注,magicX,0);
         if(止损!=0 && 止盈==0)
            t=OrderSend(货币对,OP_SELLSTOP,单量,NormalizeDouble(价位-间隔*POINT,DIGITS),滑点2,NormalizeDouble(价位-间隔*POINT+止损*POINT,DIGITS),0,备注,magicX,0);
         if(止损==0 && 止盈==0)
            t=OrderSend(货币对,OP_SELLSTOP,单量,NormalizeDouble(价位-间隔*POINT,DIGITS),滑点2,0,0,备注,magicX,0);
        }

      if(类型==OP_SELLLIMIT)
        {
         if(止损!=0 && 止盈!=0)
            t=OrderSend(货币对,OP_SELLLIMIT,单量,NormalizeDouble(价位+间隔*POINT,DIGITS),滑点2,NormalizeDouble(价位+间隔*POINT+止损*POINT,DIGITS),NormalizeDouble(价位+间隔*POINT-止盈*POINT,DIGITS),备注,magicX,0);
         if(止损==0 && 止盈!=0)
            t=OrderSend(货币对,OP_SELLLIMIT,单量,NormalizeDouble(价位+间隔*POINT,DIGITS),滑点2,0,NormalizeDouble(价位+间隔*POINT-止盈*POINT,DIGITS),备注,magicX,0);
         if(止损!=0 && 止盈==0)
            t=OrderSend(货币对,OP_SELLLIMIT,单量,NormalizeDouble(价位+间隔*POINT,DIGITS),滑点2,NormalizeDouble(价位+间隔*POINT+止损*POINT,DIGITS),0,备注,magicX,0);
         if(止损==0 && 止盈==0)
            t=OrderSend(货币对,OP_SELLLIMIT,单量,NormalizeDouble(价位+间隔*POINT,DIGITS),滑点2,0,0,备注,magicX,0);
        }
      报错组件("10");
     }

   if(隐藏备注)
      if(所跟订单号!=0)
         if(t!=-1)
            GlobalVariableSet(所跟订单号,t);

   return(t);
  }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
void 报错组件(string a)
  {
   if(IsOptimization())
      return;

   int t=GetLastError();
   string 报警;
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
   if(t!=0)
      switch(t)
        {
         //case 0:报警="错误代码:"+0+"没有错误返回";break;
         //case 1:报警="错误代码:"+1+"没有错误返回但结果不明";break;
         //case 2:报警="错误代码:"+2+"一般错误";break;
         case 3:报警="错误代码:"+3+"无效交易参量";break;
         case 4:报警="错误代码:"+4+"交易服务器繁忙";break;
         case 5:报警="错误代码:"+5+"客户终端旧版本";break;
         case 6:报警="错误代码:"+6+"没有连接服务器";break;
         case 7:报警="错误代码:"+7+"没有权限";break;
         //case 8:报警="错误代码:"+8+"请求过于频繁";break;
         case 9:报警="错误代码:"+9+"交易运行故障";break;
         case 64:报警="错误代码:"+64+"账户禁止";break;
         case 65:报警="错误代码:"+65+"无效账户";break;
         // case 128:报警="错误代码:"+128+"交易超时";break;
         case 129:报警="错误代码:"+129+"无效价格";break;
         case 130:报警="错误代码:"+130+"无效停止";break;
         case 131:报警="错误代码:"+131+"无效交易量";break;
         case 132:报警="错误代码:"+132+"市场关闭";break;
         case 133:报警="错误代码:"+133+"交易被禁止";break;
         case 134:报警="错误代码:"+134+"资金不足";break;
         case 135:报警="错误代码:"+135+"价格改变";break;
         //case 136:报警="错误代码:"+136+"开价";break;
         case 137:报警="错误代码:"+137+"经纪繁忙";break;
         //case 138:报警="错误代码:"+138+"重新开价";break;
         case 139:报警="错误代码:"+139+"定单被锁定";break;
         case 140:报警="错误代码:"+140+"只允许看涨仓位";break;
         //case 141:报警="错误代码:"+141+"过多请求";break;
         //case 145:报警="错误代码:"+145+"因为过于接近市场，修改否定";break;
         //case 146:报警="错误代码:"+146+"交易文本已满";break;
         case 147:报警="错误代码:"+147+"时间周期被经纪否定";break;
         case 148:报警="错误代码:"+148+"开单和挂单总数已被经纪限定";break;
         case 149:报警="错误代码:"+149+"当对冲备拒绝时,打开相对于现有的一个单置";break;
         case 150:报警="错误代码:"+150+"把为反FIFO规定的单子平掉";break;
         case 4000:报警="错误代码:"+4000+"没有错误";break;
         case 4001:报警="错误代码:"+4001+"错误函数指示";break;
         case 4002:报警="错误代码:"+4002+"数组索引超出范围";break;
         case 4003:报警="错误代码:"+4003+"对于调用堆栈储存器函数没有足够内存";break;
         case 4004:报警="错误代码:"+4004+"循环堆栈储存器溢出";break;
         case 4005:报警="错误代码:"+4005+"对于堆栈储存器参量没有内存";break;
         case 4006:报警="错误代码:"+4006+"对于字行参量没有足够内存";break;
         case 4007:报警="错误代码:"+4007+"对于字行没有足够内存";break;
         //case 4008:报警="错误代码:"+4008+"没有初始字行";break;
         case 4009:报警="错误代码:"+4009+"在数组中没有初始字串符";break;
         case 4010:报警="错误代码:"+4010+"对于数组没有内存";break;
         case 4011:报警="错误代码:"+4011+"字行过长";break;
         case 4012:报警="错误代码:"+4012+"余数划分为零";break;
         case 4013:报警="错误代码:"+4013+"零划分";break;
         case 4014:报警="错误代码:"+4014+"不明命令";break;
         case 4015:报警="错误代码:"+4015+"错误转换(没有常规错误)";break;
         case 4016:报警="错误代码:"+4016+"没有初始数组";break;
         case 4017:报警="错误代码:"+4017+"禁止调用DLL ";break;
         case 4018:报警="错误代码:"+4018+"数据库不能下载";break;
         case 4019:报警="错误代码:"+4019+"不能调用函数";break;
         case 4020:报警="错误代码:"+4020+"禁止调用智能交易函数";break;
         case 4021:报警="错误代码:"+4021+"对于来自函数的字行没有足够内存";break;
         case 4022:报警="错误代码:"+4022+"系统繁忙 (没有常规错误)";break;
         case 4050:报警="错误代码:"+4050+"无效计数参量函数";break;
         case 4051:报警="错误代码:"+4051+"无效参量值函数";break;
         case 4052:报警="错误代码:"+4052+"字行函数内部错误";break;
         case 4053:报警="错误代码:"+4053+"一些数组错误";break;
         case 4054:报警="错误代码:"+4054+"应用不正确数组";break;
         case 4055:报警="错误代码:"+4055+"自定义指标错误";break;
         case 4056:报警="错误代码:"+4056+"不协调数组";break;
         case 4057:报警="错误代码:"+4057+"整体变量过程错误";break;
         case 4058:报警="错误代码:"+4058+"整体变量未找到";break;
         case 4059:报警="错误代码:"+4059+"测试模式函数禁止";break;
         case 4060:报警="错误代码:"+4060+"没有确认函数";break;
         case 4061:报警="错误代码:"+4061+"发送邮件错误";break;
         case 4062:报警="错误代码:"+4062+"字行预计参量";break;
         case 4063:报警="错误代码:"+4063+"整数预计参量";break;
         case 4064:报警="错误代码:"+4064+"双预计参量";break;
         case 4065:报警="错误代码:"+4065+"数组作为预计参量";break;
         case 4066:报警="错误代码:"+4066+"刷新状态请求历史数据";break;
         case 4067:报警="错误代码:"+4067+"交易函数错误";break;
         case 4099:报警="错误代码:"+4099+"文件结束";break;
         case 4100:报警="错误代码:"+4100+"一些文件错误";break;
         case 4101:报警="错误代码:"+4101+"错误文件名称";break;
         case 4102:报警="错误代码:"+4102+"打开文件过多";break;
         case 4103:报警="错误代码:"+4103+"不能打开文件";break;
         case 4104:报警="错误代码:"+4104+"不协调文件";break;
         case 4105:报警="错误代码:"+4105+"没有选择定单";break;
         case 4106:报警="错误代码:"+4106+"不明货币对";break;
         case 4107:报警="错误代码:"+4107+"无效价格";break;
         case 4108:报警="错误代码:"+4108+"无效定单编码";break;
         case 4109:报警="错误代码:"+4109+"不允许交易";break;
         case 4110:报警="错误代码:"+4110+"不允许长期";break;
         case 4111:报警="错误代码:"+4111+"不允许短期";break;
         case 4200:报警="错误代码:"+4200+"定单已经存在";break;
         case 4201:报警="错误代码:"+4201+"不明定单属性";break;
         //case 4202:报警="错误代码:"+4202+"定单不存在";break;
         case 4203:报警="错误代码:"+4203+"不明定单类型";break;
         case 4204:报警="错误代码:"+4204+"没有定单名称";break;
         case 4205:报警="错误代码:"+4205+"定单坐标错误";break;
         case 4206:报警="错误代码:"+4206+"没有指定子窗口";break;
         case 4207:报警="错误代码:"+4207+"定单一些函数错误";break;
         case 4250:报警="错误代码:"+4250+"错误设定发送通知到队列中";break;
         case 4251:报警="错误代码:"+4251+"无效参量- 空字符串传递到SendNotification()函数";break;
         case 4252:报警="错误代码:"+4252+"无效设置发送通知(未指定ID或未启用通知)";break;
         case 4253:报警="错误代码:"+4253+"通知发送过于频繁";break;
        }
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
   if(t!=0)
     {
      while(IsTradeContextBusy())
         Sleep(300);
      RefreshRates();
      Print(a+报警);
      laber(a+报警,Yellow);
     }
  }
//+------------------------------------------------------------------+
bool 价格劣势转为挂单=false;
double 劣转挂单存续分钟=10;
bool 强制补充已平和未跟单据=false;
bool 跟踪部分平仓单据=true;
bool 隐藏备注=false;
bool 开启部分平仓=true;
bool 做单PC验证=false;
bool  做单需优价开关=false;
double  做单需优于点数=0;
//+------------------------------------------------------------------+
