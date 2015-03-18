package lambdaexpressions;

/**
 * Ejercicio de prueba para Expresiones Lambda en el cual se implementa
 * una interfaz de un solo método.
 * @author ernesto cantu
 * 18 marzo del 2015
 */
public class LambdaExpressions {

    public static void main(String[] args) {
        MyInterface myInterfaceImplementation = ()-> {
            System.out.println("I'm doing the implementation here in main");
        };      
        
        myInterfaceImplementation.doJob();
    }
}
