package com.wizeline.BO;

import com.wizeline.DTO.MiResponseDTO;
import com.wizeline.DTO.ResponseDTO;

/* Uso de por lo menos una interfaz propia: UserBO */
public interface UserBO {
    ResponseDTO createUser(String user, String pass);
    MiResponseDTO loginUser(String user, String pass);
}
