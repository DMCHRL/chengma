<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">编辑汇添溢用户</h3>
		  
		  <div class="input_box">
		  	<span class="import">用户名</span>
		  	<input type="text" name="" id="1" value="" placeholder="" v-model="userMess.firstName"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">邮箱</span>
		  	<input type="text" name="" id="3" value="" placeholder="" v-model="userMess.email"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">地区</span>
		  	<input type="text" name="" id="4" value="" placeholder="" v-model="userMess.eara"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">手机号</span>
		  	<input type="text" name="" id="6" value="" placeholder="" v-model="userMess.mobile"/>
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
				userMess: {}
			}
		},
		computed: {
			
		},
		watch: {
			mess () {
				this.userMess = JSON.parse(JSON.stringify(this.mess));
			},
		},
		components: {

		},
		methods: {
			ConfirmEdit () {
				let _this = this;
				_this.$post("/api/user/save",_this.userMess).then((res) => {
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
			}
		},
		mounted () {
//			console.log(this.mess)
			this.userMess = JSON.parse(JSON.stringify(this.mess));
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>