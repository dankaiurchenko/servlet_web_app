<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container>
        <v-layout>
            <v-data-table
                    :items="desserts"
                    class="elevation-1"
                    hide-actions
                    hide-headers
            >
                <template v-slot:items="props">
                    <td>{{ props.item.name }}</td>
                    <td class="text-xs-right">{{ props.item.calories }}</td>
                    <td class="text-xs-right">{{ props.item.fat }}</td>
                    <td class="text-xs-right">{{ props.item.carbs }}</td>
                    <td class="text-xs-right">{{ props.item.protein }}</td>
                    <td class="text-xs-right">{{ props.item.iron }}</td>
                </template>
            </v-data-table>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from "axios";

    export default {
        name: "Lecturers",
        data: function(){
            return{

            }
        },
        mounted() {
            let self = this;
            axios.defaults.headers.common = {'Authorization': this.$store.state.token};
            axios.post("/filters/logout", this.$store.state.user).then(function (response) {
                console.log(response.data);
                self.$store.commit('token', '');
                self.$store.commit('user', null);
                self.$store.commit('authenticated', false);
                axios.defaults.headers.common = {'Authorization': ''};
                self.successAutoClosable("you are logged out");
            }).catch(function (error) {
                console.log(error);
                console.log("The username and / or password is incorrect");
            });
        }
    }
</script>

<style scoped>

</style>