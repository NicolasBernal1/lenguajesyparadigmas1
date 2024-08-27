import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Funcional {

  public static int half(int n) {
    return n / 2;
  }
  public static int trip(int n){
    return n * 3;
}
  public static void main(String[] args){
    List<Integer> numbers = Arrays.asList(50, 78, 34, 1,20,88,50 );
    int sumOfEventNumbers = numbers.stream()
            .filter(number -> number % 2 == 0)
            .mapToInt(Integer::intValue)
            .sum();

    //System.out.println("Suma de los numeros pares: " + sumOfEventNumbers);

    var asd = numbers.stream()
            .distinct()
            .sorted()
            .map(Funcional::half)
            .limit(5)
            .skip(1)
            .collect(Collectors.toList());

    //reduce y flatmap



    System.out.println(asd);
  }
}
