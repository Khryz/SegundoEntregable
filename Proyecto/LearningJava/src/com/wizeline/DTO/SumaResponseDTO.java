package com.wizeline.DTO;

public class SumaResponseDTO {
    private long cantidadArray;
    private Double sumaTotal;

    public SumaResponseDTO(){}

    public SumaResponseDTO(long cantidadArray, Double sumaTotal){
        this.cantidadArray = cantidadArray;
        this.sumaTotal = sumaTotal;
    }

    public long getCantidadArray() {
        return cantidadArray;
    }

    public void setCantidadArray(long cantidadArray) {
        this.cantidadArray = cantidadArray;
    }

    public Double getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(Double sumaTotal) {
        this.sumaTotal = sumaTotal;
    }
}
