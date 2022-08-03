$(document).ready(function () {
    $("tr #deleteProveedor").click(function (e) {
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
                        eliminarProveedor(cod);
                        swal("Eliminado!", "El registro seleccionado ha sido eliminado.", "success");
                        setTimeout(function(){
                            parent.location.href = "srvProveedor?accion=listarProveedores"
                        }, 1800);
                    } else {
                        swal("Cancelado", "El registro no se eliminó", "error");
                    }
                });
    });
    
    function eliminarProveedor(cod){
        var url = "srvProveedor?accion=eliminarProveedor&cod=" + cod;
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