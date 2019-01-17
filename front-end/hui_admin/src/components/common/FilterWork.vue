<template>
	<div class="filter_box clearfix">
		
		<div class="pull-left" v-if="parent == 'banner'">
			<el-select v-model="filter.banner" placeholder="广告位置" @change="Inquire">
			    <el-option
			      v-for="item in bannerHome"
			      :key="item.value"
			      :label="item.label"
			      :value="item.value">
			    </el-option>
			</el-select>
		</div>
		<div class="pull-left" v-if="parent == 'strategy' || parent == 'apply' || parent == 'task' || parent == 'task2' || parent == 'task1'">
			<el-input
			  placeholder="名称"
			  v-model="filter.name"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
		<div class="pull-left" v-if="parent == 'strategy' || parent == 'task2'||parent == 'task1' ">
			<el-input
			  placeholder="账户"
			  v-model="filter.account"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
		<div class="pull-left" v-if="parent == 3">
			<el-input
			  placeholder="订单号"
			  v-model="filter.login"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
		<div class="pull-left" v-if="parent == 1 || parent == 4">
			<el-input
			  placeholder="登录名"
			  v-model="filter.login"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
		
		<div class="pull-left" v-if="parent == 'appuser' || parent == 'apply' || parent == 'task' || parent == 'task2'||parent == 'task1'||parent == 'integralDetail' ">
			<el-input
			  placeholder="手机号"
			  v-model="filter.phone"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
		<div class="pull-left" v-if="parent == 'apply'">
			<el-input
			  placeholder="邮箱"
			  v-model="filter.email"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
    <div class="pull-left" v-if="parent == 'integralDetail' ">
      <el-select v-model="filter.type" clearable placeholder="类型" @change="Inquire">
        <el-option label="系统赠送" value="SYSTEM"> </el-option>
        <el-option label="分享好友赠送" value="FRIEND"> </el-option>
        <el-option label="分享朋友圈赠送" value="COMMUNITY"> </el-option>
        <el-option label="登陆积分赠送" value="LOGIN"> </el-option>
        <el-option label="交易战法视频购买奖励" value="VIDEO"> </el-option>
        <el-option label="交易面对面课程购买奖励" value="COURSE"> </el-option>
        <el-option label="交易师考证购买奖励" value="EXAM"> </el-option>
      </el-select>
    </div>
		
		
		
		<!--归属-->
		<div class="pull-left" v-if="parent == 2">
			<el-select v-model="filter.pid" clearable placeholder="账户归属" @change="Inquire">
			    <el-option
			      v-for="item in parentlist"
			      :key="item.value"
			      :label="item.label"
			      :value="item.value">
			    </el-option>
			</el-select>
		</div>
		<!--组别-->
		<div class="pull-left" v-if="parent == 2">
			<el-select v-model="filter.group" clearable placeholder="账户组别" @change="Inquire">
			    <el-option
			      v-for="item in zulist"
			      :key="item.value"
			      :label="item.label"
			      :value="item.value">
			    </el-option>
			</el-select>
		</div>
		
		<div class="pull-left" v-if="parent == 'appuser'">
			<el-select v-model="filter.type" clearable placeholder="类型" @change="Inquire">
			    <el-option
			      v-for="item in typelist"
			      :key="item.value"
			      :label="item.label"
			      :value="item.value">
			    </el-option>
			</el-select>
		</div>
    <div class="pull-left" v-if="parent == 'task2'">
      <el-select v-model="filter.type" clearable placeholder="类型" @change="Inquire">
        <el-option label="跟单" value="IN"> </el-option>
        <el-option label="解绑" value="OUT"> </el-option>
      </el-select>
    </div>
		<div class="pull-left" v-if="parent == 'task' || parent == 'task1'||parent == 'task2'">
			<el-select v-model="filter.status" clearable placeholder="状态" @change="Inquire">
			    <el-option label="已通过" value="PASSED"> </el-option>
			    <el-option label="已拒绝" value="REJECT"> </el-option>
			    <el-option label="未处理" value="APPLYING"> </el-option>
			</el-select>
		</div>
		<!--反馈类型-->
		<div class="pull-left" v-if="parent == 8">
			<el-select v-model="filter.type" clearable placeholder="问题类型" @change="Inquire">
			    <el-option label="新功能建議" value="新功能建議"> </el-option>
			    <el-option label="遇到問題" value="遇到問題"> </el-option>
			</el-select>
		</div>

    <div v-if="parent == 'orderList'">
      <div class="pull-left">
        <el-input
          placeholder="订单号"
          v-model="filter.outTradeNo"
          clearable
          @keyup.enter.native="Inquire" >
        </el-input>
      </div>
      <div class="pull-left">
        <el-input
          placeholder="联系方式"
          v-model="filter.phone"
          clearable
          @keyup.enter.native="Inquire" >
        </el-input>
      </div>
      <div class="pull-left">
        <el-select v-model="filter.body" clearable placeholder="商品类别" @change="Inquire">
          <el-option label="交易战法视频" value="video"> </el-option>
          <el-option label="交易面对面" value="course"> </el-option>
          <el-option label="交易师考证" value="exam"> </el-option>
        </el-select>
      </div>
      <div class="pull-left" >
        <el-select v-model="filter.status" clearable placeholder="支付状态" @change="Inquire">
          <el-option label="已支付" value="Y"> </el-option>
          <el-option label="未支付" value="N"> </el-option>
        </el-select>
      </div>
    </div>

    <div class="pull-left"  v-if="parent == 'orderList'|| parent == 'apply'">
      <el-select v-model="filter.payType" clearable placeholder="支付方式" @change="Inquire">
        <el-option label="微信支付" value="weChat"> </el-option>
        <el-option label="积分兑换" value="integral"> </el-option>
        <el-option label="积分抵扣" value="mix"> </el-option>
      </el-select>
    </div>
		
		 <div class="pull-left" v-if="parent == 'strategy' || parent == 'task'|| parent == 'task2' ||parent == 'orderList'||parent == 'apply' ||parent =='appuser'">
		    <!--<span class="demonstration">带快捷选项</span>-->
		    <el-date-picker
		      v-model="filter.date"
		      type="daterange"
		      align="left"
		      unlink-panels
		      range-separator="至"
		      start-placeholder="开始日期"
		      end-placeholder="结束日期"
		      :picker-options="pickerOptions2"
		      value-format="yyyy-MM-dd"
		       @change="Inquire">
		    </el-date-picker>
		  </div>

		<div class="pull-left">
			<el-button type="primary" icon="el-icon-search" @click="Inquire">搜索</el-button>
		</div>
		
	</div>
