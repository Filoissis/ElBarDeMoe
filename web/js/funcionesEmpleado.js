$(document).ready(function () {
    $("tr #deleteEmpleado").click(function (e) {
        e.preventDefault();
        var cod = $(this).parent().find('#codigo').val();
        swal({
            title: "¿Está seguro?",
            text: "No se podrá recuperar este registro",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "Si, Borrar",
            cancelButtonText: "No, Cancelar",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        eliminarEmpleado(cod);
                        swal("Eliminado!", "El registro seleccionado ha sido eliminado.", "success");
                        setTimeout(function(){
                            parent.location.href = "srvEmpleado?accion=listarEmpleados"
                        }, 1800);
                    } else {
                        swal("Cancelado", "El registro no se eliminó", "error");
                    }
                });
    });
    
    function eliminarEmpleado(cod){
        var url = "srvEmpleado?accion=eliminarEmpleado&cod=" + cod;
        console.log("eliminado");
        $.ajax({
           type: 'POST', 
           url: url,
           async: true,
           success: function (r) {
               
           }
        });
    }
});