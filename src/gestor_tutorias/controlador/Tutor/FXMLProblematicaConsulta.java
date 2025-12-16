package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.Enum.EstatusProblematica;
import gestor_tutorias.filtro.FiltroGeneral;
import gestor_tutorias.pojo.Problematica;
import gestor_tutorias.validacion.Valido;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class FXMLProblematicaConsulta {

    @FXML private TextField idproblematica;
    @FXML private TextField idreporte;
    @FXML private TextField titulo;
    @FXML private TextField descripcion;
    @FXML private TextField idexperienciaeducativa;
    @FXML private ChoiceBox<String> estado;

    private final ProblematicaDAO dao = new ProblematicaDAO();

    @FXML
    private void guardarproblematica() {

        Object[] datosFormulario = {
                idreporte.getText(),
                titulo.getText(),
                descripcion.getText(),
                idexperienciaeducativa.getText(),
                estado.getValue()
        };

        // ================================
        // FILTRO + VALIDACIÓN
        // ================================
        for (Object dato : datosFormulario) {

            FiltroGeneral filtro = new FiltroGeneral(dato);
            filtro.filtrarVacio();
            filtro.filtrarDenialOfService();
            filtro.filtrarZeroDay();
            filtro.aplicarFiltroPorTipo();

            Valido valido = new Valido(dato);
            valido.validacionGeneral();
            valido.aplicarValidacionPorTipo();
        }

        // ================================
        // CONSTRUCCIÓN DEL POJO
        // ================================
        Problematica problematica = new Problematica(
                idproblematica.getText().isEmpty() ? 0 : Integer.parseInt(idproblematica.getText()),
                Integer.parseInt(idreporte.getText()),
                titulo.getText(),
                descripcion.getText(),
                idexperienciaeducativa.getText().isEmpty()
                        ? null
                        : Integer.parseInt(idexperienciaeducativa.getText()),
                EstatusProblematica.valueOf(estado.getValue())
        );

        try {
            dao.guardarProblematica(problematica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarproblematica() {
        try {
            int id = Integer.parseInt(idproblematica.getText());
            dao.eliminarProblematica(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
