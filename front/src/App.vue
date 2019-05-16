<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title class="headline text-uppercase">
                SCHOOL
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <router-link v-if="authenticated" to="/login" v-on:click.native="logout()" replace>Logout</router-link>
        </v-toolbar>
        <v-content>
            <router-view @authenticated="setAuthenticated"></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import axios from 'axios';
    import store from 'store.js';

    axios.defaults.headers.common['token'] =  store.state.token;
    axios.defaults.baseURL = 'http://localhost:8081/app/index.php';

    export default {
        name: 'App',
        data() {
            return {
                authenticated: false,
            }
        },
        mounted() {
            if (!this.authenticated) {
                this.$router.replace({name: "login"});
            }
        },
        methods: {
            setAuthenticated(status) {
                this.authenticated = status;
            },
            logout() {
                this.authenticated = false;
            }
        }
    }
</script>
