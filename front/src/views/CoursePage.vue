<template>

    <v-container>
        <v-toolbar flat>
            <v-toolbar-title v-on:click="$router.push('/courses/' +realizedCourse.courseId )" class="cursor">
                <span class="grey--text">Title :</span>
                <span class="font-weight-medium">{{course.title}}</span>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items v-if="$store.state.user.role == 'ADMIN' || $store.state.user.id == course.lecturerId">
                <v-btn flat icon v-on:click="editCourse()">
                    <v-icon>edit</v-icon>
                </v-btn>
            </v-toolbar-items>
        </v-toolbar>

        <v-layout style="width: 100%; margin-bottom: 50px">
            <v-list two-line style="width: 100%">
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Number Of Hours :</span> <span
                                class="font-weight-medium">{{course.numberOfHours}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Hours For Lectures :</span> <span
                                class="font-weight-medium">{{course.hoursForLectures}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Hours For Practice :</span> <span
                                class="font-weight-medium">{{course.hoursForPractice}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile class="wid cursor" v-on:click="$router.push('/users/' + course.lecturerId)">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">Lecturer :</span> <span
                                class="font-weight-medium">{{trainer.name + ' ' + trainer.surname}}</span>
                        </v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-layout>
        <realized-courses-table :realizedCourses="realized"></realized-courses-table>
    </v-container>
</template>

<script>
    import axios from "axios";
    import RealizedCoursesTable from "../components/RealizedCoursesTable.vue";

    export default {
        name: "CoursePage",
        components: {RealizedCoursesTable},
        data: function () {
            return {
                course: {},
                realized: [],
                trainer: {}
            }
        },
        methods: {
            editCourse() {

            }
        },
        mounted() {
            axios.defaults.headers.common = {'Authorization': this.$store.state.token};
            let self = this;
            axios.get('/courses/get-by-id',
                {
                    params: {
                        courseId: this.$route.params.id
                    }
                }).then(function (response) {
                self.course = response.data;
                axios.get('/users/get-by-id',
                    {
                        params: {
                            userId: self.course.lecturerId
                        }
                    }).then(function (response) {
                    self.trainer = response.data;
                    console.log(response);
                }).catch(function (error) {
                    console.log(error);
                });
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            });
            axios.get('/realized-courses/get-by-course',
                {
                    params: {
                        courseId: this.$route.params.id
                    }
                }).then(function (response) {
                self.realized = response.data;
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            })
        }
    }
</script>

<style scoped>
    .wid {
        width: 100%;
    }

    .cursor {
        cursor: pointer;
    }
</style>