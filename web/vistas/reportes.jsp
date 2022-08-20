<%-- 
    Document   : index
    Created on : 13/08/2022, 06:15:33 PM
    Author     : Issis Rodriguez, Jennifer Delgado Lozano, Deisy Juliana Matiz Gutierrez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("usuario") != null) {
%>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Sistema SAIB| Reportes</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
        <!-- Theme style -->
        <link href="dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css"/>
        <link href="bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="sweetalert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
              page. However, you can choose any other skin. Make sure you
              apply the skin class to the body tag so the changes take effect. -->
        <link rel="stylesheet" href="dist/css/skins/skin-blue.min.css">
        <link rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <!-- Main Header -->
            <header class="main-header">
                <a href="" class="logo">
                    <!-- mini logo for sidebar mini 50x50 pixels -->
                    <span class="logo-mini"><b>S</b>BL</span>
                    <!-- logo for regular state and mobile devices -->
                    <span class="logo-lg"><b>Sistema </b>SAIB</span>
                </a>

                <!-- Header Navbar -->
                <nav class="navbar navbar-static-top" role="navigation">
                    <!-- Sidebar toggle button-->
                    <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                        <span class="sr-only">Toggle navigation</span>
                    </a>
                    <!-- Navbar Right Menu -->
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <!-- User Account Menu -->
                            <li class="dropdown user user-menu">
                                <!-- Menu Toggle Button -->
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <!-- The user image in the navbar-->
                                    <!--img src="dist/img/user2-160x160.jpg" class="user-image" alt="User Image"-->
                                    <!-- hidden-xs hides the username on small devices so only the image appears. -->
                                    <span class="hidden-xs"> ${usuario.nombreUsuario}</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <!-- The user image in the menu -->
                                    <li class="user-header">
                                        <!--img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image"-->

                                        <p>                    
                                            Bienvenido - ${usuario.nombreUsuario}
                                            <small>Usted es, ${usuario.cargo.nombreCargo} </small>
                                        </p>
                                    </li>
                                    <!-- Menu Footer-->
                                    <li class="user-footer">
                                        <div class="pull-right">
                                            <a href="srvUsuario?accion=cerrar" class="btn btn-default btn-flat">Cerrar Session</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="main-sidebar">

                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">

                    <!-- Sidebar user panel (optional) -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="dist/img/ElBarDeMoe160x160.jpg" class="img-circle" alt="User Image">
                        </div>
                        <div class="pull-left info">
                            <p>Bienvenido,  ${usuario.nombreUsuario} </p>
                            <!-- Status -->
                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>

                    <!-- search form (Optional) -->
                    <form action="#" method="get" class="sidebar-form">
                        <div class="input-group">
                            <input type="text" name="q" class="form-control" placeholder="Search...">
                            <span class="input-group-btn">
                                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                    </form>
                    <!-- /.search form -->

                    <!-- Sidebar Menu -->
                    <ul class="sidebar-menu" data-widget="tree">
                        <li class="header">INICIO</li>
                        <!-- Optionally, you can add icons to the links -->
                        <li class="treeview">
                            <a href="#"><i class="fa fa-link"></i> <span>Panel Administrativo</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="srvProveedor?accion=listarProveedores"><i class="fa fa-truck"></i>Proveedores</a></li>
                                <li><a href="srvEmpleado?accion=listarEmpleados"><i class="fa fa-user-plus"></i>Empleados</a></li>
                                <li><a href="srvUsuario?accion=listarUsuarios"><i class="fa fa-address-card"></i>Usuarios</a></li>
                            </ul>
                        </li>
                        <li class="treeview">
                            <a href="#"><i class="glyphicon glyphicon-th-large"></i> <span>Registros</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="srvPedido?accion=listarPedidos"><i class="fa fa-archive"></i>Pedidos</a></li>
                                <li><a href="srvProducto?accion=listarProductos"><i class="fa fa-cube"></i>Productos</a></li>
                            </ul>
                        </li>
                        <li class="treeview">
                            <a href="#"><i class="fa fa-cart-arrow-down"></i> <span>Ventas</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="srvVenta?accion=nuevoVenta"><i class="fa fa-cart-arrow-down"></i>Nueva Venta</a></li>
                                <li><a href="srvVenta?accion=listarVentas"><i class="fa fa-tags"></i>Administrar Ventas</a></li>
                            </ul>
                        </li>
                        <li class="treeview active">
                            <a href="#"><i class="fa fa-area-chart"></i> <span>Reportes</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li class="active"><a href="srvVenta?accion=reporteVenta"><i class="fa fa-bar-chart"></i>Reportes Ventas</a></li>
                            </ul>
                        </li>
                    </ul>
                    <!-- /.sidebar-menu -->
                </section>
                <!-- /.sidebar -->
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>Reporte de Ventas</h1>
                </section>
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Inicio</a></li>
                        <li class="active">Reporte de Ventas</li>
                    </ol>
                </section>

                <section class="content">
                    <div class="box">    
                        <div class="box-header with-border">             
                            <h3 class="box-title">Filtros para las Ventas</h3>
                        </div>
                        <form class="form-horizontal" action="srvVenta?accion=filtroReporte" method="post" name="form1">
                            <div class="box-body">
                                <div class="table-responsive" >                                 
                                    <table class="table table-bordered table-striped dataTable table-hover" id="tablaFiltro" class="display">
                                        <thead>
                                            <tr>
                                                <th>Filtro por Empleado</th>
                                                <th>Filtro por Producto</th>
                                                <th>Filtro por Fecha</th>
                                            </tr>
                                        </thead>

                                            <tr>
                                                <td>
                                                    <div class="form-group">
                                                        <select class="form-control"  name="cboEmpleado" autofocus="">
                                                            <option value="0">Seleccione un empleado</option>
                                                            <c:forEach items="${empleados}" var="empl">
                                                                <option value="${empl.idEmpleado}"  

                                                                        >${empl.apellidoEmpleado}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </div>
                                                </td>

                                                <td>
                                                    <select class="form-control"  name="cboProducto" autofocus="">
                                                          <option value="0">Seleccione un producto</option>
                                                          <c:forEach items="${productos}" var="prod">
                                                              <option value="${prod.idProducto}" 

                                                                      >${prod.tipoProducto} - ${prod.marcaProducto}</option>
                                                          </c:forEach>
                                                      </select>

                                                </td>


                                                <td>
                                                    <input type="date" class="form-control" id="start" name="fechaInicio" value="2022-08-01"  min="2022-01-01" max="2030-12-31">
                                                    <input type="date" class="form-control" id="ends" name="fechaFinal" value="2022-08-01"  min="2022-01-01" max="2030-12-31">
                                                </td>

                                                <td><a href="<c:url value="srvVenta">
                                                           <c:param name="accion" value="filtroReporte" />
                                                       </c:url>">
                                                        
                                                        <!--button type="button" class="btn btn-success" data-toggle="tooltip"  title="Generar Reporte" data-original-title="Generar Reporte">
                                                            Generar Reporte</button-->
                                                        <button type="submit" id="" name="btnGenerar" value="Generar" class="btn btn-success"><i class="fa fa-th-list"></i> Generar Reporte</button>
                                                    </a>


                                                </td>
                                            </tr>                                                    

                                    </table>
                                </div>
                            </div>
                        </form>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <!--Pie de página-->
                        </div>
                        <!-- /.box-footer-->
                    </div>
                </section>
                
                
                <section class="content">
                    <div class="box">    
                        <div class="box-header with-border">             
                            <h3 class="box-title">Reporte de Ventas Realizadas</h3>
                        </div>
                        <div class="box-body">
                            <div class="table-responsive" >                                 
                                <table class="table table-bordered table-striped dataTable table-hover" id="tablaVentas" class="display">
                                    <thead>
                                        <tr>
                                            <th>IdVenta</th>
                                            <th>Tipo</th>
                                            <th>Marca</th>
                                            <th>Cantidad</th>
                                            <th>Precio Total</th>
                                            <th>Fecha</th>
                                            <th>Empleado</th> 
                                        </tr>
                                    </thead>
                                    <c:forEach var="vent" items="${ventas}" varStatus="iteracion">                                                    
                                        <tr>
                                            <td>${iteracion.index + 1}</td>
                                            <td>${vent.producto.tipoProducto}</td>
                                            <td>${vent.producto.marcaProducto}</td>
                                            <td>${vent.cantidadVendida}</td>
                                            <td>${vent.precioVenta}</td>
                                            <td>${vent.fechaVenta}</td>
                                            <td>${vent.empleado.apellidoEmpleado}</td>
                                            
                                        </tr>                                                    
                                    </c:forEach>                                               
                                </table>
                            </div>
                        </div>
                        
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <!--Pie de página-->
                        </div>
                        <!-- /.box-footer-->
                    </div>
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <!-- Main Footer -->
            <footer class="main-footer">
                <!-- To the right -->
                <div class="pull-right hidden-xs">
                    Tresolution - Issis Rodriguez, Jennifer Delgado, Deisy Matiz 
                </div>
                <!-- Default to the left -->
                <strong>Copyright &copy; 2022 <a href="https://www.sena.edu.co/">SENA</a>.</strong> Todos los derechos reservados.
            </footer>

            <div class="control-sidebar-bg"></div>
        </div>
        <!-- ./wrapper -->

        <!-- REQUIRED JS SCRIPTS -->

        <!-- jQuery 3 -->
        <script src="bower_components/jquery/dist/jquery.min.js"></script>
        <!-- Bootstrap 3.3.7 -->
        <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        
        <!-- AdminLTE App -->
        <script src="dist/js/adminlte.min.js"></script>
        <script src="bower_components/datatables.net/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js" type="text/javascript"></script>
        <script src="sweetalert/sweetalert.js" type="text/javascript"></script>
        
        
        <script>
            $(document).ready(function () {
                $('#tablaVentas').DataTable();
            });
        </script>
        <!-- Optionally, you can add Slimscroll and FastClick plugins.
             Both of these plugins are recommended to enhance the
             user experience. -->
    </body>
</html>
<%
    } else {
        response.sendRedirect("identificar.jsp");
    }
%>
