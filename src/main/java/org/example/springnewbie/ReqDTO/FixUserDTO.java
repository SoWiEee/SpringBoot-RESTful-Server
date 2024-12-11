package org.example.springnewbie.ReqDTO;

public class FixUserDTO {
    private String new_name;
    private String new_email;
    private String new_password;

    public String getName() { return new_name; }
    public String getEmail() { return new_email; }
    public String getPassword() { return new_password; }

    public boolean isEmpty(){
        return (new_password == null);
    }
}
