package com.timal.app.rutas.controllers;

import com.timal.app.rutas.models.Camion;
import com.timal.app.rutas.services.CamionesService;
import com.timal.app.rutas.services.IService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet("/camiones/listar")
public class ListaCamionesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Camion> service = new CamionesService(conn);
        List<Camion> camiones = service.listar();

        req.setAttribute("camiones", camiones);

        //obtener la lista de tipos desde el enumerador;
        //List<Tipos> tipos = new ArrayList<>(EnumSet.allOf(Tipos.class));
        //req.setAttribute("tipos", tipos);

        //obtener la lista de marcas desde el enumerador;
        //List<Marcas> marcas = new ArrayList<>(EnumSet.allOf(Marcas.class));
        //req.setAttribute("marcas", marcas);
        getServletContext().getRequestDispatcher("/listaCamiones.jsp").forward(req, resp);
    }
}
