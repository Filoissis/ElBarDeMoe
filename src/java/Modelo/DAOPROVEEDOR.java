package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOPROVEEDOR extends Conexion {
    
    public List<Proveedor> listarProveedores() throws Exception {
        List<Proveedor> proveedores;
        Proveedor prov;
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllProveedores()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            proveedores = new ArrayList<>();
            while (rs.next() == true) {
                prov = new Proveedor();
                prov.setIdProveedor(rs.getInt("idTblProveedor"));
                prov.setNombreProveedor(rs.getString("ProvNombre"));
                prov.setDireccionProveedor(rs.getString("ProvDireccion"));
                prov.setTelefonoProveedor(rs.getInt("ProvTelefono"));
                prov.setNitProveedor(rs.getInt("ProvNIT"));
                proveedores.add(prov);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return proveedores;
    }
    
    public void registrarProveedores(Proveedor prove) throws Exception {
        String sql;
        sql = "{ call SetProveedor('" + prove.getNombreProveedor() + "','"+ prove.getDireccionProveedor() + "'," + prove.getTelefonoProveedor() + ", " + prove.getNitProveedor() + ")}";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public Proveedor leerProveedor(Proveedor prov) throws Exception {
        Proveedor provs = null;
        ResultSet rs = null;
        String sql = "{ call mydb.GetProveedorById("+ prov.getIdProveedor() + ")}";
                
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                provs = new Proveedor();
                provs.setIdProveedor(rs.getInt("idTblProveedor"));
                provs.setNombreProveedor(rs.getString("ProvNombre"));
                provs.setDireccionProveedor(rs.getString("ProvDireccion"));
                provs.setTelefonoProveedor(rs.getInt("ProvTelefono"));
                provs.setNitProveedor(rs.getInt("ProvNIT"));
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return provs;
    }
    
    public void actualizarProveedor(Proveedor prov) throws Exception{
        String sql = "{ call mydb.UpdateProveedorById("
                + prov.getIdProveedor() + ", '"
                + prov.getNombreProveedor() + "', '"
                + prov.getDireccionProveedor() + "', "
                + prov.getTelefonoProveedor()+ ", "
                + prov.getNitProveedor()+ ")} ";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public void eliminarProveedor(Proveedor prov) throws Exception{
        String sql = "{ call mydb.DeleteProveedorById("+ prov.getIdProveedor() + ")}";
        
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
