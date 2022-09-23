package com.wizeline.BO;

import com.wizeline.DAO.UserDAO;
import com.wizeline.DAO.UserDAOImpl;
import com.wizeline.DTO.ErrorDTO;
import com.wizeline.DTO.MiResponseDTO;
import com.wizeline.DTO.ResponseDTO;
import com.wizeline.utils.Utils;

import java.util.logging.Logger;

public class UserBOImpl implements UserBO{
    private static final Logger log = Logger.getLogger(UserBOImpl.class.getName());

    @Override
    public ResponseDTO createUser(String user, String pass) {
        log.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = null;
        String result = "fail";
        if (Utils.validarValorNulo(user) && Utils.validarValorNulo(pass)) {
            UserDAO userDao = new UserDAOImpl();
            result = userDao.createUser(user, pass);

            response = new ResponseDTO("OK000", result);
            /*response.setCode("OK000");
            response.setStatus(result);*/
        }else {
            response = new ResponseDTO();

            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorDTO("ER001","Error al crear usuario"));
        }
        return response;
    }

    @Override
    public MiResponseDTO loginUser(String user, String pass) {
        log.info("Inicia procesamiento en capa de negocio");
        MiResponseDTO response = null;
        String result = "";
        if (Utils.validarValorNulo(user) && Utils.validarValorNulo(pass)) {
            UserDAO userDao = new UserDAOImpl();
            result = userDao.login(user, pass);
        }
        if("success".equals(result)) {
            response = new MiResponseDTO.MiResponseDTOBuilder()
                    .conCodigo("OK000")
                    .conStatus(result)
                    .build();
        } else {
            response = new MiResponseDTO.MiResponseDTOBuilder()
                    .conCodigo("ER001")
                    .conError(new ErrorDTO("ER001",result))
                    .conStatus("fail")
                    .build();
        }
        return response;
    }
}
