<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout row wrap>
        <v-container xs12>
            <div v-if="user !== null && user !== undefined " style="padding: 20px;">
                <v-toolbar flat color="white">
                    <span>{{user.role}}</span>
                    <v-spacer></v-spacer>
                </v-toolbar>

                <v-layout row wrap align-center>
                    <v-flex xs12 md5 class="text-xs-center">
                        <v-avatar style="margin: auto" size="75%" class="avatar">
                            <img src="https://png.pngtree.com/svg/20161212/f93e57629c.svg" alt="avatar">
                        </v-avatar>
                    </v-flex>
                    <v-flex xs12 md7 class="text-xs-right" style="padding: 20px 0">
                        <v-list two-line>
                            <v-list-tile>
                                <v-list-tile-content>
                                    <v-list-tile-title><span class="grey--text">Name :</span> <span
                                            class="font-weight-medium">{{user.name}}</span></v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>
                            <v-list-tile>
                                <v-list-tile-content>
                                    <v-list-tile-title><span class="grey--text">Surname :</span> <span
                                            class="font-weight-medium">{{user.surname}}</span></v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>
                            <v-list-tile>
                                <v-list-tile-content>
                                    <v-list-tile-title><span class="grey--text">Email :</span> <span
                                            class="font-weight-medium">{{user.email}}</span></v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>

                            <v-list-tile v-if="user.role == 'STUDENT'">
                                <v-list-tile-content>
                                    <v-list-tile-title><span class="grey--text">Teachers :</span>
                                        <span v-if="trainers.length < 1">No teachers yet</span>
                                        <span v-else v-on:click="goToUserPage(teacher) "
                                              v-for="teacher in trainers" class="font-weight-medium cursor">
                                        {{getFullName(teacher)}}</span>
                                    </v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>

                            <v-list-tile>
                                <v-list-tile-content>
                                    <v-list-tile-title><span class="grey--text">Courses :</span>
                                        <span v-if="courses.length < 1">No courses yet</span>
                                        <span v-else v-for="course in courses" v-on:click="goTo(course.id)"
                                              class="font-weight-medium cursor">{{course.title}}</span>
                                    </v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </v-list>
                    </v-flex>
                </v-layout>
            </div>
            <realized-courses-table :realized-courses="realized"></realized-courses-table>
        </v-container>
    </v-layout>
</template>

<script>
    import axios from "axios";
    import RealizedCoursesTable from "../components/RealizedCoursesTable";

    export default {
        name: "UserPage",
        components: {RealizedCoursesTable},
        data: function () {
            return {
                user: {},
                trainers: [],
                courses: [],
                realized: []
            }
        },
        methods: {
            getFullName(value) {
                return value.name + ' ' + value.surname;
            },
            goToUserPage(value) {
                this.$router.push('/users/' + value.id);
            },
            goTo(value) {
                this.$router.push('/courses/' + value);
            }
        },
        mounted() {
            axios.defaults.headers.common = {'Authorization': this.$store.state.token};
            let self = this;
            console.log(this.$store.state.user.role);
            axios.get('/users/get-by-id',
                {
                    params: {
                        userId: this.$route.params.id
                    }
                }).then(function (response) {
                self.user = response.data;
                console.log(response);
                if (self.user.role == 'ADMIN') {
                } else if (self.user.role == 'TRAINER') {
                    axios.get('/courses/get-by-lecturer',
                        {
                            params: {
                                trainerId: self.$route.params.id
                            }
                        }).then(function (response) {
                        console.log(response);
                        self.courses = response.data;
                    }).catch(function (error) {
                        console.log(error);
                    });
                    axios.get('/realized-courses/get-by-lecturer',
                        {
                            params: {
                                trainerId: self.$route.params.id
                            }
                        }).then(function (response) {
                        console.log(response);
                        self.realized = response.data;
                    }).catch(function (error) {
                        console.log(error);
                    });

                } else {
                    axios.get('/courses/get-by-student',
                        {
                            params: {
                                studentId: self.$route.params.id
                            }
                        }).then(function (response) {
                        console.log(response);
                        self.courses = response.data;
                    }).catch(function (error) {
                        console.log(error);
                    });
                    axios.get('/realized-courses/get-by-student',
                        {
                            params: {
                                studentId: self.$route.params.id
                            }
                        }).then(function (response) {
                        console.log(response);
                        self.realized = response.data;
                    }).catch(function (error) {
                        console.log(error);
                    })
                }
            }).catch(function (error) {
                console.log(error);
            });

        }
    }
</script>

<style scoped>
    .cursor {
        cursor: pointer;
        margin-right: 15px;
    }
</style>