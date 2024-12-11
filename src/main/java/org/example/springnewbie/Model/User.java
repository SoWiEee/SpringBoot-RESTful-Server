package org.example.springnewbie.Model;

import com.google.common.hash.Hashing;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.nio.charset.StandardCharsets;

public class User {

    @NotNull
    public String name;
    @Email
    public String email;
    @NotNull
    public String password;     // SHA-256

    // getter & setter
    public String getEmail() { return this.email; }
    public String getName() { return this.name; }
    public String getPasswd() { return this.password; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setPasswd(String password) { this.password = password; }

    // use SHA256 hashing
    public static String hashPasswd(String passwd) {
        return  Hashing.sha256().hashString(passwd, StandardCharsets.UTF_8).toString();
    }
}
