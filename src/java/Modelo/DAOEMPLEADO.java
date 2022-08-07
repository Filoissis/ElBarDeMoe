package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOEMPLEADO extends Conexion {
    public List<Empleado> listarEmpleados() throws Exception {
        List<Empleado> empleados;
        Empleado empl;
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllEmpleados()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            empleados = new ArrayList<>();
            while (rs.next() == true) {
                empl = new Empleado();
                empl.setIdEmpleado(rs.getInt("idTblEmpleado"));
                empl.setNombreEmpleado(rs.getString("EmplNombre"));
                empl.setApellidoEmpleado(rs.getString("EmplApellido"));
                empl.setDocumentoEmpleado(rs.getInt("EmplNumeroDocumento"));
                empl.setTelefonoEmpleado(rs.getInt("EmplTelefono"));
                empl.setSalarioEmpleado(rs.getInt("EmplSalario"));
                //empl.setUsuario(new Usuario());
                //empl.getUsuario().setNombreUsuario(rs.getString("UserNombre")); 
                empleados.add(empl);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return empleados;
    }
    
    public void registrarEmpleado(Empleado emple) throws Exception {
        String sql;
        sql = "{ call SetEmpleado('" + emple.getNombreEmpleado()+ "','"+ emple.getApellidoEmpleado()  + "'," + emple.getDocumentoEmpleado() + ", " + emple.getTelefonoEmpleado() + ", " + emple.getSalarioEmpleado() + ")}";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public Empleado leerEmpleado(Empleado emple) throws Exception {
        Empleado emplead = null;
        ResultSet rs = null;
        String sql = "{ call mydb.GetEmpleadoById("+ emple.getIdEmpleado() + ")}";
                
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                emplead = new Empleado();
                emplead.setIdEmpleado(rs.getInt("idTblEmpleado"));
                emplead.setNombreEmpleado(rs.getString("EmplNombre"));
                emplead.setApellidoEmpleado(rs.getString("EmplApellido"));
                emplead.setDocumentoEmpleado(rs.getInt("EmplNumeroDocumento"));
                emplead.setTelefonoEmpleado(rs.getInt("EmplTelefono"));
                emplead.setSalarioEmpleado(rs.getInt("EmplSalario"));
                //emplead.setUsuario(new Usuario());
                //emplead.getUsuario().setIdUsuario(rs.getInt("TblUsusario_idTblUsusario"));
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
        }
        return emplead;
    }
    
    public void actualizarEmpleado(Empleado emple) throws Exception{
        String sql = "{ call mydb.UpdateEmpleadoById("
                + emple.getIdEmpleado() + ", '"
                + emple.getNombreEmpleado()+ "', '"
                + emple.getApellidoEmpleado()+ "', "
                + emple.getDocumentoEmpleado()+ ", "
                + emple.getTelefonoEmpleado()+ ", "
                + emple.getSalarioEmpleado()+ ")} ";
        try{
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }catch(Exception e){
            this.cerrar(false);
            throw e;
        }
    }
    
    public void eliminarEmpleado(Empleado emple) throws Exception{
        String sql = "{ call mydb.DeleteEmpleadoById("+ emple.getIdEmpleado()+ ")}";
        
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
