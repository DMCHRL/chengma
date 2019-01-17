<template>
	<div class="content_box">
		<div class="titling">
			<span>工单编辑</span>
		</div>
		
		<div class="form_boxs">
			<h4>提交工单</h4>
			<div class="input_box">
				<span class="import">问题分类</span>
				<select>
					<option value="技术问题">资金问题</option>
					<option value="技术问题">账户问题</option>
					<option value="技术问题">技术问题</option>
				</select>
			</div>
			<div class="input_box">
				<span class="import">邮箱</span>
				<input type="text" name="" id="" value="" />
			</div>
			<div class="input_box">
				<span class="import">手机号</span>
				<input type="text" name="" id="" value="" />
			</div>
			<div class="input_box">
				<span class="import">问题描述</span>
				<textarea></textarea>
			</div>
			<div class="input_box">
				<span>上传附件</span>
				<div class="reward clearfix">
					<div class="pull-left pic_btn btn">
						<label class="pointer">
					        <img src="../../assets/img/d_i_13.png">
					        <p>上传图片</p>
					        <i>（不超过3张）</i>
					        <input type="file" ref="file"  @change="onFileChange" multiple style="display: none;">
					    </label>
					</div>
					<div class="pull-left" v-if="images.length >0">
						<ul class="clearfix">
							<li class="pull-left" v-for="(image,key) in images">
								<img :src="image"/>
								<span @click='delImage(key)' class="remove_btn pointer glyphicon glyphicon-remove"></span>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="btn_box">
				<button>提交</button>
				<button>重置</button>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				images: []
			}
		},
		components: {
		},
		methods: {
			onFileChange(e) {
				var files = e.target.files || e.dataTransfer.files;
				if(!files.length) return;
				this.createImage(files);
			},
			createImage(file) {
				var _this = this;
				if(typeof FileReader === 'undefined') {
					_this.$message({
						message: '您的浏览器不支持图片上传，请升级您的浏览器',
						type: 'warn'
					});
					return false;
				}
				var image = new Image();

				var leng = file.length;
				if(leng + _this.images.length > 3) {
					_this.$message({
						message: "图片数量不得超过3张！",
						type: 'warn'
					});
					return
				}
				for(var i = 0; i < leng; i++) {
					if(file[i].size / 1024 <= 1000) {
						// ////console.log(file[i])
						_this.$until.photoCompress(file[i], {
							quality: 1
						}, function(base64Codes) {
							_this.images.push(base64Codes);
						});
						continue;
					}
					if(file[i].size / 1024 > 1000) {
						// ////console.log(file[i])
						_this.$until.photoCompress(file[i], {
							quality: 0.4
						}, function(base64Codes) {
							_this.images.push(base64Codes);
						});
						continue;
					}
					if(file[i].size / 1024 > 3000) {
						// ////console.log(file[i])
						_this.$until.photoCompress(file[i], {
							quality: 0.3
						}, function(base64Codes) {
							_this.images.push(base64Codes);
						});
						continue;
					}
					if(file[i].size / 1024 > 5500) {
						// ////console.log(file[i])
						_this.$until.photoCompress(file[i], {
							quality: 0.2
						}, function(base64Codes) {
							_this.images.push(base64Codes);
						});
						continue;
					}
				}
				// ////console.log(_this.images)                        
			},
			delImage: function(index) {
				// ////console.log(index) 
				// this.images.shift(index);
				this.images.splice(index, 1);
				// ////console.log(this.images)
			}
		},
		mounted() {

		}
	}
</script>

<style scoped>
	
	.form_boxs {
		/*width: 40%;*/
	}
	.form_boxs h4 {
		font-size: 18px;
		color: #19183e;
		padding: 20px 0;
		margin-left: 10%;
	}
	.form_boxs .input_box {
		margin-bottom: 10px;
	}
	.form_boxs .input_box>span{
		font-size: 12px;
		color: #19183e;
		display: inline-block;
		width: 10%;
		text-align: right;
		padding-right: 20px;
	}
	.form_boxs .input_box:nth-child(5)>span,
	.form_boxs .input_box:nth-child(6)>span{
		position: relative;
		top: -88px;
	}
	.form_boxs .input_box span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	.form_boxs .input_box input,
	.form_boxs .input_box select{
		width: 200px;
		border: 1px solid #ccc;
		padding: 3px 10px;
	}
	.form_boxs .input_box textarea{
		width: 200px;
		height: 100px;
		border: 1px solid #ccc;
		padding: 3px 7px;
	}
	.upimg_box {
		display: inline-block;
		
	}
	.btn_box {
		margin-left: 10%;
	}
	.btn_box button{
		color: #fff;
		background-color: #F14B3B;
		padding: 5px 30px;
		margin: 30px 0;
		border-radius: 20px;
	}
	.btn_box button:last-of-type {
		margin-left: 20px;
		color: #F14B3B;
		background-color: #fff;
		border: 1px solid #F14B3B;
		padding: 3px 28px;
	}
	
	.reward {
		display: inline-block;
	}
	.reward li {
		margin: 0 10px;
		position: relative;
	}
	.reward li img {
		width: 120px;
		height: 120px;
	}
	.reward li span {
		position: absolute;
		right: 5px;
		top: 5px;
	}
	.reward li span:before{
		color: #F14B3B;
		font-size: 15px;
	}
	.pic_btn {
		border: 2px dotted #dcdcdc;
	}
	
	.pic_btn label.pointer {
		display: block;
		width: 100px;
		height: 100px;
		overflow: hidden;
		margin: 0;
	}
	.pic_btn img {
		width: 50%;
		height: 50px;
		margin-top: 0px;
	}
	
	.pic_btn p {
		font-size: 13px;
		color: #bfbfbf;
	}
	
	.pic_btn i {
		font-size: 10px;
		color: #bfbfbf;
	}
</style>