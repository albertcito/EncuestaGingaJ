/*
 * @autor: Grupo Ginga 
 * 
 */
package encuestagingaj;


import java.awt.Color;
import java.awt.Font;
import xjavax.tv.xlet.*;
import org.havi.ui.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
 
/*
 * El código que sigue crea en el emulador de TV Digital un encuesta; la pregunta de la encuesta
 * se obtiene de la base de datos en MySQL al igual que las respuestas.
 * 
 * Una vez que el usuario vota, su voto queda guardado en la base de datos identificado con su
 * número de TV para que no vuelva a votar y la pantalla mostrará las estadisticas
 * 
 */ 
public class EncuestaGingaJ implements Xlet, KeyListener{
 
 
    private XletContext contexto;
    private HScene escena;
    
    private JPanel p_opciones, p_estadisticas,panel_load;    
    private JLabel l_opciones[], l_pregunta, l_seleccionada,close,label_load;
    private String s_opciones[][], s_pregunta;
    private Color c_pregunta, c_opcion, c_opcion_over;
    private Font f_pregunta,f_opcion;    
    private int i_opcion_ancho, i_opcion_alto, i_opcion_left, 
                i_opcion_top, i_opcion_espacio, actual = -1, no_opciones,id_encuesta_actual;
    private ImageIcon i_opcion, i_opcion_over, close_hover, close_out;
    private SQLconnection mi_bd;
    private boolean cerrar; 
    
    
    
    private JProgressBar progressBar[];
    
    
    public EncuestaGingaJ(){
    }
 
 
    @Override
    public void initXlet(XletContext xc) throws XletStateChangeException {
        this.contexto = xc;
    }
 
    /*
     * Al inicializar el programa se conecta a la BD y muestra las opciones.
     */
    @Override
    public void startXlet() throws XletStateChangeException {
        
        HSceneFactory constructorEscena = HSceneFactory.getInstance();
        
        escena = constructorEscena
                    .getFullScreenScene(HScreen
                            .getDefaultHScreen()
                            .getDefaultHGraphicsDevice()
                    );
        escena.setSize(640,480);
        
        escena.setLayout(null);
        escena.addKeyListener(this);
        
        loadClose();
        load();
        escena.add(panel_load);
        
        escena.setVisible(true);
        escena.requestFocus();
        
        mi_bd = new SQLconnection("localhost","root","","ginga");
        boolean conectado = mi_bd.connectar();        
        
        if(conectado){
            escena.remove(panel_load);
            inicializaOpciones();
            escena.add(p_opciones);
            escena.requestFocus();
            escena.repaint();          
        } else {
            label_load.setText("ERROR - No se pudo conectar a la BD");
        }
        
    }
    /**
     * 
     */
    private void load(){
        panel_load = new JPanel();
            panel_load.setSize(290, 40);
            panel_load.setBackground(Color.WHITE);
            panel_load.setLocation(115, 45);
            panel_load.setLayout(null);
            panel_load.setBorder(BorderFactory.createEtchedBorder());
        label_load = new JLabel("Cargando BD....");
        label_load.setLocation(10,10);
        label_load.setSize(200,20);   
        panel_load.add(label_load);
    }
    /*
     * Inserta el boton cerrar en la gráfica.
     */
    private void loadClose(){
        cerrar = false;
        close_out = new ImageIcon("C://NetBeansProjects/EncuestaGingaJ/src/images/cerrar.png");
        close_hover = new ImageIcon("C://NetBeansProjects/EncuestaGingaJ/src/images/cerrar_hover.png");
        close=  new JLabel(close_out);
        close.setLocation(390, 35);
        close.setSize(26,26);
        escena.add(close);
    }
    
