package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOPEDIDO extends Conexion {
    public List<Pedido> listarPedidos() throws Exception {
        List<Pedido> pedidos;
        Pedido ped;
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllPedidos()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            pedidos = new ArrayList<>();
            while (rs.next() == true) {
                ped = new Pedido();
                ped.setIdPedido(rs.getInt("idPedidoFactura"));
                ped.setTipoProdPedido(rs.getString("PedTipoProducto"));
                ped.setMarcaProdPedido(rs.getString("PedMarcaProducto"));
                ped.setCantidadPedido(rs.getInt("PedCantidadProducto"));
                ped.setCostoTotal(rs.getInt("PedCostoTotal"));
                ped.setFechaPedido(rs.getDate("PedFecha"));
                ped.setEstadoPedido(rs.getBoolean("PedActivo"));
                ped.setProveedor(new Proveedor());
                ped.getProveedor().setNombreProveedor(rs.getString("ProvNombre"));
                ped.setEmpleado(new Empleado());
                ped.getEmpleado().setApellidoEmpleado(rs.getString("EmplApellido"));
                pedidos.add(ped);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return pedidos;
    }
    
    public void registrarPedido(Pedido pedi) throws Exception {
        String sql;
                
        sql = "{ call SetPedido('" + pedi.getTipoProdPedido()+ "','"+ pedi.getMarcaProdPedido()+ "'," + pedi.getCantidadPedido()+ ", " + pedi.getCostoTotal()+ ", " + (pedi.getEstadoPedido() == true ? "1":"0") + ", " + pedi.getProveedor().getIdProveedor()+ ", " + pedi.getEmpleado().getIdEmpleado()+ ")}";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public Pedido leerPedido(Pedido pedi) throws Exception {
        Pedido ped = null;
        ResultSet rs = null;
        String sql = "{ call mydb.GetPedidoById("+ pedi.getIdPedido()+ ")}";
                
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                ped = new Pedido();
                ped.setIdPedido(rs.getInt("idPedidoFactura"));
                ped.setTipoProdPedido(rs.getString("PedTipoProducto"));
                ped.setMarcaProdPedido(rs.getString("PedMarcaProducto"));
                ped.setCantidadPedido(rs.getInt("PedCantidadProducto"));
                ped.setCostoTotal(rs.getInt("PedCostoTotal"));
                ped.setFechaPedido(rs.getDate("PedFecha"));
                ped.setEstadoPedido(rs.getBoolean("PedActivo"));
                ped.setProveedor(new Proveedor());
                ped.getProveedor().setNombreProveedor(rs.getString("ProvNombre"));
                ped.setEmpleado(new Empleado());
                ped.getEmpleado().setApellidoEmpleado(rs.getString("EmplApellido"));
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return ped;
    }
    
    public void actualizarPedido(Pedido pedi) throws Exception{
        String sql = "{ call mydb.UpdatePedidoById("
                + pedi.getIdPedido()+ ", '"
                + pedi.getTipoProdPedido()+ "', '"
                + pedi.getMarcaProdPedido()+ "', "
                + pedi.getCantidadPedido()+ ", "
                + pedi.getCostoTotal()+ ", "
                + pedi.getEstadoPedido() + ", "
                + pedi.getProveedor().getIdProveedor()+ ", " 
                + pedi.getEmpleado().getIdEmpleado() + ")} ";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public void cambiarEstado(Pedido pedi) throws Exception{
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
    }   
}
