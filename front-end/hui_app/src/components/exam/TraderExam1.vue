<template>
	<div class="content_box">
		<div class="banner_box">
			<img src="http://www.txasfx.com/hui/img/banner_2.png"/>
		</div>
		<div class="sec_box sec_1">
			<div class="part_1">
				<div class="back"></div>
				<div class="content">
					<h3>考试介绍</h3>
					<p>12.15--12.16（暂定）</p>
					<p>上海市</p>
				</div>
			</div>
			<div class="part_2">
				<h4>证书介绍</h4>
				<p>由“国家人力资源和社会保障部与中国职工教育和职业培训协会”认证；是考核中国金融机构就业人员职业能力等级的行业标准，是用人单位对从业人员录用任命，专业能 力评测，晋升考核的重要标准，是未来中国金融业从业人员上岗的必备证件之一；由广东国政职业理财培训学校负责编写教材并制定考试标准。</p>
			</div>
			<div class="part_3">
				<h4>缴费介绍</h4>
				<ul class="flex_around">
					<li class="backimg4 flex_col">
						<span>3800/人</span>
						<span>初级证书</span>
					</li>
					<li class="backimg4 flex_col">
						<span>6800/人</span>
						<span>中级证书</span>
					</li>
					<li class="backimg4 flex_col">
						<span>9800/人</span>
						<span>联报初中级证书</span>
					</li>
				</ul>
			</div>
		</div>
		<div class="sec_2">
			<h3>注意事项</h3>
			<!--<p>1、购买后请填写报考人相关信息。<router-link to="/toexam">点击填写>></router-link></p>-->
			<p>1、未取得中国金融交易师初级考试合格证书者无法参加中国金融交易师中
      级考试。</p>
			<p>2、报名后有3次考试机会，1次正考，2次补考，补考需缴纳补考费280元/
      次，3次不及格需重新缴纳报名费参加考试。</p>
			<p>3、初中级联考的首次初级和首次中级考试免费，另初级和中级分别有2次
      补考机会，补考需要缴纳补考费280元/次，初级3次不及格则无法参加
      中级考试，需重新报名缴费。</p>
			<p>4、报名了考试的用户，如想报名资本交易大学堂线下课程只需补缴相应差
      价，具体可咨询客服。客服QQ：244198084。</p>
			<p>5、考试费用一经缴纳，不可退款，请谨慎购买。</p>
			<p>6、最终解释权归和讯所有。</p>
		</div>
		<div class="bottom_btn flex_row flex_align_center myback2">
			<div class="">
				<router-link to="/toexam?type=1">报名初级</router-link>
			</div>
			<div class="">
				<router-link to="/toexam?type=2">报名中级</router-link>
			</div>
			<div class="">
				<router-link to="/toexam?type=3">初级中级联报(优惠)</router-link>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		components: {
		},
		methods: {
			
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	.banner_box img {
		width: 100%;
	}
	.sec_box {
		background-color: #fff;
	}
	.sec_1 {
		border: 0.0266rem solid #e41032;
		border-radius: 0.1333rem;
		margin: 0.2666rem;
		box-shadow: 2px 7px 3px #d4d4d4 ;
		text-align: center;
		padding-bottom: 0.6rem;
	}
	.part_1 {
		background-color: #fff;
		position: relative;
		overflow: hidden;
		height: 2.1rem;
	}
	.part_1:before {
		content: '';
		display: block;
		height: 1.2rem;
		background-color: #e41032;
	}
	.part_1 .back{
	    display:block;
	    width:0;
	    height:0;
	    /*background-color: #e41032;*/
	    border-width:0.8266rem 4.75rem 0;
	    border-style:solid;
	    border-color:#e41032 transparent transparent;/*黄 透明 透明 */
	    position:absolute;
	    top:1.2rem;
	    left:0px;
	    padding-top: 1.3333rem;
	}
	.part_1 .content {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		z-index: 3;
	}
	
	.part_1 h3 {
		font-size: 0.5733rem;
		color: #fff;
		font-family: "宋体";
		line-height: 0.9rem;
	}
	.part_1 p {
		font-size: 0.2933rem;
		color: #fff;
		line-height: 0.5333rem;
	}
	.part_2 h4,
	.part_3 h4 {
		font-size: 0.32rem;
		color: #333;
		line-height: 0.8rem;
	}
	.part_2 p {
		font-size: 0.32rem;
		color: #666;
		text-align: left;
		padding: 0 0.2666rem;
	}
	.part_3 ul {
		padding: 0 0.2rem;
	}
	.part_3 li {
		width: 2.9333rem;
		height: 1.8666rem;
		box-sizing: border-box;
		padding: 0.28rem 0;
	}
	.part_3 li span {
		font-size: 0.5rem;
		color: #fff;
		font-family: "宋体";
		line-height: 0.6666rem;
	}
	.part_3 li span:last-of-type {
		font-size: 0.3466rem;
		color: #fff;
	}
	
	.sec_2 {
		padding: 0.2666rem;
	}
	.sec_2 p {
		font-size: 0.2933rem;
		color: #999;
	}
	.sec_2 h3 {
		font-size: 0.32rem;
		color: #666;
		line-height: 1rem;
	}
	.sec_2 a {
		font-size: 0.29rem;
		color: #007AFF;
	}
	.bottom_btn {
		height: 50px;
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		box-sizing: border-box;
		padding: 0.1333rem 0;
	}
	.bottom_btn div {
		width: 30%;
		text-align: center;
		border-right: 1px solid #fff;
		line-height: 40px;
	}
	.bottom_btn div a {
		color: #fff;
		font-size: 0.4rem;
	}
	.bottom_btn div:active {
		background-color: #DD6161;
	}
	.bottom_btn div:last-of-type {
		width: 40%;
		border: none;
	}
</style>