
package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOPRODUCTO extends Conexion {
    public List<Producto> listarProductos() throws Exception {
        List<Producto> productos;
        Producto prod;
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllProductos()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            productos = new ArrayList<>();
            while (rs.next() == true) {
                prod = new Producto();
                prod.setIdProducto(rs.getInt("idTblProducto"));
                prod.setTipoProducto(rs.getString("ProdTipo"));
                prod.setMarcaProducto(rs.getString("ProdMarca"));
                prod.setInventarioProducto(rs.getInt("ProdInventario"));
                prod.setCostoUnitario(rs.getInt("ProdCostoUnitario"));
                prod.setPrecioVenta(rs.getInt("ProdPrecioVenta"));
                productos.add(prod);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return productos;
    }
    
    public void registrarProducto(Producto prodc) throws Exception {
        String sql;
        sql = "{ call SetProducto('" + prodc.getTipoProducto()+ "','"+ prodc.getMarcaProducto()+ "'," + prodc.getInventarioProducto()+ ", " + prodc.getCostoUnitario()+ ", " + prodc.getPrecioVenta()+ ")}";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public Producto leerProducto(Producto pro) throws Exception {
        Producto prod = null;
        ResultSet rs = null;
        String sql = "{ call mydb.GetProductoById("+ pro.getIdProducto() + ")}";
                
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                prod = new Producto();
                prod.setIdProducto(rs.getInt("idTblProducto"));
                prod.setTipoProducto(rs.getString("ProdTipo"));
                prod.setMarcaProducto(rs.getString("ProdMarca"));
                prod.setInventarioProducto(rs.getInt("ProdInventario"));
                prod.setCostoUnitario(rs.getInt("ProdCostoUnitario"));
                prod.setPrecioVenta(rs.getInt("ProdPrecioVenta"));
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return prod;
    }
    
    public void actualizarProducto(Producto pro) throws Exception{
        String sql = "{ call mydb.UpdateProductoById("
                + pro.getIdProducto()+ ", '"
                + pro.getTipoProducto()+ "', '"
                + pro.getMarcaProducto()+ "', "
                + pro.getInventarioProducto()+ ", "
                + pro.getCostoUnitario()+ ", "
                + pro.getPrecioVenta()+ ")} ";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public void eliminarProducto(Producto pro) throws Exception{
        String sql = "{ call mydb.DeleteProductoById("+ pro.getIdProducto()+ ")}";
        
        try{
           this.conectar(false);
           this.ejecutarOrden(sql);
           this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public int inventarioTotal() throws Exception {
        //Producto prod = null;
        int invent = 0;
        ResultSet rs = null;
        String sql = "{ call mydb.GetInventarioTotal()}";
                
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                //prod = new Producto();
                //prod.setInventarioProducto(rs.getInt("ProdInventario"));
                invent = rs.getInt("ProdInventario");
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return invent;
    }
    
    public void actualizarInventario(Pedido ped) throws Exception {
        Producto prod;
        Producto inv = null;
        
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllProductos()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            while (rs.next() == true) {
                prod = new Producto();
                prod.setIdProducto(rs.getInt("idTblProducto"));
                prod.setTipoProducto(rs.getString("ProdTipo"));
                prod.setMarcaProducto(rs.getString("ProdMarca"));
                prod.setInventarioProducto(rs.getInt("ProdInventario"));
                prod.setCostoUnitario(rs.getInt("ProdCostoUnitario"));
                prod.setPrecioVenta(rs.getInt("ProdPrecioVenta"));
                
                if(prod.getTipoProducto().equals(ped.getTipoProdPedido()) && prod.getMarcaProducto().equals(ped.getMarcaProdPedido())){
                    inv = new Producto();
                    inv.setIdProducto(prod.getIdProducto());
                    inv.setTipoProducto(prod.getTipoProducto());
                    inv.setMarcaProducto(prod.getMarcaProducto());
                    inv.setInventarioProducto(prod.getInventarioProducto() + ped.getCantidadPedido());
                    inv.setCostoUnitario(ped.getCostoTotal()/ped.getCantidadPedido());
                    inv.setPrecioVenta(prod.getPrecioVenta());
                }
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        
        rs = null;
        if(inv != null){
            sql = "{ call mydb.UpdateProductoById("
                + inv.getIdProducto()+ ", '"
                + inv.getTipoProducto()+ "', '"
                + inv.getMarcaProducto()+ "', "
                + inv.getInventarioProducto()+ ", "
                + inv.getCostoUnitario()+ ", "
                + inv.getPrecioVenta()+ ")} ";
        }else{
            inv = new Producto();
                    inv.setTipoProducto(ped.getTipoProdPedido());
                    inv.setMarcaProducto(ped.getMarcaProdPedido());
                    inv.setInventarioProducto(ped.getCantidadPedido());
                    inv.setCostoUnitario(ped.getCostoTotal()/ped.getCantidadPedido());
                    inv.setPrecioVenta((ped.getCostoTotal()/ped.getCantidadPedido())*2 );
            sql = "{ call SetProducto('" + inv.getTipoProducto()+ "','"
                    + inv.getMarcaProducto()+ "'," 
                    + inv.getInventarioProducto()+ ", " 
                    + inv.getCostoUnitario()+ ", " 
                    + inv.getPrecioVenta()+ ")}";
        }
        
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
        
    }
    
    public void actualizarInventario(Venta sale) throws Exception {
        Producto prod;
        Producto inv = null;
        
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllProductos()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            while (rs.next() == true) {
                prod = new Producto();
                prod.setIdProducto(rs.getInt("idTblProducto"));
                prod.setTipoProducto(rs.getString("ProdTipo"));
                prod.setMarcaProducto(rs.getString("ProdMarca"));
                prod.setInventarioProducto(rs.getInt("ProdInventario"));
                prod.setCostoUnitario(rs.getInt("ProdCostoUnitario"));
                prod.setPrecioVenta(rs.getInt("ProdPrecioVenta"));
                
                if(prod.getIdProducto() == sale.getProducto().getIdProducto()){
                    inv = new Producto();
                    inv.setIdProducto(prod.getIdProducto());
                    inv.setTipoProducto(prod.getTipoProducto());
                    inv.setMarcaProducto(prod.getMarcaProducto());
                    if (sale.getCantidadVendida() <= prod.getInventarioProducto()){
                        inv.setInventarioProducto(prod.getInventarioProducto() - sale.getCantidadVendida());
                    } else {
                        inv.setInventarioProducto(0);
                    }
                    
                    inv.setCostoUnitario(prod.getCostoUnitario());
                    inv.setPrecioVenta(prod.getPrecioVenta());
                }
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        
        rs = null;
        
        sql = "{ call mydb.UpdateProductoById("
            + inv.getIdProducto()+ ", '"
            + inv.getTipoProducto()+ "', '"
            + inv.getMarcaProducto()+ "', "
            + inv.getInventarioProducto()+ ", "
            + inv.getCostoUnitario()+ ", "
            + inv.getPrecioVenta()+ ")} ";
        
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
