package org.example.springnewbie.ReqDTO;

public class AddUserDTO {
    private String name;
    private String email;
    private String password;

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public boolean isEmpty(){
        return (name == null || email == null || password == null);
    }
}
