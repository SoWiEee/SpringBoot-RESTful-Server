<template>
  <div>
    <h1>Fix User</h1>
    <form @submit.prevent="fixUser">
      <div>
        <label for="email">Email:</label>
        <input v-model="email" type="email" id="email" required>
      </div>
      <div>
        <label for="newName">New Name:</label>
        <input v-model="newName" type="text" id="newName" required>
      </div>
      <div>
        <label for="newEmail">New Email:</label>
        <input v-model="newEmail" type="email" id="newEmail" required>
      </div>
      <div>
        <button type="submit">Fix User</button>
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
      newName: '',
      newEmail: '',
      message: ''
    };
  },
  methods: {
    async fixUser() {
      try {
        const response = await this.$http.put('/v1/user/put/fix_user', {
          email: this.email,
          newName: this.newName,
          newEmail: this.newEmail
        });
        this.message = response.data.rsp_msg;
      } catch (error) {
        this.message = error.response.data.rsp_msg;
      }
    }
  }
};
</script>
