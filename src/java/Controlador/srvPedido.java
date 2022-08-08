
package Controlador;

import Modelo.DAOEMPLEADO;
import Modelo.DAOPEDIDO;
import Modelo.DAOPROVEEDOR;
import Modelo.Empleado;
import Modelo.Pedido;
import Modelo.Proveedor;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "srvPedido", urlPatterns = {"/srvPedido"})

public class srvPedido extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        
        try{
            if(accion != null){
                switch(accion){
                    case "listarPedidos":
                        listarPedidos(request, response);
                        break;
                    case "nuevoPedido":
                        presentarFormulario(request, response);
                        break;
                    case "registrarPedido":
                        registrarPedido(request, response);
                        break;
                    case "leerPedido":
                        presentarPedido(request, response);
                        break;
                    case "actualizarPedido":
                        actualizarPedido(request, response);
                        break;
                    /*case "eliminarProducto":
                        eliminarProducto(request, response);
                        break;
                    case "inventarioTotal":
                        inventarioTotal(request, response);
                        break;*/
                    default:
                        response.sendRedirect("identificar.jsp");
                }
            }else if (request.getParameter("cambiar")!= null){
                cambiarEstado(request, response);
            } else
            
            {
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

    private void listarPedidos(HttpServletRequest request, HttpServletResponse response) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DAOPEDIDO dao = new DAOPEDIDO();
        List<Pedido> ped = null;
        try{
            ped = dao.listarPedidos();
            request.setAttribute("pedidos", ped); 
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo listar los pedidos " + e.getMessage());
        }finally{
            dao = null;
        }
        
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/pedidos.jsp").forward(request, response);
        }catch(Exception ex){
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }
        
    }

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try{
            this.cargarProveedores(request);
            this.cargarEmpleados(request);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/nuevoPedido.jsp").forward(request, response);
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo cargar la vista " + e.getMessage());
        }
    }
    
    private void registrarPedido(HttpServletRequest request, HttpServletResponse response) {
        DAOPEDIDO daoP;
        Pedido ped = null;
        Proveedor prov;
        Empleado emp;
        
        if (request.getParameter("txtTipo") != null
                && request.getParameter("txtMarca") != null
                && request.getParameter("txtCantidad") != null
                && request.getParameter("txtCosto") != null
                && request.getParameter("cboProveedor") != null
                && request.getParameter("cboEmpleado") != null) {

            ped = new Pedido();
            ped.setTipoProdPedido(request.getParameter("txtTipo"));
            ped.setMarcaProdPedido(request.getParameter("txtMarca"));
            ped.setCantidadPedido(Integer.parseInt(request.getParameter("txtCantidad")));
            ped.setCostoTotal(Integer.parseInt(request.getParameter("txtCosto")));
            
            if (request.getParameter("chkEstado") != null) {
                ped.setEstadoPedido(true);
            } else {
                ped.setEstadoPedido(false);
            }
            
            prov = new Proveedor();
            prov.setIdProveedor(Integer.parseInt(request.getParameter("cboProveedor")));
            ped.setProveedor(prov);
            
            emp = new Empleado();
            emp.setIdEmpleado(Integer.parseInt(request.getParameter("cboEmpleado")));
            ped.setEmpleado(emp);
            
            daoP = new DAOPEDIDO();
            try {
                daoP.registrarPedido(ped);
                response.sendRedirect("srvPedido?accion=listarPedidos");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo registrar el pedido " + e.getMessage());
                request.setAttribute("pedido", ped);
                this.presentarFormulario(request, response);
            }
        }
        
    }

    private void presentarPedido(HttpServletRequest request, HttpServletResponse response) {
        DAOPEDIDO dao;
        Pedido pedi;
        if (request.getParameter("cod") != null) {
            pedi = new Pedido();
            pedi.setIdPedido(Integer.parseInt(request.getParameter("cod")));
                    
            dao = new DAOPEDIDO();
            try {
                pedi = dao.leerPedido(pedi);
                if (pedi != null) {
                    request.setAttribute("pedido", pedi);
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
            this.cargarProveedores(request);
            this.cargarEmpleados(request);
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/actualizarPedido.jsp"
                    ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getMessage());
        }
    }

    private void actualizarPedido(HttpServletRequest request, HttpServletResponse response) {
        DAOPEDIDO daoPd;
        Pedido pedid = null;
        Proveedor provd;
        Empleado empl;
                
        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtTipo") != null
                && request.getParameter("txtMarca") != null
                && request.getParameter("txtCantidad") != null
                && request.getParameter("txtCosto") != null) {
            
            pedid = new Pedido();
            pedid.setIdPedido(Integer.parseInt(request.getParameter("hCodigo")));
            pedid.setTipoProdPedido(request.getParameter("txtTipo"));
            pedid.setMarcaProdPedido(request.getParameter("txtMarca"));
            pedid.setCantidadPedido(Integer.parseInt(request.getParameter("txtCantidad")));
            pedid.setCostoTotal(Integer.parseInt(request.getParameter("txtCosto")));
            
            if (request.getParameter("chkEstado") != null) {
                pedid.setEstadoPedido(true);
            } else {
                pedid.setEstadoPedido(false);
            }
            
            provd = new Proveedor();
            provd.setIdProveedor(Integer.parseInt(request.getParameter("cboProveedor")));
            pedid.setProveedor(provd);
            
            empl = new Empleado();
            empl.setIdEmpleado(Integer.parseInt(request.getParameter("cboEmpleado")));
            pedid.setEmpleado(empl);
                        
            daoPd = new DAOPEDIDO();
            try {
                daoPd.actualizarPedido(pedid);
                response.sendRedirect("srvPedido?accion=listarPedidos");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar el Pedido " + e.getMessage());
                request.setAttribute("producto", pedid);
                
            }
            try {
                this.getServletConfig().getServletContext().
                        getRequestDispatcher("/vistas/actualizarPedido.jsp"
                        ).forward(request, response);
            } catch (Exception ex) {
                request.setAttribute("msje", "No se pudo realizar la operacion " + ex.getMessage());
            }
        }
    }
    
    private void cargarProveedores(HttpServletRequest request) {
        DAOPROVEEDOR dao = new DAOPROVEEDOR();
        List<Proveedor> prov = null;
        try{
            prov = dao.listarProveedores();
            request.setAttribute("proveedores", prov);
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo cargar los Proveedores " + e.getMessage());
        }finally{
            prov = null;
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
        DAOPRODUCTO dao = new DAOPRODUCTO();
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
    }*/

    private void cambiarEstado(HttpServletRequest request, HttpServletResponse response) {
        DAOPEDIDO daoPd;
        Pedido pedid;
        try{
            daoPd = new DAOPEDIDO();
            pedid = new Pedido();
            
            if(request.getParameter("cambiar").equals("activar")){
                pedid.setEstadoPedido(true);
            } else {
                pedid.setEstadoPedido(false);
            }
            
            if(request.getParameter("cod") != null) {
                pedid.setIdPedido(Integer.parseInt(request.getParameter("cod")));
                daoPd.cambiarEstado(pedid);
            } else {
                request.setAttribute("msje", "No se tiene el Id de Pedido ");
            }
            
        } catch (Exception ex){
            request.setAttribute("msje", "No se pudo realizar la modificación " + ex.getMessage());
        }
        
        this.listarPedidos(request, response);
    }
}
