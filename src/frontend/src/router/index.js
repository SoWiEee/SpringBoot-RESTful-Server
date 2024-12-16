import { createRouter, createWebHistory } from 'vue-router';
import AddUser from '../components/AddUser.vue';
import GetUser from '../components/GetUser.vue';
import FixUser from '../components/FixUser.vue';
import DeleteUser from '../components/DeleteUser.vue';

const routes = [
    { path: '/add_user', component: AddUser },
    { path: '/get_user', component: GetUser },
    { path: '/fix_user', component: FixUser },
    { path: '/delete_user', component: DeleteUser }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
