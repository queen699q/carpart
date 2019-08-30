package com.xiupeilian.carpart.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken {
    private String validate;

    public UserToken(String username, String password,String validate){
        super(username,password);
        this.validate=validate;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }
}
