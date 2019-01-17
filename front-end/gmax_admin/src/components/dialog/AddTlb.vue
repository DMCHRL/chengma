<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">新增账户</h3>
		<div class="input_box">
			<span>组别</span>
			<select v-model="acc.zu">
				<option v-if="user.department == 'admin'" value="demoTX">模拟账户</option>
				<option v-if="user.department == 'admin'" value="TXA2">体验账户</option>
				<option value="TXA3">真实账户</option>
			</select>
		</div>
		<!--<div class="input_box">
			<span>归属</span>
			<select name="" v-model="acc.uid">
				<option v-for="item in uidlist" :value="item.value">{{item.label}}</option>
			</select>
		</div>-->
		<div class="input_box " v-if="user.department == 'admin'">
			<span>账户名</span>
			<input type="text" name="" id="" value="" v-model="acc.name" />
		</div>
		<div class="input_box" v-if="user.department == 'admin'">
			<span>账户号</span>
			<input type="number" name="" id="" value="" v-model="acc.num" />
		</div>
		<!--<div class="input_box">
			<span>邮箱</span>
			<input type="text" name="" id="" value="" v-model="acc.email" />
		</div>-->
		
		<div slot="footer" class="dialog_footer">
			<button @click="toNewTlb">确认新增</button>
		</div>
	</div>
</template>

<script>
	export default {
		props: ["userid"],
		data() {
			return {
				acc: {
					name: '',
					email: '',
					uid: '',
					num: '',
					zu: 'TXA3'
				},
				guiShuIdList: [],
//				uidlist: []//所有账户列表
			}
		},
		computed: {
			user () {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
				    return user;
				}else {
					return {department: 'user'}
				}
			}
		},
		components: {

		},
		methods: {
			toNewTlb() {
				let _this = this;
				let datas = {
				    "account": _this.acc.num,
				    "accountName": _this.acc.name,
				    "email": _this.acc.email,
				    "userId": _this.userid,  // 账户所有人id
				    "group": _this.acc.zu
				};
				_this.post("/api/tlb-account/createTlbAccount",datas,function (res) {
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
//						"isProxy": "Y"
					}
				};
				_this.post("/api/user/list",datas,function (res) {
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
						_this.uidlist = arr;
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
//			this.initGuiShu();//获取归属列表
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>