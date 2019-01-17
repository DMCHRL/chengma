<template>
	<div class="sec_card">
		<div class="top flex_bet flex_align_center">
			<span>{{title}}</span>
			<button>
				<i class="iconfont icon-sjxdown"></i>
				银联卡
			</button>
		</div>
		<div class="card_box">
			<template v-for="(item,index) in list">

				<div class="item_box" :key="index" :style="{
                        backgroundImage:item.color == 'red'? back1:back2,
                        top:index*30+'px',
                        right: index*30+'px',
                        zIndex: index}"
				 @click="changeCard(item,index)">

					<p>{{item.bankNum}}</p>
					<span>{{item.bank}}</span>
				</div>

			</template>
		</div>
		<div class="bottom">
			<router-link to="/addBankCard">
				<span>+添加新银行卡</span>

			</router-link>
		</div>
	</div>
</template>

<script>
	import {
		XButton
	} from 'vux'
	export default {
		name: 'MyCard',
		props: {
			title: {
				type: String
			}
		},
		data() {
			return {
				back1: "url(" + require('../../../static/img/f_rec_01.png') + ")",
				back2: "url(" + require('../../../static/img/f_rec_02.png') + ")",
				list: []
			}
		},
		computed: {},
		components: {
			XButton
		},
		methods: {
			changeCard(card, index) {
				let list = this.list;
				list.splice(index, 1)
				list.push(card)
				this.list = list;
        this.$emit("initCard",card)
			},
			ClickMe() {
				this.$emit("clickme")
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
            this.$emit("initCard",list[list.length-1])
					} else {
						console.log('获取数据失败', res)
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}

			}
		},
		mounted() {
			console.log(1)
		},
		activated() {
			this.loadMyCard();
		}

	}
</script>

<style scoped>
	.top,
	.bottom {
		padding: 20px;
	}

	.top span,
	.bottom span {
		font-size: 0.373rem;
		color: #202966;
	}

	.top button {
		font-size: 0.373rem;
		color: #202966;
		border: 1px solid #202966;
		border-radius: 20px;
		padding: 0.133rem 0.466rem;
		background-color: transparent;
	}

	.top button i {
		margin-right: 0.2rem;
		color: #ea9908;
	}

	.bottom {
		text-align: right;
	}

	.card_box {
		margin-right: 30px;
		padding: 20px;
		position: relative;
		height: 5.5rem;
	}

	.item_box {
		position: absolute;
		width: 6.96rem;
		height: 4.2rem;
		padding-top: 1.866rem;
		padding-left: .7rem;
		background-size: 100% 100%;
		background-repeat: no-repeat;
		border-radius: 0.2rem;
	}

	.item_box p {
		color: #fff;
		font-size: 0.4rem;
	}

	.item_box span {
		color: #fff;
		font-size: 0.36rem;
	}
</style>
