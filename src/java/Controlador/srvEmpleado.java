
package Controlador;

import Modelo.DAOEMPLEADO;
import Modelo.Empleado;
//import Modelo.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "srvEmpleado", urlPatterns = {"/srvEmpleado"})

public class srvEmpleado extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        
        try{
            if(accion != null){
                switch(accion){
                    case "listarEmpleados":
                        listarEmpleado(request, response);
                        break;
                    case "nuevoEmpleado":
                        presentarFormulario(request, response);
                        break;
                    case "registrarEmpleado":
                        registrarEmpleado(request, response);
                        break;
                    case "leerEmpleado":
                        presentarEmpleado(request, response);
                        break;
                    case "actualizarEmpleado":
                        actualizarEmpleado(request, response);
                        break;
                    case "eliminarEmpleado":
                        eliminarEmpleado(request, response);
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

        
    private void listarEmpleado(HttpServletRequest request, HttpServletResponse response) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DAOEMPLEADO dao = new DAOEMPLEADO();
        List<Empleado> empl = null;
        try{
            empl = dao.listarEmpleados();
            request.setAttribute("empleados", empl); //("proveedores", provs);
            
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo listar los empleados " + e.getMessage());
        }finally{
            dao = null;
        }
        
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/empleados.jsp").forward(request, response);
        }catch(Exception ex){
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }
        
    }

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try{
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/nuevoEmpleado.jsp").forward(request, response);
        }catch(Exception e){
            request.setAttribute("msje", "No se pudo cargar la vista " + e.getMessage());
        }
    }
    
    private void registrarEmpleado(HttpServletRequest request, HttpServletResponse response) {
        DAOEMPLEADO daoEmple;
        Empleado emp = null;
        //Usuario usu;
        
        if (/*request.getParameter("hCodigo") != null
                && */request.getParameter("txtNombre") != null
                && request.getParameter("txtApellido") != null
                && request.getParameter("txtDocumento") != null
                && request.getParameter("txtTelefono") != null
                && request.getParameter("txtSalario") != null) {

            emp = new Empleado();
            //emp.setIdEmpleado(Integer.parseInt(request.getParameter("hCodigo")));
            emp.setNombreEmpleado(request.getParameter("txtNombre"));
            emp.setApellidoEmpleado(request.getParameter("txtApellido"));
            emp.setDocumentoEmpleado(Integer.parseInt(request.getParameter("txtDocumento")));
            emp.setTelefonoEmpleado(Integer.parseInt(request.getParameter("txtTelefono")));
            emp.setSalarioEmpleado(Integer.parseInt(request.getParameter("txtSalario")));
                                    
            daoEmple = new DAOEMPLEADO();
            try {
                daoEmple.registrarEmpleado(emp);
                response.sendRedirect("srvEmpleado?accion=listarEmpleados");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo registrar el empleado" + e.getMessage());
                request.setAttribute("empleado", emp);
                this.presentarFormulario(request, response);
            }
        }
        
    }

    private void presentarEmpleado(HttpServletRequest request, HttpServletResponse response) {
        DAOEMPLEADO dao;
        Empleado emplea;
        if (request.getParameter("cod") != null) {
            emplea = new Empleado();
            emplea.setIdEmpleado(Integer.parseInt(request.getParameter("cod")));
                    
            dao = new DAOEMPLEADO();
            try {
                emplea = dao.leerEmpleado(emplea);
                if (emplea != null) {
                    request.setAttribute("empleado", emplea);
                } else {
                    request.setAttribute("msje", "No se encontró el empleado");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }
        try {
            
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/actualizarEmpleado.jsp"
                    ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getMessage());
        }
    }

    private void actualizarEmpleado(HttpServletRequest request, HttpServletResponse response) {
        DAOEMPLEADO daoemp;
        Empleado emplea = null;
        //Usuario usur;
        
        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtNombre") != null
                && request.getParameter("txtApellido") != null
                && request.getParameter("txtDocumento") != null
                && request.getParameter("txtTelefono") != null
                && request.getParameter("txtSalario") != null) {
            
            emplea = new Empleado();
            emplea.setIdEmpleado(Integer.parseInt(request.getParameter("hCodigo")));
            emplea.setNombreEmpleado(request.getParameter("txtNombre"));
            emplea.setApellidoEmpleado(request.getParameter("txtApellido"));
            emplea.setDocumentoEmpleado(Integer.parseInt(request.getParameter("txtDocumento")));
            emplea.setTelefonoEmpleado(Integer.parseInt(request.getParameter("txtTelefono")));
            emplea.setSalarioEmpleado(Integer.parseInt(request.getParameter("txtSalario")));
                        
            daoemp = new DAOEMPLEADO();
            try {
                daoemp.actualizarEmpleado(emplea);
                response.sendRedirect("srvEmpleado?accion=listarEmpleados");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar el empleado " + e.getMessage());
                request.setAttribute("empleado", emplea);
                
            }
            try {
                this.getServletConfig().getServletContext().
                        getRequestDispatcher("/vistas/actualizarEmpleado.jsp"
                        ).forward(request, response);
            } catch (Exception ex) {
                request.setAttribute("msje", "No se pudo realizar la operacion " + ex.getMessage());
            }
        }
    }

    private void eliminarEmpleado(HttpServletRequest request, HttpServletResponse response) {
        DAOEMPLEADO dao = new DAOEMPLEADO();
        Empleado provs = new Empleado();
        if (request.getParameter("cod")!=null){
            provs.setIdEmpleado(Integer.parseInt(request.getParameter("cod")));
            try{
                dao.eliminarEmpleado(provs);
                response.sendRedirect("srvEmpleado?accion=listarEmpleados");
            }catch(Exception e){
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        }else{
            request.setAttribute("msje", "No se encontro el empleado");
        }

    }
}
