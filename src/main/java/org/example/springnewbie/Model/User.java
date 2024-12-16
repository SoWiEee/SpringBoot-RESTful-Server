package org.example.springnewbie.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class User {

    @NotNull
    public String name;
    @Email
    public String email;
    @NotNull
    public String password;

    public String getEmail() { return this.email; }
    public String getName() { return this.name; }
    public String getPasswd() { return this.password; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setPasswd(String password) { this.password = password; }
}
