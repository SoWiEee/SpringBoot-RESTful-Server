package org.example.springnewbie.Controller;

import jakarta.validation.Valid;
import org.example.springnewbie.Service.UserService;
import org.example.springnewbie.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    // ok
    @PostMapping("/post/add_user")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody @Valid User user) {
        Map<String, Object> rsp = new HashMap<>();

        // check input - ok
        if(user.getName()==null || user.getEmail()==null || user.getPasswd()==null){
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }
        // check user exist - ok
        if (userService.getUserByEmail(user.getEmail()) != null) {
            rsp.put("rsp_code", 21);
            rsp.put("rsp_msg", "[X] User already exists");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        // success !
        userService.addUser(user);
        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Update Successful");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    // ok ?
    @GetMapping("/get/get_user")
    public ResponseEntity<Map<String, Object>> getUser(@RequestHeader("email") @Valid String email) {
        Map<String, Object> rsp = new HashMap<>();

        if (email == null || email.isEmpty()) {
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }
        // 把資料做成 response
        User user = userService.getUserByEmail(email);

        // email exist - ok
        if (user == null) {
            rsp.put("rsp_code", 40);
            rsp.put("rsp_msg", "[X] Email not found");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.NOT_FOUND);
        }

        // success !
        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Fetch Successful");
        rsp.put("data", user);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @PutMapping("/put/fix_user")
    public ResponseEntity<Map<String, Object>> fixUser(@RequestBody @Valid User user, @RequestHeader String email) {
        Map<String, Object> rsp = new HashMap<>();

        // check input - ok
        if(email==null){
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        User updatedUser = userService.getUserByEmail(user.getEmail());

        // email exist - ok
        if(updatedUser==null){
            rsp.put("rsp_code", 40);
            rsp.put("rsp_msg", "[X] Email not found");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.NOT_FOUND);
        }

        // success !
        Map<String, String> userData = new HashMap<>();
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());

        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Fetch Successful");
        rsp.put("data", userData);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/delete_user")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestHeader Map<String, String> userData) {
        Map<String, Object> rsp = new HashMap<>();
        String email = userData.get("email");

        // ok
        if(email==null || email.isEmpty()){
            rsp.put("rsp_code", 31);
            rsp.put("rsp_msg", "[X] Missing parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByEmail(email);

        if(user==null){
            rsp.put("rsp_code", 40);
            rsp.put("rsp_msg", "[X] Email not found");
            rsp.put("data", null);
            return new ResponseEntity<>(rsp, HttpStatus.NOT_FOUND);
        }

        String password = userData.get("password");
        String truePasswd = user.getPasswd();

        if (password == null || password.isEmpty() || !User.hashPasswd(password).equals(truePasswd)) {
            rsp.put("rsp_code", 30);
            rsp.put("rsp_msg", "[X] Incorrect parameters");
            return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
        }

        userService.deleteUser(email);
        rsp.put("rsp_code", 20);
        rsp.put("rsp_msg", "[V] Delete Successful");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }
}
