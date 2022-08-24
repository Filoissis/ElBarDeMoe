package Controlador;

import Modelo.DAOEMPLEADO;
import Modelo.DAOPRODUCTO;
import Modelo.DAOVENTA;
import Modelo.Empleado;
import Modelo.Producto;
import Modelo.Venta;
import java.io.IOException;
import java.io.InputStream;
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
import javax.servlet.ServletOutputStream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "srvVenta", urlPatterns = {"/srvVenta"})

public class srvVenta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        try {
            if (accion != null) {
                switch (accion) {
                    case "listarVentas":
                        listarVentas(request, response);
                        break;
                    case "listarVentasVendedor":
                        listarVentasVendedor(request, response);
                        break;
                    case "nuevoVenta":
                        presentarFormulario(request, response);
                        break;
                    case "nuevaVentaVendedor":
                        presentarFormularioVendedor(request, response);
                        break;
                    case "registrarVenta":
                        registrarVenta(request, response);
                        break;
                    case "registrarVentaVendedor":
                        registrarVentaVendedor(request, response);
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
                    case "exportarReporteVentas":
                        exportarReporteVentas(request, response);
                        break;

                    default:
                        response.sendRedirect("identificar.jsp");
                }
            } else {
                response.sendRedirect("identificar.jsp");
            }
        } catch (Exception e) {
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
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/ventas.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
        }

    }

    private void listarVentasVendedor(HttpServletRequest request, HttpServletResponse response) {
        DAOVENTA dao = new DAOVENTA();
        List<Venta> vent = null;
        Venta sal = new Venta();
        Empleado empl = new Empleado();

        if (request.getParameter("idven") != null) {
            empl.setIdEmpleado(Integer.parseInt(request.getParameter("idven")));
            sal.setEmpleado(empl);

            try {
                vent = dao.listarVentas(sal);
                request.setAttribute("ventas", vent);

            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo listar las ventas " + e.getMessage());
            } finally {
                dao = null;
            }

            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/ventasVendedor.jsp").forward(request, response);
            } catch (Exception ex) {
                request.setAttribute("msje", "No se pudo realizar la petición " + ex.getMessage());
            }

        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }

    }

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.cargarProductos(request);
            this.cargarEmpleados(request);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/nuevaVenta.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo cargar la vista " + e.getMessage());
        }
    }
    
    private void presentarFormularioVendedor(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.cargarProductos(request);
            this.cargarEmpleados(request);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/nuevaVentaVendedor.jsp").forward(request, response);
        } catch (Exception e) {
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
                && request.getParameter("cboEmpleado") != null) {

            sale = new Venta();
            daoPrd = new DAOPRODUCTO();

            prod = new Producto();
            prod.setIdProducto(Integer.parseInt(request.getParameter("cboProducto")));

            try {
                prod = daoPrd.leerProducto(prod);
            } catch (Exception ex) {

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
    
    private void registrarVentaVendedor(HttpServletRequest request, HttpServletResponse response) {
        DAOVENTA daoV;
        DAOPRODUCTO daoPrd;
        Venta sale = null;
        Producto prod;
        Empleado emp;

        if (request.getParameter("cboProducto") != null
                && request.getParameter("txtCantidad") != null
                && request.getParameter("cboEmpleado") != null) {

            sale = new Venta();
            daoPrd = new DAOPRODUCTO();

            prod = new Producto();
            prod.setIdProducto(Integer.parseInt(request.getParameter("cboProducto")));

            try {
                prod = daoPrd.leerProducto(prod);
            } catch (Exception ex) {

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
                //response.sendRedirect("srvVenta?accion=listarVentasVendedor");
                this.listarVentasVendedor(request, response);
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
        try {
            prod = dao.listarProductos();
            request.setAttribute("productos", prod);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo cargar los Proveedores " + e.getMessage());
        } finally {
            prod = null;
            dao = null;
        }
    }

    private void cargarEmpleados(HttpServletRequest request) {
        DAOEMPLEADO dao = new DAOEMPLEADO();
        List<Empleado> empl = null;
        try {
            empl = dao.listarEmpleados();
            request.setAttribute("empleados", empl);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo cargar los Empleados " + e.getMessage());
        } finally {
            empl = null;
            dao = null;
        }
    }

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

            if (Integer.parseInt(request.getParameter("cboProducto")) != 0) {
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

            } else if (request.getParameter("fechaInicio") != null
                    && request.getParameter("fechaFinal") != null) {

                inicio = request.getParameter("fechaInicio");
                SimpleDateFormat fechaIni = new SimpleDateFormat("yyyy-MM-dd");
                Date iniDate = fechaIni.parse(inicio);

                fin = request.getParameter("fechaFinal");
                SimpleDateFormat fechaFin = new SimpleDateFormat("yyyy-MM-dd");
                Date finDate = fechaFin.parse(fin);

                if (iniDate.before(finDate)) {
                    vent = dao.listarVentas(inicio, fin);
                } else {
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

    private void exportarReporteVentas(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        try {
            InputStream logoEmpresa = this.getServletConfig()
                    .getServletContext()
                    .getResourceAsStream("reportesJasper/img/ElBarDeMoe.jpg"),
                    logoFooter = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/img/Duff1.png"),
                    reporteVentas = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/ReporteVentas.jasper");
            if (logoEmpresa != null && logoFooter != null && reporteVentas != null) {
                String jsonVentas = request.getParameter("lista");
                Gson gson = new Gson();
                List<Venta> reportesVentas = new ArrayList<>();
                List<Venta> reportesVentas2 = new ArrayList<>();

                reportesVentas.add(new Venta());
                reportesVentas2 = gson.fromJson(jsonVentas, new TypeToken<List<Empleado>>() {
                }.getType());
                reportesVentas.addAll(reportesVentas2);

                JasperReport report = (JasperReport) JRLoader.loadObject(reporteVentas);
                JRBeanArrayDataSource ds = new JRBeanArrayDataSource(reportesVentas.toArray());

                Map<String, Object> parameters = new HashMap();
                parameters.put("ds", ds);
                parameters.put("logoEmpresa", logoEmpresa);
                parameters.put("imagenAlternativa", logoFooter);
                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", "inline; filename=ReportesVentas.pdf");
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                out.flush();
                out.close();

            } else {
                response.setContentType("text/plain");
                out.println("No fue posible generar el reporte");
                out.println("Contacte a soporte");
            }

        } catch (Exception ex) {
            response.setContentType("text/plain");
            out.print("Ocurrió un error al intentar generar el reporte: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
