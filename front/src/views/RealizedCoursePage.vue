<template>
    <v-container>
        <v-toolbar flat>
            <v-toolbar-title v-on:click="$router.push('/courses/' +realizedCourse.courseId )" class="cursor">
                <span class="grey--text">Course title :</span>
                <span class="font-weight-medium">{{course.title}}</span>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items v-if="$store.state.user.role == 'ADMIN' || $store.state.user.id == course.lecturerId">
                <v-btn flat icon v-on:click="editRealizedCourse()">
                    <v-icon>edit</v-icon>
                </v-btn>
            </v-toolbar-items>
        </v-toolbar>
        <v-layout style="width: 100%; margin-bottom: 50px">
            <v-list two-line style="width: 100%">
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Start Date :</span> <span
                                class="font-weight-medium" v-if="realizedCourse.startDate != undefined">
                            {{realizedCourse.startDate.slice(0, 12)}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Exam Date :</span> <span
                                class="font-weight-medium" v-if="realizedCourse.examDate != undefined">
                            {{realizedCourse.examDate.slice(0, 12)}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Status :</span> <span
                                class="font-weight-medium">{{realizedCourse.status}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Number of students :</span> <span
                                class="font-weight-medium">{{users.length}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-layout>
        <h1>List of students</h1>
        <student-table-with-marks :users="users" :trainer-id="course.lecturerId"></student-table-with-marks>
    </v-container>
</template>

<script>
    import axios from "axios";
    import StudentTableWithMarks from "../components/StudentTableWithMarks.vue";

    export default {
        name: "RealizedCoursePage",
        components: {StudentTableWithMarks},
        data: function () {
            return {
                users: [],
                realizedCourse: {},
                course: []
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
        },
        methods: {
            editRealizedCourse() {

            }
        }
    }
</script>

<style scoped>
    .cursor {
        cursor: pointer;
    }
</style>