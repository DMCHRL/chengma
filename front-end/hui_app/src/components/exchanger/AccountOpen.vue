<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
		
    <div class="title_tab clearfix">
    	<span class="pull-left" :class="{active: swiper_index == 0}" @click="swiper_index = 0">基本信息</span>
    	<span class="pull-left" :class="{active: swiper_index == 1}" @click="swiper_index = 1">身份证明</span>
    	<span class="pull-left" :class="{active: swiper_index == 2}" @click="swiper_index = 2">财务信息</span>
    </div>
    <!-- <div class="title_box">
    	<span>基本信息</span>
    </div> -->
    
    <!-- <div class="title_box">
    	<span>身份证明</span>
    </div> -->
    
    <!-- <div class="title_box">
    	<span>财务信息</span>
    </div> -->
    
    <div class="hui_content" id="hui-content">
    
		<!--<div class="title_box">
			<span>账户类型</span>
		</div>
		<div class="type_box flex_around">
			<div :class="['flex_row', 'flex_align_center', {checked:userType=='normal'}]" @click="userType = 'normal'">
				<span class="radio_icon"></span>
				<span>标准账户</span>
			</div>
			<div :class="['flex_row', 'flex_align_center', {checked:userType=='ecn'}]" @click="userType = 'ecn'">
				<span class="radio_icon"></span>
				<span>ECN账户</span>
			</div>
		</div>-->
    
    <swiper v-model="swiper_index" height="500px" :show-dots="false">
    	<swiper-item>
        <div class="swiper_item_box">
          <div class="item_box flex_bet flex_align_center">
          	<div class="icon_box">
          		<img src="../../assets/img/kai_01.png"/>
          	</div>
          	<div class="input_box flex_one">
          		<input type="text" name="" id="" value="" placeholder="填写您的姓名" v-model="name" />
          	</div>
          </div>
          <div class="item_box flex_bet flex_align_center">
          	<div class="icon_box">
          		<img src="../../assets/img/kai_02.png"/>
          	</div>
          	<div class="input_box flex_one">
          		<input type="text" name="" id="" value="" placeholder="填写您的邮箱" v-model="email" />
          	</div>
          </div>
          <div class="item_box flex_bet flex_align_center">
          	<div class="icon_box">
          		<img src="../../assets/img/kai_03.png"/>
          	</div>
          	<div class="input_box flex_one">
          		<input type="text" name="" id="" value="" placeholder="填写您的手机号" v-model="phone" />
          	</div>
          </div>
          <div class="item_box flex_bet flex_align_center">
          	<div class="icon_box">
          		<img src="../../assets/img/kai_04.png"/>
          	</div>
          	<div class="input_box flex_one">
          		<input type="text" name="" id="" value="" placeholder="填写推荐码（可不填）" v-model="code" />
          	</div>
          </div>
        </div>
      </swiper-item>
      <swiper-item>
      	<div class="swiper_item_box">
      		<div class="item_box flex_bet flex_align_center">
      			<div class="icon_box">
      				<img src="../../assets/img/kai_05.png"/>
      			</div>
      			<div class="input_box flex_one">
      				<input type="text" name="" id="" value="" placeholder="请输入身份证号" v-model="idNum" />
      			</div>
      		</div>
      		<div class="photo_up">
      			<p>拍照上传身份证正反面</p>
      			<div class="flex_bet">
      				<label>
      					<div class="up_box">
      						<!-- <input type="file" name="" id="" value="" style="display: none;" accept="image/*" ref="img_input1"  @change="changeImg1"/> -->
      						<img v-if="img1" class="main_img" :src="img1"/>
      						<div v-else >
      							<div class="up_icon" @click="upLoadImg('idPositive')">
      								<img src="../../assets/img/kai_10.png"/>
      							</div>
      							<p>上传正面</p>
      						</div>
      					</div>
      				</label>
      				<label>
      					<div class="up_box">
      						<!-- <input type="file" name="" id="" value="" style="display: none;" accept="image/*" ref="img_input2"  @change="changeImg2"/> -->
      						<img v-if="img2" class="main_img" :src="img2"/>
      						<div v-else >
      							<div class="up_icon" @click="upLoadImg('idOther')">
      								<img src="../../assets/img/kai_11.png"/>
      							</div>
      							<p>上传反面</p>
      						</div>
      					</div>
      				</label>
      			</div>
      		</div>
      		<div class="item_box flex_bet flex_align_center">
      			<div class="icon_box">
      				<img src="../../assets/img/kai_06.png"/>
      			</div>
      			<div class="input_box flex_one" @click="changeType">
      				<span>{{typeName}}</span>
      				<!-- <select name="" placeholder="选择地址证明类型" v-model="addressType" >
      					<option :value="item.value" v-for="item in options2">{{item.key}}</option>
      				</select> -->
      			</div>
      		</div>
      		<div class="photo_up">
      			<label>
      				<div class="up_box up_box_2">
      					<!-- <input type="file" name="" id="" value="" style="display: none;" accept="image/*" ref="img_input4"  @change="changeImg4"/> -->
      					<img v-if="img4" class="main_img" :src="img4"/>
      					<div v-else >
      						<div class="up_icon" @click="upLoadImg('secondPositive')">
      							<img src="../../assets/img/kai_12.png"/>
      						</div>
      						<p>上传证件照片</p>
      					</div>
      				</div>
      				
      			</label>
      		</div>
      	</div>
      </swiper-item>
      <swiper-item>
      	<div class="swiper_item_box">
      		<div class="item_box flex_bet flex_align_center">
      			<div class="icon_box">
      				<img src="../../assets/img/kai_07.png"/>
      			</div>
      			<div class="input_box flex_one">
      				<input type="text" name="" id="" value="" placeholder="输入银行卡号" v-model="bankNum" />
      			</div>
      		</div>
      		<div class="item_box flex_bet flex_align_center">
      			<div class="icon_box">
      				<img src="../../assets/img/kai_08.png"/>
      			</div>
      			<div class="input_box flex_one">
      				<input type="text" name="" id="" value="" placeholder="输入开户行" v-model="bankName" />
      			</div>
      		</div>
      		<div class="item_box flex_bet flex_align_center">
      			<div class="icon_box">
      				<img src="../../assets/img/kai_09.png"/>
      			</div>
      			<div class="input_box flex_one">
      				<input type="text" name="" id="" value="" placeholder="输入开户支行" v-model="bankName2" />
      			</div>
      		</div>
      		<div class="photo_up">
      			<label>
      				<div class="up_box up_box_2">
      					<!-- <input type="file" name="" id="" value="" style="display: none;" accept="image/*" ref="img_input3"  @change="changeImg3"/> -->
      					<img v-if="img3" class="main_img" :src="img3"/>
      					<div v-else >
      						<div class="up_icon" @click="upLoadImg('cardPositive')">
      							<img src="../../assets/img/kai_13.png"/>
      						</div>
      						<p>上传银行卡正面</p>
      					</div>
      				</div>
      				
      			</label>
      		</div>
      	</div>
      </swiper-item>
    </swiper>
		
		
      
      
    </div>
    
      <div class="bottom_btn myback my_btn_box">
      	<button  @click="Commit" :disabled="isDisabled">提交</button>
      </div>
    
		
	</div>
