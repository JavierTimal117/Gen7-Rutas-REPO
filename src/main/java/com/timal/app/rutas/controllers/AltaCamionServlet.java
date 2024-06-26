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

@WebServlet("/camiones/alta")
public class AltaCamionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //obtener la lista de tipos desde el enumerador;
        List<Tipos> tipos = new ArrayList<>(EnumSet.allOf(Tipos.class));
        req.setAttribute("tipos", tipos);
        //obtener la lista de marcas desde el enumerador;
        List<Marcas> marcas = new ArrayList<>(EnumSet.allOf(Marcas.class));
        req.setAttribute("marcas", marcas);
        LocalDate fechaActual = LocalDate.now(); //2023-03-10
        List<Integer> modelos = IntStream.range(fechaActual.getYear() - 20,
                        fechaActual.getYear() + 2).boxed()
                .collect(Collectors.toList());
        req.setAttribute("modelos", modelos);
        Camion camion = new Camion();
        req.setAttribute("camion", camion);
        getServletContext().getRequestDispatcher("/altaCamion.jsp")
                .forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection conn = (Connection) req.getAttribute("conn");
        IService<Camion> service = new CamionesService(conn);
        String matricula = req.getParameter("matricula"); // Tjdhsakjdhsakjdhaskjdhaskjdhaskjdhsa
        String tipoCamion = req.getParameter("tipoCamion"); // Doble Remolque
        Integer modelo;
        try {
            modelo = Integer.valueOf(req.getParameter("modelo")); //2012
        } catch (NumberFormatException e){
            modelo = 0; // 0
        }
        String marca = req.getParameter("marca"); //DinaA
        Integer capacidad;
        try {
            capacidad = Integer.valueOf(req.getParameter("capacidad")); // 45
        } catch (NumberFormatException e){
            capacidad = 0; // 0
        }
        Double kilometraje;
        try {
            kilometraje = Double.valueOf(req.getParameter("kilometraje")); // 45
        } catch (NumberFormatException e){
            kilometraje = 0D;
        }
        boolean disponibilidad = req.getParameter("disponibilidad") != null &&
                req.getParameter("disponibilidad").equals("on");
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
        camion.setId(0L);
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
        camion.setDisponibilidad(disponibilidad);
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
            List<Integer> modelos = IntStream.range(fechaActual.getYear() - 20,
                            fechaActual.getYear() + 2)
                    .boxed().collect(Collectors.toList());
            req.setAttribute("modelos", modelos);
            //obtener la lista de marcas desde el enumerador;
            List<Marcas> marcas = new ArrayList<>(EnumSet.allOf(Marcas.class));
            req.setAttribute("marcas", marcas);
            getServletContext().getRequestDispatcher("/altaCamion.jsp")
                    .forward(req, resp);
        }
    }
}
