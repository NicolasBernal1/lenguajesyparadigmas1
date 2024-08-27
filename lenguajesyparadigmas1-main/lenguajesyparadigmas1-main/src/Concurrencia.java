public class Concurrencia {
  public static class Counter{
    private int count = 0;
    public synchronized void increment(){
      count++;
    }
    public synchronized int getCount(){
      return count;
    }

  }

  public static class IncrementThread extends Thread {
      private Counter counter;
      public IncrementThread(Counter counter){
        this.counter = counter;
      }
      public void run(){
        for(int i = 0; i < 1000; i++){
          counter.increment();
        }
      }
  }


  public static void main(String[] args) throws InterruptedException {
    Counter counter = new Counter();

    IncrementThread thread1 = new IncrementThread(counter);
    IncrementThread thread2 = new IncrementThread(counter);

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    System.out.println("Count: " + counter.getCount());
  }
}
