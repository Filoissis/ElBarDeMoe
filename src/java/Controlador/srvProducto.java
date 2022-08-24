
package Controlador;

import Modelo.DAOPRODUCTO;
import Modelo.Producto;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "srvProducto", urlPatterns = {"/srvProducto"})

public class srvProducto extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        
        try{
            if(accion != null){
                switch(accion){
                    case "listarProductos":
                        listarProducto(request, response);
                        break;
                    case "listarProductosVendedor":
                        listarProductoVendedor(request, response);
                        break;
                    case "nuevoProducto":
                        presentarFormulario(request, response);
                        break;
                    case "registrarProducto":
                        registrarProducto(request, response);
                        break;
                    case "leerProducto":
                        presentarProducto(request, response);
                        break;
                    case "actualizarProducto":
                        actualizarProducto(request, response);
                        break;
                    case "eliminarProducto":
                        eliminarProducto(request, response);
                        break;
                    case "inventarioTotal":
                        inventarioTotal(request, response);
                        break;
                    default:
                        response.sendRedirect("identificar.jsp");
                }
            }else{
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

    private void listarProducto(HttpServletRequest request, HttpServletResponse response) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DAOPRODUCTO dao = new DAOPRODUCTO();
        List<Producto> prod = null;
        try{
            prod = dao.listarProductos();
            request.setAttribute("productos", prod); 
            
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
    
     private void listarProductoVendedor(HttpServletRequest request, HttpServletResponse response) {
        DAOPRODUCTO dao = new DAOPRODUCTO();
        List<Producto> prod = null;
        try{
            prod = dao.listarProductos();
            request.setAttribute("productos", prod); 
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo listar los productos " + e.getMessage());
        }finally{
            dao = null;
        }
        
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/productosVendedor.jsp").forward(request, response);
        }catch(Exception ex){
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }
        
    }

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/nuevoProducto.jsp").forward(request, response);
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo cargar la vista " + e.getMessage());
        }
    }
    
    private void registrarProducto(HttpServletRequest request, HttpServletResponse response) {
        DAOPRODUCTO daoP;
        Producto pro = null;
        //Usuario usu;
        
        if (request.getParameter("txtTipo") != null
                && request.getParameter("txtMarca") != null
                && request.getParameter("txtInventario") != null
                && request.getParameter("txtCosto") != null
                && request.getParameter("txtPrecio") != null) {

            pro = new Producto();
            pro.setTipoProducto(request.getParameter("txtTipo"));
            pro.setMarcaProducto(request.getParameter("txtMarca"));
            pro.setInventarioProducto(Integer.parseInt(request.getParameter("txtInventario")));
            pro.setCostoUnitario(Integer.parseInt(request.getParameter("txtCosto")));
            pro.setPrecioVenta(Integer.parseInt(request.getParameter("txtPrecio")));
                                    
            daoP = new DAOPRODUCTO();
            try {
                daoP.registrarProducto(pro);
                response.sendRedirect("srvProducto?accion=listarProductos");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo registrar el producto" + e.getMessage());
                request.setAttribute("producto", pro);
                this.presentarFormulario(request, response);
            }
        }
        
    }

    private void presentarProducto(HttpServletRequest request, HttpServletResponse response) {
        DAOPRODUCTO dao;
        Producto prodc;
        if (request.getParameter("cod") != null) {
            prodc = new Producto();
            prodc.setIdProducto(Integer.parseInt(request.getParameter("cod")));
                    
            dao = new DAOPRODUCTO();
            try {
                prodc = dao.leerProducto(prodc);
                if (prodc != null) {
                    request.setAttribute("producto", prodc);
                } else {
                    request.setAttribute("msje", "No se encontró el producto");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }
        try {
            
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/actualizarProducto.jsp"
                    ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getMessage());
        }
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response) {
        DAOPRODUCTO daoPd;
        Producto prodc = null;
                
        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtTipo") != null
                && request.getParameter("txtMarca") != null
                && request.getParameter("txtInventario") != null
                && request.getParameter("txtCosto") != null
                && request.getParameter("txtPrecio") != null) {
            
            prodc = new Producto();
            prodc.setIdProducto(Integer.parseInt(request.getParameter("hCodigo")));
            prodc.setTipoProducto(request.getParameter("txtTipo"));
            prodc.setMarcaProducto(request.getParameter("txtMarca"));
            prodc.setInventarioProducto(Integer.parseInt(request.getParameter("txtInventario")));
            prodc.setCostoUnitario(Integer.parseInt(request.getParameter("txtCosto")));
            prodc.setPrecioVenta(Integer.parseInt(request.getParameter("txtPrecio")));
                        
            daoPd = new DAOPRODUCTO();
            try {
                daoPd.actualizarProducto(prodc);
                response.sendRedirect("srvProducto?accion=listarProductos");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar el Producto " + e.getMessage());
                request.setAttribute("producto", prodc);
                
            }
            try {
                this.getServletConfig().getServletContext().
                        getRequestDispatcher("/vistas/actualizarProducto.jsp"
                        ).forward(request, response);
            } catch (Exception ex) {
                request.setAttribute("msje", "No se pudo realizar la operacion " + ex.getMessage());
            }
        }
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) {
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
        
        /*try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/productos.jsp").forward(request, response);
        }catch(Exception ex){
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }*/
    }
}
