/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 
 
public class EncuestaGingaJ implements Xlet, KeyListener{
 
 
    private XletContext contexto;
    private HScene escena;
    
    private JPanel p_opciones, p_estadisticas;    
    private JLabel l_opciones[], l_pregunta, l_seleccionada;
    private String s_opciones[], s_pregunta;
    private Color c_pregunta, c_opcion, c_opcion_over;
    private Font f_pregunta,f_opcion;    
    private int i_opcion_ancho, i_opcion_alto, i_opcion_left, 
                i_opcion_top, i_opcion_espacio, actual = -1, no_opciones,id_encuesta_actual;
    private ImageIcon i_opcion, i_opcion_over;
    private SQLconnection mi_bd;
    
    public EncuestaGingaJ(){
    }
 
 
    @Override
    public void initXlet(XletContext xc) throws XletStateChangeException {
        this.contexto = xc;
    }
 
 
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
        
        
        mi_bd = new SQLconnection("localhost","root","","ginga");
        mi_bd.connectar();
        
        inicializaOpciones();
        inicializaEstadisticas();
        
        escena.add(p_opciones);
        escena.setVisible(true);
        escena.requestFocus();
    }
    
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
    
    private void inicializaEstadisticas(){
        
        p_estadisticas = new JPanel();
            p_estadisticas.setSize(290, 290);
            p_estadisticas.setBackground(Color.WHITE);
            p_estadisticas.setLocation(115, 45);
            p_estadisticas.setLayout(null);
            p_estadisticas.setBorder(BorderFactory.createEtchedBorder());
            
        l_seleccionada = new JLabel("Gracias por su respuesta",JLabel.CENTER);
            l_seleccionada.setFont(f_pregunta);
            l_seleccionada.setForeground(c_pregunta);        
            l_seleccionada.setLocation(30, 5);
            l_seleccionada.setSize(250, 60);
            l_seleccionada.setVisible(true);
        
       p_estadisticas.add(l_seleccionada);

    }
    private int getIDEncuesta(){
        int id = mi_bd.idLastPregunta("votacion");
        return id;
    }
    
    private String getPregunta(){
        String pregunta = mi_bd.getPreguntaByID("votacion",id_encuesta_actual);
        return pregunta;
    }
    
    
    private String[] getRespuestas(){
        String[] respuestaas = mi_bd.getRespuestas(id_encuesta_actual);       
        return respuestaas;
    }
    
    
    private JLabel[] getLabelsRespuestas(){
        
        f_opcion = new Font("Arial", Font.PLAIN, 15);
        c_opcion = new Color(0x555555);
        c_opcion_over = new Color(0xbde038);       
        
        Color l_op_fondo = new Color(0xf2f5f7);
        Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
        Border border = BorderFactory.createLineBorder(l_op_fondo);
        
        i_opcion_left = 20;
        i_opcion_top = 60;
        i_opcion_espacio = 10;
        i_opcion_ancho = 250;
        i_opcion_alto = 25;
            
        i_opcion = new ImageIcon("C://NetBeansProjects/Ginga1/src/images/i_opcion.png");
        i_opcion_over = new ImageIcon("C://NetBeansProjects/Ginga1/src/images/i_opcion_over.png");
        
        JLabel[] labels = new JLabel[no_opciones];
        for (int i = 0; i < no_opciones; i++) {
            labels[i] = new JLabel(s_opciones[i],i_opcion,JLabel.LEFT);
                labels[i].setFont(f_opcion);
                labels[i].setForeground(c_opcion);        
                labels[i].setLocation(i_opcion_left, i_opcion_top + i_opcion_alto*i + i_opcion_espacio);
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
  
    @Override
    public void keyPressed(KeyEvent e) {
               
        int cod = e.getKeyCode();
        System.out.println(cod);
        
        switch (cod){ 
            case 38: //tecla arriba
                if(actual > 0){
                    cambiarColor(c_pregunta);
                    cambiarIcon(i_opcion);
                    l_opciones[--actual].setForeground(c_opcion_over);
                    l_opciones[actual].setIcon(i_opcion_over);
                }
                break;
            case 40: //tecla abajo
                if(actual<3){
                    cambiarColor(c_pregunta);
                    cambiarIcon(i_opcion);
                    l_opciones[++actual].setForeground(c_opcion_over);
                    l_opciones[actual].setIcon(i_opcion_over);
                }
                break;
            case 10:
                if(actual > -1){
                    escena.remove(p_opciones);       
                    escena.add(p_estadisticas);
                    escena.requestFocus();
                    escena.repaint();
                } else{
                    //Debe seleccionar una opción
                }
                break;
        }

    }
    
    public void cambiarColor(Color my_color){
        for(int i = 0; i < 4; i++){
            l_opciones[i].setForeground(my_color);
        }
    }
    public void cambiarIcon(ImageIcon my_icon){
        for(int i = 0; i < 4; i++){
            l_opciones[i].setIcon(my_icon);
        }
    }

 
    @Override
    public void keyReleased(KeyEvent e) {}
     /* 
    * Método que seta todos os componentes da aplicaçao 
    */

}