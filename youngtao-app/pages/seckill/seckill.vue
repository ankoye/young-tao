<template>
	<view>
		<view>
			<!-- 导航栏 -->
			<u-navbar class="navbar-wrap" :border-bottom="false">
				<view class="items-center">
					<view class="mr-3">活动大厅</view>
					<u-search class="search-bar" placeholder="search" :show-action="false"></u-search>
				</view>
				<view slot="right" class="items-center">
					<text class="iconfont icon-huaban"></text>
				</view>
			</u-navbar>
			<!-- 标签 -->
			<u-sticky :enable="stickyEnable" :offset-top="stickyTop">
				<u-tabs-swiper activeColor="#dd5d7b" ref="tabs" :list="tabs" :current="0" :is-scroll="false" swiperWidth="750"></u-tabs-swiper>
			</u-sticky>
			<!-- 轮播图 -->
			<view class="module-bottom">
				<u-swiper :list="swiperList" height="300" border-radius="0">
				</u-swiper>
			</view>
			<view class="yt-container">
				<!-- 时间段 -->
				<view class="time-wrap">
					<view class="time-item" :class="{'active' : activeIdx==0}" @click="switchMenu(0)">
						<view class="hot-title">热卖中</view>
						<view class="item-text">不能错过</view>
					</view>
					<template v-if="menuList.length > 0">
						<view v-for="idx in menuList.length-1" class="time-item" :key="idx"
							:class="{'active' : activeIdx==idx}" @click="switchMenu(idx)">
							<view class="item-title">{{menuList[idx]}}:00</view>
							<view class="item-text">即将开始</view>
						</view>
					</template>
				</view>
				<!-- 商品 -->
				<view class="product-wrap">
					<view v-for="product in productList" class="module-bottom">
						<yt-seckill-product-card :data="product"></yt-seckill-product-card>
					</view>
				</view>
			</view>
		</view>
		<!-- 底部导航栏 -->
		<u-tabbar v-model="current" :list="tabbarList"></u-tabbar>
	</view>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import { getTimeMenu, listByTime } from '@/api/gsc/product.js'

@Component
export default class Seckill extends Vue {
	private menuList = [];
	private activeIdx = 0;
	private productList = [];
	
	private current = 0;
	private scrollTop = 100;
	private stickyEnable = true;
	private stickyTop = 0;
	// #ifdef MP-WEIXIN
	private stickyTop = 130;
	// #endif
	private tabs = [
		{ name: '首页' },
		{ name: '女装' },
		{ name: '数码' },
		{ name: '电脑办公' },
		{ name: '个人清洁' },
		{ name: '男装' },
		{ name: '母婴童装' },
		{ name: '运动' },
		{ name: '美妆' }
	];
	private swiperList = [{
			image: 'https://cdn.uviewui.com/uview/swiper/1.jpg',
			title: '昨夜星辰昨夜风，画楼西畔桂堂东'
		}, {
			image: 'https://cdn.uviewui.com/uview/swiper/2.jpg',
			title: '身无彩凤双飞翼，心有灵犀一点通'
		}, {
			image: 'https://cdn.uviewui.com/uview/swiper/3.jpg',
			title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
		}
	];
	private tabbarList = [{
			iconPath: "home",
			selectedIconPath: "home-fill",
			text: '活动大厅',
			customIcon: false,
		},
		{
			iconPath: "photo",
			selectedIconPath: "photo-fill",
			text: '樱桃秒杀',
			customIcon: false,
		},
		{
			iconPath: "play-right",
			selectedIconPath: "play-right-fill",
			text: '品牌秒杀',
			customIcon: false,
		},
		{
			iconPath: "account",
			selectedIconPath: "account-fill",
			text: '爆款商品',
			isDot: false,
			customIcon: false,
		}];
	
	onLoad() {
		this.initPage()
	}
	onShow() {
		this.stickyEnable= true
	}
	onHide() {
		this.stickyEnable= false
	}
	
	private initPage() {
		getTimeMenu().then(res => {
			this.menuList = []
			for (const menu of res.data) {
				this.menuList.push(menu.substring(menu.length-2));
			}
			this.appendProductList(0, 1, 10);
		});
	}
	
	private appendProductList(idx, page, size) {
		listByTime(idx, page, size).then(res => {
			this.productList.push(...res.data);
		})
	}
	
	private switchMenu(idx) {
		this.activeIdx = idx;
		this.productList = [];
		this.appendProductList(idx, 1, 10);
	}

}
</script>

<style lang="scss" scoped>
.navbar-wrap {
	.iconfont {
		font-size: 50rpx;
		margin: 0 20rpx;
	}
}
.time-wrap {
	display: flex;
	justify-content: space-between;
	align-items: center;
	text-align: center;
	margin-bottom: $module-margin;
	.time-item {
		.hot-title {
			font-size: 26rpx;
		}
		.item-title {
			font-size: 30rpx;
		}
		.item-text {
			margin-top: 6rpx;
			font-size: 20rpx;
		}
	}
	.active {
		color: #ef3520;
		.item-text {
			color: #fff;
			background-color: #fa154a;
			padding: 1rpx 10rpx;
			border-radius: 100rpx;
		}
	}
}
</style>