</template>

<script>
	export default {
		props: ['parent'],
		data() {
			return {
				filter: {
					banner: 'HOME',
					name: '',
					login: '',
					account: '',
					depart: '',
					group: '',//账户组别
					pid: '',//归属
					phone: '',
					type: '',
					status: '',
					email: '',
					date: '',
          outTradeNo:'',
          body:'',
          payType:''
				},
				bannerHome: [
					{label: '首页',value: 'HOME'},
					{label: '交易战法视频',value: 'VIDEO'},
					{label: '汇商超市',value: 'SINKS'},
					{label: '策略入驻',value: 'STRATEGY'},
					{label: '视频直播',value: 'LIVE'},
				],
				zulist: [
					{label: '模拟账户',value: 'demoTX'},
					{label: '体验账户',value: 'TXA2'},
					{label: '真实账户',value: 'TXA3'},
				],
				parentlist: [],
				typelist: [
					{label: '全部用戶',value: ''},
					{label: '跟单用户',value: 'follow'},
					{label: '消费用户',value: 'buy'},
          {label: '开户用户',value: 'open'},
				],
				pickerOptions2: {
		          shortcuts: [{
		            text: '最近一周',
		            onClick(picker) {
		              const end = new Date();
		              const start = new Date();
		              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
		              picker.$emit('pick', [start, end]);
		            }
		          }, {
		            text: '最近一个月',
		            onClick(picker) {
		              const end = new Date();
		              const start = new Date();
		              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
		              picker.$emit('pick', [start, end]);
		            }
		          }, {
		            text: '最近三个月',
		            onClick(picker) {
		              const end = new Date();
		              const start = new Date();
		              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
		              picker.$emit('pick', [start, end]);
		            }
		          }]
		        },
			}
		},
		components: {
			
		},
		methods: {
			Inquire () {
				if(this.filter.date == null){
          this.filter.date= '';
        }
				this.$emit("Inquire",this.filter)
			},
			loadParents () {
				let _this = this;
				let datas = {
					"page_number": 1,
					"page_size": 1000,
					"formParams":{
						"isProxy": "Y"
					}
				};
				_this.$until.superPost("/api/user/list",datas,function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						let item = {};
						let arr = _this.parentlist;
						let list = res.data.list;
						for (let i= 0;i<list.length;i++) {
							item = {
								value: list[i].id,
								label: list[i].firstName
							}
							arr.push(item);
						}
						_this.parentlist = arr;
					}else {
						_this.$message({
				          message: '获取归属列表失败',
				          type: 'warning'
				        });
					}
				})
			}
		},
		created () {
			if (this.parent == 2) {
				this.loadParents();
			}
			if (this.parent == 7) {
				this.typelist = [
					{label: '充值',value: 'IN'},
					{label: '提现',value: 'OUT'},
					{label: '佣金内转',value: 'INNER'}
				]
			}
		}
	}
</script>

<style scoped>
	.filter_box {
		padding: 10px 0;
		/*border-bottom: 2px solid #6562b6;*/
	}
	.filter_box .pull-left {
		margin-right: 10px;
	}
	
	.filter_box input,
	.filter_box select {
		width: 140px;
		height: 33px;
		border: 1px solid #dcdfe6;
		padding: 2px 10px;
		border-radius: 5px;
		color: #666;
	}
</style>
