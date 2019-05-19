<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout style="width: 100%">
        <v-data-table v-if="users.length > 0"
                      :items="theseUsers"
                      class="elevation-1"
                      hide-actions
                      :headers="headers"
                      style="width: 100%"
        >
            <template v-slot:items="props">
                <tr>
                    <td class="text-xs-left cursor" v-on:click="$router.push('/users/' + props.item.student.id)">
                        {{ props.item.student.name }}
                    </td>
                    <td class="text-xs-right cursor" v-on:click="$router.push('/users/' + props.item.student.id)">
                        {{ props.item.student.surname }}
                    </td>
                    <td class="text-xs-right cursor" v-on:click="$router.push('/users/' + props.item.student.id)">
                        {{ props.item.student.email }}
                    </td>
                    <td v-if="hasRights()" class="text-xs-right" style="width: 15%">
                        <v-select
                                :items="items"
                                label="Mark"
                                solo
                                flat
                                v-model="props.item.dtoMark.mark"
                                v-on:change="setMark(props.item)"
                                style="margin-top: 15px; font-size: 13px"
                        ></v-select>
                    </td>
                    <td v-else class="text-xs-right cursor" v-on:click="$router.push('/users/' + props.item.student.id)">
                        {{ props.item.dtoMark.mark }}
                    </td>

                </tr>
            </template>
        </v-data-table>
    </v-layout>
</template>

<script>
    import axios from "axios";

    export default {
        name: "StudentTableWithMarks",
        props: ['users', 'trainerId'],
        data: function () {
            return {

                items: [1, 2, 3, 4, 5]
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
            setMark(item) {
                // /set-for-student-and-course
                let self= this;
                axios.post("/marks/set-for-student-and-course", item.dtoMark)
                    .then(function (response) {
                        self.successAutoClosable("Mark is saved");
                    }).catch(function (error) {
                    console.log(error);
                    self.errorAutoClosable("Error occurred");
                });
            },
            hasRights(){
                return this.$store.state.user.role == 'ADMIN' || this.$store.state.user.id == this.trainerId
            }
        },
        computed: {
            theseUsers() {
                return this.users;
            },
            headers() {
                let h = [
                    {text: 'Name', align: 'left', value: 'name'},
                    {text: 'Surname', align: 'right', value: 'surname'},
                    {text: 'email', align: 'right', value: 'email'}
                ];
                if(this.hasRights()){
                    h.push({text: 'Set mark', align: 'right', value: 'button', width: '15%'});
                }else {
                    h.push({text: 'Mark', align: 'right', value: 'mark'});
                }
                return h;
            }
        }
    }
</script>

<style scoped>

</style>
