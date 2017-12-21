package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ControllerDadosServer {

	private static ControllerDadosServer unicaInstancia;
	private String ipServer1;
	private int portaServer1;
	private String ipServer2;
	private int portaServer2;
	private String ipServer3;
	private int portaServer3;
	private int euSou = 1;
	
	private int[] vetor = new int[3];


	private ControllerDadosServer() {
		vetor[1] = 0;
		vetor[0] = 0;
		vetor[2] = 0;
	}

	/**
	 * controla o instanciamento de objetos Controller
	 *
	 * @return unicaInstancia
	 */
	public static synchronized ControllerDadosServer getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new ControllerDadosServer();
		}
		return unicaInstancia;
	}

	/**
	 * reseta o objeto Controller ja instanciado
	 */
	public static void zerarSingleton() {
		unicaInstancia = null;
	}

	public void setIpPorta(String ip1,int porta1, String ip2, int porta2, String ip3, int porta3){
		this.ipServer1 = ip1;
		this.ipServer2 = ip2;
		this.ipServer3 = ip3;
		this.portaServer1 = porta1;
		this.portaServer2 = porta2;
		this.portaServer3 = porta3;
	}
	
	public void incrementoMeuRelogio(){
		switch(euSou){
		case 1:
			vetor[0]++;
			break;
		case 2:
			vetor[1]++;
			break;
		case 3:
			vetor[2]++;
			break;
		}
	}

	public String enviaMsg(int server) throws UnknownHostException, IOException, ClassNotFoundException{
		incrementoMeuRelogio();
		String pack = "0|" + euSou + "|" + vetor[0] + "|" + vetor[1] + "|" + vetor[2]; // envia protocolo 1 para avisar que o cliente quer jogar
		int porta = portaServer2;
		String ip = ipServer2;
		switch (server) {
		case 1:
			porta = portaServer1;
			ip = ipServer1;
			break;

		case 2:
			porta = portaServer2;
			ip = ipServer2;
			break;

		case 3:
			porta = portaServer3;
			ip = ipServer3;
			break;
		}

		//Cria o Socket para buscar o arquivo no servidor 
		Socket rec = new Socket(ip,porta);

		//Enviando o nome do arquivo a ser baixado do servidor
		ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
		saida.writeObject(pack);
		saida.flush();

		ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
		String recebido = (String) entrada.readObject(); 
		saida.close();//fecha a comunicação com o servidor
		entrada.close();
		rec.close();
		
		return recebido;
	}
	
	public int getMeuRelogio(){
		switch(euSou){
		case 1:
			return vetor[0];
		case 2:
			return vetor[1];
		case 3:
			return vetor[2];
		}
		
		return 0;
	}

	public String recebeuMsg(String server, String relogio1S, String relogio2S, String relogio3S) {
		int relogio1 = Integer.parseInt(relogio1S);
		int relogio2 = Integer.parseInt(relogio2S);
		int relogio3 = Integer.parseInt(relogio3S);
		String result = max(relogio1,relogio2,relogio3);
		incrementoMeuRelogio();
		return result;

	}
	
	public String max(int recebido1, int recebido2, int recebido3){
		int resultP = verificarPrecedencia(recebido1, recebido2, recebido3);
		String resultI = "";
		if(recebido1 > vetor[0]){
			resultI = resultI + "1" + "|";
			vetor[0] = recebido1;
		}else{
			resultI = resultI + "0" + "|";
		}
		if(recebido2 > vetor[1]){
			resultI = resultI + "1" + "|";
			vetor[1] = recebido2;
		}else{
			resultI = resultI + "0" + "|";
		}
		if(recebido3 > vetor[2]){
			resultI = resultI + "1" + "|";
			vetor[2] = recebido3;
		}else{
			resultI = resultI + "0" + "|";
		}
		System.out.println("Result P é " + resultP);
		return resultI + resultP;
	}
	
	
	/*
	 * 0 é precedencia
	 * 1 não é precedencia
	 */
	public int verificarPrecedencia(int recebido1, int recebido2, int recebido3){
		if(vetor[0] <= recebido1 && vetor[1] <= recebido2 && vetor[2] <= recebido3){
			System.out.println("Houve precedencia");
			return 0;
		}else{
			System.out.println("Não houve precedencia");
			return 1;
		}
	}

	public int getEuSou() {
		return euSou;
	}

	public void setEuSou(int euSou) {
		this.euSou = euSou;
	}
	
	public int getVetor1(){
		return vetor[0];
	}
	
	public int getVetor2(){
		return vetor[1];
	}
	
	public int getVetor3(){
		return vetor[2];
	}



}
