import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class AnalisisConcurrente {
  static class Log{
    public int ip;
    public String date;
    public String request;
    public int response;

    public Log(int ip, String date, String request, int response){
      this.ip = ip;
      this.date = date;
      this.request = request;
      this.response = response;
    }
  }
  /*
  public static List<Log> logs(int len){
    List<Log> generatedLogs;
    for(int i = 0; i < len; i++){
      Random r = new Random();
      String ip =  r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);

    }
  }
*/
  public static String generateDate(){
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss");
    String now_formated = now.format(formatter);
    return now_formated;
  }

  public static void main(String[] args) {

    System.out.println(generateDate());

  }

}
