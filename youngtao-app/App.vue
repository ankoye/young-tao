<script>
export default {
	onLaunch: function() {
		console.log('App Launch');
		// #ifdef H5
		const store = sessionStorage.getItem("store")
		if (store) {
			this.$store.dispatch('global/init', JSON.parse(store))
			sessionStorage.removeItem("store")
		}
		window.addEventListener("beforeunload", () => {
			sessionStorage.setItem("store", JSON.stringify(this.$store.state.global))
		})
		// #endif
	},
	onShow: function() {
		console.log('App Show');
	},
	onHide: function() {
		console.log('App Hide');
	}
};
</script>

<style lang="scss">
@import "colorui/main.css";
@import "colorui/icon.css";

@import "@/common/css/icon.css";

@import "uview-ui/index.scss";

page {
	height: 100%;
}

/* 解决头条小程序组件内引入字体不生效的问题 */
/* #ifdef MP-TOUTIAO */
@font-face {
	font-family: uniicons;
	src: url('/static/uni.ttf');
}
/* #endif */
</style>
