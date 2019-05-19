<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-app>
        <v-toolbar app>
            <v-toolbar-title class="headline text-uppercase">
                SCHOOL
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-menu  v-if="authenticated" offset-y>
                <template v-slot:activator="{ on }">
                    <v-btn
                            color="primary"
                            dark
                            v-on="on"
                    >
                        Menu
                    </v-btn>
                </template>
                <v-list>
                    <v-list-tile
                            v-for="(item, index) in items"
                            :key="index"
                            @click="goto(item.address)"
                    >
                        <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                    </v-list-tile>
                </v-list>
            </v-menu>
            <v-divider vertical></v-divider>
            <v-btn v-if="authenticated" v-on:click.native="logout()">Logout</v-btn>
        </v-toolbar>
        <v-content>
            <router-view @authenticated="setAuthenticated"></router-view>
        </v-content>
        <vue-snotify></vue-snotify>
    </v-app>
</template>

<script>
    import axios from 'axios';
    import store from './store.js';

    axios.defaults.baseURL = 'http://localhost:8081/app/index.php';

    export default {
        name: 'App',
        store,
        mounted() {
            if (!this.$store.state.authenticated) {
                this.$router.replace({name: "login"});
            }
        },
        data: function () {
            return {}
        },
        methods: {
            goto(address) {
                this.$router.push(address);
            },
            successAutoClosable(title) {
                this.$snotify.success(title, {
                    timeout: 2000,
                    showProgressBar: false,
                    closeOnClick: false,
                    pauseOnHover: true
                });
            },
            setAuthenticated(status) {
                this.$store.commit('authenticated', status);
                // this.$store.state.authenticated = status;
                console.log(this.$store.state.user);
                console.log(this.$store.state.authenticated);
            },
            logout() {
                let self = this;
                axios.post("/auth/logout", this.$store.state.user).then(function (response) {
                    console.log(response.data);
                    self.$store.commit('token', '');
                    self.$store.commit('user', null);
                    self.$store.commit('authenticated', false);
                    axios.defaults.headers.common = {'Authorization': ''};
                    self.$router.push('/login');
                    self.successAutoClosable("you are logged out");
                }).catch(function (error) {
                    console.log(error);
                    console.log("The username and / or password is incorrect");
                });
            }
        },
        computed: {
            authenticated() {
                // console.log(this.$store.state.user);
                // console.log(this.$store.state.authenticated);
                console.log(this.$store.state.user);
                console.log(this.$store.state.authenticated);
                return this.$store.state.authenticated;

            },
            items() {
                let a = [
                    {title: 'Users', address: '/users'},
                    {title: 'Courses', address: '/courses'},
                    {title: 'Archive', address: '/archive'}
                ];
                if (this.$store.state.user != null)
                    a.push({title: 'My Page', address: '/users/' + this.$store.state.user.id});
                return a
            }
        }
    }
</script>

<style scoped>
    @import "~ng-snotify/styles/material.css";
</style>

<style>
    .cursor {
        cursor: pointer;
    }
</style>
