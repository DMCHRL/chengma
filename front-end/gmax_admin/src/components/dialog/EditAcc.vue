<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">编辑账户</h3>
		  
		  <div class="input_box">
		  	<span class="import">账号</span>
		  	<input type="text" name="" id="5" value="" placeholder="" v-model="userMess.account"/>
		  </div>
		  
		  <div class="input_box">
		  	<span class="import">姓名</span>
		  	<input type="text" name="" id="1" value="" placeholder="" v-model="userMess.accountName"/>
		  </div>
		  <div class="input_box" v-if ="user.department == 'admin' || user.department == 'service'">
		  	<span class="import">组别</span>
			<select name="" v-model="userMess.group">
				<option value="demoTX">模拟账户</option>
				<option value="TXA2">体验账户</option>
				<option value="TXA3">真实账户</option>
			</select>
		  </div>
		   <div class="input_box" v-if ="user.department == 'admin' || user.department == 'service'">
		  	<span class="import">EA组别</span>
			<select name="" v-model="userMess.eaGroup">
				<option v-for="item in ealist" :value="item.eaGroup">{{item.eaGroupName}}</option>
			</select>
		  </div>
		  <!--<div class="input_box ">
		  	<span class="import">邮箱</span>
		  	<input type="text" name="" id="3" value="" placeholder="" v-model="userMess.email"/>
		  </div>-->
		  <div class="input_box ">
		  	<span class="import">状态</span>
			<select name="" v-model="userMess.enableTrade">
				<option value="Y">正常</option>
				<option value="N">禁用</option>
			</select>
		  </div>
		  <div class="input_box ">
		  	<span class="import">是否反向</span>
			<select name="" v-model="userMess.comment">
				<option value="Y">是</option>
				<option value="N">否</option>
			</select>
		  </div>
		  
		  <div slot="footer" class="dialog_footer">
		  	<button @click="ConfirmEdit">确认修改</button>
		  </div>
	</div>
</template>

<script>
	export default {
		props: {
			mess: {}
		},
		data() {
			return {
				userMess: {},
				ealist: []
			}
		},
		computed: {
			acctype: {
				get () {
					let _this = this;
					if (_this.userMess.group == "demoTX") {
						return 1;
					}else {
						return 2;
					}
			    },
			      // setter
			    set (newValue) {
			    	let _this = this;
			        //console.log(newValue)
			    }
			},
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
		watch: {
			mess () {
				this.userMess = JSON.parse(JSON.stringify(this.mess));
			}
		},
		components: {

		},
		methods: {
			ConfirmEdit () {
				let _this = this;
				_this.post("/api/tlb-account/updateTlbAccount",_this.userMess,function (res) {
					////console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: '修改成功',
				          type: 'success'
				      });
				      _this.$emit("close");
					}else{
						_this.$message({
				          message: '修改失败',
				          type: 'warning'
				        });
					}
				})
			},
			getEaGroup () {
				let _this = this;
				_this.fetch("/api/tlb-ea-group/getAll",function (res) {
//					console.log(res)
					if (res.data.length) {
						_this.ealist = res.data;
						_this.userMess.eaGroup = res.data[0].eaGroup;
					}
				})
			}
		},
		mounted () {
			this.userMess = JSON.parse(JSON.stringify(this.mess));
			this.getEaGroup();
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>