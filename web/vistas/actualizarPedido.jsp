<%-- 
    Document   : index
    Created on : 07/08/2022, 05:15:33 AM
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
        <title>Sistema Bodega| Inicio</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
        <!-- Theme style -->
        <link href="dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css"/>

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
                    <span class="logo-lg"><b>Sistema </b>Bodega</span>
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
                        <li class="active"><a href="#"><i class="fa fa-link"></i> <span>Panel Administrativo</span></a></li>
                        <li class="treeview">
                            <a href="#"><i class="glyphicon glyphicon-th-large"></i> <span>Registros</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li class="active"><a href="srvPedido?accion=listarPedidos"><i class="fa fa-archive"></i>Pedidos</a></li>
                                <li><a href="srvProducto?accion=listarProductos"><i class="fa fa-cube"></i>Productos</a></li>
                                <li><a href="srvProveedor?accion=listarProveedores"><i class="fa fa-truck"></i>Proveedores</a></li>
                                <li><a href="srvEmpleado?accion=listarEmpleados"><i class="fa fa-user-plus"></i>Empleados</a></li>
                                <li><a href="srvUsuario?accion=listarUsuarios"><i class="fa fa-address-card"></i>Usuarios</a></li>
                            </ul>
                        </li>
                        <li class="treeview">
                            <a href="#"><i class="fa fa-cart-arrow-down"></i> <span>Ventas</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="#"><i class="fa fa-cart-arrow-down"></i>Nueva Venta</a></li>
                                <li><a href="#"><i class="fa fa-tags"></i>Administrar Ventas</a></li>
                            </ul>
                        </li>
                        <li class="treeview">
                            <a href="#"><i class="fa fa-area-chart"></i> <span>Reportes</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="#"><i class="fa fa-bar-chart"></i>Reportes Ventas</a></li>
                            </ul>
                        </li>
                    </ul>
                    <!-- /.sidebar-menu -->
                </section>
                <!-- /.sidebar -->
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <section class="content-header">
                    <div class="row">
                        <div class="col-xs-12 col-md-3">
                        </div>
                        <div class="col-md-3 hidden-xs"></div>
                        <div class="col-xs-2 col-md-1">
                        </div>
                        <div class="col-xs-10 col-md-5 ">
                            <div class="btn-group pull-right">
                                <a href="srvPedido?accion=listarPedidos" class="btn btn-default">
                                    <i class="fa fa-align-justify"></i> Ver listado</a>                                              
                            </div>
                        </div>
                    </div>
                </section>
                <section class="content">
                    <div class="box">
                        <div class="box-header with-border">
                            <i class="fa fa-edit"></i> <h3 class="box-title">Actualizar Datos del Pedido</h3>  
                        </div>
                        <form class="form-horizontal" action="srvPedido?accion=actualizarPedido" method="post">
                            <input type="hidden" name="hCodigo" value="${pedido.idPedido}">
                            
                            <div class="box-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Tipo de Producto del Pedido</label>
                                    <div class="col-sm-4 input-group">
                                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                        <input id="nombre" type="text" required="required" class="form-control" placeholder="Ejem: Cerveza" name="txtTipo" maxlength="50"
                                               value="${pedido.tipoProdPedido}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Marca del Producto del Pedido</label>
                                    <div class="col-sm-4 input-group">
                                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                        <input id="nombre" type="text" required="required" class="form-control" placeholder="Ejem: Club Colombia" name="txtMarca" maxlength="50"
                                               value="${pedido.marcaProdPedido}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Cantidad de Unidades para el Pedido</label>
                                    <div class="col-sm-4 input-group">
                                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                        <input id="nombre" type="text" required="required" class="form-control" placeholder="Ejem: 50" name="txtCantidad" maxlength="10"
                                               value="${pedido.cantidadPedido}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Costo Total</label>
                                    <div class="col-sm-4 input-group">
                                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                        <input id="nombre" type="text" required="required" class="form-control" placeholder="Ejem: $25000" name="txtCosto" maxlength="10"
                                               value="${pedido.costoTotal}">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Fecha del pedido</label>
                                    <div class="col-sm-4 input-group">
                                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                        <input id="nombre" type="date" disabled="disabled" class="form-control" placeholder="Ejem: $25000" name="txtCosto" maxlength="10"
                                               value="${pedido.fechaPedido}">
                                    </div>
                                </div><!-- comment -->
                                
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Proveedor</label>
                                    <div class="col-sm-4 input-group">
                                        <span class="input-group-addon"><i class="fa fa-tags"></i></span>
                                        <select class="form-control"  name="cboProveedor" autofocus=""  required="">
                                            <option value="0">Seleccione un proveedor</option>
                                            <c:forEach items="${proveedores}" var="prov">
                                                <option value="${prov.idProveedor}"  
                                                        <c:if test="${prov.idProveedor == 
                                                                      pedido.proveedor.idProveedor}">
                                                              selected
                                                        </c:if>
                                                        >${prov.nombreProveedor}</option>
                                            </c:forEach>
                                        </select>
                                    </div>                                  
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Empleado</label>
                                    <div class="col-sm-4 input-group">
                                        <span class="input-group-addon"><i class="fa fa-tags"></i></span>
                                        <select class="form-control"  name="cboEmpleado" autofocus=""  required="">
                                            <option value="0">Seleccione un empleado</option>
                                            <c:forEach items="${empleados}" var="empl">
                                                <option value="${empl.idEmpleado}"  
                                                        <c:if test="${empl.idEmpleado == 
                                                                      pedido.empleado.idEmpleado}">
                                                              selected
                                                        </c:if>
                                                        >${empl.apellidoEmpleado}</option>
                                            </c:forEach>
                                        </select>
                                    </div>                                  
                                </div>
                                
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="chkEstado" 
                                                       <c:out value="${pedido.estadoPedido == false ?
                                                              'unchecked':'checked'}"
                                                       default=""/>> Activo
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <!--button type="reset" class="btn btn-danger"><i class="fa fa-close red"></i> Cancelar</button-->
                                <a href="srvPedido?accion=listarPedidos" type="reset" class="btn btn-danger">
                                    <i class="fa-close red"></i>Cancelar</a> 
                                <button type="submit" id="" name="btnRegistrar" value="Registrar" class="btn btn-success"><i class="fa fa-refresh"></i> Actualizar Producto</button>

                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                </section> 
            </div>
            <!-- /.content-wrapper -->

            <!-- Main Footer -->
            <footer class="main-footer">
                <!-- To the right -->
                <div class="pull-right hidden-xs">
                    Issis Rodriguez, Jennifer Delgado, Deisy Matiz 
                </div>
                <!-- Default to the left -->
                <strong>Copyright &copy; 2022 <a href="#">SENA</a>.</strong> Todos los derechos reservados.
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
