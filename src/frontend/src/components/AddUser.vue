<template>
  <div>
    <h1>Add User</h1>
    <form @submit.prevent="addUser">
      <div>
        <label for="name">Name:</label>
        <input v-model="name" type="text" id="name" required>
      </div>
      <div>
        <label for="email">Email:</label>
        <input v-model="email" type="email" id="email" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input v-model="password" type="password" id="password" required>
      </div>
      <div>
        <button type="submit">Add User</button>
      </div>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: '',
      email: '',
      password: '',
      message: ''
    };
  },
  methods: {
    async addUser() {
      try {
        const response = await this.$http.post('/v1/user/post/add_user', {
          name: this.name,
          email: this.email,
          password: this.password
        });
        this.message = response.data.rsp_msg;
      } catch (error) {
        this.message = error.response.data.rsp_msg;
      }
    }
  }
};
</script>
