<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout style="width: 100%">
        <v-data-table
                :items="users"
                class="elevation-1"
                hide-actions
                :headers="headers"
                style="width: 100%"
        >
            <template v-slot:items="props">
                <tr class="cursor" v-on:click="$router.push('/users/' + props.item.id)">
                    <td class="text-xs-left">{{ props.item.name }}</td>
                    <td class="text-xs-right">{{ props.item.surname }}</td>
                    <td class="text-xs-right">{{ props.item.email }}</td>
                    <td class="text-xs-right">{{ props.item.dateEntered }}</td>
                </tr>
            </template>
        </v-data-table>
    </v-layout>
</template>

<script>
    export default {
        name: "UserTable",
        props: ['users'],
        data: function () {
            return {
                headers: [
                    {text: 'Name', align: 'left', value: 'name'},
                    {text: 'Surname', align: 'right', value: 'surname'},
                    {text: 'email', align: 'right', value: 'email'},
                    {text: 'dateEntered', align: 'right', value: 'dateEntered'},
                ]
            }
        }
    }
</script>

<style scoped>

</style>