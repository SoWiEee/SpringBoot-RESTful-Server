package org.example.springnewbie.RspDTO;

import org.example.springnewbie.DTO.UserDTO;

public class GetUser_rsp extends Common_Rsp {
    private UserDTO data;

    public void setData(UserDTO user){
        this.data = user;
    }
}
