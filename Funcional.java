import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Funcional {

  public static int half(int number) {
    return number / 2;
  }
  public static List<Integer> add(List<Integer> list, Integer number){
    list.add(number);
    return list;
  }

  public static List<Integer> combineLists(List<Integer> list1, List<Integer> list2){
    list1.addAll(list2);
    return list1;
  }
  public static void main(String[] args){
    List<Integer> numbers = Arrays.asList(50, 78, 34, 1,20,88,50 );
    int sumOfEventNumbers = numbers.stream()
            .filter(number -> number % 2 == 0)
            .mapToInt(Integer::intValue)
            .sum();

    //System.out.println("Suma de los numeros pares: " + sumOfEventNumbers);

    var result = numbers.stream()
            .distinct()                           // deja elementos unicos
            .sorted()                             // ordena la lista
            .flatMap(num -> Arrays.asList(num, num * 2).stream())  // coge cada numero, lo convierte en un stream con el mismo y su *2 y une todo en un solo stream
            .map(Funcional::half)                 // aplica half a cada elemento del stream
            .limit(10)                            // limita a 10 elementos
            .skip(1)                              // salta el primer elemento
            .reduce(new ArrayList<Integer>(), Funcional::add, Funcional::combineLists);  // pone cada elemento del stream en una lista y une esa lista con una vacía resultando en una lista


    System.out.println(result);
  }