    /*
     * Se ejecuta al empezar el programa a través del metodo startXlet()
     * Muestra en pantalla la pregunta con las posibles respuesta
     */
    private void inicializaOpciones() {
        
        p_opciones = new JPanel();
            p_opciones.setSize(290, 290);
            p_opciones.setBackground(Color.WHITE);
            p_opciones.setLocation(115, 45);
            p_opciones.setLayout(null);
            p_opciones.setBorder(BorderFactory.createEtchedBorder());
   
        f_pregunta = new Font("Arial", Font.BOLD, 15);
        c_pregunta = new Color(0x555555);
        
        id_encuesta_actual = getIDEncuesta();
        s_pregunta = getPregunta();       
        s_opciones = getRespuestas();
        no_opciones = s_opciones.length;
            
        l_pregunta = new JLabel(s_pregunta,JLabel.CENTER);
            l_pregunta.setFont(f_pregunta);
            l_pregunta.setForeground(c_pregunta);
            l_pregunta.setLocation(20, 0);
            l_pregunta.setSize(250, 60);
            
        l_opciones = getLabelsRespuestas();
        
        p_opciones.add(l_pregunta);
        for (int i = 0; i < no_opciones; i++) {
            p_opciones.add(l_opciones[i]);
        }
    }
    /*
     * Muestra las estadisticas en lal pantalla
     */
    private void inicializaEstadisticas(){
        
        p_estadisticas = new JPanel();
            p_estadisticas.setSize(290, 290);
            p_estadisticas.setBackground(Color.WHITE);
            p_estadisticas.setLocation(115, 45);
            p_estadisticas.setLayout(null);
            p_estadisticas.setBorder(BorderFactory.createEtchedBorder()); 
         
            
        l_seleccionada = new JLabel(s_pregunta,JLabel.CENTER);
            l_seleccionada.setFont(f_pregunta);
            l_seleccionada.setForeground(c_pregunta);        
            l_seleccionada.setLocation(20, 0);
            l_seleccionada.setSize(250, 60);
            l_seleccionada.setVisible(true);

        
        String votos[][] = mi_bd.getVotos(id_encuesta_actual);
        int total = 0;
        for (int i = 0; i < no_opciones; i++) {
            total += Integer.parseInt(votos[i][0]);            
        }
        
        i_opcion_left = 20;
        i_opcion_top = 75;
        i_opcion_espacio = 10;
        i_opcion_ancho = 250;
        i_opcion_alto = 45;
        int valor = 0;
        progressBar = new JProgressBar[no_opciones];
        for (int i = 0; i < no_opciones; i++) {
            progressBar[i] = new JProgressBar(0,100);
            if(total == 0) valor = 0;
            else valor = (int)(Integer.parseInt(votos[i][0])*100)/total;
            progressBar[i].setValue(valor);
            progressBar[i].setStringPainted(true);
            progressBar[i].setVisible(true);
            progressBar[i].setLocation(i_opcion_left, i_opcion_top + i_opcion_alto*i + i_opcion_espacio);
            progressBar[i].setSize(250, 20);  
        }
        for (int i = 0; i < no_opciones; i++) {
            p_estadisticas.add(progressBar[i]);
        }
       
        Color l_op_fondo = new Color(0xffffff);
        int l_opcion_alto = 45;
        JLabel labels[] = new JLabel[no_opciones];
        for (int i = 0; i < no_opciones; i++) {
            labels[i] = new JLabel(votos[i][2],null,JLabel.LEFT);
                labels[i].setFont(f_opcion);
                labels[i].setLocation(i_opcion_left, 50 + l_opcion_alto*i + i_opcion_espacio);
                labels[i].setSize(i_opcion_ancho, 25);       
                labels[i].setOpaque(true);
                labels[i].setBackground(l_op_fondo);
        }
        for (int i = 0; i < no_opciones; i++) {
            p_estadisticas.add(labels[i]);
        }

        p_estadisticas.add(l_seleccionada);
    }
    
    /*
     * Retorn el ID de la última encuesta que está en la Base de Datos
     */
    private int getIDEncuesta(){
        int id = mi_bd.idLastPregunta("votacion");
        return id;
    }
    /*
     * Retorna la pregunta de la encuesta 
     */
    private String getPregunta(){
        String pregunta = mi_bd.getPreguntaByID("votacion",id_encuesta_actual);
        return pregunta;
    }
    
    /*
     * Retorna las respuestas de la encuesta con su respectivo id
     */
    private String[][] getRespuestas(){
        String[][] respuestaas = mi_bd.getRespuestas(id_encuesta_actual);       
        return respuestaas;
    }
    
