<template>
	<div class="slider_section">
		<div class="user_section">
			<img src="../../assets/img/h_01.png" />
			<p>{{name}}</p>
		</div>
		<ul class="linklist">
			<li v-for="(item,index) in slidelist" :key="index" v-if="item.identity == identity || item.identity == 'all'">
				<template v-if="item.children">
					<p class="item_box item_box_p clearfix" @click="changedown(item)">
						<img :src="'http://tlb.txasfx.com/crm/img/h_'+item.picnum+'.png'" class="pull-left" />
						<span v-text="item.name" class="pull-left"></span>
						<i v-if="item.showdown" class="glyphicon glyphicon-menu-up pull-right"></i>
						<i v-else class="glyphicon glyphicon-menu-down pull-right"></i>
					</p>
					<ul class="down_box" v-show="item.showdown">
						<li v-for="(i,indexs) in item.children" :key="indexs">
							<router-link :to="i.href">
								<span class="item_box">
									<span v-text="i.name"></span>
								</span>
							</router-link>
						</li>
					</ul>
				</template>
				<template v-else>
					<router-link :to="item.href">
						<span class="item_box clearfix">
							<img :src="'http://tlb.txasfx.com/crm/img/h_'+item.picnum+'.png'" class="pull-left" />
							<span v-text="item.name" class="pull-left"></span>
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
					//admin
					{href:'/',name:'数据',picnum:'1',identity:'admin'},
					
					{name:'用户',picnum:'4',identity:'admin',showdown:false,children:[
                        {href:'/user',name:'CRM用户'},
                        {href:'/broke',name:'佣金',picnum:'3',identity:'admin'},
                        {href:'/member',name:'潜在客户',picnum:'4',identity:'admin'},
                        {href:'/feed',name:'用户反馈',picnum:'015',identity:'admin'},
                        ]},
                    {name:'交易',picnum:'011',identity:'admin',showdown:false,children:[
                        {href:'/account',name:'交易账户'},
                        {href:'/position',name:'持仓记录'},
                        {href:'/record',name:'平仓记录'}
                        ]},
                    {name:'任务',picnum:'8',identity:'admin',showdown:false,children:[
                        {href:'/task',name:'客服任务',picnum:'011',identity:'admin'},
                        {href:'/tasked',name:'财务任务',picnum:'011',identity:'admin'},
                        ]},
					// {name:'报表',picnum:'8',identity:'admin',showdown:false,children:[{href:'/report1',name:'账户报表'},{href:'/report2',name:'佣金报表'},{href:'/report3',name:'业绩报表'}]},
					{href:'/store',name:'充值通道',picnum:'20',identity:'admin',showdown:false,children:[
                        {href:'/store',name:'微信/支付宝'},
                        {href:'/union1',name:'银联通道'},
                        {href:'/union2',name:'国际通道'},
                        {href:'/usdt',name:'USDT通道'}]},
					{href:'/push',name:'消息推送',picnum:'9',identity:'admin'},
					
					
					//company
                    {name:'交易',picnum:'011',identity:'company',showdown:true,children:[
                        {href:'/manage',name:'我的账户'},
                        {href:'/account',name:'归属账户'},
                        {href:'/record',name:'交易记录'}
                        ]},
					// {href:'/manage',name:'账户管理',picnum:'014',identity:'company',},
					{href:'/user',name:'IB',picnum:'2',identity:'company',showdown:false,children:[{href:'/user',name:'下级代理'}]},
					// {href:'/record',name:'交易记录',picnum:'8',identity:'company',showdown:false,children:[{href:'/record',name:'平仓记录'}]},
          {href:'/fundDetail',name:'出入金明细',picnum:'6',identity:'company'},

					//proxy
                    {name:'交易',picnum:'011',identity:'proxy',showdown:true,children:[
                        {href:'/manage',name:'我的账户'},
                        {href:'/account',name:'归属账户'},
                        {href:'/record',name:'交易记录'}
                        ]},
					// {href:'/manage',name:'账户管理',picnum:'014',identity:'proxy'},
					// {href:'/account',name:'归属账户',picnum:'2',identity:'proxy'},
					// {href:'/record',name:'交易记录',picnum:'8',identity:'proxy',showdown:false,children:[{href:'/record',name:'平仓记录'}]},
                    {href:'/fundDetail',name:'出入金明细',picnum:'6',identity:'proxy'},
                    {href:'/get',name:'获客',picnum:'012',identity:'proxy'},
					
					//user
                    {name:'交易',picnum:'011',identity:'user',showdown:true,children:[
                        {href:'/manage',name:'交易账户'},
                        {href:'/record',name:'交易记录'}
                        ]},
