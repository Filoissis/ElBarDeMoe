package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOUSUARIO extends Conexion {
    
    public Usuario identificar(Usuario user) throws Exception{
        Usuario usu = null;
        //conexion con;
        //Connection cn = null;
        //Statement st = null;
        ResultSet rs = null;
        String sql = "{ call GetUsuarioActivo('" + user.getNombreUsuario() + "','"+ user.getClave() + "')}";
        //con = new conexion();
        
        try{
            this.conectar(false);
            //rs = st.executeQuery(sql);
            rs = this.ejecutarOrdenDatos(sql);
            if(rs.next() == true){
                usu = new Usuario();
                usu.setIdUsuario(rs.getInt("idTblUsusario"));
                usu.setNombreUsuario(user.getNombreUsuario());
                usu.setCargo(new Cargo());
                usu.getCargo().setNombreCargo(rs.getString("CargoNombre"));
                usu.setEstado(true);
            }
            rs.close();
        }catch(Exception e){
            System.out.println("Error " + e.getMessage());
        }finally{
            this.cerrar(false);
        }
        return usu;
    }
    
    public List<Usuario> listarUsuarios() throws Exception {
        List<Usuario> usuarios;
        Usuario usu;
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllUsuarios()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            usuarios = new ArrayList<>();
            while (rs.next() == true) {
                usu = new Usuario();
                usu.setIdUsuario(rs.getInt("idTblUsusario"));
                usu.setNombreUsuario(rs.getString("UserNombre"));
                usu.setClave(rs.getString("UserClave"));
                usu.setEstado(rs.getBoolean("UserEstado"));
                usu.setCargo(new Cargo());
                usu.getCargo().setNombreCargo(rs.getString("CargoNombre"));
                usuarios.add(usu);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return usuarios;
    }
    
    public void registrarUsuarios(Usuario user) throws Exception {
        String sql;
        sql = "{ call SetUsusario('" + user.getNombreUsuario() + "','"+ user.getClave() + "'," + (user.isEstado() == true ? "1":"0") + ", " + user.getCargo().getIdCargo() + ")}";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public Usuario leerUsuario(Usuario usu) throws Exception {
        Usuario usus = null;
        ResultSet rs = null;
        String sql = "{ call mydb.GetUsuarioById('"+ usu.getIdUsuario() + "')}";
                
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usus = new Usuario();
                usus.setIdUsuario(rs.getInt("idTblUsusario"));
                usus.setNombreUsuario(rs.getString("UserNombre"));
                usus.setClave(rs.getString("UserClave"));
                usus.setEstado(rs.getBoolean("UserEstado"));
                usus.setCargo(new Cargo());
                usus.getCargo().setIdCargo(rs.getInt("TblCargo_idTblCargo"));
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return usus;
    }
    
    public void actualizarUsuarios(Usuario usu) throws Exception{
        String sql = "{ call mydb.UpdateUsuarioById("
                + usu.getIdUsuario() + ", '"
                + usu.getNombreUsuario() + "', '"
                + usu.getClave() + "', "
                + (usu.isEstado() == true ? "1": "0") + ", "
                + usu.getCargo().getIdCargo()+ ")} ";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public void eliminarUsuario(Usuario usu) throws Exception{
        String sql = "{ call mydb.DeleteUsuarioById("+ usu.getIdUsuario() + ")}";
        
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
