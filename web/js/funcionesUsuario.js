$(document).ready(function () {
    $("tr #deleteUser").click(function (e) {
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
                        eliminarUsuario(cod);
                        swal("Eliminado!", "El registro seleccionado ha sido eliminado.", "success");
                        setTimeout(function(){
                            parent.location.href = "srvUsuario?accion=listarUsuarios"
                        }, 1800);
                    } else {
                        swal("Cancelado", "El registro no se eliminó", "error");
                    }
                });
    });
    
    function eliminarUsuario(cod){
        var url = "srvUsuario?accion=eliminarUsuario&cod=" + cod;
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