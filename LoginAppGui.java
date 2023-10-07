import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginAppGui {
	private static int numeroIntentos = 2;
	private static boolean estaRegistrado = false;
	private static Timer timer;
	
	/**
	 * Metodo con la implementacion de la calse JFrame con los valores para la ventana de inicio de sesion 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Inicio de Sesion");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));

		JTextField campoUsuario = new JTextField();
		JTextField campoContraseña = new JTextField();
		JTextField campoTiempo = new JTextField();
		// Hacer que el campo de tiempo no sea editable
		campoTiempo.setEditable(false); 
		
		JButton botonInicioSesion = new JButton("Iniciar sesión");
		panel.add(new JLabel("Nombre de usuario:"));
		panel.add(campoUsuario);
		panel.add(new JLabel("Contraseña:"));
		panel.add(campoContraseña);
		panel.add(new JLabel("Tiempo transcurrido:"));
		panel.add(campoTiempo);
		panel.add(new JLabel()); 
		panel.add(botonInicioSesion);

		frame.add(panel);
		frame.setVisible(true);
		
		/**
		 * Creacion del timer para el conteo de los minutos y segundos ocupados para el inicio Ok o fallido
		 * utilizanod la clase SimpleDateFormat
		 */
		timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			private int seconds = 0;
			private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

			@Override
			public void run() {
				seconds++;
				String tiempo = dateFormat.format(new Date(seconds * 1000L));
				campoTiempo.setText(tiempo);
			}
		};
		timer.scheduleAtFixedRate(timerTask, 1000, 1000); 

		botonInicioSesion.addActionListener(new ActionListener() {
			
			/**
			 * Metodo mediante el cual se validara el inicio de sesion con dos oportunidades 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String usuario = campoUsuario.getText();
				String contraseña = campoContraseña.getText();

				if (validateCredentials(usuario, contraseña)) {
					estaRegistrado = true;
					// Aqui se detiene el temporizador si el inicio de sesión es exitoso
					timer.cancel(); 
					JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso.");
				} else {
					numeroIntentos--;
					if (numeroIntentos > 0) {
						JOptionPane.showMessageDialog(frame,
								"Inicio de sesion fallido usuario o contraseña incorrectos. Intentos restantes: " + numeroIntentos);
					} else {
						 // Aqui se detiene el temporizador si se agotan los intentos
						timer.cancel();
						JOptionPane.showMessageDialog(frame, "Agotó los intentos.");
						frame.dispose(); 
					}
				}
			}
		});
	}

	/**
	 * Metodo mediante el cual se validara si las credenciales son las correctas para poder otorgar el inicio de sesion
	 * @param usuario
	 * @param contraseña
	 * @return Omar 12345678
	 */
	private static boolean validateCredentials(String usuario, String contraseña) {

		return usuario.equals("Omar") && contraseña.equals("12345678");
	}
}