    /*
     * Retorna un arreglo de JLabel para imprimir en pantalla.
     * Aparece en la pantalla de inicio con las preguntas y respuestas
     */
    private JLabel[] getLabelsRespuestas(){
        
        f_opcion = new Font("Arial", Font.PLAIN, 15);
        c_opcion = new Color(0x555555);
        c_opcion_over = new Color(0x45bc2e);       
        
        Color l_op_fondo = new Color(0xf2f5f7);
        Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
        Border border = BorderFactory.createLineBorder(l_op_fondo);
        
        i_opcion_left = 20;
        i_opcion_top = 60;
        i_opcion_espacio = 10;
        i_opcion_ancho = 250;
        i_opcion_alto = 25;
            
        i_opcion = new ImageIcon("C://NetBeansProjects/EncuestaGingaJ/src/images/i_opcion.png");
        i_opcion_over = new ImageIcon("C://NetBeansProjects/EncuestaGingaJ/src/images/i_opcion_over.png");
        
        JLabel[] labels = new JLabel[no_opciones];
        for (int i = 0; i < no_opciones; i++) {
            labels[i] = new JLabel(s_opciones[i][0],i_opcion,JLabel.LEFT);
                labels[i].setFont(f_opcion);
                labels[i].setForeground(c_opcion); 
                labels[i].setLocation(i_opcion_left, i_opcion_top + i_opcion_alto*i + i_opcion_espacio*i);
                labels[i].setSize(i_opcion_ancho, i_opcion_alto);         
                labels[i].setOpaque(true);
                labels[i].setBackground(l_op_fondo);
                labels[i].setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
        }
        
        return labels;
    }
     
    @Override
    public void pauseXlet() {} 
 
    @Override
    public void destroyXlet(boolean bln) throws XletStateChangeException {
        if (escena != null){
            escena.setVisible(false);
            escena.removeAll();
            escena = null;
        }
        contexto.notifyDestroyed();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
  
    /*
     * Cuando se presiona un boton
     */
    @Override
    public void keyPressed(KeyEvent e) {
               
        int cod = e.getKeyCode();
        //System.out.println(cod);
        
        switch (cod){ 
            case 38: //tecla arriba
                if(no_opciones > 1){
                    close.setIcon(close_out);
                    cerrar = false;
                    if(actual > 0){
                        cambiarColor(c_pregunta);
                        cambiarIcon(i_opcion);
                        --actual;
                        if(actual < 0) actual = no_opciones;
                        l_opciones[actual].setForeground(c_opcion_over);
                        l_opciones[actual].setIcon(i_opcion_over);
                    }
                }
                break;
            case 40: //tecla abajo
                if(no_opciones > 1){
                    close.setIcon(close_out);
                    cerrar = false;
                    if(actual<3){
                        cambiarColor(c_pregunta);
                        cambiarIcon(i_opcion);
                        ++actual;
                        if(actual >= no_opciones) actual = 0;
                        l_opciones[actual].setForeground(c_opcion_over);
                        l_opciones[actual].setIcon(i_opcion_over);
                    }
                }
                break;
            case 39: //derecha
                close.setIcon(close_hover);
                cerrar = true;
                break;
            case 10:
                if(cerrar){
                    escena.setVisible(false);
                    escena.removeAll();
                    escena = null;
                }
                else if(actual > -1 && no_opciones > 1){
                    int voto = Integer.parseInt(s_opciones[actual][1]);
                    mi_bd.insertar(voto,id_encuesta_actual);
                    escena.remove(p_opciones);
                    inicializaEstadisticas();
                    escena.add(p_estadisticas);
                    escena.requestFocus();
                    escena.repaint();
                } 
                break;
        }

    }
   
    /*
     * Deja a todas las preguntas en el color por defecto
     */
    public void cambiarColor(Color my_color){
        for(int i = 0; i < no_opciones; i++){
            l_opciones[i].setForeground(my_color);
        }
    }
    /*
     * Deja todos los iconos de las repsuesta con el icono por defecto
     */
    public void cambiarIcon(ImageIcon my_icon){
        for(int i = 0; i < no_opciones; i++){
            l_opciones[i].setIcon(my_icon);
        }
    }

 
    @Override
    public void keyReleased(KeyEvent e) {}
     /* 
    * Método que seta todos os componentes da aplicaçao 
    */

}