package org.example.springnewbie.ReqDTO;


import jakarta.validation.constraints.NotNull;
import org.apache.commons.validator.routines.EmailValidator;

public class AddUserDTO {
    @NotNull(message = "Invalid name: name is NULL")
    private String name;
    @NotNull(message = "Invalid email: email is NULL")
    private String email;
    @NotNull(message = "Invalid password]: password is NULL")
    private String password;

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public boolean paramEmpty(){
        return (name.isEmpty() || email.isEmpty() || password.isEmpty());
    }

    public boolean isEmailValid(){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public boolean isEmpty(){
        return (name == null || email == null || password == null);
    }
}
