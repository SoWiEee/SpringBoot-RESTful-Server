package org.example.springnewbie.Mapper;

import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.ReqDTO.FixUserDTO;

public class FixUserDTO_Mapper {
    public UserDTO Mapping(FixUserDTO addUserDTO) {
        UserDTO dto = new UserDTO();
        dto.setName(addUserDTO.getName());
        dto.setEmail(addUserDTO.getEmail());
        dto.setPassword(addUserDTO.getPassword());
        return dto;
    }
}
