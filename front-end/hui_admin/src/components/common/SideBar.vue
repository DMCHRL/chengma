<template>
	<div class="slider_section">
		<div class="user_section">
			<router-link to="message">
				<img src="../../assets/img/2_01.png" />
				<p>{{name}}</p>
			</router-link>
		</div>
		<ul class="linklist">
			<li v-for="(item,index) in slidelist" v-if="item.identity == identity || item.identity == 'all'">
				<template v-if="item.children">
					<p class="item_box clearfix" @click="changedown(item)">
						<!--<img :src="'http://tlb.txasfx.com/crm/img/h_'+item.picnum+'.png'" class="pull-left" />-->
						<span v-text="item.name"></span>
						<i v-if="item.showdown" class="glyphicon glyphicon-menu-up _icon"></i>
						<i v-else="item.showdown" class="glyphicon glyphicon-menu-down _icon"></i>
					</p>
					<ul class="down_box" v-show="item.showdown">
						<li v-for="i in item.children">
							<router-link :to="i.href">
								<span v-text="'·  '+i.name" class="item_box"></span>
							</router-link>
						</li>
					</ul>
				</template>
				<template v-else>
					<router-link :to="item.href">
						<span v-text="item.name" class="item_box clearfix">
							<!--<img :src="'http://tlb.txasfx.com/crm/img/h_'+item.picnum+'.png'" class="pull-left" />-->
						    <!--<span  class="pull-left"></span>-->
						</span>
					</router-link>
				</template>
			</li>
		</ul>

	</div>
</template>

<script>
	export default {
		data() {
			return {
				name: '',
				identity: '',
				slidelist: [
					{
						href: '/',
						name: '首页导航',
						picnum: '1',
						identity: 'all'
					},
					//admin
					{
						href: '/banner',
						name: '广告轮播图',
						picnum: '1',
						identity: 'admin'
					},
					{
						href: '/news',
						name: '财经资讯',
						picnum: '9',
						identity: 'admin'
					},
					{
						href: '/video',
						name: '交易战法视频',
						picnum: '8',
						identity: 'admin'
					},
					{
						href: '/3',
						name: '交易面对面',
						picnum: '015',
						identity: 'admin',
						showdown: false,
						children: [{
								href: '/train',
								name: '培训课程'
							},
							{
								href: '/apply',
								name: '课程报名'
							}
						]
					},
					{
						href: '/3',
						name: '交易师考证',
						picnum: '015',
						identity: 'admin',
						showdown: false,
						children: [{
								href: '/examcourses',
								name: '考证场次'
							},
							{
								href: '/applyexam',
								name: '考证报名'
							}
						]
					},
					{
						href: '/strategy',
						name: '策略管理',
						picnum: '3',
						identity: 'admin',
						showdown: false,
						children: [
							{
								href: '/strategy',
								name: '线上策略'
							},
							{
								href: '/task1',
								name: '策略审核'
							},
							{
								href: '/task2',
								name: '跟单/解绑审核'
							},
              {
                href: '/accountList',
                name: '交易账号'
              },
              {
                href: '/tradeList',
                name: '平仓记录'
              },
						]
					},
					{
						href: '/exchanger',
						name: '汇商管理',
						picnum: '7',
						identity: 'admin',
					},
					{
						href: '/task',
						name: '客服任务',
						picnum: '011',
						identity: 'admin',
						showdown: false,
						children: [
							{
								href: '/task',
								name: '开户审核'
							}
						]
					},
					
					{
						href: '/user',
						name: '用户管理',
						picnum: '4',
						identity: 'admin',
						showdown: false,
						children: [
							{
								href: '/user',
								name: '管理员设置'
							},
							{
								href: '/appuser',
								name: 'APP用户'
							},
              {
                href: '/integralDetail',
                name: '积分明细'
              }
						]
					},
					{
						href: '/protocol',
						name: '协议管理',
						picnum: '4',
						identity: 'admin',
					},
					{
						href: '/live',
						name: '直播管理',
						picnum: '4',
						identity: 'admin',
					},
					{
						href: '/push',
						name: '消息推送',
						picnum: '4',
						identity: 'admin',
					},
					{
						href: '/orderList',
						name: '订单管理',
						picnum: '4',
						identity: 'admin',
					},
					{
						href: '/contact',
						name: '联系客服',
						picnum: '1',
						identity: 'admin'
					},
					
					//service 开户管理员
					
					{
						href: '/task',
						name: '客服任务',
						picnum: '011',
						identity: 'service',
						showdown: false,
						children: [
							{
								href: '/task',
								name: '开户审核'
							}
						]
					},
					//strategy策略管理员
					{
						href: '/strategy',
						name: '策略管理',
						picnum: '3',
						identity: 'strategy',
						showdown: false,
						children: [
              {
              	href: '/task1',
              	name: '策略审核'
              },
							{
								href: '/task2',
								name: '跟单/解绑审核'
							}
						]
					},
					//mt4 云端管理员
					{
						href: '/strategy',
						name: '策略管理',
						picnum: '3',
						identity: 'mt4',
						showdown: false,
						children: [
							{
								href: '/task2',
								name: '跟单/解绑审核'
							}
						]
					},
					//operate 运营管理员
					{
						href: '/banner',
						name: '广告轮播图',
						picnum: '1',
						identity: 'operate'
					},
					{
						href: '/news',
						name: '财经资讯',
						picnum: '9',
						identity: 'operate'
					},
					
					//teach 报教管理员
					{
						href: '/3',
						name: '交易面对面',
						picnum: '015',
						identity: 'teach',
						showdown: false,
						children: [{
								href: '/train',
								name: '培训课程'
							},
							{
								href: '/apply',
								name: '培训报名'
							}
						]
					},
					{
						href: '/3',
						name: '交易师考证',
						picnum: '015',
						identity: 'teach',
						showdown: false,
						children: [{
								href: '/examcourses',
								name: '考证场次'
							},
							{
								href: '/applyexam',
								name: '考证报名'
							}
						]
					},
					{
						href: '/live',
						name: '直播管理',
						picnum: '4',
						identity: 'teach',
					},
					{
						href: '/push',
						name: '消息推送',
						picnum: '4',
						identity: 'teach',
					},
          
				]
			}
		},
		methods: {
			changedown(item) {
				let _this = this;
				let list = _this.slidelist;
				for(let i = 0; i < list.length; i++) {
					if(list[i] == item) continue;
					list[i].showdown = false;
				}
				item.showdown = !item.showdown;
			},
			changeSide () {
				let _this = this;
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
					_this.name = user.lastName;
					_this.identity = user.department;
//					console.log(_this.identity)
				}
				
			}
		},
		created () {
			this.changeSide();
		}
	}
