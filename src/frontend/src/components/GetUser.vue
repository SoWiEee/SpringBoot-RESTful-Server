<template>
  <div>
    <h1>Get User</h1>
    <form @submit.prevent="getUser">
      <div>
        <label for="email">Email:</label>
        <input v-model="email" type="email" id="email" required>
      </div>
      <div>
        <button type="submit">Get User</button>
      </div>
    </form>
    <div v-if="user">
      <h2>User Details</h2>
      <p>Name: {{ user.name }}</p>
      <p>Email: {{ user.email }}</p>
    </div>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      user: null,
      message: ''
    };
  },
  methods: {
    async getUser() {
      try {
        const response = await this.$http.get(`/v1/user/get/get_user/${this.email}`);
        this.user = response.data;
        this.message = '';
      } catch (error) {
        this.user = null;
        this.message = error.response.data.rsp_msg;
      }
    }
  }
};
</script>
