<template>
	<!--添加银行卡弹窗-->
	<el-dialog custom-class="ibs_dialog" width="30%" :visible="show" @close="cancelDialog" >
		<h3 slot="title" class="dialog_title">{{titleText}}</h3>
		 
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">类型</span>
			</div>
			<div class="pull-left right_box">
				<el-select v-model="datas.type" placeholder="请选择">
					<el-option
						v-for="item in options"
						:key="item.value"
						:label="item.label"
						:value="item.value">
					</el-option>
				</el-select>
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">标签名</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"  v-model="datas.label" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">内容</span>
			</div>
			<div class="pull-left right_box">
				<textarea name="" rows="6"  placeholder="" v-model="datas.text"></textarea>
			</div>
		</div>
		
		<div slot="footer" class="dialog_footer">
			<el-button type='primary' @click="Commit">确认</el-button>
			<!--<button @click="cancelDialog">取消</button>-->
		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['show','body','titleText'],
		data() {
			return {
				datas: {
					label: '',
					text: '',
					type: 'qq',
				},
				options: [
					{label: 'QQ',value: 'qq'},
					{label: '手机',value: 'mobile'},
					{label: '微信',value: 'weixin'},
					{label: '微博',value: 'weibo'},
					{label: '固定电话',value: 'phone'},
				],
			}
		},
		components: {

		},
		watch: {
			body () {
				let props = JSON.parse(JSON.stringify(this.body));
				this.datas = props;
			}
		},
		methods: {
			
			Commit() {
				let _this = this;
				
				let datas = {
					label: _this.datas.label,
					type: _this.datas.type,
					text: _this.datas.text,
					id: _this.datas.id
				}
				
				this.$post("/api/hpp_server/saveHppServer",datas).then((res) => {
//					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "操作成功",
				          type: 'success'
				        });
				        _this.$emit('close') //关闭弹出
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'failing'
				        });
					}
				})

			},
			cancelDialog() {
				this.$emit('close')
			}
		},
		mounted() {
			let props = JSON.parse(JSON.stringify(this.body));
			this.datas = props;
		}
	}
</script>

<style scoped>
	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	
	.input_box {
		margin-bottom: 25px;
		position: relative;
	}
	.left_box {
		text-align: right;
		width: 35%;
	}
	.left_box span {
		padding-right: 20px;
		color: #666;
		font-size: 14px;
		line-height: 36px;
	}
	
	span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	.right_box {
		width: 60%;
	}
	.right_box input,
	.right_box select,
	.right_box textarea {
		border: 1px solid #ccc;
		padding: 5px 10px;
		border-radius: 5px;
		font-size: 14px;
		color: #666;
		width: 250px;
	}
	
	.ibs_dialog .trip_text {
		position: absolute;
		top: 100%;
		left: 0;
		padding-left: 40%;
		font-size: 12px;
		color: #F14B3B;
	}
	
	.dialog_footer {
		text-align: center;
	}
	
</style>
