package com.timal.app.rutas.repositories;

import com.timal.app.rutas.models.Ruta;

import java.sql.SQLException;

public interface IRutasRepository extends IRepository<Ruta> {
    Long guardarReturnId(Ruta ruta) throws SQLException;
}
