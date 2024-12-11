package org.example.springnewbie.ReqDTO;

import org.apache.commons.validator.routines.EmailValidator;

public class FixUserDTO {
    private String new_name;
    private String new_email;
    private String new_password;

    public String getName() { return new_name; }
    public String getEmail() { return new_email; }
    public String getPassword() { return new_password; }

    public boolean isEmailValid(){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(new_email);
    }

    public boolean isEmpty(){
        return (new_password == null);
    }
}
