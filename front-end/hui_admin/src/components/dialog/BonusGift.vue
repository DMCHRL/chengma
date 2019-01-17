<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">积分赠送</h3>
		  
		  <div class="input_box">
		  	<span class="import">名称</span>
		  	<input disabled type="text" name="" id="1" value="" placeholder="" v-model="user.userName"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">手机号</span>
		  	<input disabled type="text" name="" id="2" value="" placeholder="" v-model="user.phone"/>
		  </div>
			<div class="input_box ">
				<span class="import">积分</span>
				<input type="number" name="" id="2" value="" placeholder="正数累加,负数即减去积分" v-model="point"/>
			</div>
		  
		  <div slot="footer" class="dialog_footer_a">
				<el-button type="primary" round @click="Commit" :disabled="disabled">确认</el-button>
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
				user: {
					userName: "",
					phone: "", 
				},
				disabled: false,
				point: ''
			}
		},
		computed: {
			
		},
		watch: {
			mess () {
				this.user = JSON.parse(JSON.stringify(this.mess));
			},
		},
		components: {

		},
		methods: {
			Commit () {
				let _this = this;
				
				if (_this.point == "") {
					_this.$message({
			          message: '积分不能为空',
			          type: 'warning'
			        });
					return;
				}
				_this.disabled = true;
				
				let datas = {
					"mobile": _this.user.phone,
					"integral": _this.point
				}
				
				_this.$post("/api/hpp_integral/setIntegral",datas).then((res) => {
					_this.disabled = false;
					if (res.statusCode == "0000") {
						_this.$message({
								message: '操作成功',
								type: 'success'
							});
							
							_this.user =  {
								firstName: "",     //名称
								email: "",          //邮箱
								mobile: "", // 电话号码
								eara: "",      // 区域
								department: 'company'
							};
						_this.$emit("close");
						
					}else{
						_this.$message({
							message: res.msgCode,
							type: 'warning'
						});
					}
				})
			},
		},
		mounted() {
			this.user = JSON.parse(JSON.stringify(this.mess));
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
	.el-radio {
		line-height: 25px;
	}
	.dialog_footer_a {
		padding-top: 30px;
		text-align: center;
	}
	.dialog_footer_a button {
		padding: 10px 24px;
	}
</style>
