<template>

    <v-container>
        <v-layout style="width: 100%; margin-bottom: 50px">
            <v-list two-line style="width: 100%">
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">title :</span> <span
                                class="font-weight-medium">{{course.title}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">numberOfHours :</span> <span
                                class="font-weight-medium">{{course.numberOfHours}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">hoursForLectures :</span> <span
                                class="font-weight-medium">{{course.hoursForLectures}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile class="wid">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">hoursForPractice :</span> <span
                                class="font-weight-medium">{{course.hoursForPractice}}</span></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile class="wid cursor" v-on:click="$router.push('/users/' + course.lecturerId)">
                    <v-list-tile-content>
                        <v-list-tile-title><span class="grey--text">lecturerId :</span> <span
                                class="font-weight-medium">{{course.lecturerId}}</span></v-list-tile-title>
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
                realized: []
            }
        },
        methods:{
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
    .wid{
        width: 100%;
    }
    .cursor {
        cursor: pointer;
    }
</style>