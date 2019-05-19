<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
        <v-data-table v-if="realizedCourses.length > 0"
                :items="realizedCourses"
                class="elevation-1 cursor"
                hide-actions
                :headers="headers"
                style="width: 100%"
        >
            <template v-slot:items="props">
                <tr v-on:click="goTo(props.item.id)">
                    <td class="text-xs-right">{{ props.item.id }}</td>
                    <td class="text-xs-right" v-if="props.item.startDate != undefined">{{ props.item.startDate.slice(0, 12) }}</td>
                    <td class="text-xs-right" v-if="props.item.endDate != undefined">{{ props.item.endDate.slice(0, 12) }}</td>
                    <td class="text-xs-right" v-if="props.item.examDate != undefined">{{ props.item.examDate.slice(0, 12) }}</td>
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
                    {text: 'Start Date', align: 'right', value: 'startDate'},
                    {text: 'End Date', align: 'right', value: 'endDate'},
                    {text: 'Exam Date', align: 'right', value: 'examDate'},
                    {text: 'Status', align: 'right', value: 'status'}
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