<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Map"%>
<%@page import="com.timal.app.rutas.models.*"%>
<%@page import="com.timal.app.rutas.models.enums.*"%>

<%
Map<String, String> errores = (Map<String, String>)request.getAttribute("errores");
Camion camion = (Camion) request.getAttribute("camion");
List<Tipos> tipos = (List<Tipos>) request.getAttribute("tipos");
List<Marcas> marcas = (List<Marcas>) request.getAttribute("marcas");
List<Integer> modelos = (List<Integer>) request.getAttribute("modelos");
String tipo = camion.getTipoCamion() != null ? camion.getTipoCamion().getDescripcion() : "";
String marcaCamion = camion.getMarca() != null ? camion.getMarca().getDescripcion() : "";
Integer modeloCamion = camion.getModelo() != null ? camion.getModelo() : 0;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edicion Camion</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link href="<%=request.getContextPath()%>/css/jquery.datetimepicker.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
    <script src="<%=request.getContextPath()%>/js/moment-with-locales.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.datetimepicker.full.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


</head>
<body>
<nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header" id="div1">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#" id="enlace1">Rutas App</a>
            </div>


            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-haspopup="true" aria-expanded="false">Choferes<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="<%=request.getContextPath()%>/choferes/listar">Lista Choferes</a></li>
                            <li><a href="<%=request.getContextPath()%>/choferes/alta">Alta Chofer</a></li>

                        </ul>

                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-haspopup="true" aria-expanded="false">Camiones<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="<%=request.getContextPath()%>/camiones/listar">Lista Camiones</a></li>
                            <li><a href="<%=request.getContextPath()%>/camiones/alta">Alta Camion</a></li>

                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-haspopup="true" aria-expanded="false">Rutas<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="<%=request.getContextPath()%>/rutas/alta">Alta Ruta</a></li>

                        </ul>
                    </li>


                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
    <div class="container">

        <div class="row">
            <div class="col-md-12">
                <h2>Edicion Camion <%=camion.getId() %></h2>
            </div>
        </div>

        <%
        if(errores != null && errores.size()>0){
        %>
        <ul class="alert alert-danger mx-5 px-5">
        <% for(String error: errores.values()){%>
        <li><%=error%></li>
        <%}%>
        </ul>
        <%}%>

        <div class="row">
            <form action="<%=request.getContextPath()%>/camiones/edicion" method="post">
            <input type="hidden" name="id" value="<%=camion.getId()%>">
                <div class="col-md-12">
                    <div class="form-group">
                        <label for="">Matricula</label>
                        <input type="text" name="matricula" id="matricula" class="form-control" value="<%=camion.getMatricula() != null? camion.getMatricula(): "" %>">

                        <%
                          if(errores != null && errores.containsKey("matricula")){
                               out.println("<span class='text-danger'>"+ errores.get("matricula") + "</span>");
                          }
                        %>
                    </div>
                    <div class="form-group">
                        <label for="">Tipo Camion</label>
                        <select name="tipoCamion" id="tipoCamion" class="form-control">
                            <option value="">--- seleccionar ---</option>
                            <% for(Tipos t: tipos){%>
                            <option value="<%=t.getDescripcion()%>" <%=t.equals(camion.getTipoCamion())? "selected": ""%> ><%=t.getDescripcion()%></option>
                            <%}%>
                        </select>

                        <%
                          if(errores != null && errores.containsKey("tipoCamion")){
                               out.println("<span class='text-danger'>"+ errores.get("tipoCamion") + "</span>");
                          }
                        %>
                    </div>
                    <div class="form-group">
                        <label for="">Modelo</label>
                        <select name="modelo" id="modelo" class="form-control">
                            <option value="">--- seleccionar ---</option>
                            <% for(Integer modelo: modelos){%>
                            <option value="<%=modelo.intValue()%>" <%=modelo.equals(modeloCamion)? "selected": ""%> ><%=modelo.intValue()%></option>
                            <%}%>
                        </select>

                        <%
                          if(errores != null && errores.containsKey("modelo")){
                               out.println("<span class='text-danger'>"+ errores.get("modelo") + "</span>");
                          }
                        %>
                    </div>
                    <div class="form-group">
                        <label for="">Marca</label>
                        <select name="marca" id="marca" class="form-control">
                            <option value="">--- seleccionar ---</option>
                            <% for(Marcas marca: marcas){%>
                            <option value="<%=marca.getDescripcion()%>" <%=marca.getDescripcion().equals(marcaCamion)? "selected": ""%>><%=marca.getDescripcion()%></option>
                            <%}%>
                        </select>

                        <%
                          if(errores != null && errores.containsKey("marca")){
                               out.println("<span class='text-danger'>"+ errores.get("marca") + "</span>");
                          }
                        %>
                    </div>
                    <div class="form-group">
                        <label for="">Capacidad</label>
                        <input type="number" name="capacidad" id="capacidad" class="form-control" value="<%=camion.getCapacidad() != null? camion.getCapacidad(): "" %>">

                        <%
                          if(errores != null && errores.containsKey("capacidad")){
                               out.println("<span class='text-danger'>"+ errores.get("capacidad") + "</span>");
                          }
                        %>
                    </div>
                    <div class="form-group">
                        <label for="">Kilometraje</label>
                        <input type="text" name="kilometraje" id="kilometraje" class="form-control" value="<%=camion.getKilometraje() != null? camion.getKilometraje(): "" %>">

                        <%
                          if(errores != null && errores.containsKey("kilometraje")){
                               out.println("<span class='text-danger'>"+ errores.get("kilometraje") + "</span>");
                          }
                        %>
                    </div>

                    <div class="form-group">
                        <label for="">Disponibilidad</label>
                        <input type="checkbox" name="habilitar" id="habilitar" checked class="form-check-input">
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn btn-success">Guardar</button>
                    </div>


                </div>
            </form>
        </div>


    </div>


</body>
</html>