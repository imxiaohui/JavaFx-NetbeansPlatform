package lambdaexpressions;

/**
 * Ejercicio de prueba para implementar una interfaz de un solo método.
 * @author ernesto cantu
 * 22 de julio del 2015
 */
public class LambdaExpressionsImplements implements MyInterface {

    public static void main(String[] args) {
        
        //herencia polimorfica
        MyInterface bean = new LambdaExpressionsImplements();
        bean.doJob("I'm doing the implementation here in main");
    }

    @Override
    public void doJob(String message) {
        System.out.println(message);
    }
    
}
