public class ImplementaCalculaProceso{

     public static void main(String[] args){

	  //A partir de una interface, creo una clase anonima, la cual no tiene
	  //nombre, pero implementamos sobre la marcha a la interfaz
	  CalculaProceso cp = new CalculaProceso(){
                public int calculaProceso(int x, int y){
		   return (x*y) + 1;
		}
	  };

	  System.out.println(cp.calculaProceso(3,4));

     }
}
