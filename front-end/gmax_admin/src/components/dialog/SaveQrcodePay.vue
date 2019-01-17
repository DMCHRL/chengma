<template>
	<!--添加银行卡弹窗-->
	<el-dialog custom-class="ibs_dialog" width="30%" :show-close="false" :visible="show" append-to-body>
		<h3 slot="title" class="dialog_title">{{dialogTitle}}</h3>
		
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">授权商户名称</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="" v-model="editStore.shopName" />
			</div>
		</div>
		<div class="uplond_box clearfix">
			<div class="pull-left left_box">
				<span class="import">授权收款二维码</span>
			</div>
			<div class="pull-left right_box">
				<label>
					<input @change="onFileChange1" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
					<span class="icon_box" v-if="!editStore.img">
						<img src="../../assets/img/acc_1.png"/>
					</span>
					<div class="img_box" v-else>
						<img :src="editStore.img"/>
					</div>
				</label>
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">转账备注</span>
			</div>
			<div class="pull-left right_box">
				<textarea name="" rows="" cols="4" placeholder="" v-model="editStore.text"></textarea>
			</div>
		</div>
		<div slot="footer" class="dialog_footer">
			<button @click="Commit">确认</button>
			<button @click="cancelDialog">取消</button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['show','dialogTitle','store'],
		data() {
			return {
				editStore: {
					shopName: '',
					text: '',
					img: '',
				},
				imgshow: false
			}
		},
		components: {

		},
		watch: {
			store () {
				this.editStore = JSON.parse(JSON.stringify(this.store));
				
			}
		},
		methods: {
			onFileChange1(e) {
				let _this = this;
				let file = e.target.files[0] || e.dataTransfer.files[0];
				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					_this.editStore.img = base64Codes;
				});
			},
			Commit() {
				let _this = this;
				let datas = _this.editStore;
				
				_this.post("/api/payment_img/save",datas,function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						
						_this.$message({
				          message: "操作成功",
				          type: 'success'
				        });
						_this.$emit('close')
					}
				})

			},
			cancelDialog() {
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
	
	.input_box {
		margin-bottom: 25px;
		position: relative;
	}
	.left_box {
		text-align: right;
		width: 40%;
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
	
	.right_box input,
	.right_box select,
	.right_box textarea {
		border: 1px solid #ccc;
		padding: 5px 10px;
		border-radius: 5px;
		font-size: 14px;
		color: #666;
		width: 200px;
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
	
	.dialog_footer button {
		font-size: 18px;
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
	
	.uplond_box {
		position: relative;
		text-align: center;
	}
	
	.uplond_box label {
		width: 200px;
		min-height: 100px;
		border: 1px solid #ccc;
		border-radius: 10px;
		margin-right: 10px;
		text-align: center;
		overflow: hidden;
	}
	.icon_box{
		line-height: 100px;
	}
	.icon_box img {
		width: 30px;
		height: 30px;
	}
	
	.img_box img {
		width: 100%;
		height: auto;
	}
</style>