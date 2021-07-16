import com.celeste.tasks.controller.TaskController;
import com.celeste.tasks.model.entity.Task;
import com.celeste.tasks.model.entity.type.PriorityType;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

  public static void main(final String[] args) {
    try {
      new Main();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

  private Main() {
    final TaskController task = new TaskController(Executors.newCachedThreadPool(),
        Executors.newScheduledThreadPool(1));

//    final Task t1 = task.wait(() -> System.out.println("-"), 1, TimeUnit.SECONDS);
//    final Task t2 = task.wait(() -> System.out.println("AAADA"), 10, TimeUnit.SECONDS);
    final Task t3 = task.wait(() -> System.out.println("NOVHIOAO"), 10, TimeUnit.SECONDS, PriorityType.MINIMUM);
    final Task t4 = task.wait(() -> System.out.println("LUIZA NERDOLA"), 10, TimeUnit.SECONDS, PriorityType.SUPER);

    System.out.println("AKDAJWOADWOJKMAWD");
//    task.shutdown();
  }

}
