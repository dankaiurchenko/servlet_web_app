<template>
    <v-container>
        <h1>ALL USERS</h1>
        <user-table :users="users"></user-table>
    </v-container>
</template>

<script>
    import UserTable from "../components/UserTable.vue";
    import axios from "axios";

    export default {
        name: "UsersPage",
        components: {UserTable},
        data: function () {
            return {
                users: []
            }
        },
        mounted() {
            axios.defaults.baseURL = 'http://localhost:8081/app/index.php';
            axios.defaults.headers.common = {'Authorization': this.$store.state.token};
            if (this.$store.state.user.role == 'ADMIN') {
                axios.get('/users/get-all').then(function (response) {
                    self.users = response.data;
                    console.log(response);
                }).catch(function (error) {
                    console.log(error);
                })
            }
        }
    }
</script>

<style scoped>

</style>