package com.timal.app.rutas.services;

import com.timal.app.rutas.models.Camion;
import com.timal.app.rutas.models.Chofer;
import com.timal.app.rutas.models.Ruta;

import java.util.List;

public interface IRutasService extends IService<Ruta>{
    List<Camion> listarCamiones();
    List<Chofer> listarChoferes();
    Long guardarReturnId(Ruta ruta);
}
