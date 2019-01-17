<template>
	<el-dialog custom-class="ibs_dialog" :show-close="false" :visible="dialogVisible" width="20%">
		<h3 slot="title" class="dialog_title">新增套餐</h3>
		<div class="input_box">
			<span class="import">名称</span>
			<el-input v-model="name" placeholder="请输入内容"></el-input>
		</div>
		<div class="input_box ">
			<span class="import">价格</span>
			<el-input type="number" v-model="price" placeholder="请输入内容"></el-input>
		</div>
		<div class="input_box ">
			<span class="import">套餐包括</span>
			<el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="include">
			</el-input>
		</div>

		<div slot="footer" class="dialog_footer">
			<button @click="Add">新增</button>
			<button @click="close">取消</button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['title', 'dialogVisible', "url", 'loginAccount'],
		data() {
			return {
				name: '',
				price: '',
				include: ''
			}
		},
		computed: {
			user() {
				let userStr = localStorage.getItem("user");
				if(userStr) {
					let user = JSON.parse(userStr);
					return user;
				} else {
					return {
						department: 'user'
					}
				}
			}
		},
		watch: {
			dialogVisible() {
				
			}
		},
		components: {

		},
		methods: {
			Add() {
				this.$emit('Add',{
					name: this.name,
					price: this.price,
					include: this.include
				})
			},
			close() {
				this.$emit('close')
			}
		},
		mounted() {

		}
	}
</script>

<style scoped>
	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	
	.ibs_dialog .input_box {
		margin-bottom: 10px;
	}
	
	.ibs_dialog .input_box span {
		display: inline-block;
		width: 30%;
		text-align: right;
		padding-right: 10px;
		color: #000;
	}
	
	.ibs_dialog .input_box span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	
	.el-input,
	.el-textarea {
		width: 50%;
	}
	
	.dialog_footer {
		text-align: center;
	}
	
	.dialog_footer button {
		font-size: 14px;
		background-color: #F14B3B;
		border-radius: 20px;
		padding: 3px 30px;
		color: #fff;
	}
	
	.dialog_footer button:last-of-type {
		background-color: #fff;
		color: #F14B3B;
		border: 1px solid #F14B3B;
		padding: 1.5px 28px;
		margin-left: 20px;
	}
</style>