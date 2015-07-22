package lambdaexpressions;

/**
 * Ejercicio de prueba para Expresiones Lambda en el cual se implementa
 * una interfaz de un solo método.
 * @author ernesto cantu
 * 18 marzo del 2015
 */
public class LambdaExpressions {

    public static void main(String[] args) {
        //Implementación anonima interna de la interfase MyInterface
        //SE crea una Clase sin nombre dentro de main y se asigna a un objeto del tipo MyInterface
        MyInterface myInterfaceImplementation = (String message)-> {
            System.out.println(message);
        };      
        
        myInterfaceImplementation.doJob("I'm doing the implementation here in main");
    }
}
