<template>
	<div class="content_box">
		<!--<div class="clearfix">-->
			<!--<div class="pull-left">
				<img src="http://tlb.txasfx.com/crm/img/huo_1.png" alt="" />
				<span class="sharebtn"></span>
			</div>-->
			<!--<div class="qrcode_box pull-right">-->
				<!--<img src="http://tlb.txasfx.com/crm/img/huo_1.png" alt="" />-->
				<div class="qrcode_box">
					<qrcode 
					:value="qrcodeUrl" 
					v-if="qrcodeUrl" 
					:options="{ size: qrsize }">
					</qrcode>
				</div>
				<span class="sharebtn sharebtn1">
					<button class="tag-read" :data-clipboard-text="qrcodeUrl" @click="copy">立即阅读</button>
				</span>
			<!--</div>-->
		<!--</div>-->
	</div>
</template>

<script>
	import QRCode from '@xkeshi/vue-qrcode'
	import Clipboard from 'clipboard'
	export default {
		data() {
			return {
				qrsize: 220,
				qrcodeUrl: "二维码错误"
			}
		},
		components: {
			qrcode : QRCode
		},
		methods: {
			makeQrcode () {
				let user = JSON.parse(localStorage.getItem("user"));
				let id = user.id;
				if (id) {
					this.qrcodeUrl = this.$until.shareHost+"/#/regiter?id="+id;
				}
			},
			copy() {
				let _this = this;
		        var clipboard = new Clipboard('.tag-read')
		        clipboard.on('success', e => {
		          //console.log('复制成功')
		          _this.$message({
			          message: '复制链接成功，可粘贴发送至好友',
			          type: 'success'
			        });
		          // 释放内存
		          clipboard.destroy()
		        })
		        clipboard.on('error', e => {
		          // 不支持复制
		          //console.log('该浏览器不支持自动复制')
		          _this.$message({
			          message: '该浏览器不支持自动复制',
			          type: 'warning'
			        });
		          // 释放内存
		          clipboard.destroy()
		        })
		    },
			changeSize () {
				let ww = document.documentElement.clientWidth || document.body.clientWidth;
				this.qrsize = 0.1*ww;
				
			}
		},
		mounted() {
			this.makeQrcode();
			this.changeSize();
		}
	}
</script>

<style scoped>
	.content_box {
		position: relative;
		height: 100%;
		background-image: url(http://tlb.txasfx.com/crm/img/huoke_3.png);
		/*height: 800px;*/
		background-size: 100% auto;
		background-repeat: no-repeat;
		background-position: center;
	}
	
	.qrcode_box {
		width: 220px;
		/*height: 220px;*/
		position: absolute;
		top: 50%;
		left: 10px;
		right: 0;
		margin: auto;
	}
	
	
	.sharebtn {
		position: absolute;
		top: 70%;
		left: 0;
		right: 0;
		margin: auto;
		
		width: 200px;
		height: 50px;
		background-image: url(http://tlb.txasfx.com/crm/img/huo_btn_bg.png);
		background-size: 100% 100%;
		background-repeat: no-repeat;
		margin-top: 50px;
		cursor: pointer;
	}
	
	.sharebtn button {
		opacity: 0;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		width: 100%;
	}
</style>