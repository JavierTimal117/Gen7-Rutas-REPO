package com.timal.app.rutas.models.enums;

public enum Marcas {
    VOLVO("Volvo"),
    ALLIANCE("Alliance"),
    FORD("Ford"),
    MERCEDES_BENZ("Mercedes Benz"),
    DINA("Dina");

    //ATRIBUTOS
    private String descripcion;

    //constructor
    Marcas(String descripcion) {
        this.descripcion = descripcion;
    }

    //getters and setters

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //metodo que recibe como argumento un string
    //y devuelve una Marca
    public static Marcas getFromString(String estatus){
        switch (estatus) {
            case "Volvo":
                return VOLVO;
            case "Alliance":
                return ALLIANCE;
            case "Ford":
                return FORD;
            case "Dina":
                return DINA;
            default:
                return null;
        }
    }
}
