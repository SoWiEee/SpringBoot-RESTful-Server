package org.example.springnewbie.Dao;

import org.example.springnewbie.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Repository
public class UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<User> UserRowMapper = new UserRowMapper();

    public void addUser(User user) {
        String sql = "INSERT INTO User(name, email, password) VALUES (:name, :email, :password)";
        
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("password", user.getPasswd());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        return namedParameterJdbcTemplate.queryForObject(sql, params, UserRowMapper);
    }

    public void fixUser(User user){
        String sql = "UPDATE User SET name = :name, password = :password WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("password", user.getPasswd());
        params.put("email", user.getEmail());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteUser(String email) {
        String sql = "DELETE FROM User WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException{
            User user = new User();
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPasswd(rs.getString("password"));
            return user;
        }
    }
}
