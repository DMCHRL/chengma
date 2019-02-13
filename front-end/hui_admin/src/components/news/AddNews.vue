<template>
	<div class="content_box">
		<div class="titling">
			<span>新增新闻</span>
		</div>
		<div class="main_box">
			<div class="left_box clearfix">
				<div class="pull-left">
					<div class="input_box clearfix">
						<div class="pull-left">
							<span>标题：</span>
						</div>
						<div class="pull-left">
							<input type="text" name="" id="" value="" placeholder="请输入标题" v-model="newsTitle" />
						</div>
					</div>
					<div class="input_box clearfix">
							<div class="pull-left">
								<span>栏目：</span>
							</div>
							<div class="pull-left">
								<input type="text" name="" id="" value="" placeholder="请输入栏目" v-model="newsType" />

							</div>
					</div>
					<div class="input_box clearfix">
						<div class="pull-left">
							<span>首页推荐：</span>
						</div>
						<div class="pull-left">
							<el-switch v-model="flager" active-color="#13ce66" inactive-color="#ff4949">
									</el-switch>
						</div>
					</div>
				</div>
				<div class="pull-left">
					<div class="input_box clearfix">
						<div class="pull-left">
							<span>封面：</span>
						</div>
						<div class="pull-left">
							<label>
								<input ref="bookFace" type="file" accept="image/*" style="display: none;" @change="imgUpload" name="" id="" value="" />
								<div class="uplond_box">
									<img v-if="bookFace" :src="bookFace" class="main_img"/>
									<img v-else src="../../assets/img/acc_1.png"/>
								</div>
							</label>
						</div>
					</div>

				</div>
			</div>

			<div id="editor" type="text/plain" style="width:1300px;height:400px;"></div>
		</div>

		<div class="btn_box">
			<button @click="clearContent">清空</button>
			<button class="submit" @click="submits" :disabled="isDisabled">提交</button>
		</div>

	</div>
</template>

<script>
	import '../../../static/utf8-jsp/ueditor.config'
	import '../../../static/utf8-jsp/ueditor.all'
	import '../../../static/utf8-jsp/lang/zh-cn/zh-cn'
	export default {
		data() {
			return {
				ue: null,
				uedata: [],
				xierudata: [],
				isDisabled: false,
				newsTitle: '',
				newsType: '股市要闻',
				bookFace: null,
				news: {},
				old: '',
				flager: true
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			}
		},
		methods: {
			getNews () {
				let _this = this;
				_this.$fetch("/api/hpp_news/get/"+_this.id).then((res) => {
					if (res.data) {
						_this.newsTitle = res.data.title;
						_this.bookFace = res.data.listImg;
						_this.newsType = res.data.type;
						_this.old = res.data.context;

						setTimeout(function () {
							_this.xieru();
						},1000)
					}
				})
			},
			initUeditor () {
				this.ue = UE.getEditor('editor', {
					BaseUrl: '',
					UEDITOR_HOME_URL: 'static/utf8-jsp/',
					// toolbars:[]
				});
			},
			imgUpload () {
				let _this = this;
				let obj = _this.$refs.bookFace.files[0];
				_this.$until.uploadImage("/api/uploadImage",obj).then((res) => {
					let imgUrl = res.target.responseText;
					_this.bookFace = imgUrl;
				})
			},
			clearContent () {
				this.ue.execCommand('cleardoc');
			},
			submits() {
				let _this = this;
				let content = this.ue.getContent();
				let datas = {
					title: _this.newsTitle,//	是	string	新闻标题
					type: _this.newsType,//	是	string	新闻类型
					listImg: _this.bookFace,//	是	string	新闻列表显示图
					context: content,//
					id: _this.id,
					status : _this.flager?'Y':'N'
				}
				_this.$post("/api/hpp_news/saveHppNews",datas).then((res) => {
//					console.log(res)
					if(res.statusCode == "0000") {
						_this.$message({
							message: "新增成功",
							type: 'success'
						});
						_this.$router.go(-1)
					} else {
						_this.$message({
							message: res.msgCode,
							type: 'error'
						});
					}
				})
			},
			xieru() {
				UE.getEditor('editor').setContent(this.old);
			}
		},
		mounted() {
			this.initUeditor();
			if (this.id != undefined) {
				this.getNews();
			}
		},
		destroyed() {
		  this.ue.destroy();
		}
	}
</script>

<style scoped>
	.main_box {
		background-color: #fff;
	}
	.left_box {
		padding: 20px 10px;
	}
	.input_box {
		margin-bottom: 10px;
		line-height: 30px;
		margin-right: 20px;
	}
	.input_box input[type="text"]{
		width: 350px;
		border: 1px solid #ddd;
		line-height: 30px;
		padding: 0 10px;
		font-size: 14px;
	}
	.input_box select {
		height: 30px;
		width: 150px;
		border-color: #ddd;
	}
	.input_box span {
		line-height: 30px;
		font-size: 14px;
		color: #666;
		margin-right: 10px;
	}
	.uplond_box {
		width: 300px;
		height: 150px;
		line-height: 150px;
		text-align: center;
		border: 1px solid #ddd;
		cursor: pointer;
	}
	.uplond_box img {
		width: 30px;
	}
	.uplond_box img.main_img {
		width: auto;
		height: 100%;
	}
	.btn_box {
    width: 100%;
		background-color: #fff;
		padding-right: 100px;
		padding-top: 50px;
		text-align: center;
	}
	.btn_box button {
		background-color: transparent;
		color: #666;
		padding: 5px 40px;
		border-radius: 20px;
		background-color: #fff;
	}
	.btn_box button.submit {
		font-size: 14px;
		background-color: #f2825b;
		color: #fff;
		margin: 0 10px;
	}
	.edit_box button {
		font-size: 14px;
		background-color: #007AFF;
		color: #fff;
		margin-bottom: 10px;
		padding: 4px 20px;
		border-radius: 20px;
	}
</style>
