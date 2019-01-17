<template>
	<!--修改密码弹窗-->
		<el-dialog
			custom-class="dialog_box"
		   :visible="show"
		   width="30%"
			 @close="close">
		  <h3 slot="title" class="dialog_title">新增/编辑消息</h3>
		  <div class="input_box clearfix">
		  	<span class="import pull-left">消息类别</span>
		  	<div class="pull-left">
					<input type="text" name="" id="1" value="" placeholder="" v-model="mess.type"/>
				</div>
		  </div>
		  <div class="input_box clearfix">
		  	<span class="import pull-left">消息内容</span>
		  	<div class="pull-left">
					<textarea type="text" name="" id="2" value="" placeholder="" v-model="mess.context"></textarea>
				</div>
		  </div>
		  
		  <div slot="footer" class="dialog_footer">
				<!-- <button @click="commitSend">发送</button> -->
		  	<button @click="commit">保存</button>
		  </div>
		</el-dialog>
</template>

<script>
	export default {
		props: [ 'show', 'message'],
		data() {
			return {
				mess: {
					type: '',
					context: ''
				}
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
		watch: {
			show () {
				this.mess = JSON.parse(JSON.stringify(this.message))
			}
		},
		components: {
			
		},
		methods: {
			commitSend () {
				let _this = this;
				
			},
			commit () {
				let _this = this;
				
				if (_this.mess.type == "") {
					_this.$message({
			          message: '消息类型不能为空',
			          type: 'warning'
			        });
					return;
				}
				if (_this.mess.context == "") {
					_this.$message({
			          message: '消息内容不能为空',
			          type: 'warning'
			        });
					return;
				}
				
				let datas = {
					id: _this.mess.id,
					type: _this.mess.type,
					context: _this.mess.context
				}
				
				_this.post("/api/notice/saveNotice",datas,function (res) {
					// console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: '新增成功',
				          type: 'success'
				       });
				       
						_this.close();
					   
					}else {
						_this.$message({
				          message: '新增失败',
				          type: 'warning'
				        });
					}
				})
			},
			close () {
				this.$emit('close')
			}
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>