package org.example.springnewbie.ReqDTO;


import org.apache.commons.validator.routines.EmailValidator;

public class AddUserDTO {
    private String name;
    private String email;
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
