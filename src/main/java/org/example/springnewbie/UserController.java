package org.example.springnewbie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private Map<String, User> userDB = new HashMap<>();

    @PostMapping("/add_user")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody Map<String, String> user, @RequestHeader("TimeStamp") String timeStamp){
        Map<String, Object> rsp = new HashMap<>();
        String name = user.get("name");
        String email = user.get("email");
        String passwd = user.get("passwd");

        // check input
        if(name==null || email==null || passwd==null){
            rsp.put("status", 31);
            rsp.put("message", "[X] Missing parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }
        if(userDB.containsKey(email)){
            rsp.put("status", 21);
            rsp.put("message", "[X] User already exists");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }
        try{
            passwd = User.hashPasswd(passwd);
        } catch (NoSuchAlgorithmException e) {
            rsp.put("status", 30);
            rsp.put("message", "[X] Incorrect parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        User newUser =  new User(name, email, passwd);
        userDB.put(email, newUser);

        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Update Successful");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }
}
