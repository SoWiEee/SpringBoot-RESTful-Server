package org.example.springnewbie.Controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.apache.commons.validator.routines.EmailValidator;
import org.example.springnewbie.DTO.UserDTO;
import org.example.springnewbie.JwtToken;
import org.example.springnewbie.ReqDTO.AddUserDTO;
import org.example.springnewbie.ReqDTO.FixUserDTO;
import org.example.springnewbie.ReqDTO.LoginUserDTO;
import org.example.springnewbie.RspDTO.Common_Rsp;
import org.example.springnewbie.RspDTO.GetUser_rsp;
import org.example.springnewbie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Gson GSON = new Gson();

    @PostMapping("/post/login")
    public ResponseEntity login(@RequestBody @Valid LoginUserDTO user) {
        Common_Rsp rsp = new Common_Rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UserDTO tempUser = userService.getUserByEmail(user.getEmail());

        if(tempUser == null) {
            rsp.USER_NOT_FOUND();
        }else if(!tempUser.getPassword().equals(user.getPassword())) {
            rsp.PASSWD_INCORRECT();
        }else {
            headers.set("token", JwtToken.generateToken(user.getName()));
            rsp.SUCCESS();
        }

        return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
    }

    @PostMapping("/post/add_user")
    public ResponseEntity addUser(@RequestBody @Valid AddUserDTO req) {
        Common_Rsp rsp = new Common_Rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(req == null || req.paramEmpty()) {
            rsp.PARAMS_MISSING();

        }else if(!req.isEmailValid()){
            rsp.PARAMS_INCORRECT();

        }else if(userService.getUserByEmail(req.getEmail()) != null){
            rsp.USER_EXISTED();

        }else{
            userService.addUser(req);
            rsp.SUCCESS();
        }
        return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
    }

    @GetMapping("/get/get_user")
    public ResponseEntity getUser(@RequestHeader("email") String email) {
        GetUser_rsp rsp = new GetUser_rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        EmailValidator validator = EmailValidator.getInstance();
        UserDTO user = userService.getUserByEmail(email);

        if (email == null || email.isEmpty()) {
            rsp.PARAMS_MISSING();
        }else if (!validator.isValid(email)){
            rsp.PARAMS_INCORRECT();
        }else if (user == null) {
            rsp.EMAIL_NOT_FOUND();
        }else{
            rsp.SUCCESS();
            rsp.setData(user);
        }

        return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
    }

    @PutMapping("/put/fix_user")
    public ResponseEntity fixUser(@RequestBody @Valid FixUserDTO user, @RequestHeader String email) {
        Common_Rsp rsp = new Common_Rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        EmailValidator validator = EmailValidator.getInstance();
        UserDTO updatedUser = userService.getUserByEmail(user.getEmail());

        if(email == null || user.isEmpty()){
            rsp.PARAMS_MISSING();
        }else if(!validator.isValid(email) || !user.isEmailValid()){
            rsp.PARAMS_INCORRECT();
        }else if(updatedUser==null){
            rsp.EMAIL_NOT_FOUND();
        }else if (!user.getPassword().equals(updatedUser.getPassword())) {
            rsp.PASSWD_INCORRECT();
        }else{
            rsp.SUCCESS();
            userService.fixUser(user);
        }

        return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/delete_user")
    public ResponseEntity deleteUser(@RequestHeader("email") String email, @RequestHeader String password) {
        Common_Rsp rsp = new Common_Rsp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        EmailValidator validator = EmailValidator.getInstance();
        UserDTO user = userService.getUserByEmail(email);

        if(email == null || password == null){
            rsp.PARAMS_MISSING();
        }else if (!validator.isValid(email)){
            rsp.PARAMS_INCORRECT();
        }else if(user == null){
            rsp.EMAIL_NOT_FOUND();
        }else if (!user.getPassword().equals(password)) {
            rsp.PASSWD_INCORRECT();
        }else{
            userService.deleteUser(email);
            rsp.SUCCESS();
        }

        headers.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        headers.add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie, Authorization, Custom-Header,token,priority,*");
        return new ResponseEntity<>(GSON.toJson(rsp), headers, HttpStatus.OK);
    }
}
