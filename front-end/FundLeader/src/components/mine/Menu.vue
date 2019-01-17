<template>
	<div class="flex_wrap">
		<div class="item_box">
			<router-link to="/myjoin">
				<div>
					<div class="img_box">
						<img src="../../assets/img/f_m_01.png" alt="">
					</div>
					<p>我的合伙</p>
				</div>
			</router-link>
		</div>
		<div class="item_box border_grad_up">
			<router-link to="/fund-details">
				<div>
					<div class="img_box">
						<img src="../../assets/img/f_m_02.png" alt="">
					</div>
					<p>资金明细</p>
				</div>
			</router-link>
		</div>

		<div class="item_box border_grad_left">
			<router-link to="/recharge">
				<div>
					<div class="img_box">
						<img src="../../assets/img/f_m_03.png" alt="">
					</div>
					<p>我要充值</p>
				</div>
			</router-link>
		</div>
		<div class="item_box border_left border_grad_right">
			<router-link to="/withdraw">
				<div>
					<div class="img_box">
						<img src="../../assets/img/f_m_04.png" alt="">
					</div>
					<p>我要提现</p>
				</div>
			</router-link>
		</div>
		<div class="item_box no_border_left">
			<router-link to="/account">
				<div>
					<div class="img_box">
						<img src="../../assets/img/f_m_05.png" alt="">
					</div>
					<p>账户安全</p>
				</div>
			</router-link>
		</div>
		<div class="item_box border_grad_down">
			<router-link to="/about">
                <div>
				<div class="img_box">
					<img src="../../assets/img/f_m_06.png" alt="">
				</div>
				<p>关于我们</p>
                </div>
			</router-link>
		</div>

	</div>
</template>

<script>
	export default {
		name: 'minemenu',
		data() {
			return {

			}
		},
		computed: {},
		components: {

		},
		methods: {},
		mounted() {},
		activated() {}
	}
</script>

<style scoped>
	.item_box {
		width: 50%;
		padding: 0.533rem 0;
		background-color: #fff;
	}

	.border_left {
		border-left: 1px solid #bfbfbf;
	}

	.border_grad_up {
		border-left: 1px solid #bfbfbf;
		border-image: -webkit-linear-gradient(bottom, #bfbfbf, #fff) 30 30 stretch;
		border-image: -moz-linear-gradient(bottom, #bfbfbf, #fff) 30 30 stretch;
		border-image: linear-gradient(bottom, #bfbfbf, #fff) 30 30 stretch;
	}

	.border_grad_down {
		border-left: 1px solid #bfbfbf;
		border-image: -webkit-linear-gradient(top, #bfbfbf, #fff) 30 30 stretch;
		border-image: -moz-linear-gradient(top, #bfbfbf, #fff) 30 30 stretch;
		border-image: linear-gradient(top, #bfbfbf, #fff) 30 30 stretch;
	}


	.border_grad_left {
		border-top: 1px solid #bfbfbf;
		border-bottom: 1px solid #bfbfbf;
		border-image: -webkit-linear-gradient(right, #bfbfbf, #fff) 30 30 stretch;
		border-image: -moz-linear-gradient(right, #bfbfbf, #fff) 30 30 stretch;
		border-image: linear-gradient(right, #bfbfbf, #fff) 30 30 stretch;
	}

	.border_grad_right {
		border-top: 1px solid #bfbfbf;
		border-bottom: 1px solid #bfbfbf;
		border-image: -webkit-linear-gradient(left, #bfbfbf, #fff) 30 30 stretch;
		border-image: -moz-linear-gradient(left, #bfbfbf, #fff) 30 30 stretch;
		border-image: linear-gradient(left, #bfbfbf, #fff) 30 30 stretch;
	}

	.img_box {
		width: 0.866rem;
		height: 0.866rem;
		margin: 0 auto;
	}

	.img_box img {
		border-radius: 0;
	}

	.item_box p {
		text-align: center;
		font-size: 0.32rem;
		color: #202966;
		margin-top: 0.266rem;
	}
</style>
