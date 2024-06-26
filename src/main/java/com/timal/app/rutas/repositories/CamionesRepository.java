package com.timal.app.rutas.repositories;

import com.timal.app.rutas.models.Camion;
import com.timal.app.rutas.models.enums.Marcas;
import com.timal.app.rutas.models.enums.Tipos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamionesRepository implements IRepository<Camion>{
    private Connection conn;
    public CamionesRepository(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Camion> listar() throws SQLException {
        List<Camion> camiones = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM camiones")) {
            while (rs.next()) {
                Camion a = getCamion(rs);
                camiones.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return camiones;
    }

    @Override
    public Camion getById(Long id) throws SQLException {
        Camion camion = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM camiones WHERE id_camion = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    camion = getCamion(rs);
                }
            }
        }
        return camion;
    }

    @Override
    public void guardar(Camion camion) throws SQLException {
        String sql= "";
        if (camion.getId() != null && camion.getId() > 0) {
            sql = "update camiones set MATRICULA=?, TIPO_CAMION=?, " +
                    "MODELO=?, " +
                    "MARCA=?, CAPACIDAD=?, " +
                    "KILOMETRAJE=?, DISPONIBILIDAD=? where ID_CAMION=?";
        } else {
            sql = "insert into camiones (ID_CAMION, MATRICULA, " +
                    "TIPO_CAMION, MODELO, MARCA, CAPACIDAD, KILOMETRAJE, " +
                    "DISPONIBILIDAD) values (SEQUENCE2.NEXTVAL,?,?,?,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (camion.getId() != null && camion.getId() > 0) {
                stmt.setString(1, camion.getMatricula());
                stmt.setString(2, camion.getTipoCamion().getDescripcion());
                stmt.setInt(3, camion.getModelo());
                stmt.setString(4, camion.getMarca().getDescripcion());
                stmt.setInt(5, camion.getCapacidad());
                stmt.setDouble(6, camion.getKilometraje());
                stmt.setBoolean(7, camion.getDisponibilidad());
                stmt.setLong(8, camion.getId());
            }
            else {
                stmt.setString(1, camion.getMatricula());
                stmt.setString(2, camion.getTipoCamion().getDescripcion());
                stmt.setInt(3, camion.getModelo());
                stmt.setString(4, camion.getMarca().getDescripcion());
                stmt.setInt(5, camion.getCapacidad());
                stmt.setDouble(6, camion.getKilometraje());
                stmt.setBoolean(7, camion.getDisponibilidad());
            }
            stmt.executeUpdate();
        }
    }
    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from camiones where id_camion=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    //modelado de datos
    public List<Camion> personalizado(){
        List<Camion> a = new ArrayList<>();
        String sql = "select chofer.id, camion.matricul from cacmiones inner join choferes where ---- group bu";

        return a;
    }

    private Camion getCamion(ResultSet rs) throws SQLException {
        Camion a = new Camion();
        a.setId(rs.getLong("ID_CAMION"));
        a.setMatricula(rs.getString("matricula"));
        a.setTipoCamion(Tipos.getFromString(rs.getString("TIPO_CAMION")));
        a.setModelo(rs.getInt("modelo"));
        a.setMarca(Marcas.getFromString(rs.getString("marca")));
        a.setCapacidad(rs.getInt("capacidad"));
        a.setKilometraje(rs.getDouble("kilometraje"));
        a.setDisponibilidad(rs.getBoolean("disponibilidad"));
        return a;
    }
}
