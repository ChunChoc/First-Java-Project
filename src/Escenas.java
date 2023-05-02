import javax.swing.*;

class Escenas extends JFrame{
	Menu menu = new Menu(this);
	Juego juego = new Juego(this);
	Sonidos sonidos =  new Sonidos();
	public Escenas(){
		
        this.add(menu);
        this.setTitle("Culebrita"); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
		this.add(juego);
		sonidos.reproducirMenu();
    }
	
	public void cambiarAJuego(){
		menu.setVisible(false);
		juego.setVisible(true);
		juego.requestFocus();
		juego.empezarJuego();
		sonidos.detenerMenu();
		
	}
	
	public void cambiarAMenu(){
		juego.juegoTerminado();
		juego.setVisible(false);
		menu.setVisible(true);
		menu.requestFocus();
		sonidos.reproducirMenu();
		sonidos.detenerJuego();
	}
}