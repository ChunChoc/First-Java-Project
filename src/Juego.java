import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

class Juego extends JPanel implements ActionListener{
    public static final int ANCHURA = 600;
    public static final int ALTURA = 600;
    public static final int TAMAÑO_PIXELES = 25;
    public static final int UNIDADES_GENERALES = (int) (ANCHURA/TAMAÑO_PIXELES) * (ALTURA/TAMAÑO_PIXELES);
    public static final int UNIDADES_HORIZONTALES = ANCHURA/TAMAÑO_PIXELES;
    public static final int UNIDADES_VERTICALES = ALTURA/TAMAÑO_PIXELES;
    public static final int RETRASO = 100;
    public static final int TAMAÑO_INICIAL_SERPIENTE = 6;
    private int manzanaX;
    private int manzanaY;
    private Timer contador = new Timer(RETRASO, this);
    private char dirección;
    private int[] serpienteX = new int[UNIDADES_GENERALES];
    private int[] serpienteY = new int[UNIDADES_GENERALES];
    private int tamañoSerpiente;
    private int manzanasComidas;
	Escenas escenaPrincipal;
    boolean entradaTeclado = false;
	String[] mensajesChidos = {"Papu no", "Nimodo", "xd"};
	String mensajesChidosAleatorios = "";
    Sonidos sonidos =  new Sonidos();

    Juego(JFrame frame){
		escenaPrincipal = (Escenas) frame;
        //Si el panel no es focuseable, no va a escuchar las teclas
        this.setFocusable(true);
		this.requestFocus();
        this.addKeyListener(new MyKeyAdapter());
        this.setPreferredSize(new Dimension(ANCHURA,ALTURA));
        this.setBackground(Color.black);
        //Timer es una clase de Swing que dispara un ActionEvent cada intervalo dado de milisegundos.
        //En este caso, el temporizador activa esta clase cada cuarto de segundo.
    }

    public void empezarJuego(){
        sonidos.reproducirJuego();
        tamañoSerpiente = TAMAÑO_INICIAL_SERPIENTE;
        manzanasComidas = 0;
        for(int i = 0; i < tamañoSerpiente; i++){
            serpienteX[i] = 0;
            serpienteY[i] = 0;
        }
        dirección = 'R';
        contador.start();
        manzanaNueva();

		mensajesChidosAleatorios = mensajesChidos[aleatorio(mensajesChidos.length)];
    }
	
    public void actionPerformed(ActionEvent ev){
        mover();
        verChoque();
        comerManzana();
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); 

        //Dibujar manzana
        g.setColor(Color.red);
        g.fillOval(manzanaX , manzanaY, TAMAÑO_PIXELES, TAMAÑO_PIXELES);
        //Dibujar cabeza serpiente
        g.setColor(Color.green);
        g.fillRect(serpienteX[0], serpienteY[0], TAMAÑO_PIXELES, TAMAÑO_PIXELES);
        //Dibujar cuerpo serpiente
        for(int i = 1; i < tamañoSerpiente; i++){
            g.fillRect(serpienteX[i], serpienteY[i], TAMAÑO_PIXELES, TAMAÑO_PIXELES);
        }

        //Dibujar puntuación
        g.setColor(Color.yellow);
        g.setFont(new Font("BigBlue_TerminalPlus NF", Font.PLAIN, 25));
        FontMetrics fontSize = g.getFontMetrics();
        int fontX = ANCHURA - fontSize.stringWidth("Puntos: " + manzanasComidas) - 10;
        int fontY = fontSize.getHeight();
        g.drawString("Puntos: " + manzanasComidas, fontX, fontY);
        
