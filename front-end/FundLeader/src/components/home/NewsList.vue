<template>
		<div class="content_box">
			<my-header :leftOptions="headOption" ></my-header>
            <div class="hui_content" id="hui-content">
			<div class="news_list">
				<template v-for="item in list">
					<router-link :to="'/news-details?id='+item.id">
						<div class="item_box flex_bet">
							<div class="news_text flex_one">
								<span class="title">{{item.title}}</span>
								<div class="foot flex_bet">
									<span class="one">{{item.type}}</span>
									<span class="two">已有{{item.read}}人阅读</span>
									<span class="three" v-text="(item.updateTime+'.').substring(5,19)"></span>
								</div>
							</div>
							<div class="news_img">
								<img :src="item.listImg"></img>
							</div>
						</div>
					</router-link>
					
				</template>
				
			</div>
			
			<p v-show="isNoMore" class="trip_p">没有更多了~~</p>
			
			<div v-show="isloading"  class="trip_box">
				<spinner :type="'ios'" size='30px'></spinner>
			</div>
            
			<div v-show="notrip" class="trip_box" >
				<img src="../../assets/img/no_1.png"/>
				<p>暂无新闻</p>
			</div>
            
            
            </div>
		</div>
</template>

<script>
	import { LoadMore } from 'vux'
	export default {
		data() {
			return {
				headOption: {title: '财经资讯',backText: '',showBack: true},
				notrip: false,
				isNoMore: false,
				isloading: false,
                pageNum: 1,
                list: [],
                totalPage: 1,
			}
		},
		filters: {
			strCut (val) {
				return val.substring(5,19);
			}
		},
		watch: {
			list (val) {
				if (val.length) {
					this.notrip = false;
				}else {
					this.notrip = true;
				}
			}
		},
		components: {
			LoadMore
		},
		methods: {
            handleScroll() {
                let _this = this;
                let div = document.getElementById('hui-content');
                //获取滚动条当前的位置 
                var scrollTop = 0;
                if(div && div.scrollTop) {
                    scrollTop = div.scrollTop;
                } else if(document.body) {
                    scrollTop = document.body.scrollTop;
                }
                
                //获取当前可视范围的高度 
                var clientHeight = 0;
                if(document.body.clientHeight && div.clientHeight) {
                    clientHeight = Math.min(document.body.clientHeight, div.clientHeight);
                } else {
                    clientHeight = Math.max(document.body.clientHeight, div.clientHeight);
                }
                //获取文档完整的高度 
                let docHeight = Math.max(document.body.scrollHeight, div.scrollHeight);
                
                if(scrollTop + clientHeight == docHeight) {
                    
                    // console.log('到底了')
                    _this.pageNum++;
                    _this.initPage();
                    
                }
                
            },
			initPage () {
				let _this = this;
                if (_this.isloading) return;
                if (_this.pageNum > _this.totalPage) {
                    _this.isNoMore = true;
                    return;
                }
                
				_this.isloading = true;
				let datas = {
					page_number: _this.pageNum,//	是	int	当前页
					page_size: 10,//	是	int	每页大小
					formParams: {//	是	json	查询条件
						orderByColumn: 'updateTime',
						sort: 'DESC'
					}
				}
				_this.$post("/api/hpp_news/pageList",datas).then((res) => {
					// console.log(res)
					_this.isloading = false;
					_this.list = _this.list.concat(res.data.list);
                    _this.totalPage = res.data.totalPage;
					
				})
			}
		},
        mounted() {
			this.initPage();
        	
        },
		activated () {
            document.getElementById('hui-content').addEventListener("scroll", this.handleScroll);
            
		}
	}
</script>

<style scoped>
	.news_box {
		background-color: #fff;
		padding-bottom: 0.5rem;
	}
	.news_header {
		line-height: 0.9333rem;
		border-bottom: 0.0133rem solid #f4f4f4;
		margin-bottom: 0.0666rem;
		box-sizing: border-box;
		padding-left: 0.2666rem;
	}
	.news_header div {
		font-size: 0.4rem;
		color: #333;
	}
	.news_header div.shutline {
		height: 0.0666rem;
		width: 1.55rem;
	}
	
	
	.news_list .item_box {
		padding: 0.3333rem;
        border-bottom: 2px solid #fff;
	}
    .news_list  a {
        display: block;
        background-color: #fff;
    }
    .news_list  a:visited{
        background-color: #f8f8f8;
    }
	.news_text {
		height: 1.6rem;
		position: relative;
		padding-right: 0.2666rem;
	}
	.news_text span.title {
		font-size: 0.33rem;
		color: #666;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 3;
	}
	.news_text .foot {
		position: absolute;
		bottom: 0;
		width: 100%;
		box-sizing: border-box;
		padding-right: 0.4666rem;
	}
	.news_text .foot span {
		font-size: 0.2933rem;
	}
	.news_text span.three {
		color: #999;
	}
	.news_text span.one {
		color: #e51534;
	} 
	.news_text span.two {
		color: #ccc;
	} 
	.news_img {
		width: 2.4266rem;
		height: 1.6rem;
		border-radius: 0.1rem;
		overflow: hidden;
	}
	.news_img img {
		width: 100%;
		min-height: 100%;
	}
	
	.no_more {
		font-size: 10px;
		color: #D0D0D0;
		padding: 20px 0;
		text-align: center;
	}
</style>