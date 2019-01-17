<template>
	<div class="page_box">
		<my-header :leftOptions="headOption"></my-header>

		<div class="body_box box_pb0" id="my-box">

			<div class="con_box">
        
          <div class="art_body" v-html="datas.context"></div>
          
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
					title: '协议',
					backText: '',
					showBack: true,
					text: '一款有态度的APP'
				},
        datas: {
          context: ''
        }
			}
		},
		filters: {

		},
		computed: {
      type () {
        return this.$route.query.type
      }
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
  .sec_2 p {
    text-align: center;
    font-size: 0.3rem;
    color: #202a67;
    padding: 5px 20px;
  }
  
  
</style>
