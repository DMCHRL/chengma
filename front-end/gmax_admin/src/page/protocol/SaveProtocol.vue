<template>
	<div class="content_box">
		<div class="titling">
			<span>{{pageName}}</span>
		</div>
		<div class="main_box">
			<div class="input_box clearfix">
				<div class="pull-left">
					<span>协议描述：</span>
				</div>
				<div class="pull-left">
					<input type="text" name="" id="" value="" placeholder="" v-model="typeDesc" />
				</div>
			</div>
			<div class="input_box clearfix">
				<div class="pull-left">
					<span>协议标识：</span>
				</div>
				<div class="pull-left">
					<input type="text" name="" id="" value="" placeholder="" v-model="type" />
				</div>
			</div>
		</div>
		<!--<div class="edit_box">
			<button @click="xieru">初始化文本</button>
			
		</div>-->
		<div id="editor" type="text/plain" style="width:1024px;height:500px;"></div>

		<div class="btn_box">
			<button @click="clearContent">清空</button>
			<button class="submit" @click="submits" :disabled="isDisabled">提交</button>
			
		</div>

	</div>
</template>

<script>
    import { uploadImage } from "@/until/until"
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
				bookFace: null,
				news: {},
				
				typeDesc: '',
				type: '',
				old: '',
				pageName: '新增协议',
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
				_this.fetch("/api/protocol/get/"+_this.id,function (res) {
//					console.log(res)
					if (res.data) {
						_this.typeDesc = res.data.typeDesc;
						_this.type = res.data.type;
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
				uploadImage("/api/uploadImage",obj).then((res) => {
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
					id: _this.id,
					type: _this.type,//	是	string	协议类型
					typeDesc: _this.typeDesc,//	是	string	协议类型
					context: content//
				}
				_this.post("/api/protocol/saveProtocol",datas,function (res) {
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
				this.pageName = '编辑协议';
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
		width: 1024px;
		padding: 20px 10px;
	}
	.input_box {
		margin-bottom: 10px;
		margin-right: 10px;
	}
	.input_box input[type="text"]{
		width: 500px;
		border: 1px solid #ddd;
		line-height: 30px;
		padding: 0 10px;
		font-size: 14px;
	}
	.input_box select {
		height: 30px;
		width: 150px;
		border-color: #f4f4f4;
	}
	.input_box span {
		line-height: 30px;
		font-size: 14px;
		color: #666;
		margin-right: 10px;
	}
	.uplond_box {
		width: 200px;
		height: 100px;
		line-height: 100px;
		text-align: center;
		border: 1px solid #f4f4f4;
		margin-right: 90px;
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
		text-align: center;
		padding: 30px 0;
		width: 1024px;
		background-color: #fff;
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