<template>
  <div id="app">
    <nav>
      <ul>
        <li><router-link to="/add_user">Add User</router-link></li>
        <li><router-link to="/get_user">Get User</router-link></li>
        <li><router-link to="/fix_user">Fix User</router-link></li>
        <li><router-link to="/delete_user">Delete User</router-link></li>
      </ul>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style>
  nav ul {
    list-style-type: none;
    padding: 0;
  }

  nav ul li {
    display: inline;
    margin-right: 10px;
  }
</style>
