package com.timal.app.rutas.services;

import com.timal.app.rutas.models.Camion;
import com.timal.app.rutas.models.Chofer;
import com.timal.app.rutas.models.Ruta;
import com.timal.app.rutas.repositories.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RutasService implements IRutasService{

    private IRepository<Chofer> choferesRepo;
    private IRepository<Camion> camionesRepo;
    private IRutasRepository rutasRepository;

    public RutasService(Connection connection) {
        this.choferesRepo = new ChoferesRepository(connection);
        this.camionesRepo = new CamionesRepository(connection);
        this.rutasRepository = new RutasRepository(connection);
    }

    @Override
    public List<Camion> listarCamiones() {
        try {
            return camionesRepo.listar();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public List<Chofer> listarChoferes() {
        try {
            return choferesRepo.listar();
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long guardarReturnId(Ruta ruta) {
        try {
            return rutasRepository.guardarReturnId(ruta);
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Ruta> listar() {
        return null;
    }

    @Override
    public Optional<Ruta> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public void guardar(Ruta ruta) {

    }

    @Override
    public void eliminar(Long id) {

    }
}
