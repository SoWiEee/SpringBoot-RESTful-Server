package org.example.springnewbie.Service;

import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.Dao.UserDao;
import org.example.springnewbie.ReqDTO.AddUserDTO;
import org.example.springnewbie.ReqDTO.FixUserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final UserDao userDao = new UserDao();

    public void addUser(AddUserDTO dto) {
        userDao.addUser(dto);
    }

    public UserDTO getUserByEmail(String email) {
        UserDTO user = userDao.getUserByEmail(email);
        if(user == null){
            return null;
        }else{
            return user;
        }
    }

    public void fixUser(FixUserDTO dto) {
        userDao.fixUser(dto);
    }

    public void deleteUser(String email) {
        userDao.deleteUser(email);
    }
}




