package org.example.springnewbie.Dao;

import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.ReqDTO.AddUserDTO;
import org.example.springnewbie.ReqDTO.FixUserDTO;
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

    private final RowMapper<UserDTO> UserRowMapper = new UserRowMapper();

    public void addUser(AddUserDTO userDTO) {
        try {
            Connection conn = DriverManager.getConnection(CONNURL, username, password);

            String sql = "INSERT INTO User(name, email, password) VALUES (:name, :email, :password)";
            Map<String, Object> params = new HashMap<>();
            params.put("name", userDTO.getName());
            params.put("email", userDTO.getEmail());
            params.put("password", userDTO.getPassword());
            namedParameterJdbcTemplate.update(sql, params);

        } catch(SQLException e) {
            throw new RuntimeException("[X] Connect Database Failed", e);
        }
    }

    public UserDTO getUserByEmail(String email) {
        try {
            Connection conn = DriverManager.getConnection(CONNURL, username, password);
            String sql = "SELECT * FROM User WHERE email = :email";

            Map<String, Object> params = new HashMap<>();
            params.put("email", email);

            List<UserDTO> users = namedParameterJdbcTemplate.query(sql, params, UserRowMapper);
            return users.isEmpty() ? null : users.getFirst();
        } catch(SQLException e) {
            throw new RuntimeException("[X] Connect Database Failed", e);
        }

    }

    public void fixUser(FixUserDTO userDTO) {
        try {
            Connection conn = DriverManager.getConnection(CONNURL, username, password);

            String sql = "UPDATE User SET name = :name, password = :password WHERE email = :email";
            Map<String, Object> params = new HashMap<>();
            params.put("name", userDTO.getName());
            params.put("password", userDTO.getPassword());
            params.put("email", userDTO.getEmail());
            namedParameterJdbcTemplate.update(sql, params);
        } catch(SQLException e) {
            throw new RuntimeException("[X] Connect Database Failed", e);
        }

    }

    public void deleteUser(String email) {
        try {
            Connection conn = DriverManager.getConnection(CONNURL, username, password);
            String sql = "DELETE FROM User WHERE email = :email";
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            namedParameterJdbcTemplate.update(sql, params);
        } catch(SQLException e) {
            throw new RuntimeException("[X] Connect Database Failed", e);
        }

    }

    public static class UserRowMapper implements RowMapper<UserDTO> {
        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
            UserDTO dto = new UserDTO();
            dto.setName(rs.getString("name"));
            dto.setEmail(rs.getString("email"));
            dto.setPassword(rs.getString("password"));
            return dto;
        }
    }
}
