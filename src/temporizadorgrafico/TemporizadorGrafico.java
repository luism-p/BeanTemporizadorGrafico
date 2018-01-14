package temporizadorgrafico;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.EventListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Beans temporizador gráfico, como puedes obsrvas esta basado en el ejemplo del tema
 * pero con algunas modificaciones que me causaban errores.
 * @author Luis
 */
public class TemporizadorGrafico extends JLabel implements ActionListener,Serializable {
    
    /**
     * clase interna para crear un evento personalizado.
     */
    public class FinCuentaAtrasEvent extends java.util.EventObject  {
    // constructor

        /**
         *
         * @param source
         */
        public FinCuentaAtrasEvent(Object source)   {
            super(source);
        }//close constructor.
    }//close class event.

    /**
     * Define la interfaz para el nuevo tipo de evento
     */
    public interface FinCuentaAtrasListener extends EventListener   {

        /**
         * recibe el evento capturado.
         * @param ev
         */
        void capturarFinCuentaAtras(FinCuentaAtrasEvent ev);
    }//close interface.

    /**
     * Variable que controla el tiempo.
     */
    protected int tiempo;
    private Timer t;
     
    /**
     * Realmente por mas que investigo no le veo su función.
     * La he dejado simplemente por que usted la tiene en el ejemplo.
     */
    public static final String PROP_TIEMPO = "tiempo";
    private FinCuentaAtrasListener receptor;
    
        /**
     * Constructor sin argumentos. Se establece como tiempo por defecto 5 segundos.
     */
    public TemporizadorGrafico() {  
        tiempo = 5;
        t = new Timer (1000, this);
        setText(Integer.toString(tiempo));
    }//close constructor.

    /**
     * Get the value of tiempo
     *
     * @return the value of tiempo
     */
    public int getTiempo() {
        return tiempo;
    }//close get.

    /**
     * Set the value of tiempo
     *
     * @param tiempo new value of tiempo
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
        setText(Integer.toString(tiempo));
        repaint();
    }//close set.
    
    /**
     *  Activo es en si mismo una propiedad (sin atributo subyacente)
     * Gestiona si el temporizador está funcionado o no.
     * @param valor
     */
    public final void setActivo(boolean valor) {
        if (valor == true){
            t.start();
        }else{
            t.stop();
        }//close if-else.
    }//close set.

    /**
     *
     * @return retorna el estado del temporizador.
     */
    public boolean getActivo() {
        return t.isRunning();
    }//close get.
    
    //Accion que se realiza cada vez que se cumplen los 1000 milisegudos establecidos
    //para t. se modifica el valor del texto de la etiqueta, se repinta y se disminuye
    //en un segundo el tiempo restante.
    //Cuando el tiempo llega a cero se para el Timer y se lanza un evento de finalización
    //de cuenta atrás.
    @Override
    public void actionPerformed(ActionEvent e){
        // Aquí el código que queramos ejecutar.
        tiempo--;
        setText(Integer.toString(tiempo));
        repaint();
        if(tiempo == 0){
            setActivo(false);            
            if(receptor != null)
                receptor.capturarFinCuentaAtras( new FinCuentaAtrasEvent(this));      
        }//close if.
    }//close void event.

    /**
     * método que agrega el evento.
     * @param receptor
     */
    public void addFinCuentaAtrasListener(FinCuentaAtrasListener receptor){
        this.receptor = receptor;
    }//close void.

    /**
     * método qie elimina el evento.
     * @param receptor
     */
    public void removeFinCuentaAtrasListener(FinCuentaAtrasListener receptor){
        this.receptor=null;
    }//close void.
}//close class.
