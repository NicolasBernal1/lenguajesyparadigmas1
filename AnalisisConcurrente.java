import java.net.StandardSocketOptions;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Filter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnalisisConcurrente {
  public static List<Map<String, String>> processLogs(String logs){
    List<Map<String, String>> processedLogs = Stream.of(logs.split("\n"))// parte el string por lineas
            .map(line -> line.trim().replaceAll("[{}']", ""))//quita los {}
            .map(line -> Arrays.stream(line.split(", "))//parte por comas
                    .map(pair -> pair.split(": "))//parte por :
                    .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1])) //combierte en un mapa de clave valor
            )
            .toList(); //pone todo en una lista
    return processedLogs;
  }

  public class FilterLogs implements Callable<List<Map<String, String>>> {
    public List<Map<String, String>> logs;
    public FilterLogs(List<Map<String, String>> logs){
      this.logs = logs;
    }

    @Override
    public List<Map<String, String>> call(){ //filted(), sorted(), collect()
      DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      return logs.stream()
              .filter(log -> "POST".equals(log.get("request_method")))
              .sorted((log1, log2) -> {
                LocalDateTime date1 = LocalDateTime.parse(log1.get("date"), FORMATTER);
                LocalDateTime date2 = LocalDateTime.parse(log2.get("date"), FORMATTER);
                return date1.compareTo(date2);
              })
              .collect(Collectors.toList());
    }
  }
  public class DistinctIps implements Callable<List<Map<String,String>>> {
    public List<Map<String, String>> logs;
    public DistinctIps(List<Map<String, String>> logs){
      this.logs = logs;
    }

    @Override
    public List<Map<String,String>> call(){ //map(), distinct()
      DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      Set<String> result = logs.stream()
              .map(log -> log.get("ip"))
              .distinct()
              .collect(Collectors.toSet());
      return logs.stream()
              .filter(log -> result.contains(log.get("ip")))
              .collect(Collectors.toList());
    }
  }

  public void executorServiceProcessing(int threads, List<Map<String, String>> toProcessData){
    ExecutorService executor = Executors.newFixedThreadPool(4); // Un pool de 4 threads
    List<Future<List<Map<String, String>>>> futures = new ArrayList<>();
    FilterLogs filterLogs = new FilterLogs(toProcessData);
    DistinctIps distinctIps = new DistinctIps(toProcessData);

    futures.add(executor.submit(filterLogs));
    futures.add(executor.submit(distinctIps));

    try {
      for (Future<List<Map<String, String>>> future : futures) {
        List<Map<String, String>> result = future.get();
        System.out.println("Result: " + result);
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    } finally {
      // Cerrar el ExecutorService
      executor.shutdown();
    }



    executor.shutdown();
  }

  public static void caller(int threads, List<Map<String, String>> toProcessData){
    AnalisisConcurrente instance = new AnalisisConcurrente();
    instance.executorServiceProcessing(threads, toProcessData);

  }


  public static void main(String[] args) {
    String data = "{'date': '2024-01-01 12:00:00', 'ip': '192.168.0.1', 'request_method': 'DELETE', 'code': 494}\n" +
            "{'date': '2024-01-02 13:30:00', 'ip': '192.168.0.2', 'request_method': 'POST', 'code': 230}\n" +
            "{'date': '2024-01-03 15:45:00', 'ip': '192.168.0.3', 'request_method': 'DELETE', 'code': 221}\n" +
            "{'date': '2024-01-04 17:00:00', 'ip': '192.168.0.4', 'request_method': 'PUT', 'code': 277}\n" +
            "{'date': '2024-01-05 18:15:00', 'ip': '192.168.0.5', 'request_method': 'DELETE', 'code': 448}\n" +
            "{'date': '2024-02-01 09:30:00', 'ip': '10.0.0.1', 'request_method': 'PUT', 'code': 223}\n" +
            "{'date': '2024-02-02 10:45:00', 'ip': '10.0.0.2', 'request_method': 'POST', 'code': 256}\n" +
            "{'date': '2024-02-03 11:00:00', 'ip': '10.0.0.3', 'request_method': 'PUT', 'code': 346}\n" +
            "{'date': '2024-02-04 14:30:00', 'ip': '10.0.0.4', 'request_method': 'POST', 'code': 494}\n" +
            "{'date': '2024-02-05 16:00:00', 'ip': '10.0.0.5', 'request_method': 'PUT', 'code': 305}\n" +
            "{'date': '2024-03-01 08:00:00', 'ip': '172.16.0.1', 'request_method': 'DELETE', 'code': 405}\n" +
            "{'date': '2024-03-02 12:30:00', 'ip': '172.16.0.2', 'request_method': 'PUT', 'code': 431}\n" +
            "{'date': '2024-03-03 13:45:00', 'ip': '172.16.0.3', 'request_method': 'POST', 'code': 202}\n" +
            "{'date': '2024-03-04 15:00:00', 'ip': '172.16.0.4', 'request_method': 'POST', 'code': 416}\n" +
            "{'date': '2024-03-05 17:30:00', 'ip': '172.16.0.5', 'request_method': 'PUT', 'code': 422}\n" +
            "{'date': '2024-04-01 11:00:00', 'ip': '192.168.1.1', 'request_method': 'POST', 'code': 204}\n" +
            "{'date': '2024-04-02 13:30:00', 'ip': '192.168.1.2', 'request_method': 'DELETE', 'code': 446}\n" +
            "{'date': '2024-04-03 14:45:00', 'ip': '192.168.1.3', 'request_method': 'POST', 'code': 323}\n" +
            "{'date': '2024-04-04 16:00:00', 'ip': '192.168.1.4', 'request_method': 'POST', 'code': 336}\n" +
            "{'date': '2024-04-05 17:15:00', 'ip': '192.168.1.5', 'request_method': 'GET', 'code': 219}\n" +
            "{'date': '2024-05-01 10:30:00', 'ip': '10.1.1.1', 'request_method': 'GET', 'code': 264}\n" +
            "{'date': '2024-05-02 11:45:00', 'ip': '10.1.1.2', 'request_method': 'DELETE', 'code': 468}\n" +
            "{'date': '2024-05-03 13:00:00', 'ip': '10.1.1.3', 'request_method': 'POST', 'code': 209}\n" +
            "{'date': '2024-05-04 14:15:00', 'ip': '10.1.1.4', 'request_method': 'DELETE', 'code': 312}\n" +
            "{'date': '2024-05-05 15:30:00', 'ip': '10.1.1.5', 'request_method': 'POST', 'code': 338}\n" +
            "{'date': '2024-06-01 09:00:00', 'ip': '172.31.255.1', 'request_method': 'GET', 'code': 337}\n" +
            "{'date': '2024-06-02 10:15:00', 'ip': '172.31.255.2', 'request_method': 'DELETE', 'code': 290}\n" +
            "{'date': '2024-06-03 11:30:00', 'ip': '172.31.255.3', 'request_method': 'GET', 'code': 471}\n" +
            "{'date': '2024-06-04 12:45:00', 'ip': '172.31.255.4', 'request_method': 'DELETE', 'code': 268}\n" +
            "{'date': '2024-06-05 14:00:00', 'ip': '172.31.255.5', 'request_method': 'POST', 'code': 204}\n" +
            "{'date': '2024-07-01 08:30:00', 'ip': '192.168.2.1', 'request_method': 'PUT', 'code': 214}\n" +
            "{'date': '2024-07-02 09:45:00', 'ip': '192.168.2.2', 'request_method': 'DELETE', 'code': 415}\n" +
            "{'date': '2024-07-03 11:00:00', 'ip': '192.168.2.3', 'request_method': 'DELETE', 'code': 363}\n" +
            "{'date': '2024-07-04 12:15:00', 'ip': '192.168.2.4', 'request_method': 'DELETE', 'code': 438}\n" +
            "{'date': '2024-07-05 13:30:00', 'ip': '192.168.2.5', 'request_method': 'PUT', 'code': 232}\n" +
            "{'date': '2024-08-01 10:00:00', 'ip': '10.2.2.1', 'request_method': 'POST', 'code': 429}\n" +
            "{'date': '2024-08-02 11:15:00', 'ip': '10.2.2.2', 'request_method': 'POST', 'code': 318}\n" +
            "{'date': '2024-08-03 12:30:00', 'ip': '10.2.2.3', 'request_method': 'PUT', 'code': 304}\n" +
            "{'date': '2024-08-04 13:45:00', 'ip': '10.2.2.4', 'request_method': 'POST', 'code': 227}\n" +
            "{'date': '2024-08-05 15:00:00', 'ip': '10.2.2.5', 'request_method': 'PUT', 'code': 281}\n" +
            "{'date': '2024-09-01 09:30:00', 'ip': '172.20.10.1', 'request_method': 'PUT', 'code': 346}\n" +
            "{'date': '2024-09-02 10:45:00', 'ip': '172.20.10.2', 'request_method': 'PUT', 'code': 253}\n" +
            "{'date': '2024-09-03 11:00:00', 'ip': '172.20.10.3', 'request_method': 'POST', 'code': 361}\n" +
            "{'date': '2024-09-04 13:30:00', 'ip': '172.20.10.4', 'request_method': 'DELETE', 'code': 486}\n" +
            "{'date': '2024-09-05 15:00:00', 'ip': '172.20.10.5', 'request_method': 'POST', 'code': 208}";

    List<Map<String, String>> processedData = processLogs(data);
    caller(4, processedData);

  }
}
