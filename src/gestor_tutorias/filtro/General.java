package gestor_tutorias.filtro;

public class General {
    public String quitarEspacioBorde(String texto){}
    public String quitarEspacioMedio(String texto){}
    public String cambiarLetra(String texto){}
    public String quitarCaracterIlegible(String texto){}
    public String quitarCaracterHtml(String texto){}
    public String quitarHtml(String texto){}
    public String normalizarRuta(String ruta){
        /*
         * USO:
         * Normaliza rutas eliminando secuencias peligrosas.
         *
         * ATAQUE:
         * Path Traversal
         *
         * CAPA:
         * FILTRO
         *
         * NOTA:
         * Se usa solo si el sistema necesita aceptar rutas controladas.
         */
    }

}
