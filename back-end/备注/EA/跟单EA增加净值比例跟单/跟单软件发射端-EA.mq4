
//+------------------------------------------------------------------+
#property copyright "潤貓hk" 
#property link""
#import "kernel32.dll"
int CopyFileW(string a0,string a1,int a2);
bool CreateDirectoryW(string a0,int a1);
string 中转文件名="";
string FILES文件夹路径="";
string FILES文件夹路径2;
string 中转路径="";
string 中转路径2="";
input string 间隔符="*";
int X=20;
int Y=20;
int Y间隔=15;
color 标签颜色=Yellow;
int 标签字体大小=10;
ENUM_BASE_CORNER 固定角=0;
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
int OnInit()
  {
   if(IsDllsAllowed()==false)
      Alert("请允许调用动态链接库");
   OnTick();
   return(INIT_SUCCEEDED);
  }
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
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
  }
//+------------------------------------------------------------------+
//||
//+------------------------------------------------------------------+
void OnTick()
  {
//if(TimeCurrent()>StringToTime("2115.07.11")){Alert("过期了");return(0);}

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
      string 内容[100];
      内容[0]="----------编程跟单软件发射端------------";
      内容[1]="--------------已开启跟单发射端--------------";

      for(int ixx=0;ixx<=1;ixx++)
         固定位置标签("标签"+ixx,内容[ixx],X,Y+Y间隔*ixx,标签颜色,标签字体大小,固定角);

      中转文件名=DoubleToStr(AccountNumber(),0);

      RefreshRates();
      int handle;
      handle=FileOpen(中转文件名+".csv",FILE_CSV|FILE_WRITE|FILE_SHARE_WRITE|FILE_SHARE_READ,间隔符);
      if(handle>0)
        {
         for(int i=OrdersTotal();i>=0;i--)
            if(OrderSelect(i,SELECT_BY_POS))
              {
               FileWrite(handle,OrderTicket(),OrderSymbol(),OrderType(),OrderLots(),OrderStopLoss(),
                         OrderTakeProfit(),OrderComment(),OrderMagicNumber(),
                         OrderOpenTime()-TimeCurrent()+TimeLocal(),OrderOpenPrice(),MarketInfo(OrderSymbol(),MODE_TICKVALUE), AccountEquity());

              }
         FileClose(handle);
        }

      handle=FileOpen(中转文件名+"2.csv",FILE_CSV|FILE_WRITE|FILE_SHARE_WRITE|FILE_SHARE_READ,间隔符);
      if(handle>0)
        {
         for(i=OrdersHistoryTotal();i>=0;i--)
            if(OrderSelect(i,SELECT_BY_POS,MODE_HISTORY))
               if(OrderType()<6)
                 {
                  FileWrite(handle,OrderTicket(),OrderSymbol(),OrderType(),OrderLots(),OrderStopLoss(),OrderTakeProfit(),OrderComment(),OrderMagicNumber(),OrderOpenTime()-TimeCurrent()+TimeLocal(),OrderOpenPrice(),AccountEquity());
                 }
         FileClose(handle);
        }

      if(FILES文件夹路径!="")
         FILES文件夹路径2=FILES文件夹路径;
      else
         FILES文件夹路径2=TerminalInfoString(TERMINAL_DATA_PATH)+"\\MQL4\\Files";

      int t=CopyFileW(FILES文件夹路径2+"\\"+中转文件名+".csv",中转路径2+"\\"+中转文件名+".csv",0);

      t=CopyFileW(FILES文件夹路径2+"\\"+中转文件名+"2.csv",中转路径2+"\\"+中转文件名+"2.csv",0);

      if(!(!IsStopped() && IsExpertEnabled() && IsTesting()==false && IsOptimization()==false))
         return;
      Sleep(300);
     }
   return;
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