<template>
	<div class="content_box">
		<div class="sec_1">
			<div class="item_box item_box_1 flex_bet flex_align_center">
				<div class="icon_box">
					<img src="../../assets/img/8_04.png"/>
				</div>
				<div class="inp_box flex_one">
					<select name="" v-model="type">
						<option value="1">初级</option>
						<option value="2">中级</option>
						<option value="3">初中级联报</option>
					</select>
				</div>
			</div>
			
			<div class="item_box flex_bet flex_align_center">
				<div class="icon_box">
					<img src="../../assets/img/8_01.png"/>
				</div>
				<div class="inp_box flex_one">
					<input type="number" name="" id="" value="" placeholder="请输入手机号" />
				</div>
			</div>
			<div class="item_box flex_bet flex_align_center">
				<div class="icon_box">
					<img src="../../assets/img/8_02.png"/>
				</div>
				<div class="inp_box flex_one">
					<input type="text" name="" id="" value="" placeholder="请输入姓名" />
				</div>
			</div>
			<div class="item_box flex_bet flex_align_center">
				<div class="icon_box">
					<img src="../../assets/img/8_03.png"/>
				</div>
				<div class="inp_box flex_one">
					<input type="text" name="" id="" value="" placeholder="请输入邮箱" />
				</div>
			</div>
			
		</div>
		<div class="bottom_btn">
			<button class="myback">确认</button>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				type: 1
			}
		},
		components: {
			
		},
		methods: {
			
		},
		mounted () {
			this.type = this.$route.query.type
		}
	}
</script>

<style scoped>
	.content_box {
		height: 100%;
		background-color: #fff;
	}
	.sec_1 {
		background-color: #fff;
	}
	.item_box {
		height: 1.2rem;
	}
	.item_box_1 {
		height: 1.4rem;
		border-bottom: .2rem solid #f5f5f5;
	}
	.icon_box img {
		width: 0.6666rem;
		height: 0.6666rem;
		margin: 0 0.4666rem;
	}
	.inp_box {
		height: 100%;
		border-bottom: 1px solid #ededed;
	}
	.inp_box input,
	.inp_box select{
		height: inherit;
		width: 100%;
		color: #666;
		background-color: transparent;
		border: none;
	}
	
	.bottom_btn_1 {
		padding: 1rem 0;
		text-align: center;
		background-color: #fff;
	}
	.bottom_btn_1 button {
		border-radius: 0.5333rem;
		color: #fff;
		font-size: 0.3466rem;
		padding: 0.2rem 1.0666rem;
	}
	
	
</style>