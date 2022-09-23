package com.wizeline.DTO;

import java.util.Map;

public class UserDTO {
    /*Encapsulacion de almenos una clase: Clase UserDTO*/
    /*Uso de por lo menos dos beans: Bean UserDTO*/
    private String user;
    private String pass;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UserDTO getParameters(Map<String, String> userParam){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser(userParam.get("user"));
        userDTO.setPass(userParam.get("pass"));
        return userDTO;
    }
}
