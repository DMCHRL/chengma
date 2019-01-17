<template>
	<!--添加银行卡弹窗-->
	<el-dialog custom-class="ibs_dialog" width="30%" :visible="show" @close="cancelDialog" >
		<h3 slot="title" class="dialog_title">{{titleText}}</h3>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">序列</span>
			</div>
			<div class="pull-left right_box">
				<input type="number"  v-model="editVideoType.sortNum" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">直播名称</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"  v-model="editVideoType.name" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">直播路径</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"  v-model="editVideoType.url" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">介绍</span>
			</div>
			<div class="pull-left right_box">
				<textarea name="" rows="6"  placeholder="" v-model="editVideoType.text"></textarea>
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">展示图</span>
			</div>
			<div class="pull-left right_box">
				<label>
					<input ref="background" type="file" accept="image/*" style="display: none;" @change="imgUpload" name="" id="" value="" />
					<div class="uplond_box">
						<img v-if="background" :src="background" class="main_img"/>
						<img v-else src="../../assets/img/acc_1.png"/>
					</div>
				</label>
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
		props: ['show','videoType','titleText'],
		data() {
			return {
				editVideoType: {
					name: '',
					img: '',
					text: '',
					url: '',
					sortNum: ''
				},
				radioValue: 'HOME',
				bannerHome: [
					{label: '首页',value: 'HOME'},
					{label: '交易面对面',value: 'VIDEO'},
					{label: '汇商超市',value: 'SINKS'},
					{label: '策略入驻',value: 'STRATEGY'},
					{label: '直播',value: 'LIVE'},
				],
				background: null,
			}
		},
		components: {

		},
		watch: {
			videoType () {
				let video = JSON.parse(JSON.stringify(this.videoType));
				this.editVideoType = video;
				this.background = video.img;
			}
		},
		methods: {
			imgUpload () {
				let _this = this;
				let obj = _this.$refs.background.files[0];
				_this.$until.uploadImage("/api/uploadImage",obj).then((res) => {
					let imgUrl = res.target.responseText;
					_this.background = imgUrl;
				})
			},
			Commit() {
				let _this = this;
				
				let datas = {
					sortNum: _this.editVideoType.sortNum,
					name: _this.editVideoType.name,
					img: _this.background,
					text: _this.editVideoType.text,
					url: _this.editVideoType.url,
					id: _this.editVideoType.id
				}
				
				this.$post("/api/hpp_live/createHppLive",datas).then((res) => {
//					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "新增成功",
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
