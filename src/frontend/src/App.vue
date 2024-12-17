<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>User Management</v-toolbar-title>
      <v-spacer></v-spacer>
      <li><router-link to="/add_user">Add User</router-link></li>
      <li><router-link to="/get_user">Get User</router-link></li>
      <li><router-link to="/fix_user">Fix User</router-link></li>
      <li><router-link to="/delete_user">Delete User</router-link></li>
      <v-btn>Button</v-btn>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>>
</template>

<script>
export default {
  name: 'App'
};
</script>

