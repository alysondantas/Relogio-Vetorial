package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ControllerDadosServer;
import controller.ControllerServer;
import threads.ThreadAtualizaGUI;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Principal {

	private JFrame frame;
	private JTextField porta;
	private JTextField ipServer1;
	private JTextField portaServer1;
	private JTextField ipServer2;
	private JTextField portaServer2;
	private JTextField ipServer3;
	private JTextField portaServer3;
	private JRadioButton rdbtnServidor;
	private JRadioButton rdbtnServidor2;
	private JRadioButton rdbtnServidor3;
	private JButton btnEvento;
	private JButton btnMensagem;
	private final ButtonGroup tipo = new ButtonGroup();
	private ControllerServer controllerServer = ControllerServer.getInstance();
	private ControllerDadosServer controllerDados = ControllerDadosServer.getInstance();
	private JLabel lblQualEsse;
	private JTextArea textArea;
	private JButton startserver;
	private JLabel lblServidor;
	private JLabel lblEu;
	private JLabel relogioLogico;
	private JButton buttonAtualizar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String nome = System.getProperty("os.name");//recupera o nome do SO
		if(nome.substring(0, 7).equals("Windows")){//se ele for WINDOWS é colocado um LookAndFeel do windows para rodar melhorar a aparencia
			try { 
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 503, 417);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 467, 356);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Inicio", null, panel, null);
		panel.setLayout(null);
		
		rdbtnServidor = new JRadioButton("Servidor 1");
		rdbtnServidor.setSelected(true);
		rdbtnServidor.setBounds(10, 224, 86, 23);
		panel.add(rdbtnServidor);
		tipo.add(rdbtnServidor);
		
		rdbtnServidor2 = new JRadioButton("Servidor 2");
		rdbtnServidor2.setBounds(10, 253, 86, 23);
		panel.add(rdbtnServidor2);
		tipo.add(rdbtnServidor2);
		
		rdbtnServidor3 = new JRadioButton("Servidor 3");
		rdbtnServidor3.setBounds(10, 279, 86, 23);
		panel.add(rdbtnServidor3);
		tipo.add(rdbtnServidor3);
		
		startserver = new JButton("Start Server");
		startserver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciaServidor();
			}
		});
		startserver.setBounds(337, 279, 115, 23);
		panel.add(startserver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 442, 154);
		panel.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEnabled(false);
		scrollPane.setViewportView(textArea);
		
		porta = new JTextField();
		porta.setText("1099");
		porta.setEnabled(false);
		porta.setColumns(10);
		porta.setBounds(366, 253, 86, 20);
		panel.add(porta);
		
		JLabel label = new JLabel("Porta:");
		label.setBounds(331, 257, 46, 14);
		panel.add(label);
		
		btnEvento = new JButton("Evento");
		btnEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				geraEvento();
			}
		});
		btnEvento.setEnabled(false);
		btnEvento.setBounds(102, 224, 89, 23);
		panel.add(btnEvento);
		
		btnMensagem = new JButton("Mensagem");
		btnMensagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviaMsg();
			}
		});
		btnMensagem.setEnabled(false);
		btnMensagem.setBounds(102, 279, 89, 23);
		panel.add(btnMensagem);
		
		lblQualEsse = new JLabel("Qual \u00E9 esse servidor?");
		lblQualEsse.setBounds(10, 187, 238, 14);
		panel.add(lblQualEsse);
		
		JLabel lblRelogioLogico = new JLabel("Rel\u00F3gio vetorial:");
		lblRelogioLogico.setBounds(285, 215, 101, 14);
		panel.add(lblRelogioLogico);
		
		relogioLogico = new JLabel("0|0|0");
		relogioLogico.setBounds(285, 233, 46, 14);
		panel.add(relogioLogico);
		
		lblServidor = new JLabel("Servidor:");
		lblServidor.setBounds(285, 176, 72, 14);
		panel.add(lblServidor);
		
		lblEu = new JLabel("0");
		lblEu.setBounds(337, 176, 46, 14);
		panel.add(lblEu);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Configurações", null, panel_1, null);
		panel_1.setLayout(null);
		
		ipServer1 = new JTextField();
		ipServer1.setText("192.168.0.104");
		ipServer1.setColumns(10);
		ipServer1.setBounds(10, 33, 108, 20);
		panel_1.add(ipServer1);
		
		portaServer1 = new JTextField();
		portaServer1.setText("1099");
		portaServer1.setColumns(10);
		portaServer1.setBounds(257, 33, 86, 20);
		panel_1.add(portaServer1);
		
		JLabel LportaServer1 = new JLabel("Servidor 1 Porta:");
		LportaServer1.setBounds(257, 11, 91, 14);
		panel_1.add(LportaServer1);
		
		JLabel label_2 = new JLabel("Servidor 1 IP:");
		label_2.setBounds(10, 11, 77, 14);
		panel_1.add(label_2);
		
		ipServer2 = new JTextField();
		ipServer2.setText("192.168.0.104");
		ipServer2.setColumns(10);
		ipServer2.setBounds(10, 98, 108, 20);
		panel_1.add(ipServer2);
		
		portaServer2 = new JTextField();
		portaServer2.setText("1100");
		portaServer2.setColumns(10);
		portaServer2.setBounds(257, 98, 86, 20);
		panel_1.add(portaServer2);
		
		JLabel lportaServer2 = new JLabel("Servidor 2 Porta:");
		lportaServer2.setBounds(257, 76, 91, 14);
		panel_1.add(lportaServer2);
		
		JLabel LIpServer2 = new JLabel("Servidor 2 IP:");
		LIpServer2.setBounds(10, 76, 77, 14);
		panel_1.add(LIpServer2);
		
		ipServer3 = new JTextField();
		ipServer3.setText("192.168.0.104");
		ipServer3.setColumns(10);
		ipServer3.setBounds(10, 168, 108, 20);
		panel_1.add(ipServer3);
		
		portaServer3 = new JTextField();
		portaServer3.setText("1101");
		portaServer3.setColumns(10);
		portaServer3.setBounds(257, 168, 86, 20);
		panel_1.add(portaServer3);
		
		JLabel lblServidorPorta_1 = new JLabel("Servidor 3 Porta:");
		lblServidorPorta_1.setBounds(257, 146, 91, 14);
		panel_1.add(lblServidorPorta_1);
		
		JLabel lblServidorIp_1 = new JLabel("Servidor 3 IP:");
		lblServidorIp_1.setBounds(10, 146, 77, 14);
		panel_1.add(lblServidorIp_1);
		
		buttonAtualizar = new JButton("Atualizar");
		buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizaips();
			}
		});
		buttonAtualizar.setBounds(10, 227, 89, 23);
		panel_1.add(buttonAtualizar);
	}
	
	public void atualizaips(){
		String ipServer1 = this.ipServer1.getText();
		int portaServer1 = Integer.parseInt(this.portaServer1.getText());
		String ipServer2 = this.ipServer2.getText();
		int portaServer2 = Integer.parseInt(this.portaServer2.getText());
		String ipServer3 = this.ipServer3.getText();
		int portaServer3 = Integer.parseInt(this.portaServer3.getText());
		
		controllerDados.setIpPorta(ipServer1, portaServer1, ipServer2, portaServer2, ipServer3, portaServer3);
	}
	
	
	public void iniciaServidor(){
		if(controllerServer!=null){
			String ipServer1 = this.ipServer1.getText();
			int portaServer1 = Integer.parseInt(this.portaServer1.getText());
			String ipServer2 = this.ipServer2.getText();
			int portaServer2 = Integer.parseInt(this.portaServer2.getText());
			String ipServer3 = this.ipServer3.getText();
			int portaServer3 = Integer.parseInt(this.portaServer3.getText());
			this.portaServer3.setEnabled(false);
			this.portaServer2.setEnabled(false);
			this.portaServer1.setEnabled(false);
			this.ipServer1.setEnabled(false);
			this.ipServer2.setEnabled(false);
			this.ipServer3.setEnabled(false);
			this.buttonAtualizar.setEnabled(false);
			lblQualEsse.setText("Para qual servidor mandar a mensagem:");
			//int porta = Integer.parseInt(this.porta.getText());
			btnEvento.setEnabled(true);
			btnMensagem.setEnabled(true);
			startserver.setEnabled(false);
			
			int i = 1099;//porta padrão
			int eu = 1;
			String servidor = "servidor";
			if(rdbtnServidor.isSelected()){
				i = 1099;
				eu = 1;
			}else if(rdbtnServidor2.isSelected()){
				i = 1100;
				porta.setText("1100");
				eu = 2;
			}else if(rdbtnServidor3.isSelected()){
				i = 1101;
				porta.setText("1101");
				eu = 3;
			}
			lblEu.setText(eu + "");
			
			controllerServer.iniciaServer(i,textArea);
			controllerDados.setIpPorta(ipServer1, portaServer1, ipServer2, portaServer2, ipServer3, portaServer3);
			controllerDados.setEuSou(eu);
			ThreadAtualizaGUI t = new ThreadAtualizaGUI(relogioLogico);
			t.start();
		}
	}
	
	public void geraEvento(){
		controllerDados.incrementoMeuRelogio();
	}
	
	public void enviaMsg(){
		int i = 2;//porta padrão
		if(rdbtnServidor.isSelected()){
			i = 1;
		}else if(rdbtnServidor2.isSelected()){
			i = 2;
		}else if(rdbtnServidor3.isSelected()){
			i = 3;
		}
		try {
			textArea.setText(textArea.getText() + "\n" + "Enviando nova mensagem para o servidor: " + i + " Meu vetor = " + controllerDados.getVetor1() + "|" + controllerDados.getVetor2() + "|" + controllerDados.getVetor3());
			controllerDados.enviaMsg(i);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//textArea.setText(textArea.getText() + "\n" + "Conexao falhou com o servidor: " + i);
			e.getMessage();
		}
	}
}
