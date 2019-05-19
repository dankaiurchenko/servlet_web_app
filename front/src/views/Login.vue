<template>
    <v-container fluid bg fill-height grid-list-md>

        <v-layout align-center justify-center row fill-height style="margin: auto" v-show="loginForm">
            <v-flex xs12 sm6 md5>
                <h1>Login</h1>
                <v-text-field
                        v-model="username"
                        label="Email"
                ></v-text-field>
                <v-text-field
                        v-model="password"
                        label="Password"
                ></v-text-field>
                <v-layout>
                    <v-btn large color="success" v-on:click="login()">Login</v-btn>
                    <v-spacer></v-spacer>
                    <v-btn large color="success" v-on:click="loginForm = false">Register</v-btn>
                </v-layout>
            </v-flex>
        </v-layout>


        <v-layout align-center justify-center row fill-height style="margin: auto" v-show="!loginForm">
            <v-flex xs12 sm6 md5>
                <h1>Register</h1>
                <v-text-field
                        v-model="newUser.name"
                        label="Name"
                        v-validate="'required'"
                        :data-vv-name="'name'"
                        :error-messages="errors.collect('name')"
                ></v-text-field>
                <v-text-field
                        v-model="newUser.surname"
                        label="Surname"
                        v-validate="'required'"
                        :data-vv-name="'surname'"
                        :error-messages="errors.collect('surname')"
                ></v-text-field>
                <v-text-field
                        v-model="newUser.email"
                        label="Email"
                        v-validate="'required|email'"
                        :data-vv-name="'email'"
                        :error-messages="errors.collect('email')"
                ></v-text-field>
                <v-text-field
                        v-model="newUser.password"
                        label="Password"
                        v-validate="'required'"
                        :data-vv-name="'password'"
                        :error-messages="errors.collect('password')"
                ></v-text-field>
                <v-radio-group v-model="newUser.role" row>
                    <v-radio label="TRAINER" value="TRAINER"></v-radio>
                    <v-radio label="STUDENT" value="STUDENT"></v-radio>
                </v-radio-group>
                <v-layout>
                    <v-btn large color="success" v-on:click="register()">Register</v-btn>
                    <v-spacer></v-spacer>
                    <v-btn large color="success" v-on:click="loginForm = true">Login</v-btn>
                </v-layout>

            </v-flex>
        </v-layout>

    </v-container>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'Login',
        data() {
            return {
                loginForm: true,
                username: "",
                password: "",
                newUser: {
                    name: '',
                    surname: '',
                    email: '',
                    password: '',
                    dateEntered: new Date().toISOString().slice(0, 10),
                    role: 'STUDENT'
                },
                roles: ['TRAINER', 'STUDENT']
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
            errorAutoClosable(title) {
                this.$snotify.error(title, {
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
                        self.$emit("authenticated", true);
                    }).catch(function (error) {
                        console.log(error);
                        self.errorAutoClosable("The username and / or password is incorrect");
                    });
                }
            },
            register() {
                let self= this;
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        axios.post("/register", this.newUser).then(
                            function (response) {
                                console.log(response.data);
                                self.loginForm = true;
                                self.newUser = {
                                    name: '',
                                    surname: '',
                                    email: '',
                                    password: '',
                                    dateEntered: new Date().toISOString().slice(0, 10),
                                    role: 'STUDENT'
                                };
                                self.$validator.reset();
                                self.successAutoClosable("You registered successfully");
                            })
                            .catch(function (error) {
                                console.log(error);
                                self.errorAutoClosable("Something went wrong");
                            });
                        return;
                    }
                    if (!result) {
                        self.errorAutoClosable("Valid input required")
                    }
                });

            }
        }
    }
</script>

<style scoped>

</style>