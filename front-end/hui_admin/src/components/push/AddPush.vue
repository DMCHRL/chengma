<template>
	<div class="dialog_box">
		<h3 slot="title" class="dialog_title">新增/编辑推送消息</h3>

		 <!-- <div class="input_box ">
		  	<span class="">消息路径</span>
		  	<input type="text" name="" id="2" value="" placeholder="" v-model="url"/>
		  </div>-->
		  <div class="input_box">
		  	<span class="import">消息类型（标题）</span>
		  	<input type="text" name="" id="1" value="" maxlength="15"  placeholder="15字以内" v-model="type"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">消息内容</span>
		  	<textarea name="" rows="6" cols="" placeholder="" v-model="context"></textarea>
		  </div>

		  <div slot="footer" class="dialog_footer">
		  	<button @click="Commit">确认</button>
		  </div>
	</div>
</template>

<script>
	export default {
		props: ['mess'],
		data() {
			return {
				id: '',
				url: '',
				type: '',
				context: ''
			}
		},
		watch: {
			mess (val) {
				let obj = JSON.parse(JSON.stringify(val));
				this.url = obj.url;
				this.type = obj.type;
				this.context = obj.context;
				this.id = obj.id;
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
			Commit () {
				let _this = this;

				if (_this.type == "") {
					_this.$message({
			          message: '消息类型不能为空',
			          type: 'warning'
			        });
					return;
				}
				if (_this.context == "") {
					_this.$message({
			          message: '消息类型不能为空',
			          type: 'warning'
			        });
					return;
				}

				let datas = {
					id: _this.id,
					url: _this.url,
					type: _this.type,
					context: _this.context
				}
				_this.$post("/api/hpp_notice/saveHppNotice",datas).then((res) => {
					////console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: '添加成功',
				          type: 'success'
				        });

				        _this.type = '';
				        _this.content = '';

						_this.$emit("close");
					}else{
						_this.$message({
				          message: '添加失败',
				          type: 'warning'
				        });
					}
				})
			},
		},
		mounted() {
			let obj = JSON.parse(JSON.stringify(this.mess));
			this.url = obj.url;
			this.type = obj.type;
			this.context = obj.context;
			this.id = obj.id;
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>
