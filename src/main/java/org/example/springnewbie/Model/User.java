package org.example.springnewbie.Model;

import com.google.common.hash.Hashing;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class User {

    @NotBlank
    public String name;
    @Email
    public String email;
    @NotBlank
    public String passwd;     // SHA-256

    // getter & setter
    public String getEmail() { return this.email; }
    public String getName() { return this.name; }
    public String getPasswd() { return this.passwd; }

    // use SHA256 hashing
    public static String hashPasswd(String passwd) throws NoSuchAlgorithmException {
        return  Hashing.sha256().hashString(passwd, StandardCharsets.UTF_8).toString();
    }
}
