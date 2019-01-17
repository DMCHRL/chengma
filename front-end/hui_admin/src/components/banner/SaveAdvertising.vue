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
				<span class="import">广告跳转路径</span>
			</div>
			<div class="pull-left right_box">
				<input type="text"  v-model="editVideoType.url" />
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">广告介绍</span>
			</div>
			<div class="pull-left right_box">
				<textarea name="" rows="" cols="4" placeholder="请输入介绍" v-model="editVideoType.text"></textarea>
			</div>
		</div>
		<div class="input_box clearfix">
			<div class="pull-left left_box">
				<span class="import">广告位置</span>
			</div>
			<div class="pull-left right_box">
				<el-select v-model="radioValue" placeholder="广告位置" >
				    <el-option
				      v-for="item in bannerHome"
				      :key="item.value"
				      :label="item.label"
				      :value="item.value">
				    </el-option>
				</el-select>
				<!--<el-radio v-model="radioValue" label="HOME">首页</el-radio>
  				<el-radio v-model="radioValue" label="VIDEO">视频页</el-radio>
  				<el-radio v-model="radioValue" label="SINKS">汇商页</el-radio>
  				<el-radio v-model="radioValue" label="STRATEGY">策略入驻页</el-radio>-->
			</div>
		</div>
		<div class="uplond_box clearfix">
			<div class="pull-left left_box">
				<span class="import">广告图</span>
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
					img: '',
					text: '',
					type: '',
					url: '',
					sortNum: ''
				},
				radioValue: 'HOME',
				bannerHome: [
					{label: '首页',value: 'HOME'},
					{label: '交易战法视频',value: 'VIDEO'},
					{label: '汇商超市',value: 'SINKS'},
					{label: '策略入驻',value: 'STRATEGY'},
					{label: '直播',value: 'LIVE'},
				],
			}
		},
		components: {

		},
		watch: {
			videoType () {
				let video = JSON.parse(JSON.stringify(this.videoType));
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
			Commit() {
				let _this = this;
				
				let datas = {
					sortNum: _this.editVideoType.sortNum,
					type: _this.radioValue,
					img: _this.editVideoType.img,
					text: _this.editVideoType.text,
					url: _this.editVideoType.url,
					id: _this.editVideoType.id
				}
				
				this.$post("/api/hpp_advertisement/saveHppAdvertisement",datas).then((res) => {
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
	.right_box textarea{
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
	
	.uplond_box {
		position: relative;
		width: auto;
		border: none;
	}
	
	.uplond_box label {
		width: 200px;
		min-height: 100px;
		border: 1px solid #ccc;
		border-radius: 10px;
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
