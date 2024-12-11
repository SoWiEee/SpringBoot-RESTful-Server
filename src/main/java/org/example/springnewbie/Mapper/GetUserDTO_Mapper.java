package org.example.springnewbie.Mapper;

import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.Model.User;

public class GetUserDTO_Mapper {

    public UserDTO Mapping(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPasswd());
        return dto;
    }
}
