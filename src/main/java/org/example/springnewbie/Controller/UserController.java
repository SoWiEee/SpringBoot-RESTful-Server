package org.example.springnewbie.Controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.Mapper.AddUserDTO_Mapper;
import org.example.springnewbie.ReqDTO.AddUserDTO;
import org.example.springnewbie.ReqDTO.DeleteUserDTO;
import org.example.springnewbie.ReqDTO.FixUserDTO;
import org.example.springnewbie.RspDTO.Common_Rsp;
import org.example.springnewbie.RspDTO.GetUser_rsp;
import org.example.springnewbie.Service.UserService;
import org.example.springnewbie.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Gson GSON = new Gson();

    @PostMapping("/post/add_user")
    public ResponseEntity addUser(@RequestBody AddUserDTO req) {
        Common_Rsp rsp = new Common_Rsp();
        AddUserDTO_Mapper mapper = new AddUserDTO_Mapper();

        if(req.isEmpty()){
            rsp.PARAMS_MISSING();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.BAD_REQUEST);
        }else if(userService.getUserByEmail(req.getEmail()) != null){
            rsp.USER_EXISTED();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.BAD_REQUEST);
        }else{
            UserDTO userDTO = mapper.Mapping(req);
            userService.addUser(userDTO);
            rsp.SUCCESS();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.OK);
        }
    }

    @GetMapping("/get/get_user")
    public ResponseEntity getUser(@RequestHeader("email") @Valid String email) {
        GetUser_rsp rsp = new GetUser_rsp();

        if (email == null || email.isEmpty()) {
            rsp.PARAMS_MISSING();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.BAD_REQUEST);
        }

        UserDTO user = userService.getUserByEmail(email);

        if (user == null) {
            rsp.EMAIL_NOT_FOUND();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.NOT_FOUND);
        }

        rsp.SUCCESS();
        rsp.setData(user);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @PutMapping("/put/fix_user")
    public ResponseEntity fixUser(@RequestBody FixUserDTO user, @RequestHeader String email) {
        Common_Rsp rsp = new Common_Rsp();

        if(email==null || user.isEmpty()){
            rsp.PARAMS_MISSING();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.BAD_REQUEST);
        }

        UserDTO updatedUser = userService.getUserByEmail(user.getEmail());

        if(updatedUser==null){
            rsp.EMAIL_NOT_FOUND();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.NOT_FOUND);
        }

        rsp.SUCCESS();
        userService.fixUser(user);
        return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.OK);
    }

    @DeleteMapping("/delete/delete_user")
    public ResponseEntity deleteUser(@RequestHeader DeleteUserDTO req) {
        Common_Rsp rsp = new Common_Rsp();
        String email = req.getEmail();
        String password = req.getPassword();

        if(req.isEmpty()){
            rsp.PARAMS_MISSING();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.BAD_REQUEST);
        }

        UserDTO user = userService.getUserByEmail(email);

        if(user==null){
            rsp.EMAIL_NOT_FOUND();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.NOT_FOUND);
        }

        boolean passwdMatch = user.getPassword().equals(password);

        if (!passwdMatch) {
            rsp.PASSWD_INCORRECT();
            return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.BAD_REQUEST);
        }

        userService.deleteUser(email);
        rsp.SUCCESS();
        return new ResponseEntity<>(GSON.toJson(rsp), HttpStatus.OK);
    }
}
