import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from 'vuex-persistedstate';
import Cookies from 'js-cookie';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        token : '',
        user : {},
        authenticated : false
    },
    mutations: {
        token (state, token) {
            state.token = token
        },
        user(state, user) {
            state.user = user
        },
        authenticated(state, authenticated) {
            state.authenticated = authenticated
        },
    },
    actions: {},
    plugins: [createPersistedState({
        storage: {
            getItem: key => Cookies.get(key),
            setItem: (key, value) => Cookies.set(key, value, {expires: 3}),
            removeItem: key => Cookies.remove(key)
        }
    })]
});
