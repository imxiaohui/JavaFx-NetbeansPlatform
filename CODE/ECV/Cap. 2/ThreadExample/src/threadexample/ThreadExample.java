package threadexample;

import javax.swing.JOptionPane;

/**
 * Thread First Example.
 * @author ernesto
 */
public class ThreadExample implements Runnable {

    @Override
    public void run() {
        Long x = Long.parseLong(JOptionPane.showInputDialog(null, "Introduce a number"));
        String name = JOptionPane.showInputDialog(null, "Introduce a thread name");
        
        for(int i = 1; i <= x; i++){
            System.out.println(i);
            threadMethod(name);
        }
    }
    
    public void threadMethod( String thread ){
        System.out.println( thread );
    }
    
    public static void main(String[] args) {
        ThreadExample runnable1 = new ThreadExample();
        ThreadExample runnable2 = new ThreadExample();
        
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        
        thread1.start();
        thread2.start();
    }
    
}
