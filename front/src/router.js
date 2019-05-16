import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import Login from "./views/Login.vue";
import Courses from "./views/Courses.vue";
import Lecturers from "./views/Lecturers.vue";
import Students from "./views/Students.vue";
import Archive from "./views/Archive.vue";

Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [
        {
          path: "/",
          name: "home",
          component: Home
        },
        {
            path: "/about",
            name: "about",
            component: () =>
                import(/* webpackChunkName: "about" */ "./views/About.vue")
        },
        {path: "/login", name: "login", component: Login},
        {path: "/courses", component: Courses},
        {path: "/lecturers", component: Lecturers},
        {path: "/students", component: Students},
        {path: "/archive", component: Archive}
    ]
});
