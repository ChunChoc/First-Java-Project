import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Menu extends JPanel{
    public static final Font FUENTE_MENU = new Font("BigBlue_TerminalPlus NF", 0 , 35);
    public static final int ANCHURA = Juego.ANCHURA;
    public static final int ALTURA = Juego.ALTURA;
    public static final String[] OBJETOS_MENU = {"Jugar", "Salir"};
    private int elementoSeleccionado = 0;
	Escenas escenaPrincipal;
    Sonidos sonidos =  new Sonidos();

    public Menu(JFrame frame){
		escenaPrincipal = (Escenas) frame;
        this.addKeyListener(new MyKeyAdapter());
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(ANCHURA,ALTURA));
        //Se hace focusable para que tome teclas
        this.setFocusable(true);
		this.requestFocus();
    }

    public void paintComponent(Graphics g){
        //paintComponent() de super para manener el color seteado en JPanel.
        super.paintComponent(g);
        dibujarTítulo(g);
        dibujarMenu(g);
    }

    private void dibujarTítulo(Graphics g){

        FontMetrics metrics = g.getFontMetrics();

        try {
            BufferedImage imagenTitulo = ImageIO.read(new File("C:\\Users\\ChunChoc\\Documents\\ProyectoFinal\\Culebrita\\res\\logo.png"));
    
            int xImagen = (ANCHURA - imagenTitulo.getWidth()) / 2;
            int yImagen = metrics.getHeight() + 100;
            g.drawImage(imagenTitulo, xImagen, yImagen, null);
    
        } catch (IOException e) {
   
        }
    }

    private void dibujarMenu(Graphics g){
        g.setColor(Color.white);
        g.setFont(FUENTE_MENU);

        FontMetrics metrics = g.getFontMetrics();
        for(int i = 0; i < OBJETOS_MENU.length; i++){
            int x = (ANCHURA - metrics.stringWidth(OBJETOS_MENU[i])) / 2; //Horizontal center
            int y = metrics.getHeight() + 300 + (i * (metrics.getHeight() + 20));
            g.drawString(OBJETOS_MENU[i], x, y);
            
            if (elementoSeleccionado == i){
                dibujarTriángulo(x - 30, y - 20, g);
                g.setColor(Color.white);
            }
        }
    }

    private void dibujarTriángulo(int x, int y, Graphics g){
        g.setColor(Color.yellow);
        int[] xPoints = {x, x + 20, x};
        int[] yPoints = {y, y+10, y+20};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    private class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    decrementMenu();
                    repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    incrementMenu();
                    repaint();
                    break;
                case KeyEvent.VK_ENTER:
                    cambiarEscena();
					break;
				case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
				
            }
            
        }
    }

    private void cambiarEscena(){
        switch(elementoSeleccionado){
            case 0:
                escenaPrincipal.cambiarAJuego();
                break;
            case 1:   
                System.exit(0);
                break;
        }
    }

    private void incrementMenu(){
        sonidos.reproducirSelect();
        int lastItemIndex = OBJETOS_MENU.length - 1;
        if(elementoSeleccionado < lastItemIndex){ //3
            elementoSeleccionado++;
        }else{
            elementoSeleccionado = 0;
        }
    }

    private void decrementMenu(){
        sonidos.reproducirSelect();
        int lastItemIndex = OBJETOS_MENU.length - 1;
        if(elementoSeleccionado > 0){
            elementoSeleccionado--;
        }else{
            elementoSeleccionado = lastItemIndex;
        }
    }

    public int getelementoSeleccionado(){
        return elementoSeleccionado;
    }

}