        if(!contador.isRunning()){
            //Mostrar mensaje de juego terminado
            g.setColor(Color.white);
            g.setFont(new Font("BigBlue_TerminalPlus NF", Font.PLAIN, 80));
            String message = mensajesChidosAleatorios;
            fontSize = g.getFontMetrics();
            fontX = (ANCHURA - fontSize.stringWidth(message)) / 2 ;
            fontY = (ALTURA - fontSize.getHeight()) /2;
            g.drawString(message, fontX, fontY);

            g.setFont(new Font("BigBlue_TerminalPlus NF", Font.PLAIN, 24));
            message = "Enter para reiniciar";
            fontSize = g.getFontMetrics();
            fontX = (ANCHURA - fontSize.stringWidth(message)) / 2 ;
            fontY = fontY + fontSize.getHeight() + 20;
            g.drawString(message, fontX, fontY);
        }
    }	
    
    public void manzanaNueva(){
        int x = aleatorio(UNIDADES_HORIZONTALES) * TAMAÑO_PIXELES;
        int y = aleatorio(UNIDADES_VERTICALES) * TAMAÑO_PIXELES;
        Point provisional = new Point(x,y);
        Point posiciónSerpiente = new Point();
        boolean manzanaNuevaPermission = true;
        for(int i = 0; i < tamañoSerpiente; i++){
            posiciónSerpiente.setLocation(serpienteX[i], serpienteY[i]);
            if(provisional.equals(posiciónSerpiente)){
                manzanaNuevaPermission = false;
            }
        }
        if(manzanaNuevaPermission){
            manzanaX = x;
            manzanaY = y;
        }else{
            manzanaNueva();
        }
    }

    public void verChoque(){
        if(serpienteX[0] >= (ANCHURA) || serpienteX[0] < 0 || serpienteY[0] >= (ALTURA) || serpienteY[0] < 0){
            juegoTerminado();
            sonidos.detenerJuego();
            sonidos.reproducirMuerte();
        }
        for(int i = 1; i < tamañoSerpiente; i++){
            if((serpienteX[0] == serpienteX[i]) && (serpienteY[0] == serpienteY[i])){
                juegoTerminado();
                sonidos.detenerJuego();
                sonidos.reproducirMuerte();
            }
        }
    }
    
	public void comerManzana(){
        if(serpienteX[0] == manzanaX && serpienteY[0] == manzanaY){
            tamañoSerpiente++;
            manzanasComidas++;
            manzanaNueva();
            sonidos.reproducirComer();
        }
    }
    
	public void mover(){
        //Este metodo se ejecuta cada vez que timer nos lo permite
        //Hay que recorrer la serpiente de atras para adelante
        for(int i = tamañoSerpiente; i > 0; i--){
            serpienteX[i] = serpienteX[i-1]; 
            serpienteY[i] = serpienteY[i-1]; 
        }
        switch(dirección){
            case 'R':
                serpienteX[0] += TAMAÑO_PIXELES;
                break;
            case 'L':
                serpienteX[0] -= TAMAÑO_PIXELES;
                break;
            case 'U':
                serpienteY[0] -= TAMAÑO_PIXELES;
                break;
            case 'D':
                serpienteY[0] += TAMAÑO_PIXELES;
                break;
        }
        entradaTeclado = false;
    }
    
	public void juegoTerminado(){
        contador.stop();
    }

    public int aleatorio(int rango){
        return (int)(Math.random() * rango);
    }
    
    class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent k){
            
            switch(k.getKeyCode()){
                case (KeyEvent.VK_DOWN):
                    if(dirección != 'U' && entradaTeclado == false){
                        dirección = 'D';
                        entradaTeclado = true;
                    }
                    break;
                case (KeyEvent.VK_UP):
                    if(dirección != 'D' && !entradaTeclado){
                        dirección = 'U';
                        entradaTeclado = true;
                    }
                    break;
                case (KeyEvent.VK_LEFT):
                    if(dirección != 'R' && entradaTeclado == false){
                        dirección = 'L';
                        entradaTeclado = true;
                    }
                    break;
                case (KeyEvent.VK_RIGHT):
                    if(dirección != 'L' && entradaTeclado == false){
                        dirección = 'R';
                        entradaTeclado = true;
                    }
                    break;
                case (KeyEvent.VK_ENTER):
                    if(!contador.isRunning()){
                        empezarJuego();
                        sonidos.detenerMuerte();
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    sonidos.detenerJuego();
                    sonidos.reproducirEsc();
                    sonidos.detenerMuerte();
					escenaPrincipal.cambiarAMenu();
					break;
            }
        }
    }
}