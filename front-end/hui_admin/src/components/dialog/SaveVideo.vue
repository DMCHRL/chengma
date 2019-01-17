<template>
	<!--添加银行卡弹窗-->
	<el-dialog custom-class="ibs_dialog" width="30%" :show-close="false" :visible="show" append-to-body>
		<h3 slot="title" class="dialog_title">视频详情</h3>
		
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">序号：</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="" v-model="videoMess.sortNum" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">视频标题</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="请输入标题" v-model="videoMess.videoName" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">视频介绍</span>
			</div>
			<div class="pull-left right_box">
				<textarea name="" rows="" cols="4" placeholder="请输入介绍" v-model="videoMess.videoText"></textarea>
			</div>
		</div>
		<!--<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">视频URL</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="请输入URL" v-model="videoMess.videoUrl" />
			</div>
		</div>-->
		
		<div class="uplond_box clearfix">
			<div class="pull-left left_box">
				<span class="import">视频封面</span>
			</div>
			<div class="pull-left right_box">
				<label>
					<input @change="onFileChange1" type="file" ref="file" style="display:none;" accept="image/*" name="file" >
					<span class="icon_box" v-if="!videoMess.img">
						<img src="../../assets/img/acc_1.png"/>
					</span>
					<div class="img_box" v-else>
						<img :src="videoMess.img"/>
					</div>
				</label>
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">视频资费</span>
			</div>
			<div class="pull-left right_box">
				<el-radio v-model="radioValue" label="1">收费</el-radio>
  				<el-radio v-model="radioValue" label="2">免费</el-radio>
			</div>
		</div>
		<div class="input_box clearfix" v-if="radioValue == '1'">
			<div class="pull-left left_box">
				<span class="import">视频价格</span>
			</div>
			<div class="pull-left right_box">
				<input type="number" placeholder="例：9.9" v-model="videoMess.price" />
			</div>
		</div>
		<div class="input_box clearfix" v-if="radioValue == '1'">
			<div class="pull-left left_box">
				<span class="import">视频积分</span>
			</div>
			<div class="pull-left right_box">
				<input type="number" placeholder="例：1000" v-model="videoMess.integralPrice" />
			</div>
		</div>
		<div slot="footer" class="dialog_footer">
			<button @click="cardCommit" :disabled="commitDisabled">确认</button>
			<button @click="cancelDialog">取消</button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['show','editVideoMess'],
		data() {
			return {
				videoMess: {
					img: '',
					videoName: '',
					videoText: '',
					videoUrl: '',
					price: '',
					integralPrice: '',
					id: '',
					sortNum: ''
				},
				radioValue: '2',
				commitDisabled: false,
				commitText: '确认新增',
			}
		},
		computed: {
			typeid () {
				return this.$route.query.typeid;
			}
		},
		watch: {
			editVideoMess () {
				let video = JSON.parse(JSON.stringify(this.editVideoMess))
				this.videoMess = video;
			}
		},
		components: {

		},
		methods: {
			onFileChange1(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					_this.videoMess.img = base64Codes;
				});
			},
			cardCommit() {
				let _this = this;
				let datas = {
					sortNum:　_this.videoMess.sortNum,
					videoTypeId: _this.typeid,
					videoUrl: _this.videoMess.videoUrl,
					videoText: _this.videoMess.videoText,
					videoName: _this.videoMess.videoName,
					feeFlag: _this.radioValue,
					price: _this.videoMess.price,
					integralPrice: _this.videoMess.integralPrice,
					img: _this.videoMess.img,
					id: _this.videoMess.id
				}
				_this.commitDisabled = true;
				_this.commitText = "上传中...";
				this.$post("/api/hpp_video/saveHppVideo",datas).then((res) => {
//					console.log(res)
					_this.commitDisabled = false;
					_this.commitText = "确认新增";
					
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "新增成功",
				          type: 'success'
				        });
				        _this.$emit('close') //关闭弹出
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'error'
				        });
					}
				})

			},
			cancelDialog() {
				this.$emit('close')
			}
		},
		mounted() {
			let video = JSON.parse(JSON.stringify(this.editVideoMess))
			this.videoMess = video;
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
		width: auto;
		border: none;
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