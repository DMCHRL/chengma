<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
        
        <div class="hui_content" id="hui-content">
        
		<my-swiper :list="bannerList"></my-swiper>
		
		<div class="welcome_box">
			<p>欢迎交易高手入驻“汇添溢”。您的到来将给您和用户更多的惊喜！信号入驻需要经过审核，请按照下表填写。我们将在一个工作日内与您取得联系。</p>
		</div>
        
        <div class="list_box">
		<div class="item_box flex_bet flex_align_center">
			<div class="name_box">
				<span>策略名称</span>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="text" name="" id="1" value="" v-model="title" placeholder="" />
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="name_box">
				<span>策略副标</span>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="text" name="" id="2" value="" v-model="title2" placeholder=""/>
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="name_box">
				<span>策略介绍</span>
			</div>
			<div class="text_box flex_one flex_align_center">
				<textarea name="" rows="5" cols="" v-model="text" maxlength="100" placeholder="策略介绍100字以内"></textarea>
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="name_box">
				<span>交易平台</span>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="text" name="" id="41" value="" v-model="platform" />
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="name_box">
				<span>交易方式</span>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="text" name="" id="4122" value="" v-model="way" placeholder="请输入程序化交易或手工交易"/>
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="name_box">
				<span>交易账户</span>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="number" name="" id="43" value="" v-model="account" />
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="name_box">
				<span>观摩密码</span>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="password" name="" id="5" value="" v-model="passWord"/>
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="name_box">
				<span>服务器</span>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="text" name="" id="42" value="" v-model="server" />
			</div>
		</div>
        
        </div>
        
        </div>
		
		<div class="bottom_btn myback">
			<button :disabled="isDisable" @click="Commit">确认申请</button>
		</div>
        
        
	</div>
</template>

<script>
	import MySwiper from "../common/Swiper"
	export default {
		data() {
			return {
				headOption: {title: '策略入驻申请',backText: '',showBack: true},
				way: '',
				typeNum: 'STEADY',
				isDisable: false,
				account: '',
				passWord: '',
				title: '',
				title2: '',
				text: '',
				platform: '',
				server: '',
				bannerList: []
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			},
			user () {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
				    return user;
				}else {
					return null;
				}
			}
		},
		components: {
			MySwiper
		},
		methods: {
			changType (num) {
				this.typeNum = num;
			},
			Commit () {
				let _this = this;
				if (_this.user.account == '') {
					_this.$vux.toast.text('请输入交易账号','middle')
					return;
				}
				if (_this.user.passWord == '') {
					_this.$vux.toast.text('请输入交易密码','middle')
					return;
				}
				let datas = {
					tradeType: _this.way,
					strategyName: _this.title,//	是	string	策略名称
					strategyName2: _this.title2,//	否	string	策略次名
					strategyText: _this.text,//	否	string	备注
					account: _this.account,//	是	string	账号
					password: _this.passWord,//
					platform: _this.platform,
					server: _this.server
				}
				_this.isDisable = true;
				_this.$post("/api/hpp_strategy/apply",datas).then((res) => {
					_this.isDisable = false;
					if (res.statusCode == '0000') {
						_this.$vux.toast.text('提交策略成功','middle')
						_this.$router.go(-1)
					}else {
						_this.$vux.toast.text(res.msgCode,'middle')
					}
				})
			},
			getBannerList () {
				let _this = this;
				_this.$fetch("/api/hpp_advertisement/loadByStrategy").then((res) => {
//					console.log(res)
					_this.bannerList = res.data.list;
				})
			},
		},
		mounted () {
			this.getBannerList();
		}
	}
</script>

<style scoped>
    
    .list_box {
        padding-bottom: 50px;
        background-color: #fff;
    }
    
	.welcome_box {
		background-color: #fff;
		padding: 0.2666rem;
	}
	.welcome_box p {
		text-indent: 2em;
		color: #666;
		font-size: 0.3rem;
		line-height: 0.5333rem;
	}
	
	.name_box {
		width: 20%;
		text-align: center;
		line-height: 1.2rem;
	}
	
	.text_box {
		height: inherit;
		border-bottom: 1px solid #ededed;
	}
	.text_box input {
		width: 100%;
		font-size: 0.3rem;
		height: 1.2rem;
		color: #666;
	}
	.text_box textarea {
		width: 100%;
		border: none;
		font-size: 0.35rem;
		color: #666;
		padding: 0.1333rem;
		padding-left: 0;
	}
	.btn_box {
		padding: 1.3333rem 0;
		text-align: center;
		background-color: #fff;
	}
	.btn_box button {
		color: #fff;
		font-size: 0.3466rem;
		padding: 0 1.3333rem;
		line-height: .8rem;
		border-radius: 1rem;
	}
	
</style>