<template>
	<el-dialog custom-class="dialog_box" :show-close="false" :visible="dialogVisible" width="30%" @open="dialogOpen">
		<h3 slot="title" class="dialog_title">新增套餐</h3>
		<div class="input_box">
			<span class="import">套餐序号</span>
			<el-input type='number' v-model="meals.sort" placeholder=""></el-input>
		</div>
		<div class="input_box">
			<span class="import">名称</span>
			<el-input v-model="meals.name" placeholder=""></el-input>
		</div>
		<div class="input_box ">
			<span class="import">价格</span>
			<el-input type='number' v-model="meals.price" placeholder=""></el-input>
		</div>
		<div class="input_box ">
			<span class="import">套餐包括</span>
			<el-input type="textarea" :rows="3" placeholder="" v-model="meals.include">
			</el-input>
		</div>

		<div slot="footer" class="dialog_footer">
			<button @click="commit">确认</button>
			<button @click="close">取消</button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['dialogVisible'],
		data() {
			return {
				meals:{
					sort: '',
					name: '',
					price: '',
					include: ''
				}
			}
		},
		computed: {
			
		},
		watch: {
			
		},
		components: {

		},
		methods: {
			commit() {
				let _this = this;
				if (_this.meals.sort == '') {
					_this.$message({
						message: '请填写内容完整',
						type: 'warning'
					});
					return;
				}
				if (_this.meals.name == '') {
					_this.$message({
						message: '请填写内容完整',
						type: 'warning'
					});
					return;
				}
				if (_this.meals.price == '') {
					_this.$message({
						message: '请填写内容完整',
						type: 'warning'
					});
					return;
				}
				if (_this.meals.include == '') {
					_this.$message({
						message: '请填写内容完整',
						type: 'warning'
					});
					return;
				}
				
				_this.$emit('Add',_this.meals)
			},
			close() {
				this.$emit('close')
			},
			dialogOpen () {
				this.meals = {
						sort: '',
						name: '',
						price: '',
						include: ''
					}
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
	
	.dialog_box .input_box {
		margin-bottom: 10px;
	}
	
	.dialog_box .input_box span {
		display: inline-block;
		width: 30%;
		text-align: right;
		padding-right: 10px;
		color: #000;
	}
	
	.dialog_box .input_box span.import:before {
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