import java.util.Scanner;
public class gato{
         char [][] tablero;
         char ia;
         char usuario;
        public gato(){
                tablero = new char[3][3];
                ia='X';
                usuario='O';
        }


        public int[] MejorJugada(){
                int alfa=-10000;
                int betha=10000;
                //Movimiento de la IA
                int mejor=-1000;
                int[] movimiento = new int[2];
                for(int i=0;i<3;i++){
                        for(int j=0;j<3;j++){
                                //si está vacío, checar jugada
                                if(tablero[i][j] == (char)0){
                                        tablero[i][j] = ia;
                                        int marcador = Minimax(0, false, alfa, betha);
                                        tablero[i][j] = (char)0;
                                        if(marcador > mejor){
                                                mejor=marcador;
                                                movimiento[0]=i;
                                                movimiento[1]=j;
                                        }
                                }
                        }
                }
                return movimiento;
        }



        public int Minimax(int profundidad, boolean isMax, int a, int b){
                char resultado = HayGanador(); //devuelve quien ganó (X,O,t)
                if(resultado != (char)0){
                        int marcador;
                        if(resultado == 'X')
                                marcador = 1;
                        else if(resultado == 'O')
                                marcador = -1;
                        else
                                marcador = 0;

                        return marcador;
                }


                if(isMax){
                        int mejor=-1000;
                        for(int i=0;i<3;i++){
                                for(int j=0;j<3;j++){
                                        //si está vacío, checar jugada
                                        if(tablero[i][j] == (char)0){
                                                tablero[i][j] = ia;
                                                int marcador = Minimax(profundidad+1, false, a, b);
                                                tablero[i][j] = (char)0;

                                                if(marcador>a)
                                                        a=marcador;
						//se queda con el máximo
                                               // if(marcador > mejor) {
                                                //        mejor = marcador;
                                               // }
                                                if(a>=b)
                                                    return b;
                                        }
                                }
                        }
                        return a;   //return mejor; 
                }
                else{
                        int mejor = 1000;
                        for(int i=0;i<3;i++){
                                for(int j=0;j<3;j++){
                                        //si está vacío, checar jugada
                                        if(tablero[i][j] == (char)0){
                                                tablero[i][j] = usuario;
                                                int marcador = Minimax(profundidad+1, true,a,b);
                                                if(marcador<b)
                                                    b=marcador;
                                                tablero[i][j] = (char)0;
						//se queda con el minimo
                                                //if(marcador < mejor) {
                                                  //      mejor = marcador;
                                               // }
                                                if(a>=b)
                                                    return a;
                                        }
                                }
                        }
                        return b;   //return mejor; //return b

                }
        }



        public char HayGanador(){
                //Horizontal
                for(int i=0;i<3;i++){
                        if((char)0 != tablero[i][0] && tablero[i][0] == tablero[i][1] && tablero[i][0] == tablero[i][2]){
                                return tablero[i][0];
                        }
                }

                //Vertical
                for(int i=0;i<3;i++){
                        if((char)0 != tablero[0][i] && tablero[0][i] == tablero[1][i] && tablero[0][i] == tablero[2][i]){
                                return tablero[0][i];
                        }
                }

                //diagonal
                if((char)0 != tablero[0][0] && tablero[0][0] == tablero[1][1] && tablero[0][0] == tablero[2][2]){
                        return tablero[0][0];
                }
                else if((char)0 != tablero[0][2] && tablero[0][2] == tablero[1][1] && tablero[0][2] == tablero[2][0]){
                        return tablero[0][2];
                }

		//Empate
		for(int i=0;i<3;i++){
                                for(int j=0;j<3;j++){
					if(tablero[i][j]==(char)0){
						return (char)0;
					}
				}
		}

                return 'E'; 


        }


        public void MuestraTablero(){
                System.out.println(tablero[0][0]+"     | "+tablero[0][1]+"   | "+tablero[0][2]);
                System.out.println("-----------------");
                System.out.println(tablero[1][0]+"     | "+tablero[1][1]+"   | "+tablero[1][2]);
                System.out.println("-----------------");
                System.out.println(tablero[2][0]+"     | "+tablero[2][1]+"   | "+tablero[2][2]);
        }


        public static void main(String[] args) {
                gato gato=new gato();

                //¿Quién inicia?
                Scanner sc = new Scanner(System.in);
                char turno='C';
                if(turno=='C'){
                        System.out.println("La computadora inicia");
                }
                else{
                        System.out.println("El usuario inicia");
                }

                gato.MuestraTablero();
                boolean hayWinner=false;


                //Ciclo del juego
                while(!hayWinner){
                        //turno de la computadora
                        if(turno=='C'){
                                System.out.println("Turno de la computadora:");
                                int[] movimiento=gato.MejorJugada();
                                gato.tablero[movimiento[0]][movimiento[1]]=gato.ia;
                                turno='H'; 
                        }
                        //turno del Uusario (humano)
                        else{
                                boolean bol=true;
                                //ciclo para que el usuario escoja una posición correcta
                                while(bol){
                                        System.out.println("Turno del usuario:");
                                        int indice1 = sc.nextInt();
                                        int indice2 = sc.nextInt();
                                        //ciclo para que no haya problemas con el arreglo n caso de que el usuario esocga un mal indice fuera del tablero
                                        if(indice1<=2 && indice1>=0 && indice2<=2 && indice2>=0){
                                                //Si es un espacio perimitido
                                                if(gato.tablero[indice1][indice2] == (char)0){ 
                                                        gato.tablero[indice1][indice2]=gato.usuario;
                                                        bol=false;
                                                }
                                        }
                                }
                                turno='C';

                        }

                        //Ya que se acabó el turno
                        gato.MuestraTablero();
                        char resultado=gato.HayGanador();
                        //si ya hay ganador
                        if(resultado != (char)0){
				hayWinner=true;
				if(resultado=='E'){
					System.out.println("Empate");
				}
				else{
                                	System.out.println("El ganador es: "+resultado);
				}
                        }

                } 
        }
}

