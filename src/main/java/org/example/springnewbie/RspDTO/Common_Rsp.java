package org.example.springnewbie.RspDTO;

public class Common_Rsp {
    private String rsp_msg;
    private int rsp_code;

    public String getRsp_msg() { return rsp_msg; }
    public int getRsp_code() { return rsp_code; }

    public void SUCCESS(){
        this.rsp_code = 20;
        this.rsp_msg = "Success";
    }

    public void PARAMS_MISSING(){
        this.rsp_code = 31;
        this.rsp_msg = "Missing parameters";
    }

    public void PASSWD_INCORRECT(){
        this.rsp_code = 30;
        this.rsp_msg = "Incorrect password";
    }

    public void USER_EXISTED() {
        this.rsp_code = 21;
        this.rsp_msg = "User already exist";
    }

    public void EMAIL_NOT_FOUND() {
        this.rsp_code = 40;
        this.rsp_msg = "Email not found";
    }
}