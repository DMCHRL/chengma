<template>
  <!-- 国际通道 -->
	<el-dialog custom-class="ibs_dialog" width="40%" :show-close="false" :visible="show" append-to-body>
		<h3 slot="title" class="dialog_title">{{dialogTitle}}</h3>
		
    <div class="input_box clearfix">
    	<div class="pull-left left_box">
    		<span class="import">商户名称</span>
    	</div>
    	<div class="pull-left right_box">
    		<input type="text" placeholder="" v-model="editStore.company" />
    	</div>
    </div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">收款人名称</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="" v-model="editStore.userName" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">账户类型</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="" v-model="editStore.accountType" />
			</div>
		</div>
    <div class="input_box clearfix">
    	<div class="pull-left left_box">
    		<span class="import">账户类型（英文）</span>
    	</div>
    	<div class="pull-left right_box">
    		<input type="text" placeholder="" v-model="editStore.accountTypeEn" />
    	</div>
    </div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">账户号码</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="" v-model="editStore.account" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">银行名称</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="" v-model="editStore.bankName" />
			</div>
		</div>
    <div class="input_box clearfix">
    	<div class="pull-left left_box">
    		<span class="import">银行名称（英文）</span>
    	</div>
    	<div class="pull-left right_box">
    		<input type="text" placeholder="" v-model="editStore.bankNameEn" />
    	</div>
    </div>
    <div class="input_box clearfix">
    	<div class="pull-left left_box">
    		<span class="import">银行地址</span>
    	</div>
    	<div class="pull-left right_box">
    		<input type="text" placeholder="" v-model="editStore.address" />
    	</div>
    </div>
    <div class="input_box clearfix">
    	<div class="pull-left left_box">
    		<span class="import">银行地址（英文）</span>
    	</div>
    	<div class="pull-left right_box">
    		<input type="text" placeholder="" v-model="editStore.addressEn" />
    	</div>
    </div>
    <div class="input_box clearfix">
    	<div class="pull-left left_box">
    		<span class="import">银行编码</span>
    	</div>
    	<div class="pull-left right_box">
    		<input type="text" placeholder="" v-model="editStore.bankCode" />
    	</div>
    </div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">汇款编码（备注）</span>
			</div>
			<div class="pull-left right_box">
				<textarea name="" rows="" cols="3" placeholder="" v-model="editStore.swiftCode"></textarea>
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
				editStore: {},
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
				
				_this.post("/api/receivables_en/saveReceivablesEn",datas,function (res) {
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
		width: 200px;
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
		width: 400px;
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