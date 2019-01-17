<template>
	<div class="page_box">
		<fund-header :leftOptions="headOption"></fund-header>

		<div class="body_box box_pt150 box_pb0" id="my-box">

			<div class="con_box">

				<div class="sec_1">
                    <div class="item_box flex_bet flex_align_center" v-if="userMess.password == '1'">
                    	
                    		<i class="iconfont icon-right-bottom-triangle"></i>
                    		<input class="flex_one" type="password" value="" placeholder="请输入原密码" v-model="datas.oldPassword" />
                    	
                    </div>
                    <div class="item_box flex_bet flex_align_center">
                    	
                    		<i class="iconfont icon-right-bottom-triangle"></i>
                    		<input class="flex_one" type="password" value="" placeholder="请输入支付密码" v-model="datas.password" />
                    	
                    </div>
                    <div class="item_box flex_bet flex_align_center">
                    	
                    		<i class="iconfont icon-right-bottom-triangle"></i>
                    		<input class="flex_one" type="password" value="" placeholder="请再次输入" v-model="rePassword" @change="isErr = false" />
                    	
                    </div>
                    
                    <div class="err_box" v-show="isErr">
                        <p>两次密码输入不一致，请重新输入。</p>
                    </div>
				</div>
                
                <sub-btn :text="subbtn.text" :disabled="subbtn.disabled" @clickme="Submit"></sub-btn>
                
			</div>


		</div>

	</div>
</template>

<script>
    import { mapState, mapActions	} from 'vuex'
	export default {
		directives: {},
		data() {
			return {
				headOption: {
					title: '支付密码',
					backText: '',
					showBack: true,
					text: '为了您的账户安全，建议您完善以下设置'
				},
                subbtn: {
                	text: '确认提交',
                	disabled: false
                },
                datas: {
                	headImg: '',//	否	string	头像
                	name: '',//	否	string	昵称
                	idPositive: '',//	否	string	身份证正面
                	idNegative: '',//	否	string	身份证反面
                	email: '',//	否	string	邮箱
                	password: '',//	否	string	交易密码
                	cid: '',//	否	string	个推识别码
                    oldPassword: '',
                },
                isErr: false,
                rePassword: '',
                oldPass: ''
			}
		},
		filters: {

		},
		computed: {
            ...mapState(['userMess']),
        },
		components: {

		},
		methods: {
            async Submit() {
            	try {
                    if (this.userMess.password == '1') {
                        if (this.datas.oldPassword == '') {
                            this.$vux.toast.text('请先输入原密码', 'middle')
                            return;
                        }
                        
                    }
            		if (this.datas.password == '') {
            			this.$vux.toast.text('请先输入密码', 'middle')
            			return;
            		}
                    if (this.datas.password != this.rePassword) {
                    	this.isErr = true;
                    	return;
                    }
            		const res = await this.fetch("/api/user/updateUserInfo", this.datas, "POST");
            		console.log(res)
            		if (res.statusCode == '0000') {
            			this.$vux.toast.text('密码设置成功', 'middle')
            			this.datas = {
            				headImg: '',//	否	string	头像
            				name: '',//	否	string	昵称
            				idPositive: '',//	否	string	身份证正面
            				idNegative: '',//	否	string	身份证反面
            				email: '',//	否	string	邮箱
            				password: '',//	否	string	交易密码
            				cid: '',//	否	string	个推识别码
            				oldPassword: '',
            			}
                        this.rePassword = '';
            			this.$router.go(-1)
            		} else {
            			this.$vux.toast.text(res.msgCode, 'middle')
            		}
            	} catch (err) {
            		console.log('获取数据失败', err);
            	}
            },
		},
		activated() {

		}
	}
</script>

<style scoped>
    
	.con_box {
        background-color: #f8f9fd;
        height: 100%;
    }
    
    .sec_1 {
        margin-bottom: 260px;
    }

    .item_box {
        margin: 0 0.346rem;
        border-bottom: 1px solid #cccccc;
        height: 1.066rem;
    }

    .item_box input {
        font-size: 0.32rem;
        color: #666;
        width: 100%;
        height: 100%;
        padding: 0 0.266rem;
    }

    .item_box i {
        color: #fe9f02;
        font-size: .3rem;
        margin-right: 0.1rem;
    }
    
    .err_box {
        margin-top: 0.4rem;
        text-align: center;
        color: #FF0000;
        font-size: .35rem;
    }


</style>
