<template>
	<div class="filter_box clearfix">
		<!--
		1,用户列表；2，账户列表；3,交易记录；4，佣金列表；5，潜在客户；6，客服任务；7，财务任务；8，反馈；
		-->
		<div class="pull-left" v-if="parent == 2 || parent == 7 || parent == 8 || parent == 3">
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
		<div class="pull-left" v-if="parent == 1 || parent == 2 || parent == 4 || parent == 6 || parent == 7 || parent == 8">
			<el-input
			  placeholder="名称"
			  v-model="filter.name"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
		<div class="pull-left" v-if="parent == 5 || parent == 6 || parent == 1 ">
			<el-input
			  placeholder="手机号"
			  v-model="filter.phone"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
		<div class="pull-left" v-if="parent == 1 ">
			<el-input
			  placeholder="邮箱"
			  v-model="filter.email"
			  clearable
			  @keyup.enter.native="Inquire" >
			</el-input>
		</div>
		<!--身份-->
		<div class="pull-left" v-if="parent == 1">
			<el-select v-model="filter.depart" clearable placeholder="用户身份">
			    <el-option
			      v-for="item in deplist"
			      :key="item.value"
			      :label="item.label"
			      :value="item.value">
			    </el-option>
			</el-select>
		</div>
		
		<!--归属-->
		<div class="pull-left" v-if="parent == 2">
			<el-select v-model="filter.pid" clearable placeholder="账户归属">
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
			<el-select v-model="filter.group" clearable placeholder="账户组别">
			    <el-option
			      v-for="item in zulist"
			      :key="item.value"
			      :label="item.label"
			      :value="item.value">
			    </el-option>
			</el-select>
		</div>
		
		<div class="pull-left" v-if="parent == 6 || parent == 7 ">
			<el-select v-model="filter.type" clearable placeholder="审核事项">
			    <el-option
			      v-for="item in typelist"
			      :key="item.value"
			      :label="item.label"
			      :value="item.value">
			    </el-option>
			</el-select>
		</div>
		<div class="pull-left" v-if="parent == 6 || parent == 7 ">
			<el-select v-model="filter.status" clearable placeholder="状态">
			    <el-option label="已通过" value="PASSED"> </el-option>
			    <el-option label="已拒绝" value="REJECT"> </el-option>
			    <el-option label="未处理" value="APPLYING"> </el-option>
			</el-select>
		</div>
		<!--反馈类型-->
		<div class="pull-left" v-if="parent == 8">
			<el-select v-model="filter.type" clearable placeholder="问题类型">
			    <el-option label="新功能建議" value="新功能建議"> </el-option>
			    <el-option label="遇到問題" value="遇到問題"> </el-option>
			</el-select>
		</div>
		
		 <div class="pull-left" v-if="parent == 3 || parent == 6 || parent == 7 || parent == 9  ">
		    <!--<span class="demonstration">带快捷选项</span>-->
		    <el-date-picker
		      v-model="filter.date"
		      type="daterange"
		      align="left"
		      unlink-panels
		      range-separator="至"
		      start-placeholder="申请开始日期"
		      end-placeholder="申请结束日期"
		      :picker-options="pickerOptions2"
		      value-format="yyyy-MM-dd"
		      @focus="pickerFocus"
		      >
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
					date: ['','']
				},
				deplist: [
					{label: '城市合作伙伴',value: 'company'},
					{label: '合作商',value: 'proxy'},
					{label: '客服',value: 'service'},
					{label: '财务',value: 'account'},
					{label: '普通用户',value: 'user'},
				],
				zulist: [
					{label: '模拟账户',value: 'demoTX'},
					{label: '体验账户',value: 'TXA2'},
					{label: '真实账户',value: 'TXA3'},
				],
				parentlist: [],
				typelist: [
					{label: '开户申请',value: 'APPLYACCOUNT'}
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
			fmtDate (obj) {
			    var date =  new Date(obj);
			    var y = 1900+date.getYear();
			    var m = "0"+(date.getMonth()+1);
			    var d = "0"+date.getDate();
			    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
			},
			pickerFocus () {
				let _this = this;
				const end = new Date();
	            const start = new Date();
	            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
	            this.filter.date = [_this.fmtDate(start), _this.fmtDate(end)];
			},
			Inquire () {
				if (this.filter.date == null) {
					this.filter.date = ['','']
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