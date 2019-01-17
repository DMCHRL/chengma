<template>
	<div class="content_box">
		
		<div class="titling">
			<span>考证报名</span>
			<!--<button @click="saveShow = true">新增考证</button>-->
			<span class="dao pull-right" @click="outExe">导出excel</span>
		</div>
		
		<filter-work @Inquire="Inquire" :parent="'apply'"></filter-work>
		
		
		<div class="list_body">
			<div class="clearfix table_head myback2">
				<div class="pull-left">
					<span>姓名</span>
				</div>
				<div class="pull-left">
					<span>手机</span>
				</div>
				<div class="pull-left">
					<span>邮箱</span>
				</div>
				<div class="pull-left">
					<span class="pointer" @click="Sort('createAt')">报名时间<i  class="el-icon-d-caret"></i></span>
				</div>
				<div class="pull-left">
					<span>考证名称</span>
				</div>
        <div class="pull-left">
          <span>套餐</span>
        </div>
        <div class="pull-left meal">
          <span>套餐包含</span>
        </div>
        <div class="pull-left">
          <span>价格</span>
        </div>
        <div class="pull-left">
          <span>支付方式</span>
        </div>
        <div class="pull-right">
          <span>支付状态</span>
        </div>
			</div>
			<div v-for="item in list" class="item_box clearfix">
				<div class="pull-left">
					<span>{{item.username}}</span>
				</div>
				
				<div class="pull-left">
					<span>{{item.phone}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.email}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.createAt}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.examName}}</span>
				</div>
        <div class="pull-left">
          <span>{{item.mealName}}</span>
        </div>
        <div class="pull-left meal">
          <el-tooltip placement="right-start" >
            <div slot="content" class="_content"><p>{{item.mealInclude}}</p></div>
            <el-button  type="text" ><span v-text="(item.mealInclude+'.').substr(0, 5)"></span></el-button>
          </el-tooltip>
        </div>
        <div class="pull-left">
          <span>{{item.mealPrice}}</span>
        </div>
        <div class="pull-left">
          <span>{{item.payType | formatPayType}}</span>
        </div>
        <div class="pull-right">
          <span>{{item.status|formatStatus}}</span>
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
					size: 15
				},
				filter: {
				  date:'',
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
	      formatStatus: function (val) {
	        return val==="Y"?"已支付":"未支付";
	      },
        formatPayType:function(val){
          switch (val) {
            case "integral":return "积分兑换";
            case "weChat" : return "微信支付";
            case "mix" : return "积分抵扣";
            default: return "未知";
          }
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
						formParams: {
              username: _this.filter.name,
              phone: _this.filter.phone,
              email: _this.filter.email,
							"orderByColumn": _this.filter.orderByColumn,
							"sort": _this.filter.sort,
            }
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
			Inquire (filter) {
				this.filter = filter;
				this.currentChange(1);
			},
			closeInit () {
				this.saveShow = false;
				this.initPage();
			},
			initPage () {
				let _this = this;
				let datas = {
					page_number: this.page.num,
					page_size: this.page.size,
					formParams: {
						username: _this.filter.name,
						phone: _this.filter.phone,
						email: _this.filter.email,
            payType: _this.filter.payType,
            startTime: _this.filter.date[0],
            endTime: _this.filter.date[1],
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
					}
				};
				this.$post("/api/hpp_exam_apply/pageList",datas).then((res) => {
					if (res.data) {
						_this.list = res.data.list;
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
	.table_head {
		line-height: 40px;
	}
	.item_box {
		line-height: 40px;
		border-bottom: 1px solid #999;
	} 
	.table_head>div,
	.item_box>div{
		width: 140px;
		text-align: center;
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
	
</style>
