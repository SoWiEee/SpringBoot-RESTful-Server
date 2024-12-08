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

    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    @PostMapping("/v1/add_user")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody Map<String, String> user) {
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

        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Update Successful");
        userDB.put(email, newUser);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @GetMapping("/v1/get_user")
    // user email to fetch user
    public ResponseEntity<Map<String, Object>> getUser(@RequestHeader("email") String email){
        Map<String, Object> rsp = new HashMap<>();

        if(email==null || email.isEmpty()){
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        if (!isEmailValid(email)) {
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

    @PutMapping("/v1/fix_user")
    public ResponseEntity<Map<String, Object>> fixUser(@RequestBody Map<String, String> updateData, @RequestHeader("email") String email){
        Map<String, Object> rsp = new HashMap<>();

        if(email==null || email.isEmpty()){
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        if (!isEmailValid(email)) {
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

        String newName = updateData.get("new_name");
        String newEmail = updateData.get("new_email");
        String password = updateData.get("passwd");
        String truePasswd = user.getPasswd();

        try {
            if (password == null || password.isEmpty() || !User.hashPasswd(password).equals(truePasswd)) {
                rsp.put("rsp_code", 50);
                rsp.put("rsp_msg", "[X] Incorrect password");
                return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchAlgorithmException e) {
            rsp.put("rsp_code", 30);
            rsp.put("rsp_msg", "[X] Incorrect parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        // update user info
        if (newName != null && !newName.isEmpty()) {
            user.setName(newName);
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            user.setEmail(newEmail);
            userDB.remove(email);
            userDB.put(newEmail, user);
        }else{
            userDB.put(email, user);
        }

        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Fix Successful");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @DeleteMapping("/v1/delete_user")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestHeader Map<String, String> updateData){
        Map<String, Object> rsp = new HashMap<>();
        String email = updateData.get("email");

        // ok
        if(email==null || email.isEmpty()){
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        if (!isEmailValid(email)) {
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

        String password = updateData.get("passwd");
        String truePasswd = user.getPasswd();

        try {
            if (password == null || password.isEmpty() || !User.hashPasswd(password).equals(truePasswd)) {
                rsp.put("rsp_code", 30);
                rsp.put("rsp_msg", "[X] Incorrect parameters");
                return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchAlgorithmException e) {
            rsp.put("rsp_code", 30);
            rsp.put("rsp_msg", "[X] Incorrect parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        userDB.remove(email);
        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Delete Successful");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }
}
