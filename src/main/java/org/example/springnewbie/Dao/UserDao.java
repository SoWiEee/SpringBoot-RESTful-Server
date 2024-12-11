package org.example.springnewbie.Dao;

import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CONNURL = "jdbc:mysql://localhost:3306/myjdbc";
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private final RowMapper<User> UserRowMapper = new UserRowMapper();

    public void addUser(UserDTO userDTO) {
        try{
            Connection conn = DriverManager.getConnection(CONNURL, username, password);
            String sql = "INSERT INTO User(name, email, password) VALUES (:name, :email, :password)";
            Map<String, Object> params = new HashMap<>();
            params.put("name", userDTO.getName());
            params.put("email", userDTO.getEmail());
            params.put("password", userDTO.getPassword());
            namedParameterJdbcTemplate.update(sql, params);
        }catch(SQLException e){
            throw new RuntimeException("[X] Connect Database Failed", e);
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = :email";

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        List<User> users = namedParameterJdbcTemplate.query(sql, params, UserRowMapper);
        return users.isEmpty() ? null : users.getFirst();
    }

    public void fixUser(UserDTO userDTO) {
        String sql = "UPDATE User SET name = :name, password = :password WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("name", userDTO.getName());
        params.put("password", userDTO.getPassword());
        params.put("email", userDTO.getEmail());
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
