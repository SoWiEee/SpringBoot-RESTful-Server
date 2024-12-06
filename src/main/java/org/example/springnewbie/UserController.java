package org.example.springnewbie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
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
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }
        if(userDB.containsKey(email)){
            rsp.put("rsp_code", 21);
            rsp.put("rsp_msg", "[X] User already exists");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }
        try{
            passwd = User.hashPasswd(passwd);
        } catch(NoSuchAlgorithmException e) {
            rsp.put("rsp_code", 30);
            rsp.put("rsp_msg", "[X] Incorrect parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        User newUser =  new User(name, email, passwd);
        userDB.put(email, newUser);

        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Update Successful");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @GetMapping("/get_user")
    // user email to fetch user
    public ResponseEntity<Map<String, Object>> getUser(@RequestHeader("TimeStamp") String timeStamp, @RequestHeader("email") String email){
        Map<String, Object> rsp = new HashMap<>();

        if(email==null || email.isEmpty()){
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern p = Pattern.compile(emailRegex);
        if (!p.matcher(email).matches()) {
            rsp.put("rsp_code", 30);
            rsp.put("rsp_msg", "[X] Incorrect parameters");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        User user = userDB.get(email);

        if(user==null){
            rsp.put("rsp_code", 40);
            rsp.put("rsp_msg", "[X] Email not found");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.NOT_FOUND);
        }

        // user exist
        Map<String, String> userData = new HashMap<>();
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Fetch Successful");
        rsp.put("data", userData);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

}
