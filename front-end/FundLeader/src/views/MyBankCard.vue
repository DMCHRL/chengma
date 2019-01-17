<template>
	<div class="page_box">
		<my-header :leftOptions="headOption">
      <div slot="right-box">
      	<router-link to="/addbankcard">
      		 <x-icon type="ios-plus-empty" size="30"></x-icon>
      	</router-link>
      </div>
    </my-header>

		<div class="body_box box_pb0" id="my-box">

			<div class="con_box">

				<ul>
					<template v-for="(item,index) in list">
						<li :key="index" class="item_box">
							<swipeout>
								<swipeout-item transition-mode="follow">
									<div slot="content" class="card_box" :style="{backgroundImage:item.color == 'red'? back1:back2}">
										<p>{{item.bankNum}}</p>
										<span>{{item.bank}}</span>
									</div>
									<div slot="right-menu">
										<swipeout-button type="warn">
											<div class="delete_btn flex_align_center flex_center" @click="Delete(item.id,index)">
												删除
											</div>
										</swipeout-button>
									</div>
								</swipeout-item>
							</swipeout>
						</li>
					</template>
				</ul>

        <p class="trip_p" v-show="!list.length">暂无绑定银行卡</p>
			</div>


		</div>

	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
	import {
		Swipeout,
		SwipeoutItem,
		SwipeoutButton,
		XButton,
		Icon,
		TransferDomDirective as TransferDom
	} from 'vux'
	export default {
		directives: {},
		data() {
			return {
				headOption: {
					title: '我的银行卡',
					backText: '',
					showBack: true,
					text: '一款有态度的APP'
				},
				back1: "url(" + require('../../static/img/f_rec_01.png') + ")",
				back2: "url(" + require('../../static/img/f_rec_02.png') + ")",
				list: []
			}
		},
		filters: {

		},
		computed: {
			...mapState(['userMess']),
		},
		components: {
			Swipeout,
			SwipeoutItem,
			SwipeoutButton,
			XButton,
			Icon,
		},
		methods: {
			// ...mapActions(['getUserMess']),
      async Delete(id,index) {
        try {
          const res = await this.fetch("/api/bank_info/delete/"+id)
          console.log(res)
          if (res.statusCode == '0000') {
            this.$vux.toast.text('删除成功', 'middle')
            this.list.splice(index,1)
          } else {
            this.$vux.toast.text(res.msgCode, 'middle')
          }
        } catch (err) {
          console.log('获取数据失败', err);
        }

      },
			async loadMyCard() {
				try {
					const res = await this.fetch("/api/bank_info/loadMyBank")
					console.log(res)
					if (res.statusCode == '0000') {
						let list = res.data.list;
						if (list.length) {
							list.forEach((item, index) => {
								item.color = index % 2 == 0 ? 'red' : 'green'
							})
						}
						this.list = list;

					} else {
						console.log('获取数据失败', res)
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}

			}
		},
		activated() {
			this.loadMyCard();
		}
	}
</script>

<style scoped>
	.card_box {
			height: 3.5rem;
			padding-top: 1.2rem;
			padding-left: .7rem;
			background-size: 100% 100%;
			background-repeat: no-repeat;
			border-radius: 5px;
      margin: 0.2rem 0.3rem;
		}
	
		.card_box p {
			color: #fff;
			font-size: 0.4rem;
		}
	
		.card_box span {
			color: #fff;
			font-size: 0.36rem;
		}
    .vux-x-icon {
      fill: #fff;
      position: relative;
      top: -5px;
    }
    .delete_btn {
      height: 100%;
    }
</style>
