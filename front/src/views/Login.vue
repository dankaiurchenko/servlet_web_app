<template>
    <div id="login">
        <h1>Login</h1>
        <input type="text" name="username" v-model="username" placeholder="Username"/>
        <input type="password" name="password" v-model="password" placeholder="Password"/>
        <button type="button" v-on:click="login()">Login</button>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'Login',
        data() {
            return {
                username: "",
                password: ""
            }
        },
        methods: {
            successAutoClosable(title) {
                this.$snotify.success(title, {
                    timeout: 2000,
                    showProgressBar: false,
                    closeOnClick: false,
                    pauseOnHover: true
                });
            },
            login() {
                let self = this;
                if (this.username != "" && this.password != "") {
                    axios.post("/login", {
                        name: "",
                        surname: "",
                        email: this.username,
                        role: "",
                        password: this.password
                    }).then(function (response) {
                        self.successAutoClosable("you are logged in");
                        console.log(response.data);
                        self.$router.replace({name: "home"});
                        self.$store.commit('token', response.data.token);
                        self.$store.commit('user', response.data);
                        axios.defaults.headers.common = {'Authorization': response.data.token};
                        // self.$store.commit('token', response.data.token);
                        // self.$store.state.user = response.data;
                        // self.$store.state.token = self.$store.state.user.token;
                        self.$emit("authenticated", true);
                        // self.parent.authenticated = true;
                    }).catch(function (error) {
                        console.log("The username and / or password is incorrect");
                    });
                }
            }
        }
    }
</script>

<style scoped>
    #login {
        width: 500px;
        border: 1px solid #CCCCCC;
        background-color: #FFFFFF;
        margin: auto;
        margin-top: 200px;
        padding: 20px;
    }
</style>