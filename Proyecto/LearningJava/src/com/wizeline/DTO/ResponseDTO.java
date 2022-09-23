package com.wizeline.DTO;

public class ResponseDTO {
    /*Encapsulacion de almenos una clase: Clase ResponseDTO*/
    /*Uso de por lo menos dos beans: Bean ResponseDTO*/
    private String status;
    private String code;

    /* Sobrecarga de almenos un constructor: ResponseDTO */
    public ResponseDTO(){
    }

    public ResponseDTO(String status, String code){
        this.status = status;
        this.code = code;
    }

    private ErrorDTO errors = new ErrorDTO();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ErrorDTO getErrors() {
        return errors;
    }

    public void setErrors(ErrorDTO errors) {
        this.errors = errors;
    }
}
