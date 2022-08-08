
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
}
