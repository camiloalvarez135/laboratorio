import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;


import akka.actor.UntypedActor;



public class BancoA { //implementci�n sin calendarizaci�n
		
	static Integer cuenta = 0; //cuenta compartida
	static Integer op_ant;				//cantidad de operaciones de antonio
	static Integer [] ops_ant;		//operaciones hechas por antonio
	static Integer op_bla ;			//cantidad de operaciones de blanca
	static Integer [] ops_bla;		//operaciones hechas por blanca
	
	public static void main(String[] args) {
		
		
		
		
		//con archivo
		if(args.length != 0){
		String input_file = new String();
			File f = new File( args[0] );
			BufferedReader entrada;
			try {
			entrada = new BufferedReader( new FileReader( f ) );
			while(entrada.ready()){
				input_file += entrada.readLine() + "\n";
			}
			}catch (IOException e) {
			e.printStackTrace();
			}
		
		
			String[] ops_entrada = input_file.split("\n");
			
			//System.out.println(input_file);
			op_ant =Integer.valueOf(ops_entrada[0]);
			System.out.println(ops_entrada.length);
			ops_ant = new Integer[op_ant];
			int k = 0;
			for(int i = 2 ; i < 2+op_ant; i++, k++){
				System.out.println(i);
				ops_ant[k]=Integer.valueOf(ops_entrada[i]);
			}
			
			op_bla =Integer.valueOf(ops_entrada[1]);
			ops_bla = new Integer[op_bla];
			int j = 0 ;
			for(int i = 2 + op_ant ; i < ops_entrada.length; i++, j++)
				ops_bla[j]=Integer.valueOf(ops_entrada[i]);
			
			
		}else{		//por inserci�n
			System.out.println("# operaciones Antonio:");
			Scanner input = new Scanner(System.in);
			op_ant = input.nextInt();
			ops_ant = new Integer [op_ant];
			for(int i = 0 ; i < op_ant; i++){
				System.out.println("Operaci�n # "  + (i+1) + " : ");
				ops_ant[i] = input.nextInt();
			}
			System.out.println("# operaciones blanca:");
			op_bla = input.nextInt();
			ops_bla = new Integer [op_bla];
			for(int i = 0 ; i < op_bla ; i++){
				System.out.println("Operaci�n # "  + (i+1) + " : ");
				ops_bla[i] = input.nextInt();
		}
		
		input.close();
		}
		
		
		
		
		ActorSystem system = ActorSystem.create("Banco");
		ActorRef Antonio = system.actorOf(new Props(UsuarioA.class), "Usuario1");
		ActorRef  Blanca = system.actorOf(new Props(UsuarioA.class), "Usuario2");
		
				
		Antonio.tell("run1", null);
		Blanca.tell("run2", null);
				
		system.shutdown();
		system.awaitTermination(); //JOIN		
		System.out.println(cuenta);
	}

}


class UsuarioA extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		
		if( message instanceof String){
			
			String s = (String) message;
			switch (s) {
			case "run1":
				
				Integer cuentaUser = 0;	
				
				for(int i = 0 ; i < BancoA.op_ant; i++){
						System.out.println( "Antonio : " + BancoA.ops_ant[i]);
						cuentaUser += BancoA.ops_ant[i];
					}
				
				BancoA.cuenta += cuentaUser;				
				break;
				
			case "run2":
				
				Integer cuentaUser2 = 0;	
				
				for(int i = 0 ; i < BancoA.op_bla; i++){
					
						System.out.println( "Blanca : " + BancoA.ops_bla[i]);
						cuentaUser2 += BancoA.ops_bla[i];
					}
				
				BancoA.cuenta += cuentaUser2;				
				break;


			default:
				System.out.println("uh?");
				break;
			}
		
		}
		
	}

}