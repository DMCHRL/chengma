<template>
	<!--添加银行卡弹窗-->
	<el-dialog custom-class="ibs_dialog" width="40%" :show-close="true" @close="cancelDialog" :visible="show">
		<h3 slot="title" class="dialog_title"></h3>
		
		<div class="_dialog_body" v-show="!videoname">
			<input ref="file" id="file_btn" type="file" name="myfile" @change="UpladFile" style="display: none;" />
			<label for="file_btn" class="upload_btn">
				<i class="el-icon-upload el-icon--right"></i>立刻上传
			</label>
			<p>点击上传视频，支持'3gp','rmvb','flv','wmv','avi','mkv','mp4','mp3','wav'格式</p>
		</div>
		
		<div class="_dialog_body _dialog_body_2" v-show="videoname">
			<p>视频上传中，请不要关闭浏览器</p>
			<el-progress :text-inside="true" :stroke-width="18" :percentage="progressval" status="exception"></el-progress>
			<p class="">
				<span>{{videoname}}</span>
				<span>完成{{progressval}}%</span>
			</p>
			<button @click="cancelDialog">取消</button>
		</div>
		
		<div slot="footer" class="dialog_footer"></div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['show'],
		data() {
			return {
				videoname: '',
				videourl: '',
				progressval: 0
			}
		},
		computed: {
		},
		components: {

		},
		methods: {
			getFileType(filePath){
				let startIndex = filePath.lastIndexOf(".");
				if(startIndex != -1)
					return filePath.substring(startIndex+1, filePath.length).toLowerCase();
				else return "";
			},
			UpladFile () {
				let _this = this;
				let fileObj = _this.$refs.file.files[0]; // js 获取文件对象
				
				let filePath = _this.$refs.file.value;
				
				if("" != filePath){
					let fileType = _this.getFileType(filePath);
					
					if("mp4"!=fileType && "3gp"!=fileType && "rmvb"!=fileType && "flv"!=fileType && "wmv"!=fileType && "avi"!=fileType && "mkv"!=fileType && "wav"!=fileType ){
//						'mp4','3gp','rmvb','flv','wmv','avi','mkv','wav'
						_this.$message({
				          message: "请选择视频上传",
				          type: 'error'
				        });
						return;
					}
//					else{
//						//获取附件大小（单位：KB）
//						var fileSize = _this.$refs.file.files[0].size / 1024;
//				        if(fileSize > 2048){
//				        	alert("图片大小不能超过2MB");
//				        }
//					}
				}
				
				if(fileObj.name) {
					this.videoname = fileObj.name;
					
					var url = _this.$until.host+"/api/upload"; // 接收上传文件的后台地址 
					var form = new FormData(); // FormData 对象
					form.append("video", fileObj); // 文件对象 
					let xhr = new XMLHttpRequest(); // XMLHttpRequest 对象
					xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
					xhr.onload = function (evt) {
						console.log(evt)
						//服务断接收完文件返回的结果  注意返回的字符串要去掉双引号
						if(evt.target.responseText) {
							
							let videoMess = {
								videoUrl: evt.target.responseText,
								videoName: fileObj.name,
								img: ''
							}
							_this.$emit("uploadSuccess",videoMess)
							
						} else {
							_this.$message({
					          message: "上传失败",
					          type: 'error'
					        });
						}
					}; //请求完成
//					xhr.onerror = uploadFailed; //请求失败
					xhr.upload.onprogress = function (evt) {
						_this.progressval = Math.round(evt.loaded / evt.total * 100);
					}; //【上传进度调用方法实现】
					xhr.upload.onloadstart = function() { //上传开始执行方法
						let ot = new Date().getTime(); //设置上传开始时间
						let oloaded = 0; //设置上传开始时，以上传的文件大小为0
					};
					xhr.send(form); //开始上传，发送form数据
					
				}
			},
			cancelDialog() {
				this.videoname = '';
				this.$emit('close')
			}
		},
		mounted() {
			this.videoname = '';
		}
	}
</script>

<style scoped>
	._dialog_body {
		text-align: center;
		padding: 30px 0;
	}
	.upload_btn {
		font-size: 16px;
		color: #fff;
		background-color: #FF5655;
		padding: 5px 30px;
		border-radius: 5px;
		cursor: pointer;
	}
	.upload_btn i {
		margin-right: 10px;
	}
	._dialog_body p {
		line-height: 50px;
		font-size: 14px;
		color: #666;
	}
	
	._dialog_body_2 {
		padding: 0 10%;
	}
	._dialog_body_2 p {
		font-size: 14px;
		color: #666;
		line-height: 50px;
	}
	._dialog_body_2 button {
		padding: 2px 30px;
	}
</style>