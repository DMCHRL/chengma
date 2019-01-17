<template>
	<div class="page_box">
		<fund-header :leftOptions="headOption"></fund-header>

		<div class="body_box box_pt150 box_pb0" id="my-box">

			<div class="con_box">

				<div class="sec_1">
                    <div class="item_box flex_bet flex_align_center">
                    	
                    		<i class="iconfont icon-right-bottom-triangle"></i>
                    		<input class="flex_one" type="text" value="" placeholder="请输入常用邮箱" v-model="datas.email" />
                    	
                    </div>
				</div>
                
                
                <sub-btn :text="subbtn.text" :disabled="subbtn.disabled" @clickme="Submit"></sub-btn>
                
			</div>


		</div>

	</div>
</template>

<script>
	export default {
		directives: {},
		data() {
			return {
				headOption: {
					title: '绑定邮箱',
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
                }
			}
		},
		filters: {

		},
		computed: {},
		components: {

		},
		methods: {
            async Submit() {
            	try {
            		if (this.datas.email == '') {
            			this.$vux.toast.text('请先填写正确的邮箱', 'middle')
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
        margin-bottom: 300px;
    }

    .item_box {
        margin: 0 0.346rem;
        border-bottom: 1px solid #cccccc;
        height: 1.066rem;
    }

    .item_box input {
        font-size: 0.35rem;
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


</style>
