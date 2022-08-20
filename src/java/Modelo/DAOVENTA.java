package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DAOVENTA extends Conexion {
    public List<Venta> listarVentas() throws Exception {
        List<Venta> ventas;
        Venta vent;
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllVentas()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            ventas = new ArrayList<>();
            while (rs.next() == true) {
                vent = new Venta();
                vent.setIdVenta(rs.getInt("idTblVenta"));
                vent.setProducto(new Producto());
                vent.getProducto().setTipoProducto(rs.getString("ProdTipo"));
                vent.getProducto().setMarcaProducto(rs.getString("ProdMarca"));
                vent.setCantidadVendida(rs.getInt("VentaCantidad"));
                vent.setPrecioVenta(rs.getInt("VentaPrecioTotal"));
                vent.setFechaVenta(rs.getDate("VentaFecha"));
                vent.setEmpleado(new Empleado());
                vent.getEmpleado().setApellidoEmpleado(rs.getString("EmplApellido"));
                ventas.add(vent);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return ventas;
    }
    
    public List<Venta> listarVentas(Venta sale) throws Exception {
        List<Venta> ventas;
        Venta vent;
        ResultSet rs = null;
        String sql = "";
        
        if (sale.getProducto().getIdProducto() > 0){
            sql = "{ call mydb.GetVentaByProducto(" + sale.getProducto().getIdProducto() + ")}";
        } else if(sale.getEmpleado().getIdEmpleado() > 0){
            sql = "{ call mydb.GetVentaByEmpleado(" + sale.getEmpleado().getIdEmpleado() + ")}";
        } else {
            sql = "{ call mydb.GetAllVentas()}";
        }
        
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            ventas = new ArrayList<>();
            while (rs.next() == true) {
                vent = new Venta();
                vent.setIdVenta(rs.getInt("idTblVenta"));
                vent.setProducto(new Producto());
                vent.getProducto().setTipoProducto(rs.getString("ProdTipo"));
                vent.getProducto().setMarcaProducto(rs.getString("ProdMarca"));
                vent.setCantidadVendida(rs.getInt("VentaCantidad"));
                vent.setPrecioVenta(rs.getInt("VentaPrecioTotal"));
                vent.setFechaVenta(rs.getDate("VentaFecha"));
                vent.setEmpleado(new Empleado());
                vent.getEmpleado().setApellidoEmpleado(rs.getString("EmplApellido"));
                ventas.add(vent);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return ventas;
    }
    
    public List<Venta> listarVentas(String fInicial, String fFinal) throws Exception {
        List<Venta> ventas;
        Venta vent;
        ResultSet rs = null;
        String sql = "{ call mydb.GetVentaByFechas('" + fInicial + "', '" + fFinal + "')}";
        
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            ventas = new ArrayList<>();
            while (rs.next() == true) {
                vent = new Venta();
                vent.setIdVenta(rs.getInt("idTblVenta"));
                vent.setProducto(new Producto());
                vent.getProducto().setTipoProducto(rs.getString("ProdTipo"));
                vent.getProducto().setMarcaProducto(rs.getString("ProdMarca"));
                vent.setCantidadVendida(rs.getInt("VentaCantidad"));
                vent.setPrecioVenta(rs.getInt("VentaPrecioTotal"));
                vent.setFechaVenta(rs.getDate("VentaFecha"));
                vent.setEmpleado(new Empleado());
                vent.getEmpleado().setApellidoEmpleado(rs.getString("EmplApellido"));
                ventas.add(vent);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return ventas;
    }
    
    public void registrarVenta(Venta sale) throws Exception {
        String sql;
                
        sql = "{ call SetVenta(" + sale.getProducto().getIdProducto() + "," + sale.getCantidadVendida() + ", " + sale.getPrecioVenta()+ ", " + sale.getEmpleado().getIdEmpleado()+ ")}";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public Venta leerVenta(Venta sale) throws Exception {
        Venta vent = null;
        ResultSet rs = null;
        String sql = "{ call mydb.GetVentaById("+ sale.getIdVenta()+ ")}";
                
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                vent = new Venta();
                vent.setIdVenta(rs.getInt("idTblVenta"));
                vent.setProducto(new Producto());
                vent.getProducto().setTipoProducto(rs.getString("ProdTipo"));
                vent.getProducto().setMarcaProducto(rs.getString("ProdMarca"));
                vent.setCantidadVendida(rs.getInt("VentaCantidad"));
                vent.setPrecioVenta(rs.getInt("VentaPrecioTotal"));
                vent.setFechaVenta(rs.getDate("VentaFecha"));
                vent.setEmpleado(new Empleado());
                vent.getEmpleado().setApellidoEmpleado(rs.getString("EmplApellido"));
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return vent;
    }
    
    public void actualizarVenta(Venta sale) throws Exception{
        String sql = "{ call mydb.UpdateVentaById("
                + sale.getIdVenta()+ ", "
                + sale.getProducto().getIdProducto()+ ", "
                + sale.getCantidadVendida()+ ", "
                + sale.getPrecioVenta()+ ", "
                + sale.getEmpleado().getIdEmpleado() + ")} ";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    /*public void cambiarEstado(Pedido pedi) throws Exception{
        String sql = "{ call mydb.UpdateEstadoPedidoByID("
                + pedi.getIdPedido()+ ", "
                + pedi.getEstadoPedido()+ ")} ";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }*/
    
}
