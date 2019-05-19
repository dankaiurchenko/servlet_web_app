<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container>
        <v-layout style="margin-bottom: 50px; margin-top: 20px;">
            <h1 >All courses</h1>
            <v-spacer></v-spacer>
            <v-btn v-if="canCreate()">New one</v-btn>
        </v-layout>
        <v-layout>
            <v-data-table
                    :items="courses"
                    class="elevation-1"
                    hide-actions
                    :headers="headers"
                    style="width: 100%">
                <template v-slot:items="props">
                    <tr class="cursor" v-on:click="goTo(props.item.id)">
                        <td class="text-xs-right">{{ props.item.title }}</td>
                        <td class="text-xs-right">{{ props.item.numberOfHours }}</td>
                        <td class="text-xs-right">{{ props.item.hoursForLectures }}</td>
                        <td class="text-xs-right">{{ props.item.hoursForPractice }}</td>
                        <td class="text-xs-right">{{ props.item.lecturerId }}</td>
                    </tr>
                </template>
            </v-data-table>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from 'axios';

    export default {
        name: "Courses",
        data: function () {
            return {
                courses: [],
                headers: [
                    {text: 'Title', align: 'left', value: 'title'},
                    {text: 'numberOfHours', value: 'numberOfHours'},
                    {text: 'hoursForLectures', value: 'hoursForLectures'},
                    {text: 'hoursForPractice', value: 'hoursForPractice'},
                    {text: 'lecturerId', value: 'lecturerId'}
                ],
            }
        },
        methods:{
            goTo(id){
                this.$router.push('/courses/'+ id)
            },
            canCreate(){
                return this.$store.state.user.role == 'ADMIN' || this.$store.state.user.role == 'TRAINER'
            }
        },
        mounted() {
            axios.defaults.headers.common = {'Authorization': this.$store.state.token};
            let self = this;
            console.log(this.$store.state.user.role);
            // if (this.$store.state.user.role == 'ADMIN') {
            axios.get('/courses/get-all').then(function (response) {
                self.courses = response.data;
                console.log(response);

            }).catch(function (error) {
                console.log(error);
            })
            // } else if (this.$store.state.user.role == 'TRAINER') {
            //     axios.get('/courses/get-by-lecturer',
            //         {
            //             params: {
            //                 trainerId: this.$store.state.user.id
            //             }
            //         }).then(function (response) {
            //         console.log(response);
            //         self.courses = response.data;
            //     }).catch(function (error) {
            //         console.log(error);
            //     })
            // } else {
            //     axios.get('/courses/get-by-student',
            //         {
            //             params: {
            //                 studentId: this.$store.state.user.id
            //             }
            //         }).then(function (response) {
            //         console.log(response);
            //         self.courses = response.data;
            //     }).catch(function (error) {
            //         console.log(error);
            //     })
            // }

        }
    }
</script>

<style scoped>
    .cursor {
        cursor: pointer;
    }
</style>