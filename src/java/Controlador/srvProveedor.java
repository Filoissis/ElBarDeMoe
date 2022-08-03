package Controlador;

import Modelo.DAOPROVEEDOR;
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

@WebServlet(name = "srvProveedor", urlPatterns = {"/srvProveedor"})

public class srvProveedor extends HttpServlet {
       
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        
        try{
            if(accion != null){
                switch(accion){
                    case "listarProveedores":
                        listarProveedor(request, response);
                        break;
                    case "nuevoProveedor":
                        presentarFormulario(request, response);
                        break;
                    case "registrarProveedor":
                        registrarProveedor(request, response);
                        break;
                    case "leerProveedor":
                        presentarProveedor(request, response);
                        break;
                    case "actualizarProveedor":
                        actualizarProveedor(request, response);
                        break;
                    case "eliminarProveedor":
                        eliminarProveedor(request, response);
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

        private Proveedor obtenerProveedor(HttpServletRequest request) {
        Proveedor p = new Proveedor();
        p.setNombreProveedor(request.getParameter("txtProv"));
        return p;
    }

    private void listarProveedor(HttpServletRequest request, HttpServletResponse response) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DAOPROVEEDOR dao = new DAOPROVEEDOR();
        List<Proveedor> provs = null;
        try{
            provs = dao.listarProveedores();
            request.setAttribute("proveedores", provs);
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo listar los proveedores " + e.getMessage());
        }finally{
            dao = null;
        }
        
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/proveedores.jsp").forward(request, response);
        }catch(Exception ex){
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }
        
    }

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/nuevoProveedor.jsp").forward(request, response);
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo cargar la vista " + e.getMessage());
        }
    }
    
    private void registrarProveedor(HttpServletRequest request, HttpServletResponse response) {
        DAOPROVEEDOR daoProv;
        Proveedor pro = null;
        
        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtNombre") != null
                && request.getParameter("txtDireccion") != null
                && request.getParameter("txtTelefono") != null
                && request.getParameter("txtNIT") != null) {

            pro = new Proveedor();
            pro.setIdProveedor(Integer.parseInt(request.getParameter("hCodigo")));
            pro.setNombreProveedor(request.getParameter("txtNombre"));
            pro.setDireccionProveedor(request.getParameter("txtDireccion"));
            pro.setTelefonoProveedor(Integer.parseInt(request.getParameter("txtTelefono")));
            pro.setNitProveedor(Integer.parseInt(request.getParameter("txtNIT")));
                        
            daoProv = new DAOPROVEEDOR();
            try {
                daoProv.registrarProveedores(pro);
                response.sendRedirect("srvProveedor?accion=listarProveedores");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo registrar el usuario" + e.getMessage());
                request.setAttribute("proveedor", pro);
                this.presentarFormulario(request, response);
            }
        }
        
    }

    private void presentarProveedor(HttpServletRequest request, HttpServletResponse response) {
        DAOPROVEEDOR dao;
        Proveedor provs;
        if (request.getParameter("cod") != null) {
            provs = new Proveedor();
            provs.setIdProveedor(Integer.parseInt(request.getParameter("cod")));
                    
            dao = new DAOPROVEEDOR();
            try {
                provs = dao.leerProveedor(provs);
                if (provs != null) {
                    request.setAttribute("proveedor", provs);
                } else {
                    request.setAttribute("msje", "No se encontró el proveedor");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }
        try {
            
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/actualizarProveedor.jsp"
                    ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getMessage());
        }
    }

    private void actualizarProveedor(HttpServletRequest request, HttpServletResponse response) {
        DAOPROVEEDOR daoPro;
        Proveedor provs = null;
        
        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtNombre") != null
                && request.getParameter("txtDireccion") != null
                && request.getParameter("txtTelefono") != null
                && request.getParameter("txtNIT") != null) {
            
            provs = new Proveedor();
            provs.setIdProveedor(Integer.parseInt(request.getParameter("hCodigo")));
            provs.setNombreProveedor(request.getParameter("txtNombre"));
            provs.setDireccionProveedor(request.getParameter("txtDireccion"));
            provs.setTelefonoProveedor(Integer.parseInt(request.getParameter("txtTelefono")));
            provs.setNitProveedor(Integer.parseInt(request.getParameter("txtNIT")));
            
            daoPro = new DAOPROVEEDOR();
            try {
                daoPro.actualizarProveedor(provs);
                response.sendRedirect("srvProveedor?accion=listarProveedores");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar el usuario " + e.getMessage());
                request.setAttribute("proveedor", provs);
                
            }
            try {
                this.getServletConfig().getServletContext().
                        getRequestDispatcher("/vistas/actualizarProveedor.jsp"
                        ).forward(request, response);
            } catch (Exception ex) {
                request.setAttribute("msje", "No se pudo realizar la operacion " + ex.getMessage());
            }
        }
    }

    private void eliminarProveedor(HttpServletRequest request, HttpServletResponse response) {
        DAOPROVEEDOR dao = new DAOPROVEEDOR();
        Proveedor provs = new Proveedor();
        if (request.getParameter("cod")!=null){
            provs.setIdProveedor(Integer.parseInt(request.getParameter("cod")));
            try{
                dao.eliminarProveedor(provs);
                response.sendRedirect("srvProveedor?accion=listarProveedores");
            }catch(Exception e){
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        }else{
            request.setAttribute("msje", "No se encontro el proveedor");
        }

    }
    
    
}
