import Vue from "vue";
import './plugins/vuetify'
import App from "./App.vue";
import router from "./router";
import Snotify, {SnotifyPosition} from 'vue-snotify';
// import store from "./store";

const options = {
    toast: {
        position: SnotifyPosition.rightTop
    }
};

Vue.config.productionTip = false;
Vue.use(Snotify, options);
new Vue({
    router,
    // store,
    render: h => h(App)
}).$mount("#app");
