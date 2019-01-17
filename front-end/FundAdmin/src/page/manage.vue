<template>
	<div class="manage_page fillcontain">
		<el-row style="height: 100%;">
	  		<el-col :span="3"  style="min-height: 100%; background-color: #324057;">
                <div class="logo_box">
                    <img src="../assets/img/logo_01.png" alt="">
                </div>
				<el-menu :default-active="defaultActive" style="min-height: 100%;" theme="dark" router>
					<el-menu-item index="manage"><i class="el-icon-menu"></i>首页</el-menu-item>
					<el-submenu index="2">
						<template slot="title"><i class="iconfont icon-fund"></i>基金管理</template>
						<el-menu-item index="fundList">基金列表</el-menu-item>
						<el-menu-item index="addFund">创建基金</el-menu-item>
						
					</el-submenu>
					<el-submenu index="3">
						<template slot="title"><i class="iconfont icon-user"></i>用户管理</template>
                        <el-menu-item index="userlist">用户列表</el-menu-item>
						<el-menu-item index="addPowerUser">添加发起人/资产管理人</el-menu-item>
						<el-menu-item index="addAdminUser">添加系统用户</el-menu-item>
					</el-submenu>
                    
					<el-submenu index="4">
						<template slot="title"><i class="iconfont icon-money"></i>资金管理</template>
						<el-menu-item index="flowRecord">出入金记录</el-menu-item>
					</el-submenu>
					<el-submenu index="5">
						<template slot="title"><i class="iconfont icon-account"></i>交易账户</template>
						<el-menu-item index="accountList">账户列表</el-menu-item>
						<el-menu-item index="addAccount">添加交易账户</el-menu-item>
					</el-submenu>
					<el-submenu index="6">
						<template slot="title"><i class="iconfont icon-receipt"></i>收款管理</template>
						<el-menu-item index="receiptList">收款账户</el-menu-item>
						<el-menu-item index="addReceiptAccount">新增收款账户</el-menu-item>
					</el-submenu>
          <el-submenu index="7">
          	<template slot="title"><i class="iconfont icon-banner"></i>轮播图管理</template>
          	<el-menu-item index="banner">轮播图展示</el-menu-item>
          	<el-menu-item index="addBanner">新增轮播图</el-menu-item>
          </el-submenu>
          <el-submenu index="8">
          	<template slot="title"><i class="iconfont icon-agree"></i>协议管理</template>
          	<el-menu-item index="agreement">协议展示</el-menu-item>
          	<el-menu-item index="addAgreement">新增协议</el-menu-item>
          </el-submenu>
          
                   
				</el-menu>
			</el-col>
			<el-col :span="21" style="height: 100%;overflow: auto;">
				<keep-alive>
				    <router-view></router-view>
				</keep-alive>
			</el-col>
		</el-row>
  	</div>
</template>

<script>
    export default {
		computed: {
			defaultActive: function(){
				return this.$route.path.replace('/', '');
			}
		},
    }
</script>


<style lang="less" scoped>
	@import '../style/mixin';
	.manage_page{
		
	}
    i.iconfont {
        margin: 5px;
        font-size: 18px;
    }
    .logo_box {
        margin: 30px 0;
        padding: 5px 0;
        text-align: center;
        background-color: rgba(236, 239, 255, 1);
    }
    .logo_box img {
        width: 80%;
    }
</style>
