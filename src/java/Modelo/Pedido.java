package Modelo;

import java.util.Date;

public class Pedido {
    private int idPedido;
    private String tipoProdPedido;
    private String marcaProdPedido;
    private int cantidadPedido;
    private int costoTotal;
    private Date fechaPedido;
    private boolean estadoPedido;
    private Proveedor proveedor;
    private Empleado empleado;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getTipoProdPedido() {
        return tipoProdPedido;
    }

    public void setTipoProdPedido(String tipoProdPedido) {
        this.tipoProdPedido = tipoProdPedido;
    }

    public String getMarcaProdPedido() {
        return marcaProdPedido;
    }

    public void setMarcaProdPedido(String marcaProdPedido) {
        this.marcaProdPedido = marcaProdPedido;
    }

    public int getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public int getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(int costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public boolean getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(boolean estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    
}
