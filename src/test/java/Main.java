import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

  public static void main(final String[] args) {
    new Main();
  }

  private Main() {
    final Queue<String> queue = new PriorityQueue<>();
    queue.add("A");
    queue.add("B");

    System.out.println(queue.poll());
    System.out.println(queue.poll());
  }

}
