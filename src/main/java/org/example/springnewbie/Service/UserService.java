package org.example.springnewbie.Service;

import org.example.springnewbie.Dao.UserDao;
import org.example.springnewbie.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserDao userDao;

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    }

    public void fixUser(User user) {
        userDao.fixUser(user);
    }

    public void deleteUser(String email) {
        userDao.deleteUser(email);
    }
}




