package gestor_tutorias.controlador.Tutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FXMLReporteTutoriaEditar {
    @FXML
    private void initialize() {
        // inicialización básica
    }

    public void configurarVentana(){
        obtenerDatos();
        cargarDatos();
        definirCampoEditable();

    }
    public void obtenerDatos(){
        //obetener datos de la bd
    }
    public void cargarDatos(){
        //pone los datos en los campos con un .setEditable(true) para decidir manualmente que se edita y que no
    }
    public void definirCampoEditable(){
        //permite hacer los campos editables o n o editable con facilidad, (se pueden tocar)
    }
    public void clicCancelar(ActionEvent actionEvent){
        //manda de vuelta
    }
    public void juntarDatos(){
        //reune datos con //private string fecha=setText(parseString(fechaReporteTutoria));
    }
    public void clicGuardar(ActionEvent actionEvent) {

        //filtroGeneral(fecha);
        //validoEstudiante(fecha);
        //guarda con insert
    }
    public void clicCerrar(ActionEvent actionEvent) {
    }

}
