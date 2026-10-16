package dev.carlos.smartpantry.entities.enums;

public enum StatusIngrediente {
    DISPONIVEL("disponivel"),
    ESGOTADO("esgotado"),
    VENCIDO("vencido");

    private final String status;

    StatusIngrediente(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
