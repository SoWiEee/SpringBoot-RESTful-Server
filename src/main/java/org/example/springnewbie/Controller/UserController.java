package org.example.springnewbie.Controller;

import com.google.gson.Gson;
import org.apache.commons.validator.routines.EmailValidator;
import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.Mapper.AddUserDTO_Mapper;
import org.example.springnewbie.ReqDTO.AddUserDTO;
import org.example.springnewbie.ReqDTO.FixUserDTO;
import org.example.springnewbie.RspDTO.Common_Rsp;
import org.example.springnewbie.RspDTO.GetUser_rsp;
import org.example.springnewbie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Gson GSON = new Gson();

    @PostMapping("/post/add_user")
    public ResponseEntity addUser(@RequestBody AddUserDTO req) {
        Common_Rsp rsp = new Common_Rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AddUserDTO_Mapper mapper = new AddUserDTO_Mapper();

        if(req == null || req.paramEmpty()) {
            rsp.PARAMS_MISSING();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);

        }else if(!req.isEmailValid()){
            rsp.PARAMS_INCORRECT();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);

        }else if(userService.getUserByEmail(req.getEmail()) != null){
            rsp.USER_EXISTED();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);

        }else{
            UserDTO userDTO = mapper.Mapping(req);
            userService.addUser(userDTO);
            rsp.SUCCESS();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
        }
    }

    @GetMapping("/get/get_user")
    public ResponseEntity getUser(@RequestHeader("email") String email) {
        GetUser_rsp rsp = new GetUser_rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        EmailValidator validator = EmailValidator.getInstance();

        if (email == null || email.isEmpty()) {
            rsp.PARAMS_MISSING();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);
        }

        if(!validator.isValid(email)){
            rsp.PARAMS_INCORRECT();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);
        }

        UserDTO user = userService.getUserByEmail(email);

        if (user == null) {
            rsp.EMAIL_NOT_FOUND();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.NOT_FOUND);
        }

        rsp.SUCCESS();
        rsp.setData(user);
        return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
    }

    @PutMapping("/put/fix_user")
    public ResponseEntity fixUser(@RequestBody FixUserDTO user, @RequestHeader String email) {
        Common_Rsp rsp = new Common_Rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        EmailValidator validator = EmailValidator.getInstance();

        if(email==null || user.isEmpty()){
            rsp.PARAMS_MISSING();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);
        }

        if(!validator.isValid(email) || !user.isEmailValid()){
            rsp.PARAMS_INCORRECT();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);
        }

        UserDTO updatedUser = userService.getUserByEmail(user.getEmail());

        if(updatedUser==null){
            rsp.EMAIL_NOT_FOUND();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.NOT_FOUND);
        }

        boolean passwdMatch = user.getPassword().equals(updatedUser.getPassword());
        if (!passwdMatch) {
            rsp.PASSWD_INCORRECT();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);
        }

        rsp.SUCCESS();
        userService.fixUser(user);
        return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/delete_user")
    public ResponseEntity deleteUser(@RequestHeader("email") String email, @RequestHeader String password) {
        Common_Rsp rsp = new Common_Rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        EmailValidator validator = EmailValidator.getInstance();

        if(email == null || password == null){
            rsp.PARAMS_MISSING();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);
        }else if (!validator.isValid(email)){
            rsp.PARAMS_INCORRECT();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);
        }

        UserDTO user = userService.getUserByEmail(email);

        if(user==null){
            rsp.EMAIL_NOT_FOUND();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.NOT_FOUND);
        }

        boolean passwdMatch = user.getPassword().equals(password);

        if (!passwdMatch) {
            rsp.PASSWD_INCORRECT();
            return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.BAD_REQUEST);
        }

        userService.deleteUser(email);
        rsp.SUCCESS();
        return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
    }
}
