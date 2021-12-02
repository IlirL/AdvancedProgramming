import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) throws IOException {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race {
    // vashiot kod ovde
    ArrayList<Driver> drivers;

    public F1Race() {
        this.drivers = new ArrayList<Driver>();
    }

    public void readResults(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        this.drivers = br.lines()
                .map(line -> new Driver(line))
                .collect(Collectors.toCollection(ArrayList::new));
        br.close();
    }

    public void printSorted(OutputStream outputStream)
    {
        PrintStream ps = new PrintStream(outputStream);
        Collections.sort(drivers);
        for(int i = 0; i<drivers.size(); i++)
        {
            ps.println(String.format("%d. %-10s%10s", i+1, drivers.get(i).getName(), drivers.get(i).bestLap().toString()));
        }
        ps.close();
    }

    @Override
    public String toString() {
        return String.format("");
    }
}

class Lap implements Comparable<Lap> {
    private int minutes, seconds, nanoseconds;
    private String timeAsString;
    public Lap(int minutes, int seconds, int nanoseconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.nanoseconds = nanoseconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getNanoseconds() {
        return nanoseconds;
    }

    public void setTimeAsString(String timeAsString) {
        this.timeAsString = timeAsString;
    }

    @Override
    public int compareTo(Lap o) {
        return Comparator.comparing(Lap::getMinutes)
                .thenComparing(Lap::getSeconds)
                .thenComparing(Lap::getNanoseconds)
                .compare(this, o);

        //this returns the bigger time, meaning the worst time
    }

    @Override
    public String toString() {
        return timeAsString;
    }
}

class Driver implements  Comparable<Driver>{
    private String name;
    private Lap lap1, lap2, lap3;

    public Driver(String name, Lap lap1, Lap lap2, Lap lap3) {
        this.name = name;
        this.lap1 = lap1;
        this.lap2 = lap2;
        this.lap3 = lap3;
    }

    public Driver(String line) {
        String[] lines = line.split("\\s+");
        this.name = lines[0];
       String [] lap1Times = lines[1].split(":");
        this.lap1 = new Lap(Integer.parseInt(lap1Times[0]), Integer.parseInt(lap1Times[1]), Integer.parseInt(lap1Times[2]));

        String [] lap2Times = lines[2].split(":");
        this.lap2 = new Lap(Integer.parseInt(lap2Times[0]), Integer.parseInt(lap2Times[1]), Integer.parseInt(lap2Times[2]));

        String [] lap3Times = lines[3].split(":");
        this.lap3 =  new Lap(Integer.parseInt(lap3Times[0]), Integer.parseInt(lap3Times[1]), Integer.parseInt(lap3Times[2]));
        lap1.setTimeAsString(lines[1]);
        lap2.setTimeAsString(lines[2]);
        lap3.setTimeAsString(lines[3]);
    }

    public Lap bestLap()
    {
        Lap max = lap1;
        if(max.compareTo(lap2) == 1)
            max = lap2;
        if(max.compareTo(lap3) == 1)
            max = lap3;

        return max;
    }

    @Override
    public int compareTo(Driver o) {
        return bestLap().compareTo(o.bestLap());
    }

    public String getName() {
        return name;
    }

    public Lap getLap1() {
        return lap1;
    }

    public Lap getLap2() {
        return lap2;
    }

    public Lap getLap3() {
        return lap3;
    }
}