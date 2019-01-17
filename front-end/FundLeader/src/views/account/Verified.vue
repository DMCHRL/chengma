<template>
	<div class="page_box">
		<fund-header :leftOptions="headOption"></fund-header>

		<div class="body_box box_pt150 box_pb0" id="my-box">

			<div class="con_box">

				<div class="sec_1">
					<p class="title">上传身份证正反面</p>

					<div class="item_box">
						<img v-if="datas.idPositive" :src="datas.idPositive" alt="">
						<div v-else class="up_btn_box" @click="upLoadImg('idPositive')">
							<img src="../../assets/img/f_ver_01.png" alt="">
							<p>上传证件人像面</p>
						</div>
					</div>
					<div class="item_box">
						<img v-if="datas.idNegative" :src="datas.idNegative" alt="">
						<div v-else class="up_btn_box" @click="upLoadImg('idNegative')">
							<img src="../../assets/img/f_ver_02.png" alt="">
							<p>上传证件国徽面</p>
						</div>
					</div>

				</div>


				<sub-btn :text="subbtn.text" :disabled="subbtn.disabled" @clickme="Submit"></sub-btn>

			</div>


		</div>

	</div>
</template>

<script>
    import {uploadImage} from '../../utils/util'
	export default {
		directives: {},
		data() {
			return {
				headOption: {
					title: '实名认证',
					backText: '',
					showBack: true,
					text: '为了您的账户安全，建议您完善以下设置'
				},
				subbtn: {
					text: '确认提交',
					disabled: false
				},
                whichImg: null,
                buttons: [
                	{title: '拍照'},
                	{title: '相册选取'},
                ],
                datas: {
                    headImg: '',//	否	string	头像
                    name: '',//	否	string	昵称
                    idPositive: '',//	否	string	身份证正面
                    idNegative: '',//	否	string	身份证反面
                    email: '',//	否	string	邮箱
                    password: '',//	否	string	交易密码
                    cid: '',//	否	string	个推识别码
                }
			}
		},
		filters: {

		},
		computed: {},
		components: {

		},
		methods: {
			canvasDataURL(path, obj, callback) {
				var img = new Image();
				img.src = path;
				img.onload = function() {
					var that = this;
					// 默认按比例压缩
					var w = that.width,
						h = that.height,
						scale = w / h;
					w = obj.width || w;
					h = obj.height || (w / scale);
					var quality = 0.7; // 默认图片质量为0.7
					//生成canvas
					var canvas = document.createElement('canvas');
					var ctx = canvas.getContext('2d');
					// 创建属性节点
					var anw = document.createAttribute("width");
					anw.nodeValue = w;
					var anh = document.createAttribute("height");
					anh.nodeValue = h;
					canvas.setAttributeNode(anw);
					canvas.setAttributeNode(anh);
					ctx.drawImage(that, 0, 0, w, h);
					// 图像质量
					if (obj.quality && obj.quality <= 1 && obj.quality > 0) {
						quality = obj.quality;
					}
					// quality值越小，所绘制出的图像越模糊
					var base64 = canvas.toDataURL('image/jpeg', quality);
					// 回调函数返回base64的值
					callback(base64);
				}
			},
			convertBase64UrlToBlob(urlData) {
				var arr = urlData.split(','),
					mime = arr[0].match(/:(.*?);/)[1],
					bstr = atob(arr[1]),
					n = bstr.length,
					u8arr = new Uint8Array(n);
				while (n--) {
					u8arr[n] = bstr.charCodeAt(n);
				}
				return new Blob([u8arr], {
					type: mime
				});
			},

			resolveUrl(p) {
				let _this = this;
				_this.$vux.loading.show({
					text: '上传中...'
				})
				plus.io.resolveLocalFileSystemURL(p, function(entry) {

					let src = entry.toLocalURL();
					_this.canvasDataURL(src, {
						quality: 0.7,
						width: 500
					}, function(base64) {
						let blob = _this.convertBase64UrlToBlob(base64)
						_this.upLoadAndShow(blob)
					})
				}, function(e) {
					console.log('读取拍照文件错误：' + e.message);
				});
			},
			//点击上传图片
			upLoadImg(input) {
				let _this = this;
				_this.whichImg = input;

				plus.nativeUI.actionSheet({
					cancel: '取消',
					buttons: _this.buttons
				}, function(e) {
					if (e.index > 0) {
						if (e.index == 1) {
							_this.imageCameraPicture();
						} else {
							_this.imageGalleryPicture();
						}
					}

				})

			},
			// 拍照添加图片
			imageCameraPicture() {
				let _this = this;
				var cmr = plus.camera.getCamera();
				cmr.captureImage(function(p) {
					_this.resolveUrl(p)
				}, function(e) {
					console.log('拍照失败：' + e.message);
				});
			},
			// 从相册添加图片
			imageGalleryPicture() {
				let _this = this;
				// console.log('从相册添加分享图片：');
				plus.gallery.pick(function(p) {
					_this.resolveUrl(p)
				});
			},

			upLoadAndShow(entry) {
				let _this = this;
				_this.subbtn.disabled = true;

				uploadImage("/api/uploadImage", entry).then((res) => {
					console.log(res)
					_this.$vux.loading.hide();
					_this.subbtn.disabled = false;
					let imgUrl = res.target.responseText;

					if (_this.whichImg == 'idPositive') {
						_this.datas.idPositive = imgUrl;
					} else {
						_this.datas.idNegative = imgUrl;
					}

				})
			},
			
			async Submit() {
				try {
					if (this.datas.idPositive == '') {
						this.$vux.toast.text('请先上传照片', 'middle')
						return;
					}
					if (this.datas.idNegative == '') {
						this.$vux.toast.text('请先上传照片', 'middle')
						return;
					}
					const res = await this.fetch("/api/user/updateUserInfo", this.datas, "POST");
					console.log(res)
					if (res.statusCode == '0000') {
						this.$vux.toast.text('提交成功', 'middle')
                        this.datas = {
                            headImg: '',//	否	string	头像
                            name: '',//	否	string	昵称
                            idPositive: '',//	否	string	身份证正面
                            idNegative: '',//	否	string	身份证反面
                            email: '',//	否	string	邮箱
                            password: '',//	否	string	交易密码
                            cid: '',//	否	string	个推识别码
                        }
                        this.$router.go(-1)
					} else {
						this.$vux.toast.text(res.msgCode, 'middle')
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}
			},
			plusReady() {
				console.log('plusReady')
			}
		},
		activated() {
			let _this = this;
			if (window.plus) {
				_this.plusReady();
			} else {
				document.addEventListener("plusready", _this.plusReady, false);
			}
		}
	}
</script>

<style scoped>
	.con_box {
		background-color: #f8f9fd;
		height: 100%;
	}

	.sec_1 p.title {
		color: #28347e;
		font-size: 0.373rem;
		text-align: center;
		padding: .8rem 0;
	}

	.item_box {
		width: 4.533rem;
		height: 3.173rem;
		margin: 0 auto;
		text-align: center;
		background-color: #f4f5ff;
		border-radius: 10px;
		overflow: hidden;
		margin-bottom: 0.8rem;
	}
    .item_box>img {
        width: 100%;
    }
	.up_btn_box {
		position: relative;
		height: 100%;
	}

	.up_btn_box img {
		width: 2.826rem;
		margin: 0.2rem;
	}

	.up_btn_box p {
		background-color: #ffa721;
		line-height: 0.8rem;
		color: #fff;
		position: absolute;
		bottom: 0;
		left: 0;
		right: 0;
	}
</style>
