package org.example.springnewbie.ReqDTO;

import org.apache.commons.validator.routines.EmailValidator;

public class DeleteUserDTO {
    private String email;
    private String password;

    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public boolean isEmailValid(){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public boolean isEmpty(){
        return (email == null || password == null);
    }
}
