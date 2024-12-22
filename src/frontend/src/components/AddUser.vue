<template>
  <v-container>
      <h1>Add User</h1>
      <v-form @submit.prevent="addUser" ref="form">
        <v-text-field v-model="name" label="Name" required></v-text-field>
        <v-text-field v-model="email" label="Email" type="email" required></v-text-field>
        <v-text-field v-model="password" label="Password" type="password" required></v-text-field>
        <v-btn type="submit" color="primary">Add User</v-btn>
      </v-form>
    <v-alert v-if="message" type="info">{{ message }}</v-alert>
  </v-container>
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