// 					{href:'/manage',name:'账户管理',picnum:'014',identity:'user',},
// 					{href:'/record',name:'交易记录',picnum:'8',identity:'user',showdown:false,children:[{href:'/record',name:'平仓记录'}]},
                    {href:'/fundDetail',name:'出入金明细',picnum:'6',identity:'user'},
					{href:'/get',name:'分享有奖',picnum:'012',identity:'user',},{href:'/get',name:'获客',picnum:'012',identity:'company',},
					
					//account
					{href:'/tasked',name:'财务任务',picnum:'011',identity:'account'},
					//service
					{href:'/task',name:'客服任务',picnum:'011',identity:'service'},
					{name:'用户',picnum:'4',identity:'service',showdown:false,children:[
					    {href:'/user',name:'CRM用户'},
					    {href:'/member',name:'潜在客户',picnum:'4',identity:'service'},
					    {href:'/feed',name:'用户反馈',picnum:'015',identity:'service'},
					    ]},
					{name:'交易',picnum:'011',identity:'service',showdown:false,children:[
					    {href:'/account',name:'交易账户'},
					    {href:'/record',name:'平仓记录'}
					    ]},
					{href:'/push',name:'消息推送',picnum:'9',identity:'service'},
					
					//公共
					{href:'/message',name:'基本信息',picnum:'10',identity:'all',},
				]
			}
		},
		methods: {
			changedown(item) {
				let _this = this;
				if (_this.identity == 'admin') {
                    let list = _this.slidelist;
                    for (let i = 0; i < list.length; i++) {
                    	if (list[i] == item) continue;
                    	list[i].showdown = false;
                    }
                }
				item.showdown = !item.showdown;
			},
			changeSide() {
				let _this = this;
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
					_this.name = user.lastName;
					_this.identity = user.department;
				}

			}
		},
		created() {
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
        padding-right: 10px;
        /* overflow-y: scroll; */
	}
	.user_section{
    	background-color: #2c2b5c;
		padding: 30px 0;
        height: 25%;
    }

	.user_section>img {
		width: 35%;
		margin-bottom: 15px;
	}

	.user_section p {
		font-size: 16px;
		color: #fff;
		line-height: 25px;
	}

	.user_section p img {
		width: 20px;
		margin: 0;
	}

	.linklist {
    	background-color: #2c2b5c;
        min-height: 75%;
	}

	/*.linklist li {
		margin-bottom: 10px;
	}*/

	.linklist li a {
		display: block;
		padding: 5px;
		padding-right: 6px;
		padding-left: 0;
		margin-right: -7px;
	}

	.linklist .item_box {
		display: block;
		/*padding: 5px 8px;*/
		line-height: 34px;
		cursor: pointer;
	}

	.linklist .item_box img {
		width: 8.5%;
		margin-left: 30%;
		margin-top: 8px;
	}

	.linklist .item_box span {
		width: 50%;
		text-align: left;
		color: #bbbaff;
		font-size: 16px;
		margin-left: 5%;
	}

	.linklist li a.router-link-exact-active,
	.linklist li a:hover {
		background-image: url(http://tlb.txasfx.com/crm/img/h_bg_1.png);
		background-repeat: no-repeat;
		background-position: right center;
		background-size: auto 100%;
	}

	.linklist li a.router-link-exact-active .item_box,
	.linklist li a:hover .item_box {
		background-color: #434197;
	}

	.linklist li a.router-link-exact-active span,
	.linklist li a:hover span {
		color: #fff;
	}

	.down_box li {
		margin-bottom: 5px;
	}

	.down_box li a {
		margin-right: -7px;
		padding-right: 6px;
	}

	.down_box .item_box span {
		text-align: center;
		padding: 0;
	}

	.linklist li .item_box_p {
		position: relative;
		line-height: 40px;
	}

	.item_box_p:hover {
		background-color: #434197;
	}

	.item_box_p i {
		position: absolute;
		right: 20px;
		top: 10px;
		color: #bbbaff;
	}
</style>
