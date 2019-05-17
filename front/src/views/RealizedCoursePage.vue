<template>
    <v-container>
        <v-layout style="width: 100%; margin-bottom: 50px">
            <v-list two-line style="width: 100%">
                <v-list-tile class="wid cursor" v-on:click="$router.push('/courses/' +realizedCourse.courseId )">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Course title :</span> <span
                                class="font-weight-medium">{{course.title}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">startDate :</span> <span
                                class="font-weight-medium">{{realizedCourse.startDate}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">examDate :</span> <span
                                class="font-weight-medium">{{realizedCourse.examDate}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">status :</span> <span
                                class="font-weight-medium">{{realizedCourse.status}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile class="wid cursor" v-on:click="$router.push('/users/' + course.lecturerId)">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">number of students :</span> <span
                                class="font-weight-medium">{{users.length}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-layout>
        <h1>List of students</h1>
        <user-table :users="users"></user-table>
    </v-container>
</template>

<script>
    import UserTable from "../components/UserTable.vue";
    import axios from "axios";

    export default {
        name: "RealizedCoursePage",
        components: {UserTable},
        data: function () {
            return{
                users: [],
                realizedCourse: {},
                course : []
            }
        },
        mounted() {
            axios.defaults.headers.common = {'Authorization': this.$store.state.token};
            let self = this;
            axios.get('/realized-courses/get-by-id',
                {
                    params: {
                        realizedCourseId: this.$route.params.id
                    }
                }).then(function (response) {
                self.realizedCourse = response.data;
                console.log(response);
                axios.get('/courses/get-by-id',
                    {
                        params: {
                            courseId: self.realizedCourse.courseId
                        }
                    }).then(function (response) {
                    self.course = response.data;
                    console.log(response);
                }).catch(function (error) {
                    console.log(error);
                });
            }).catch(function (error) {
                console.log(error);
            });
            axios.get('/users/get-by-course',
                {
                    params: {
                        realizedCourseId: this.$route.params.id
                    }
                }).then(function (response) {
                self.users = response.data;
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            })
        }
    }
</script>

<style scoped>
    .cursor {
        cursor: pointer;
    }
</style>