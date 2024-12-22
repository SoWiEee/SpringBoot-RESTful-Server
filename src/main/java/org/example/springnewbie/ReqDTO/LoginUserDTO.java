package org.example.springnewbie.ReqDTO;

import jakarta.validation.constraints.NotNull;

public class LoginUserDTO {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public boolean paramEmpty(){
        return (name.isEmpty() || email.isEmpty() || password.isEmpty());
    }
}
