package com.timal.app.rutas.services;

import com.timal.app.rutas.models.Camion;
import com.timal.app.rutas.repositories.CamionesRepository;
import com.timal.app.rutas.repositories.IRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CamionesService implements IService<Camion>{

    private IRepository<Camion> camionesRepo;
    public CamionesService(Connection conexion) {
        camionesRepo = new CamionesRepository(conexion);
    }


    @Override
    public List<Camion> listar() {
        try {
            return camionesRepo.listar();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Camion> getById(Long id) {
        try {
            return Optional.ofNullable(camionesRepo.getById(id));
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    public void guardar(Camion camion) {
        try {
            camionesRepo.guardar(camion);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            camionesRepo.eliminar(id);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage(), throwables.getCause());
        }
    }
}
