package gestor_tutorias.validacion;

public class ValidacionGeneral{
    private Object dato;

    public ValidacionGeneral(Object dato){this.dato=dato;}
    public boolean validarCommandInjection(String texto){
        /*
         * USO:
         * Previene Command Injection.
         * Rechaza entradas que puedan ejecutar comandos del sistema.
         *
         * ATAQUE:
         * Command Injection (ej: ; rm -rf /)
         *
         * CAPA:
         * VALIDACIÓN
         *
         * NOTA:
         * Se usa cuando el campo solo debe aceptar texto plano.
         */
    }
    public boolean validarPathTraversal(String texto){
        /*
         * USO:
         * Previene Path Traversal.
         * Rechaza rutas que intenten salir del directorio permitido.
         *
         * ATAQUE:
         * Path Traversal (ej: ../../config.txt)
         *
         * CAPA:
         * VALIDACIÓN
         *
         * NOTA:
         * No permite /, \\, .. en la entrada.
         */
    }

    public boolean datoValidado(){
        if( dato==null){return false;}

        if(dato instanceof String){
            String s=(String)dato;

            if(s.trim().isEmpty())return false;
            String s=(String)dato;
            if(s.length()<1)return false;
            if(s.length()>50)return false;
            if(!s.matches("[a-zA-Z0-9 ]+"))return false;
        }

        if(dato instanceof Integer){if((Integer)dato<=0)return false;}

        if(dato instanceof Character){if(!Character.isLetter((Character)dato))return false;}

        if(dato instanceof Double||dato instanceof Float){
            double v=((Number)dato).doubleValue();
            if(v<0||v>100)return false;
        }

        if(dato!=null&&dato.getClass().isEnum()){
            Object[] valores=dato.getClass().getEnumConstants();
            if(valores!=null&&valores.length>0&&dato.equals(valores[0]))return false;
        }

        return true;
    }
}
