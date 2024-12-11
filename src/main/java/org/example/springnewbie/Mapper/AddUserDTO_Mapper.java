package org.example.springnewbie.Mapper;

import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.ReqDTO.AddUserDTO;

public class AddUserDTO_Mapper {

    public UserDTO Mapping(AddUserDTO fixUserDTO) {
        UserDTO dto = new UserDTO();
        dto.setName(fixUserDTO.getName());
        dto.setEmail(fixUserDTO.getEmail());
        dto.setPassword(fixUserDTO.getPassword());
        return dto;
    }
}
