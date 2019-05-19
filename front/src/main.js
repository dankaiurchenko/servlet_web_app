import Vue from "vue";
import './plugins/vuetify'
import App from "./App.vue";
import router from "./router";
import Snotify, {SnotifyPosition} from 'vue-snotify';
import VeeValidate from 'vee-validate';
// import store from "./store";

const options = {
    toast: {
        position: SnotifyPosition.rightBottom
    }
};
Vue.config.productionTip = false;
Vue.use(Snotify, options);
Vue.use(VeeValidate);
new Vue({
    router,
    // store,
    render: h => h(App)
}).$mount("#app");
