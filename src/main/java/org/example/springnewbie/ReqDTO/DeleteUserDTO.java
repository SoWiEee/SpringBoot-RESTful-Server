package org.example.springnewbie.ReqDTO;

public class DeleteUserDTO {
    private String email;
    private String password;

    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public boolean isEmpty(){
        return (email == null || password == null);
    }
}
