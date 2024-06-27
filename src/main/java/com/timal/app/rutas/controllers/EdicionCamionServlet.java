package com.timal.app.rutas.controllers;

import com.timal.app.rutas.models.Camion;
import com.timal.app.rutas.models.enums.Marcas;
import com.timal.app.rutas.models.enums.Tipos;
import com.timal.app.rutas.services.CamionesService;
import com.timal.app.rutas.services.IService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet("/camiones/edicion")
public class EdicionCamionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Camion> service = new CamionesService(conn);
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e){
            id = 0L;
        }
        Camion camion = new Camion();
        if (id > 0) {
            Optional<Camion> o = service.getById(id);
            if (o.isPresent()) {
                camion = o.get();
                req.setAttribute("camion", camion);
                //obtener la lista de tipos desde el enumerador;
                List<Tipos> tipos = new ArrayList<>(EnumSet.allOf(Tipos.class));
                req.setAttribute("tipos", tipos);
                LocalDate fechaActual = LocalDate.now();
                List<Integer> modelos = IntStream.range(fechaActual.getYear() - 20, fechaActual.getYear() + 2).boxed().collect(Collectors.toList());
                req.setAttribute("modelos", modelos);
                //obtener la lista de marcas desde el enumerador;
                List<Marcas> marcas = new ArrayList<>(EnumSet.allOf(Marcas.class));
                req.setAttribute("marcas", marcas);
                getServletContext().getRequestDispatcher("/edicionCamion.jsp").forward(req, resp);
            }
            else
            {
                //resp.sendRedirect(req.getContextPath() + "/choferes/listar");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el camion en la base de datos!");
            }
        }
        else {
            //resp.sendRedirect(req.getContextPath() + "/choferes/listar");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Error el id es null, se debe enviar como parametro en la url!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Camion> service = new CamionesService(conn);
        String matricula = req.getParameter("matricula");
        String tipoCamion = req.getParameter("tipoCamion");



        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e){
            id = 0L;
        }

        Integer modelo;
        try {
            modelo = Integer.valueOf(req.getParameter("modelo"));
        } catch (NumberFormatException e){
            modelo = 0;
        }
        String marca = req.getParameter("marca");
        Integer capacidad;
        try {
            capacidad = Integer.valueOf(req.getParameter("capacidad"));
        } catch (NumberFormatException e){
            capacidad = 0;
        }

        Double kilometraje;
        try {
            kilometraje = Double.valueOf(req.getParameter("kilometraje"));
        } catch (NumberFormatException e){
            kilometraje = 0D;
        }

        boolean habilitar = req.getParameter("habilitar") != null &&
                req.getParameter("habilitar").equals("on");



        Map<String, String> errores = new HashMap<>();
        if (matricula == null || matricula.isBlank()){
            errores.put("matricula", "la matricula es requerida!");
        }
        if (tipoCamion == null || tipoCamion.isBlank()){
            errores.put("tipoCamion", "el tipo de camion es requerido!");
        }
        if (modelo.equals(0)){
            errores.put("modelo", "el modelo es requerido!");
        }
        if (marca == null || marca.isBlank()){
            errores.put("marca", "la marca es requerida!");
        }
        if (capacidad.equals(0)) {
            errores.put("capacidad", "la capacidad es requerida!");
        }

        if (kilometraje.equals(0D)) {
            errores.put("kilometraje", "el kilometraje es requerido!");
        }


        Camion camion = new Camion();
        camion.setId(id);
        camion.setMatricula(matricula);
        if (tipoCamion != null && !tipoCamion.isBlank()){
            camion.setTipoCamion(Tipos.getFromString(tipoCamion));
        }
        if (marca != null || !marca.isBlank()){
            camion.setMarca(Marcas.getFromString(marca));
        }
        camion.setModelo(modelo);
        camion.setCapacidad(capacidad);
        camion.setKilometraje(kilometraje);
        camion.setDisponibilidad(habilitar);



        if (errores.isEmpty()) {
            service.guardar(camion);
            resp.sendRedirect(req.getContextPath() + "/camiones/listar");
        } else {
            req.setAttribute("errores", errores);
            req.setAttribute("camion", camion);
            //obtener la lista de tipos desde el enumerador;
            List<Tipos> tipos = new ArrayList<>(EnumSet.allOf(Tipos.class));
            req.setAttribute("tipos", tipos);
            LocalDate fechaActual = LocalDate.now();
            List<Integer> modelos = IntStream.range(fechaActual.getYear() - 20, fechaActual.getYear() + 2).boxed().collect(Collectors.toList());
            req.setAttribute("modelos", modelos);
            //obtener la lista de marcas desde el enumerador;
            List<Marcas> marcas = new ArrayList<>(EnumSet.allOf(Marcas.class));
            req.setAttribute("marcas", marcas);
            getServletContext().getRequestDispatcher("/edicionCamion.jsp").forward(req, resp);
        }
    }
}
