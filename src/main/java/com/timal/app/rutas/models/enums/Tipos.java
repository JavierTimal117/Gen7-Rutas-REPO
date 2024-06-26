package com.timal.app.rutas.models.enums;

public enum Tipos {

    TRAILER("Trailer"),
    TORTON("Torton"),
    DOBLE_REMOLQUE("Doble remolque"),
    DOBLE_REMOLQUE2("Doble remolque2"),
    DOBLE_REMOLQUE3("Doble remolque3"),
    VOLTEO("Volteo"),
    SEMI_REMOLQUE("Semi remolque");




    private String descripcion;





    Tipos(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static Tipos getFromString(String estatus){
        switch (estatus) {
            case "Trailer":
                return TRAILER;
            case "Torton":
                return TORTON;
            case "Doble remolque":
                return DOBLE_REMOLQUE;
            case "Volteo":
                return VOLTEO;
            case "Semi remolque":
                return SEMI_REMOLQUE;
            default:
                return null;
        }
    }



}
