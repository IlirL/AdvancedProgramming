import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
//            System.out.println("ilir");
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde
//public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) -
class WeatherStation
{
    private ArrayList<Weather> weathers;
    private int currentSize;
    private int max_size;

    public WeatherStation(int size) {
        this.weathers = new ArrayList<>(size);
        this.max_size = size;
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
        boolean addThisMeasurement = true;
        for(int i = 0; i<weathers.size(); i++)
            if(difference_minutes_smaller(date, weathers.get(i).getDate()))
                addThisMeasurement = false;

            if(addThisMeasurement)
            {
                //delete all those that are max_size days older that this one
                for(int i = 0; i<weathers.size(); i++)
                {
                    if(weathers.get(i).getDate().after(date))
                    {
                        addThisMeasurement = false;
                        return;
                    }
                }

                for(int i = 0; i<weathers.size(); i++)
                {
                    if(!difference_day_smaller(weathers.get(i).getDate(), date))
                        weathers.remove(i);

                }
                    weathers.add(weathers.size(), new Weather(temperature, wind, humidity, visibility, date));
            }
    }

    public int total()
    {
        return weathers.size();
    }

    public void status(Date from, Date to)
    {
//       ArrayList<Weather> status_weathers = (ArrayList<Weather>) weathers.stream()
//               .filter(weather -> weather.getDate().after(from) && weather.getDate().before(to))
//                .collect(Collectors.toCollection(ArrayList::new))
//                .stream().sorted(Comparator.comparing(Weather::getDate));

        ArrayList<Weather> status_weathers = (ArrayList<Weather>) weathers.stream()
                .filter(weather -> weather.getDate().compareTo(from) != -1 && weather.getDate().compareTo(to) != 1)
                .collect(Collectors.toCollection(ArrayList::new));
//        ArrayList<Weather> status_weathers = weathers;

       if(status_weathers.size()== 0)
           throw new RuntimeException();

       status_weathers.stream().forEach(weather -> System.out.println(weather.toString()));
        System.out.println(String.format("Average temperature: %.2f",
                status_weathers.stream().mapToDouble(Weather::getTemperature).sum() / (float)status_weathers.size()));
    }
    public  boolean  difference_minutes_smaller(Date date1, Date date2)
    {
        //returns if difference is smaller that 2.5
        return ((double)Math.abs(date1.getTime() - date2.getTime())/1000) < 150;
    }

    public boolean difference_day_smaller(Date d1, Date d2)
    {
        //returns if difference in days is greater than 5
        return ((((double)Math.abs(d1.getTime() - d2.getTime())/1000)/3600)/24) < max_size;
    }
}

class Weather{
    private float temperature, wind, hummidity, visibility;
    private Date date;

    public Weather(float temperature, float wind, float hummidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.hummidity = hummidity;
        this.visibility = visibility;
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getWind() {
        return wind;
    }

    public float getHummidity() {
        return hummidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return temperature + " " + wind + " km/h " + hummidity + "% "+ visibility+ " km " + date;
    }
}