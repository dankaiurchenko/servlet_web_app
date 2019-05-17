<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
        <v-data-table
                :items="realizedCourses"
                class="elevation-1 cursor"
                hide-actions
                :headers="headers"
                style="width: 100%"
        >
            <template v-slot:items="props">
                <tr v-on:click="goTo(props.item.id)">
                    <td class="text-xs-right">{{ props.item.id }}</td>
                    <td class="text-xs-right">{{ props.item.startDate }}</td>
                    <td class="text-xs-right">{{ props.item.endDate }}</td>
                    <td class="text-xs-right">{{ props.item.examDate }}</td>
                    <td class="text-xs-right">{{ props.item.status }}</td>
                </tr>
            </template>
        </v-data-table>
    </v-layout>
</template>

<script>
    export default {
        name: "RealizedCoursesTable",
        props: ['realizedCourses'],
        data: function () {
            return {
                headers: [
                    {text: 'Title', align: 'left', value: 'title'},
                    {text: 'startDate', value: 'startDate'},
                    {text: 'endDate', value: 'endDate'},
                    {text: 'examDate', value: 'examDate'},
                    {text: 'status', value: 'status'}
                ],
            }
        },
        methods: {
            goTo(id) {
                this.$router.push('/realized-courses/' + id)
            }
        }
    }
</script>

<style scoped>
    .cursor {
        cursor: pointer;
    }
</style>