</script>

<style scoped>
	/*公共侧栏*/
	
	.slider_section {
		width: 100%;
		height: 100%;
		text-align: center;
		background-color: #fff;
	}
	
	.user_section img {
		width: 80px;
		margin-top: 25px;
		margin-bottom: 20px;
		border: 2px solid #ededed;
		border-radius: 50%;
		background-color: #ededed;
	}
	
	.user_section p {
		font-size: 16px;
		color: #666;
		line-height: 25px;
	}
	
	.linklist {
		margin-top: 25px;
	}
	/*.linklist li {
		margin-bottom: 10px;
	}*/
	
	
	.item_box {
		display: block;
		/*padding: 5px 8px;*/
		line-height: 40px;
		cursor: pointer;
		position: relative;
		text-align: center;
		font-size: 16px;
		color: #4e4e4e;
	}
	
	._icon {
		position: absolute;
		right: 10px;
		top: 0;
		bottom: 0;
		line-height: 40px;
	}

	.linklist li a {
		display: block;
		border-right: 2px solid #fff;
	}
	
	.linklist li a.router-link-exact-active,
	.linklist li a:hover {
		/*background-image: url(http://tlb.txasfx.com/crm/img/h_bg_1.png);
		background-repeat: no-repeat;
		background-position: right center;
		background-size: auto 100%;*/
		background-color: #efefef;
		border-right: 2px solid #ec5046;
	}
	
	.linklist li a.router-link-exact-active .item_box,
	.linklist li a:hover .item_box {
		background-color: #efefef;
		color: #ec5046;
	}
	.linklist li a {
		text-decoration: none;
	}
	
	.down_box li {
		margin-bottom: 5px;
	}
	
	.linklist .down_box span.item_box {
		padding-left: 80px;
		text-align: left;
	}

</style>
