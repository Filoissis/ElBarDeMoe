function reporte(accion){
    $.get('../srvVenta?accion=listarVentas', function (r){
        if (r){
            $('#accion').val(accion);
            $('#lista').val(JSON.stringify(r));
            $('#frmReporte').submit();
        }else{
            alert('El reporte no se pudo generar debido a un error: ' + r);
        }
    });
}