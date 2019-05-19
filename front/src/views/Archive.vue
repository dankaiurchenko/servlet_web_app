<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container>
        <v-layout style="width: 100%">
            <v-data-table :items="marks"
                          class="elevation-1"
                          hide-actions
                          :headers="headers"
                          style="width: 100%"
            >
                <template v-slot:items="props">
                    <tr>
                        <td class="text-xs-left cursor"
                            v-on:click="$router.push('/realized-courses/' + props.item.realizedCourseId)">
                            {{ props.item.courseName }}
                        </td>
                        <td class="text-xs-right cursor">
                            {{ props.item.trainerName }}
                        </td>
                        <td class="text-xs-right cursor" v-if="props.item.endDate != undefined">
                            {{ props.item.endDate.slice(0, 12) }}
                        </td>
                        <td class="text-xs-right cursor">
                            {{ props.item.mark }}
                        </td>

                    </tr>
                </template>
            </v-data-table>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from "axios";

    export default {
        name: "Archive",
        data: function () {
            return {
                headers: [
                    {text: 'Course name', align: 'left', value: 'course'},
                    {text: 'Trainer name', align: 'right', value: 'trainer'},
                    {text: 'End of course', align: 'right', value: 'courseEnd'},
                    {text: 'Mark', align: 'right', value: 'mark'}
                ],
                marks: []
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
        },
        mounted() {
            axios.defaults.headers.common = {'Authorization': this.$store.state.token};
            let self = this;
            axios.get("/marks/all-of-student",
                {
                    params: {
                        studentId: self.$store.state.user.id
                    }
                }).then(function (response) {
                self.marks = response.data;
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            });
        }
    }
</script>

<style scoped>

</style>
