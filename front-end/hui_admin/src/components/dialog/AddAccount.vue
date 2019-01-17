<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">新增客户</h3>
		<div class="input_box">
			<span>归属</span>
			<!--<input type="text" name="" id="" value="" v-model="acc.gui" />-->
			<select name="" v-model="acc.pid">
				<option v-for="item in pidlist" :value="item.value">{{item.label}}</option>
			</select>
		</div>
		<div class="input_box">
			<span>组别</span>
			<select v-model="acc.zu">
				<option value="demoTX">模拟账户</option>
				<option value="TXA2">体验账户</option>
				<option value="TXA3">真实账户</option>
			</select>
		</div>
		<div class="input_box">
			<span>手机号</span>
			<input type="number" name="" id="" value="" v-model="acc.phone" />
		</div>
		<div class="input_box">
			<span>姓名</span>
			<input type="text" name="" id="" value="" v-model="acc.name" />
		</div>
		<div class="input_box">
			<span>邮箱</span>
			<input type="text" name="" id="" value="" v-model="acc.email" />
		</div>
		<div class="input_box">
			<span>套利宝号</span>
			<input type="number" name="" id="" value="" v-model="acc.num" />
		</div>
		

		<div slot="footer" class="dialog_footer">
			<button @click="toNewTlb">确认新增</button>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				acc: {
					phone: '',
					name: '',
					email: '',
					pid: '',
					num: '',
					zu: 'demoTX'
				},
				guiShuIdList: [],
				pidlist: []//归属选择列表
			}
		},
		components: {

		},
		methods: {
			toNewTlb() {
				let _this = this;
				let datas = {
					"mobile": _this.acc.phone,
				    "account": _this.acc.num,
				    "accountName": _this.acc.name,
				    "email": _this.acc.email,
				    "parentId": _this.acc.pid,  // 归属（查找所有代理用户）
				    "group": _this.acc.zu
				};
				_this.$until.superPost("/api/tlb-account/createCrmTlbAccount",datas,function (res) {
//					//console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: '新增成功',
				          type: 'success'
				        });
					}else{
						_this.$message({
				          message: res.msgCode,
				          type: 'warning'
				        });
					}
					
					_this.acc = {
						phone: '',
						name: '',
						email: '',
						pid: '',
						num: '',
						zu: 'demoTX'
					};
					
					_this.$emit("close");
					
				})
			},
			initGuiShu () {
				let _this = this;
				let datas = {
					"page_number": 1,
					"page_size": 1000,
					"formParams":{
						"isProxy": "Y"
					}
				};
				_this.$until.superPost("/api/user/list",datas,function (res) {
//					//console.log(res)
					if (res.statusCode == "0000") {
						let item = {};
						let arr = [];
						let list = res.data.list;
						for (let i= 0;i<list.length;i++) {
							item = {
								value: list[i].id,
								label: list[i].firstName
							}
							arr.push(item);
						}
						_this.pidlist = arr;
					}else {
						_this.$message({
				          message: '获取归属列表失败',
				          type: 'warning'
				        });
					}
				})
			}
		},
		mounted() {
			this.initGuiShu();//获取归属列表
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>