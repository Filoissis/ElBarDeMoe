
package Modelo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DAOCARGO extends Conexion {
    public List<Cargo> listarCargos() throws Exception {
        List<Cargo> cargos;
        Cargo carg;
        ResultSet rs = null;
        String sql = "{ call mydb.GetAllCargos()}";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            cargos = new ArrayList<>();
            while (rs.next() == true) {
                carg = new Cargo();
                carg.setIdCargo(rs.getInt("idTblCargo")); 
                carg.setNombreCargo(rs.getString("CargoNombre"));
                carg.setEstado(rs.getBoolean("CargoEstado"));
                cargos.add(carg);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return cargos;
    }
}
