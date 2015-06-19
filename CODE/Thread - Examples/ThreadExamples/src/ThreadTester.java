
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadTester{
	
    public static void main(String [] args) throws InterruptedException{
        HelloRunner r = new HelloRunner();
        Thread t1 = new Thread(r);
        t1.setName("t1");
        
        Thread t2 = new Thread(r);
        t2.setName("t2");
        
        t1.start();
        t2.start();
    }
}

class HelloRunner implements Runnable{
	
    public int i=1;

    public void run(){
        
        while (i<100){
            System.out.println("Hello " + i++ + " " + Thread.currentThread().getName());
            Thread.yield();
        }
    }
}