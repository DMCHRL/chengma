<template>
	<div class="page_box">
		<my-header :leftOptions="headOption"></my-header>

		<div class="body_box box_pb0" id="my-box">

			<div class="con_box">

				<div class="sec_1">
            <div class="img_box">
              <img src="../assets/img/logo_01.png" alt="">
            </div>
				</div>
        <div class="sec_2">
          
          <h3><i></i>简介<i></i></h3>
          <div class="art_body" v-html="datas.context"></div>


          <h3><i></i>联系我们<i></i></h3>
          <p>在线客服QQ: <a :href="'mqqwpa://im/chat?chat_type=wpa&uin='+ datas.qq +'&version=1&src_type=web&web_src=oicqzone.com'">{{datas.qq}}</a></p>
          <p>电话：<a :href="'tel:'+datas.phone">{{datas.phone}}</a> </p>
        </div>

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
					title: '关于我们',
					backText: '',
					showBack: true,
					text: '一款有态度的APP'
				},
        type: 'ABOUT_US',
        datas: {}
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
      // ...mapActions(['getUserMess']),
			async initData() {
				try {
					const res = await this.fetch("/api/protocol/getType/" + this.type);
					console.log(res)
					if (res.statusCode == '0000') {
						this.datas = res.data;
            this.headOption.title = res.data.typeDesc;
					} else {
						this.datas = {};
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}
			},
		},
		activated() {
      this.initData();
		}
	}
</script>

<style scoped>
	.sec_1 {
    padding: 0.5rem 0;
  }
  .img_box {
    width: 2.5rem;
    height: 2.5rem;
    overflow: hidden;
    border-radius: 50%;
    margin: 0 auto;
  }
  .sec_2 h3 {
    font-size: 0.373rem;
    color: #202a67;
    text-align: center;
    line-height: 0.666rem;
  }
  .sec_2 i {
    display: inline-block;
    width: 8px;
    height: 8px;
    border-radius: 8px;
    background-color: #202a67;
    margin: 0 6px;
    position: relative;
    top: -1.5px;
  }
  .sec_2 p{
    text-align: center;
    font-size: 0.3rem;
    color: #202a67;
    padding: 5px 20px;
  }
  .sec_2 p a {
    font-size: 0.3rem;
    color: #202a67;
  }
  
  .art_body>p{
    text-indent: 2em;
    text-align: center;
    padding: 0 20px;
    color: #202a67 !important;
    font-size: 0.3rem !important;
  }
</style>