</template>

<script>
	import { Group, PopupRadio, Swiper, SwiperItem, } from 'vux'
	import {uploadImage} from '../../utils/util'
	export default {
		data() {
			return {
				headOption: {title: '申请开户',backText: '',showBack: true},
				isDisabled: false,
				userType: 'normal',
				name: '',
				phone: '',
				email: '',
				code: '',
				idNum: '',
				addressType: 'driver',
        typeName: '驾驶证',
				bankNum: '',
				bankName: '',
				bankName2: '',
				img1: null,
				img2: null,
				img3: null,
				img4: null,
        
        whichImg: null,
        buttons: [
        	{title: '拍照'},
        	{title: '相册选取'},
        ],
        typeButtons: [
        	{value: 'driver',title: '驾驶证'},
        	{value: 'charge',title: '水费或电费发票'},
        	{value: 'passport',title: '护照及其它'}
        ],
        swiper_index: 0
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			}
		},
		components: {
		    Group,
		    PopupRadio,
        Swiper,
        SwiperItem
		},
		methods: {
      
      canvasDataURL (path, obj, callback) {
      	var img = new Image();
      	img.src = path;
      	img.onload = function() {
      		var that = this;
      		// 默认按比例压缩
      		var w = that.width,	h = that.height, scale = w / h;
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
      convertBase64UrlToBlob (urlData) {
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
      
      resolveUrl (p) {
        let _this = this;
        _this.$vux.loading.show({
         text: '上传中...'
        })
        plus.io.resolveLocalFileSystemURL(p,function(entry){
                    
            let src = entry.toLocalURL();
            _this.canvasDataURL(src,{quality:0.7,width:500},function (base64) {
              // console.log(base64)
              let blob = _this.convertBase64UrlToBlob(base64)
              // console.log(blob)
              _this.upLoadAndShow(blob)
            })
            
//             var image = new Image();
//             image.crossOrigin = '';
//             image.src = src;
//             image.onload = function(){
//               var base64 = _this.getBase64Image(image);
//              console.log(base64)
//               var imgBlob = _this.convertBase64UrlToBlob(base64);
//               
//               console.log(imgBlob)
//               return;
//               _this.upLoadAndShow(imgBlob)
//               
//             };
        },function(e){
            console.log('读取拍照文件错误：'+e.message);
        });
      },
      //点击上传图片
      upLoadImg (input) {
      	let _this = this;
      	_this.whichImg = input;
      	
      	plus.nativeUI.actionSheet({
      		cancel: '取消',
      		buttons: _this.buttons
      	}, function(e) {
      		if (e.index > 0) {
      			if (e.index == 1) {
      				_this.imageCameraPicture();
      			}else{
      				_this.imageGalleryPicture();
      			}
      		}
      		
      	})
      	
      },
      // 拍照添加图片
      imageCameraPicture(){
        let _this = this;
      	var cmr=plus.camera.getCamera();
      	cmr.captureImage(function(p){
      		_this.resolveUrl(p)
      	},function(e){
      		console.log('拍照失败：'+e.message);
      	});
      },
      // 从相册添加图片
      imageGalleryPicture(){
        let _this = this;
      	// console.log('从相册添加分享图片：');
      	plus.gallery.pick(function(p){
          _this.resolveUrl(p)
        });
      },
      
			upLoadAndShow (entry) {
				let _this = this;
				_this.isDisabled = true;
        
				uploadImage("/api/uploadImage",entry).then((res) => {
          // console.log(res)
          _this.$vux.loading.hide();
					_this.isDisabled = false;
					let imgUrl = res.target.responseText;
          
          if (_this.whichImg == 'idPositive') {
            _this.img1 = imgUrl;
          }else if (_this.whichImg == 'idOther') {
            _this.img2 = imgUrl;
          }else if (_this.whichImg == 'cardPositive') {
            _this.img3 = imgUrl;
          }else if (_this.whichImg == 'secondPositive'){
            _this.img4 = imgUrl;
          }else {
            console.log(_this.whichImg)
            console.log(imgUrl)
          }
          
				})
			},
// 			changeImg2 () {
// 				let _this = this;
// 				_this.isDisabled = true;
// 				let imgObj = _this.$refs.img_input2.files[0];
//         console.log(imgObj)
// 				uploadImage("/api/uploadImage",imgObj).then((res) => {
// 					let imgUrl = res.target.responseText;
// 					_this.img2 = imgUrl;
// 					_this.isDisabled = false;
// 				})
// 			},
			
      changeType () {
        let _this = this;
        plus.nativeUI.actionSheet({
        	cancel: '取消',
        	buttons: _this.typeButtons
        }, function(e) {
          if (e.index > 0) {
            var button = _this.typeButtons[e.index - 1];
            _this.addressType = button.value;
            _this.typeName = button.title;
          }
        })
      },
			Commit () {
				let _this = this;
				let datas = {
					username: _this.name,// 是  string 姓名   
					phone: _this.phone,// 是  string  手机号    
					email: _this.email,//      是  string  邮箱    
					recommendation: _this.code,
					sinksType: _this.id,//      是  string  汇商id  
					userType: _this.userType,//      是  string  用户类型 NORMAL  OR ECN   
					idNumber: _this.idNum,//      是  string  身份证号    
					idPositive: _this.img1,//      是  string  身份证正面    
					idOther: _this.img2,//      是  string  身份证反面    
					cardNumber: _this.bankNum,//      是  string  银行卡号    
					openingBank: _this.bankName,//      是  string  开户银行    
					branch: _this.bankName2,//      是  string  开户支行    
					cardPositive: _this.img3,//      是  string  银行卡正面  
					secondPositive: _this.img4,
					secondType: _this.addressType
				}
				_this.isDisabled = true;
				_this.$post("/api/hpp_user/saveHppUser",datas).then((res) => {
//					console.log(res)
					_this.isDisabled = false;
					if (res.statusCode == '0000') {
						_this.$vux.toast.text('提交成功')
						_this.$router.go(-1)
					}else {
						_this.$vux.toast.text(res.msgCode)
					}
				})
			},
      plusReady () {
      	console.log('plusReady')
      }
		},
		mounted () {
      let _this = this;
      if(window.plus){
        _this.plusReady();
      }else{
        document.addEventListener("plusready",_this.plusReady,false);
      }
		}
	}
</script>

<style scoped>
	.content_box {
		background-color: #fff;
	}
  .hui_content {
  	padding-top: 115px;
  }
  
  .title_tab {
    background-color: #fff;
    position: fixed;
    top: 75px;
    left: 0;
    width: 100%;
    height: 40px;
    line-height: 40px;
    z-index: 99;
  }
  .title_tab span {
    width: 33.33%;
    text-align: center;
    font-size: .35rem;
    color: #666;
    border-bottom: 2px solid #999;
    line-height: 38px;
  }
  .title_tab span.active {
    border-color: #ff5752;
    color: #ff5752;
  }
  
  
	/* .title_box {
		height: 1.0666rem;
		border-bottom: 1px solid #ff5752;
		padding: 0 0.2933rem ;
	}
	.title_box span{
		display: inline-block;
		line-height: 1rem;
		border-bottom: 3px solid #ff5752;
		font-size: 0.3466rem;
		color: #333;
	}
	
	.type_box {
		height: 1.0666rem;
		border-bottom: 1px solid #ccc;
	}
	.type_box span {
		font-size: 0.3466rem;
		color: #333;
	}
	.radio_icon {
		width: 0.2rem;
		height: 0.2rem;
		border: 0.0366rem solid #ccc;
		border-radius: 50%;
		margin-right: 0.2666rem;
	}
	.checked .radio_icon {
		border-color: #ff5752;
	} */
  
  
	.icon_box img {
		width: 0.64rem;
		height: 0.64rem;
		margin: 0 0.5333rem;
	}
	.input_box input,
	.input_box select,
  .input_box span{
    display: block;
		width: 100%;
		line-height: 1.1733rem;
		border: none;
		border-bottom: 1px solid #ccc;
		color: #666;
    font-size: .3rem;
		background-color: #fff;
	}
	
	.photo_up{
		padding:0.2666rem ;
	}
	.photo_up>p {
		font-size: 0.3733rem;
		color: #f6615f;
		text-align: center;
		line-height: 1.0666rem;
	}
	.up_box {
		width: 4.5333rem;
		height: 3.0933rem;
		border-radius: 0.1333rem;
		text-align: center;
		background-color: #f4f4f4;
		overflow: hidden;
		margin: 0 auto;
	}
	.up_box_2 {
		width: 8rem;
	}
	
	.up_box p {
		background-color: #f6615f;
		color: #fff;
		font-size: 0.3733rem;
		line-height: 0.8rem;
	}
	.up_box img {
		width: 2.84rem;
		height: 1.7866rem;
		margin: 0.26rem 0;
	}
	.up_box img.main_img {
		width: auto;
		height: 100%;
		margin: 0;
	}
	.my_btn_box {
		/* position: relative;
    margin-top: 100px; */
	}
	
</style>