public class ImplementaCalculaProceso{

     public static void main(String[] args){

	  //A partir de una interface, creo una clase anonima, la cual no tiene
	  //nombre, pero implementamos sobre la marcha a la interfaz
	  CalculaProceso cp = (int x, int y) -> {
		System.out.println((x*y)+1);
	  };

	  cp.calculaProceso(3,4);

     }
}
