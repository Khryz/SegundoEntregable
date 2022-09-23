package com.wizeline.DTO;

public class MiResponseDTO {
    /* Patron de diseño creacional: Builder */
    private String status;
    private String code;

    private ErrorDTO errors = new ErrorDTO();

    private MiResponseDTO(MiResponseDTOBuilder builder){
        this.status = builder.status;
        this.code = builder.code;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public ErrorDTO getErrors() {
        return errors;
    }

    /* Clase interna dentro de otra clase: MiResponseDTOBuilder */
    public static final class MiResponseDTOBuilder {
        private String status;
        private String code;

        private ErrorDTO errors = new ErrorDTO();

        public MiResponseDTOBuilder(){
        }

        public MiResponseDTOBuilder conStatus(String status){
            this.status = status;
            return this;
        }

        public MiResponseDTOBuilder conCodigo(String status){
            this.status = status;
            return this;
        }

        public MiResponseDTOBuilder conError(ErrorDTO errors){
            this.errors = errors;
            return this;
        }

        public MiResponseDTO build(){
            MiResponseDTO miResponseDTO = new MiResponseDTO(this);
            return miResponseDTO;
        }
    }
}
