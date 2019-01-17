<template>
	<div class="content_box">
		
		<div class="titling">
			<span>平仓记录</span>
			<!--<button @click="saveShow = true">新增考证</button>-->
			<!--<span class="dao pull-right" @click="outExe">导出excel</span>-->
		</div>

    <div class="filter_box clearfix">

      <div class="pull-left">
        <el-input
          placeholder="交易账号"
          v-model="filter.account"
          clearable
          @keyup.enter.native="Inquire" >
        </el-input>
      </div>
      <div class="pull-left">
        <div class="block">
          <el-date-picker
            v-model="filter.date"
            type="datetimerange"
            start-placeholder="交易开始日期"
            end-placeholder="交易结束日期"
            :unlink-panels="true"
            value-format="yyyy-MM-dd"
            :default-time="['00:00:00']">
          </el-date-picker>
        </div>
      </div>
      <div class="pull-left">
        <el-button type="primary" icon="el-icon-search" @click="Inquire">搜索</el-button>
      </div>
    </div>

		<div class="list_body">
			<div class="clearfix table_head myback2">
        <div class="pull-left">
          <span>交易账户</span>
        </div>
				<div class="pull-left">
					<span>订单号</span>
				</div>
				<div class="pull-left ">
					<span>交易品种</span>
				</div>
        <div class="pull-left ">
          <span class="pointer" @click="Sort('openTime')">建仓时间<i  class="el-icon-d-caret"></i></span>
        </div>
				<div class="pull-left">
					<span class="pointer" @click="Sort('closeTime')">平仓时间<i  class="el-icon-d-caret"></i></span>
				</div>
				<div class="pull-left">
					<span>建仓价格</span>
				</div>
				<div class="pull-left">
					<span>平仓价格</span>
				</div>
        <div class="pull-left">
          <span>手数</span>
        </div>
        <div class="pull-left ">
          <span>方向</span>
        </div>
			</div>
			<div v-for="item in list" class="item_box clearfix">
				<div class="pull-left">
					<span>{{item.account}}</span>
				</div>
				
				<div class="pull-left">
					<span>{{item.orderNo}}</span>
				</div>
				<div class="pull-left ">
					<span>{{item.symbol}}</span>
				</div>
				<div class="pull-left ">
					<span>{{item.openTime}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.closeTime}}</span>
				</div>
        <div class="pull-left">
          <span>{{item.openPrice}}</span>
        </div>
        <div class="pull-left">
          <span>{{item.closePrice}}</span>
        </div>
        <div class="pull-left">
          <span>{{item.lots}}</span>
        </div>
        <div class="pull-left">
          <span :class="{'red':item.orderType=='SELL','blue':item.orderType=='BUY'}">{{item.orderType }}</span>
        </div>
			</div>

      <div class="item_box clearfix">
        <div class="pull-left">
          <span class="black">总计</span>
        </div>
        <div class="pull-left">
          <span>&nbsp;</span>
        </div>
        <div class="pull-left ">
          <span>&nbsp;</span>
        </div>
        <div class="pull-left ">
          <span>&nbsp;</span>
        </div>
        <div class="pull-left">
          <span>&nbsp;</span>
        </div>
        <div class="pull-left">
          <span>&nbsp;</span>
        </div>
        <div class="pull-left">
          <span>&nbsp;</span>
        </div>
        <div class="pull-left ">
          <span class="red">{{filter.totalLots}}</span>
        </div>
        <div class="pull-left">
          &nbsp;
        </div>
      </div>

			
			<div class="trip_box" v-show="!list.length">
				<img src="../../assets/img/nodata_2.png" />
				<p>暂无数据</p>
			</div>
		</div>
		
		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	import SaveVideoType from '../dialog/SaveVideoType'
	export default {
		data() {
			return {
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 13
				},
				filter: {
          account: '',
          date:'',
          totalLots:'',
					"orderByColumn": 'createAt',
					"sort": 'DESC',
        },
				saveShow: false
			}
		},
		components: {
			SaveVideoType,
			Pagination,
			FilterWork
		},
		computed: {
			today () {
				var myDate = new Date();
				var time = myDate.getFullYear() +'-'+ (myDate.getMonth()+1) +'-'+ myDate.getDate();
				return time;
			}
		},
	    filters:{
        formatOrderType: function (val) {
	        var orderType = document.getElementById("orderType");
	        // console.log(orderType);
	        val === "SELL" ? orderType.style.textColor='green':orderType.style.textColor='red';
	      }
	    },
		methods: {
			outExe() {
				let _this = this;
				this.$confirm('此操作将导出excel文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let datas = {
						page_number: 1, //	是	int	当前页
						page_size: 1000,//	是	int	每页大小
						formParams: {}
					};
					
					_this.$postForExcel('/api/export/examApply',datas,'考证报名记录').then();
					
        }).catch(() => {

        });
			},
			deleteType (title,id) {
				let _this = this;
				
				_this.$confirm("删除["+title+"]视频集合，不可恢复。是否继续?", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$fetch("/api/hpp_video_type/delete/"+id).then((res) => {
						console.log(res)
						if (res.statusCode == "0000") {
							_this.$message({
						          message: '删除成功',
						          type: 'success'
						    });
						    _this.initPage();
						}else {
							_this.$message({
						          message: res.msgCode,
						          type: 'warning'
						    });
						}
					})
				}).catch(() => {});
			},
			currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			Sort (param) {//列表排序
				if (!param) return;
				this.filter.orderByColumn = param;
				this.filter.sort =='DESC' ? this.filter.sort ='ASC': this.filter.sort = 'DESC';
				this.initPage();
			},
			Inquire () {
			  if(this.filter.date == null){
          this.filter.date='';
        }
				this.currentChange(1);
			},
			closeInit () {
				this.saveShow = false;
				this.initPage();
			},
			initPage () {
				let _this = this;
        let datas = {
          page_number: _this.page.num,  //页码
          page_size: _this.page.size,   // 每页条数
          formParams:{
            "account": _this.filter.account,
            "startTime": _this.filter.date[0],
            "endTime": _this.filter.date[1],
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
          }
        };
        this.$post("/api/hpp_strategy_trade/pageList",datas).then((res) => {
					if (res.data) {
						_this.list = res.data.list;
            _this.filter.totalLots = res.data.totalLots;
						_this.page.total = parseInt(res.data.total);
					}
				})
			}
		},
		mounted () {
			this.initPage();
		}
	}
</script>

<style scoped>

  .filter_box {
    padding: 20px;
  }
  .filter_box>div {
    margin-right: 20px;
  }

  .table_head {
  line-height: 40px;
  }
  .item_box {
  line-height: 40px;
  border-bottom: 1px solid #999;
  }
  .table_head>div,
  /*.table_head>div.detail{
    width: 150px;
  }*/

	.item_box>div{
		width: 165px;
		text-align: center;
	}
  .item_box>div.detail,.table_head>div.detail{
    width: 200px;
  }

  .table_head>div.meal,
  .item_box>div.meal {
    width: 200px;
  }
	.table_head>div span,
	.item_box>div span {
		color: #fff;
		font-size: 14px;
	}
	.item_box>div span {
		color: #666;
	}

  .item_box>div span.red{
    color:red;
  }
  .item_box>div span.blue{
    color:dodgerblue;
  }
  .item_box>div span.black{
    color:black;
    font-weight: bold ;
  }


	
</style>
