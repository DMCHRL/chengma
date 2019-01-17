<template>
	<!--添加银行卡弹窗-->
	<el-dialog custom-class="ibs_dialog" width="30%" :show-close="false" :visible="show" >
		<h3 slot="title" class="dialog_title">{{titleText}}</h3>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">序列号</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="" v-model="editVideoType.sortNum" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">视频集标题</span>
			</div>
			<div class="pull-left right_box">
				<input type="text" placeholder="" v-model="editVideoType.videoTypeName" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">视频集介绍</span>
			</div>
			<div class="pull-left right_box">
				<textarea name="" rows="4" cols="4" placeholder="" v-model="editVideoType.text"></textarea>
			</div>
		</div>
		<div class="uplond_box clearfix">
			<div class="pull-left left_box">
				<span class="import">视频集封面</span>
			</div>
			<div class="pull-left right_box">
				<label>
					<input @change="onFileChange1" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
					<span class="icon_box" v-show="!editVideoType.img">
						<img src="../../assets/img/acc_1.png"/>
					</span>
					<div class="img_box" v-show="editVideoType.img" >
						<img :src="editVideoType.img"/>
					</div>
				</label>
			</div>
		</div>
		<div slot="footer" class="dialog_footer">
			<button @click="cardCommit">确认</button>
			<button @click="cancelDialog">取消</button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['show','videoType','titleText'],
		data() {
			return {
				editVideoType: {
					img: '',
					text: '',
					videoTypeName: '',
					sortNum: ''
				}
			}
		},
		components: {

		},
		watch: {
			videoType () {
				let video = JSON.parse(JSON.stringify(this.videoType))
				console.log(video)
				this.editVideoType = video;
			}
		},
		methods: {
			onFileChange1(e) {
				let _this = this;
				let file = e.target.files[0] || e.dataTransfer.files[0];
				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					_this.editVideoType.img = base64Codes;
				});
			},
			cardCommit() {
				let _this = this;
				let datas = {
					sortNum: _this.editVideoType.sortNum,
					videoTypeName: _this.editVideoType.videoTypeName,
					img: _this.editVideoType.img,
					text: _this.editVideoType.text,
					id: _this.editVideoType.id
				}
				
				this.$post("/api/hpp_video_type/saveHppVideoType",datas).then((res) => {
					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "新增成功",
				          type: 'success'
				        });
				        _this.$emit('close') //关闭弹出
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
		width: 250px;
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