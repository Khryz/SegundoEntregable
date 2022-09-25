package com.wizeline.DTO;

import java.util.Map;

public class SumaRequestDTO {
    private Double numero1;
    private Double numero2;

    public Double getNumero1() {
        return numero1;
    }

    public void setNumero1(Double numero1) {
        this.numero1 = numero1;
    }

    public Double getNumero2() {
        return numero2;
    }

    public void setNumero2(Double numero2) {
        this.numero2 = numero2;
    }

    public SumaRequestDTO getRequest(Map<String, String> requestParams){
        SumaRequestDTO dto = new SumaRequestDTO();

        dto.setNumero1(Double.parseDouble(requestParams.get("numero1")));
        dto.setNumero2(Double.parseDouble(requestParams.get("numero2")));
        return dto;
    }
}
