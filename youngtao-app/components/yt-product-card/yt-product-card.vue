<template>
	<view class="yt-goods-card" :style="{margin}" @click="onclick">
		<view class="yt-goods-image">
			<image class="image" :src="data.images[0]"></image>
		</view>
		<view class="yt-goods-content">
			<view class="yt-goods-title">
				<text class="yt-goods-title-tip">自营</text>
				<text class="yt-goods-title-text">{{data.title}}</text>
			</view>
			<view class="yt-goods-price">
				<text class="yt-goods-price-original">￥{{data.price}}</text>
				<text class="yt-goods-price-favour">￥{{data.oldPrice}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['margin', 'data'],
		data() {
			return {
			};
		},
		methods: {
			onclick() {
				uni.navigateTo({url: `/pages/product/product?spuId=${this.data.spuId}`});
			}
		}
	}
</script>

<style lang="scss" scoped>
@import "common/css/constant.scss";

.yt-goods-card {
	background-color: #FFF;
	border-radius: $border-radius;
	width: $card-widht;
	.yt-goods-image {
		text-align: center;
		.image {
			border-top-right-radius: $border-radius;
			border-top-left-radius: $border-radius;
			height: 325rpx;
		}
	}
	.yt-goods-content {
		padding: 10rpx 20rpx;
	}
	.yt-goods-title {
		display: -webkit-box;
		overflow: hidden;
		text-overflow: ellipsis;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		.yt-goods-title-tip {
			font-size: 26rpx;
			color: #ffffff;
			background-color: $primary-color;
			padding: 2rpx 10rpx;
			margin-right: 10rpx;
			border-radius: 10rpx;
		}
		.yt-goods-title-text {
		}
	}
	.yt-goods-price {
		margin-top: 10rpx;
		font-size: 28rpx;
		line-height: 1.5;
		position: relative;
		.yt-goods-price-original {
			color: $primary-color;
		}
		.yt-goods-price-favour {
			color: #969696;
			text-decoration: line-through;
			margin-left: 10rpx;
		}
	}
}
</style>
