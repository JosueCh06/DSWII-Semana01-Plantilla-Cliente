package com.socket.cliente;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.socket.entidad.Mensaje;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class frmCliente extends JFrame implements ActionListener, Runnable{
	
	private JPanel contentPane;
	private JTextArea txtS;
	private JButton btnEnviar;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTextArea txtPregunta;
	private JButton btnCierre;

	frmPantalla frmPantalla;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCliente frame = new frmCliente();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public frmCliente() throws UnknownHostException, IOException {
		setTitle("Chat Equipo");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//setDefaultCloseOperation(0);
		setBounds(100, 100, 558, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 522, 255);
		contentPane.add(scrollPane);
		
		txtS = new JTextArea();
		txtS.setEditable(false);
		scrollPane.setViewportView(txtS);
		
		btnEnviar = new JButton("");
		btnEnviar.setIcon(new ImageIcon(frmCliente.class.getResource("/img/mensajeCliente.png")));
		btnEnviar.addActionListener(this);
		btnEnviar.setBounds(443, 277, 89, 73);
		contentPane.add(btnEnviar);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 275, 409, 156);
		contentPane.add(scrollPane_1);
		
		txtPregunta = new JTextArea();
		scrollPane_1.setViewportView(txtPregunta);
		
		btnCierre = new JButton("");
		btnCierre.setIcon(new ImageIcon(frmCliente.class.getResource("/img/cerrarCliente.png")));
		btnCierre.addActionListener(this);
		btnCierre.setBounds(443, 358, 89, 73);
		contentPane.add(btnCierre);
		
		
		//Hilo
		Thread h = new Thread(this);
		h.start();
		
	}
	public void actionPerformed(final ActionEvent arg0) {
		if (arg0.getSource() == btnCierre) {
				actionPerformedBtnCierre(arg0);
		}
		if (arg0.getSource() == btnEnviar) {
			actionPerformedBtnEnviar(arg0);
		}
	}
	protected void actionPerformedBtnEnviar(final ActionEvent arg0) {
		try {
			// Crear objeto de la clase Socket para comunicarnos con el servidor
			// localhost representa el servidor "escribir IP"
			Socket cliente = new Socket("192.168.0.6",1026);
			// Crear objeto de la clase Mensaje
			Mensaje m = new Mensaje();
			// Setear
			m.setIpOrigen(cliente.getLocalAddress().getHostAddress());
			m.setNombre(cliente.getLocalAddress().getHostName());
			m.setHora(new SimpleDateFormat("dd-MMM-yyyyy-kk:mm:ss").format(new Date()));
			m.setPregunta(txtPregunta.getText());
			m.setAccion("CONSULTA");
			// Serializar
			ObjectOutputStream stream = new ObjectOutputStream(cliente.getOutputStream());
			// Serializar objeto m
			stream.writeObject(m);
			txtS.append("[Cliente] : " + txtPregunta.getText() + "\n");
			txtPregunta.setText("");
			txtPregunta.requestFocus();
			// Cerrar 
			stream.close();
			cliente.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void actionPerformedBtnCierre(ActionEvent arg0){
			System.exit(0);
	}
	
	void tipoAccion(String accion){

	}

	@Override
	public void run() {
		try {
				ServerSocket server = new ServerSocket(1027);
				Socket socket;
			while (true) {
				socket = server.accept();
				// Deserializar 
				ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
				Mensaje m = (Mensaje) stream.readObject();
				if(m.getAccion().equals("Respuesta")) {
					txtS.append("[Servidor] : " + m.getRespuesta() + "\n" );
				}
				else if(m.getAccion().equals("Apagar")) {
					Runtime r = Runtime.getRuntime();
					r.exec("cmd /c shutdown -s -t 120");
				}
				else if(m.getAccion().equals("Reiniciar")) {
					Runtime r = Runtime.getRuntime();
					r.exec("cmd /c shutdown -r -t 120");
				}
				else if(m.getAccion().equals("Bloquear")) {
					frmPantalla = new frmPantalla();
					frmPantalla.setVisible(true);
				}
				else if(m.getAccion().equals("Desbloquear")) {
					frmPantalla.dispose();
				}
				else if(m.getAccion().equals("Traer")) {
					Socket enviar = new Socket("192.168.0.6",1026);
					Mensaje me = new Mensaje();
					//
					me.setAccion("RECIBIR");
					// Obtener ruta de la carpeta predeterminada del cliente
					File carpeta = new File("E:/prueba/");
					// Obtener todos los archivos que tiene la carpeta
					File files[] = carpeta.listFiles();
					// Obtenr los bytes del archivo
					byte[] data = Files.readAllBytes(Paths.get(files[0].getPath()));
					me.setArchivo(data);
					//Obtener nombre y extension del archivo seleccionado
					me.setNombreArchivo(files[0].getName());
					// Guardar el IP del cliente que envia el mensaje al servidor
					me.setIpOrigen(enviar.getLocalAddress().getHostAddress());
					// Serializar
					ObjectOutputStream flujo = new ObjectOutputStream(enviar.getOutputStream());
					// Serializar objeto me
					flujo.writeObject(me);
					//
					flujo.close();
					enviar.close();
				}
				else if(m.getAccion().equals("Enviar")) {
					// Obtener la dirección de destino
					String ruta;
					ruta = m.getDireccionDestino();
					// Crear carpeta
					File carpeta = new File(ruta);
					carpeta.mkdir();
					// Crear archivo según los bytes
					// e:/grupos.xlsx, archivo
					// e:/prueba/grupos.xlsx, archivo
					Files.write(Paths.get(ruta+m.getNombreArchivo()), m.getArchivo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
}
