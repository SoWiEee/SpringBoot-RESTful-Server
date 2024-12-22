<template>
  <div>
    <h1>Delete User</h1>
    <form @submit.prevent="deleteUser">
      <div>
        <label for="email">Email:</label>
        <input v-model="email" type="email" id="email" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input v-model="password" type="password" id="password" required>
      </div>
      <div>
        <button type="submit">Delete User</button>
      </div>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
      message: ''
    };
  },
  methods: {
    async deleteUser() {
      try {
        const response = await this.$http.delete('/v1/user/delete/delete_user', {
          data: {
            email: this.email,
            password: this.password
          }
        });
        this.message = response.data.rsp_msg;
      } catch (error) {
        this.message = error.response.data.rsp_msg;
      }
    }
  }
};
</script>
