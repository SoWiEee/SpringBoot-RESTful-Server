package org.example.springnewbie.Service;

import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.Dao.UserDao;
import org.example.springnewbie.Mapper.FixUserDTO_Mapper;
import org.example.springnewbie.Mapper.GetUserDTO_Mapper;
import org.example.springnewbie.Model.User;
import org.example.springnewbie.ReqDTO.FixUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserDao userDao;

    public void addUser(UserDTO userDTO) {
        userDao.addUser(userDTO);
    }

    public UserDTO getUserByEmail(String email){
        GetUserDTO_Mapper mapper = new GetUserDTO_Mapper();
        User user = userDao.getUserByEmail(email);
        if(user == null){
            return null;
        }else{
            UserDTO dto = mapper.Mapping(user);
            return dto;
        }
    }

    public void fixUser(FixUserDTO user) {
        FixUserDTO_Mapper mapper = new FixUserDTO_Mapper();
        UserDTO dto = mapper.Mapping(user);
        userDao.fixUser(dto);
    }

    public void deleteUser(String email) {
        userDao.deleteUser(email);
    }
}




