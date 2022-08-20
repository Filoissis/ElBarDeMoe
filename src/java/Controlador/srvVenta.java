
package Controlador;

import Modelo.DAOEMPLEADO;
import Modelo.DAOPRODUCTO;
import Modelo.DAOVENTA;
import Modelo.Empleado;
import Modelo.Producto;
import Modelo.Venta;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "srvVenta", urlPatterns = {"/srvVenta"})

public class srvVenta extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        
        try{
            if(accion != null){
                switch(accion){
                    case "listarVentas":
                        listarVentas(request, response);
                        break;
                    case "nuevoVenta":
                        presentarFormulario(request, response);
                        break;
                    case "registrarVenta":
                        registrarVenta(request, response);
                        break;
                    case "leerVenta":
                        presentarVenta(request, response);
                        break;
                    case "actualizarVenta":
                        actualizarVenta(request, response);
                        break;
                    case "reporteVenta":
                        reporteVenta(request, response);
                        break;
                    case "filtroReporte":
                        filtroReporte(request, response);
                        break;
                        
                    default:
                        response.sendRedirect("identificar.jsp");
                }
            }else {
                response.sendRedirect("identificar.jsp");
            }
        }catch(Exception e){
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/mensaje.jsp").forward(request, response);

            } catch (Exception ex) {
                System.out.println("Error " + e.getMessage());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listarVentas(HttpServletRequest request, HttpServletResponse response) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DAOVENTA dao = new DAOVENTA();
        List<Venta> vent = null;
        try{
            vent = dao.listarVentas();
            request.setAttribute("ventas", vent); 
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo listar las ventas " + e.getMessage());
        }finally{
            dao = null;
        }
        
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/ventas.jsp").forward(request, response);
        }catch(Exception ex){
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }
        
    }

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try{
            this.cargarProductos(request);
            this.cargarEmpleados(request);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/nuevaVenta.jsp").forward(request, response);
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo cargar la vista " + e.getMessage());
        }
    }
    
    private void registrarVenta(HttpServletRequest request, HttpServletResponse response) {
        DAOVENTA daoV;
        DAOPRODUCTO daoPrd;
        Venta sale = null;
        Producto prod;
        Empleado emp;
        
        if (request.getParameter("cboProducto") != null
                && request.getParameter("txtCantidad") != null
                && /*request.getParameter("txtCosto") != null
                &&*/ request.getParameter("cboEmpleado") != null) {

            sale = new Venta();
            daoPrd = new DAOPRODUCTO();
            
            prod = new Producto();
            prod.setIdProducto(Integer.parseInt(request.getParameter("cboProducto")));
            
            try{
                prod = daoPrd.leerProducto(prod);
            } catch (Exception ex){
                
            }
            sale.setProducto(prod);
            
            sale.setCantidadVendida(Integer.parseInt(request.getParameter("txtCantidad")));
            
            sale.setPrecioVenta(prod.getPrecioVenta() * sale.getCantidadVendida());
            
            emp = new Empleado();
            emp.setIdEmpleado(Integer.parseInt(request.getParameter("cboEmpleado")));
            sale.setEmpleado(emp);
            
            daoV = new DAOVENTA();
            
            try {
                daoV.registrarVenta(sale);
                daoPrd.actualizarInventario(sale);
                response.sendRedirect("srvVenta?accion=listarVentas");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo registrar la venta " + e.getMessage());
                request.setAttribute("venta", sale);
                this.presentarFormulario(request, response);
            }
        }
        
    }

    private void presentarVenta(HttpServletRequest request, HttpServletResponse response) {
        DAOVENTA dao;
        Venta sale;
        if (request.getParameter("cod") != null) {
            sale = new Venta();
            sale.setIdVenta(Integer.parseInt(request.getParameter("cod")));
                    
            dao = new DAOVENTA();
            try {
                sale = dao.leerVenta(sale);
                if (sale != null) {
                    request.setAttribute("venta", sale);
                } else {
                    request.setAttribute("msje", "No se encontró el pedido");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }
        try {
            this.cargarProductos(request);
            this.cargarEmpleados(request);
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/actualizarVenta.jsp"
                    ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getMessage());
        }
    }

    private void actualizarVenta(HttpServletRequest request, HttpServletResponse response) {
        DAOVENTA daoVt;
        Venta vent = null;
        Producto prod;
        Empleado empl;
                
        if (request.getParameter("hCodigo") != null
                && request.getParameter("cboProducto") != null
                && request.getParameter("txtCantidad") != null
                && request.getParameter("txtCosto") != null) {
            
            vent = new Venta();
            vent.setIdVenta(Integer.parseInt(request.getParameter("hCodigo")));
            vent.setCantidadVendida(Integer.parseInt(request.getParameter("txtCantidad")));
            vent.setPrecioVenta(Integer.parseInt(request.getParameter("txtCosto")));
            
            prod = new Producto();
            prod.setIdProducto(Integer.parseInt(request.getParameter("cboProducto")));
            vent.setProducto(prod);
            
            empl = new Empleado();
            empl.setIdEmpleado(Integer.parseInt(request.getParameter("cboEmpleado")));
            vent.setEmpleado(empl);
                        
            daoVt = new DAOVENTA();
            try {
                daoVt.actualizarVenta(vent);
                response.sendRedirect("srvVenta?accion=listarVentas");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar el Pedido " + e.getMessage());
                request.setAttribute("venta", vent);
                
            }
            try {
                this.getServletConfig().getServletContext().
                        getRequestDispatcher("/vistas/actualizarVenta.jsp"
                        ).forward(request, response);
            } catch (Exception ex) {
                request.setAttribute("msje", "No se pudo realizar la operacion " + ex.getMessage());
            }
        }
    }
    
    private void cargarProductos(HttpServletRequest request) {
        DAOPRODUCTO dao = new DAOPRODUCTO();
        List<Producto> prod = null;
        try{
            prod = dao.listarProductos();
            request.setAttribute("productos", prod);
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo cargar los Proveedores " + e.getMessage());
        }finally{
            prod = null;
            dao = null;
        }
    }
    
    private void cargarEmpleados(HttpServletRequest request) {
        DAOEMPLEADO dao = new DAOEMPLEADO();
        List<Empleado> empl = null;
        try{
            empl = dao.listarEmpleados();
            request.setAttribute("empleados", empl);
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo cargar los Empleados " + e.getMessage());
        }finally{
            empl = null;
            dao = null;
        }
    }

    /*private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) {
        DAOPRODUCTO dao = new DAOPRODUCTO();
        Producto prods = new Producto();
        if (request.getParameter("cod")!=null){
            prods.setIdProducto(Integer.parseInt(request.getParameter("cod")));
            try{
                dao.eliminarProducto(prods);
                response.sendRedirect("srvProducto?accion=listarProductos");
            }catch(Exception e){
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        }else{
            request.setAttribute("msje", "No se encontro el producto");
        }

    }

    private void inventarioTotal(HttpServletRequest request, HttpServletResponse response) {
        DAOPEDIDO dao = new DAOPEDIDO();
        //Producto prod = null;
        int inventario = 0;
        try{
            inventario = dao.inventarioTotal();
            request.setAttribute("invent", inventario); 
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo listar los productos " + e.getMessage());
        }finally{
            dao = null;
        }
        
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/productos.jsp").forward(request, response);
        }catch(Exception ex){
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }
    }

    private void cambiarEstado(HttpServletRequest request, HttpServletResponse response) {
        DAOPEDIDO daoPd;
        DAOPRODUCTO daoProd;
        Pedido pedid;
        
        try{
            daoPd = new DAOPEDIDO();
            daoProd = new DAOPRODUCTO();
            pedid = new Pedido();
                        
            if(request.getParameter("cambiar").equals("activar")){
                pedid.setEstadoPedido(true);
            } else {
                pedid.setEstadoPedido(false);
            }
            
            if(request.getParameter("cod") != null) {
                pedid.setIdPedido(Integer.parseInt(request.getParameter("cod")));
                daoPd.cambiarEstado(pedid);
                pedid = daoPd.leerPedido(pedid);
                daoProd.actualizarInventario(pedid);
            } else {
                request.setAttribute("msje", "No se tiene el Id de Pedido ");
            }
            
        } catch (Exception ex){
            request.setAttribute("msje", "No se pudo realizar la modificación " + ex.getMessage());
        }
        
        this.listarPedidos(request, response);
    }*/

    private void reporteVenta(HttpServletRequest request, HttpServletResponse response) {
        DAOVENTA dao = new DAOVENTA();
        List<Venta> vent = null;
       
        try {
            vent = dao.listarVentas();
            request.setAttribute("ventas", vent);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar las ventas " + e.getMessage());
        } finally {
            dao = null;
        }

        try {
            this.cargarProductos(request);
            this.cargarEmpleados(request);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }

    }

    private void filtroReporte(HttpServletRequest request, HttpServletResponse response) {
        DAOVENTA dao = new DAOVENTA();
        List<Venta> vent = null;
        Venta ven = null;
        Producto prod;
        Empleado empl;
        String inicio;
        String fin;
              
        try {
            ven = new Venta();
        
            if (Integer.parseInt(request.getParameter("cboProducto")) != 0){
                prod = new Producto();
                empl = new Empleado();
                prod.setIdProducto(Integer.parseInt(request.getParameter("cboProducto")));
                empl.setIdEmpleado(0);
                ven.setProducto(prod);
                ven.setEmpleado(empl);
                vent = dao.listarVentas(ven);

            } else if (Integer.parseInt(request.getParameter("cboEmpleado")) != 0) {
                prod = new Producto();
                empl = new Empleado();
                empl.setIdEmpleado(Integer.parseInt(request.getParameter("cboEmpleado")));
                prod.setIdProducto(0);
                ven.setEmpleado(empl);
                ven.setProducto(prod);
                vent = dao.listarVentas(ven);
                
            } else if (request.getParameter("fechaInicio") != null && 
                    request.getParameter("fechaFinal") != null) {
                
                inicio = request.getParameter("fechaInicio");
                SimpleDateFormat fechaIni = new SimpleDateFormat("yyyy-MM-dd");
                Date iniDate = fechaIni.parse(inicio);
                
                fin = request.getParameter("fechaFinal");
                SimpleDateFormat fechaFin = new SimpleDateFormat("yyyy-MM-dd");
                Date finDate = fechaFin.parse(fin);
                        
                if(iniDate.before(finDate)) {
                    vent = dao.listarVentas(inicio, fin);
                }else{
                    vent = dao.listarVentas();
                }
                
            } else {
                vent = dao.listarVentas();
            }
        
            //vent = dao.listarVentas(ven);
            request.setAttribute("ventas", vent);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar las ventas " + e.getMessage());
        } finally {
            dao = null;
        }

        try {
            this.cargarProductos(request);
            this.cargarEmpleados(request);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }
        
        
    }
}
