import com.celeste.tasks.model.entity.type.PriorityType;
import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    Arrays.stream(PriorityType.values()).map(Enum::ordinal).forEach(System.out::println);
  }

}
