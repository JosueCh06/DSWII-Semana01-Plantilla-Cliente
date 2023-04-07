package com.socket.cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class frmPantalla extends JFrame {

	//private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmPantalla frame = new frmPantalla();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmPantalla() {
		getContentPane().setBackground(Color.RED);
		setBackground(Color.RED);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setUndecorated(true);//quita bordes a jframe
       
        
        //this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE  );//evita cerra jframe con ALT+C
        this.setExtendedState( MAXIMIZED_BOTH );//maximizado
        this.setAlwaysOnTop(true);//siempre al frente       
        //nueva instancia de jBlocked pasando como parametros e este JFrame
        //new Blocked( this ).block();
		
		
	}

}
