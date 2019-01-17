<template>
	<div class="page_box">
		<my-header :leftOptions="headOption"></my-header>

		<div class="body_box box_pb0" id="my-box">

			<div class="con_box">

				<div class="sec_1">

					<div class="item_box flex_bet flex_align_center" @click="upLoadImg">
						<div class="left_box">
							<span>头像</span>
						</div>
						<div class="right_box">
							<img :src="userMess.headImg" alt="">
							<i class="iconfont icon-xiangyou"></i>
						</div>
					</div>
					<div class="item_box flex_bet flex_align_center" @click="show3 = true">
						<div class="left_box">
							<span>名字</span>
						</div>
						<div class="right_box">
							<span>{{userMess.name}}</span>
							<i class="iconfont icon-xiangyou"></i>
						</div>
					</div>
					<div class="item_box flex_bet flex_align_center">
						<div class="left_box">
							<span>身份</span>
						</div>
						<div class="right_box">
							<div class="id_tab">
								<span>{{userMess.department | formatDepart}}</span>
							</div>
						</div>
					</div>

				</div>
        
        <div v-transfer-dom>
        	<confirm 
        		v-model="show3"
        		ref="confirm3"
        		show-input
        		:title="'修改名字'"
        		:input-attrs="{type: 'text'}"
        		@on-cancel="onCancel"
        		@on-confirm="onConfirm"
        		@on-show="onShow">
        	</confirm>
        </div>
        
			</div>


		</div>

	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
	import {
		formatDepartment,
		uploadImage
	} from '@/utils/util'
  import { Confirm, TransferDomDirective as TransferDom } from 'vux'
	export default {
		directives: {
			TransferDom
		},
		data() {
			return {
				headOption: {
					title: '个人信息',
					backText: '',
					showBack: true,
					text: '为了您的账户安全，建议您完善以下设置'
				},
				buttons: [{
						title: '拍照'
					},
					{
						title: '相册选取'
					},
				],
				datas: {
					headImg: '', //	否	string	头像
					name: '', //	否	string	昵称
					idPositive: '', //	否	string	身份证正面
					idNegative: '', //	否	string	身份证反面
					email: '', //	否	string	邮箱
					password: '', //	否	string	交易密码
					cid: '', //	否	string	个推识别码
				},
        show3: false
			}
		},
		filters: {
			formatDepart: function(value) {
				return formatDepartment(value)
			},

		},
		computed: {
			...mapState(['userMess']),
		},
		components: {
      Confirm
		},
		methods: {
			...mapActions(['getUserMess']),
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
			upLoadImg() {
				let _this = this;
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
				uploadImage("/api/uploadImage", entry).then((res) => {
					console.log(res)
					_this.$vux.loading.hide();
					let imgUrl = res.target.responseText;
          _this.datas.headImg = imgUrl;
          
          _this.Submit()

				})
			},
      onCancel () {},
      onConfirm (val) {
      	this.datas.name = val;
      	this.Submit();
      },
      onShow () {
      	this.$refs.confirm3.setInputValue(this.datas.name)
      },
      async Submit() {
      	try {
      		
      		const res = await this.fetch("/api/user/updateUserInfo", this.datas, "POST");
      		console.log(res)
      		if (res.statusCode == '0000') {
      			this.$vux.toast.text('修改成功', 'middle')
      			this.datas = {
      				headImg: '',//	否	string	头像
      				name: '',//	否	string	昵称
      				idPositive: '',//	否	string	身份证正面
      				idNegative: '',//	否	string	身份证反面
      				email: '',//	否	string	邮箱
      				password: '',//	否	string	交易密码
      				cid: '',//	否	string	个推识别码
      			}
            this.getUserMess();
      		} else {
      			this.$vux.toast.text(res.msgCode, 'middle')
      		}
      	} catch (err) {
      		console.log('获取数据失败', err);
      	}
      },

		},
		activated() {
			this.getUserMess();
		}
	}
</script>

<style scoped>
	.con_box {
		background-color: #eee;
		height: 100%;
	}

	.item_box {
		background-color: #fff;
		padding: 0.3rem 0.346rem;
		border-bottom: 1px solid #f2f2f2;
	}

	.left_box span {
		font-size: 0.373rem;
		color: #333;
	}

	.left_box i {
		color: #fe9f02;
		font-size: .3rem;
		margin-right: 0.1rem;
	}

	.right_box span {
		color: #888;
		font-size: 0.32rem;
	}

	.right_box span.yellow {
		color: #fe9f02;
	}

	.right_box span.green {
		color: green;
	}

	.right_box i {
		color: #888;
		font-size: .3rem;
		margin-left: 0.1rem;
	}

	.right_box img {
		border-radius: 0.2rem;
		width: 1rem;
		height: 1rem;
	}

	.sec_1 .sub_btn {
		padding: 20px 60px;
		padding-top: 230px;
	}

	.sec_1 .sub_btn .weui-btn_primary {
		background-color: #F1B0B7;
	}

	.id_tab {
		top: 0;
		margin-right: 0.266rem;
	}

	.id_tab span {
		color: #fff;
	}
</style>
