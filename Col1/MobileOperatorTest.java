import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MobileOperatorTest {
    public static void main(String[] args) throws IOException {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---- READING OF THE SALES REPORTS ----");
        mobileOperator.readSalesRepData(System.in);
        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}

enum Type{
    S,M
}

class CustomerBill{
    private String customerId;
    private Type package_type;
    private int minutes, sms;
    private double internet;
    private double basic_price;

    public CustomerBill(String customerId, Type package_type, int minutes, int sms, double internet)
            throws InvalidIdException {
        this.customerId = customerId;
        if(!checkCorrectId())
            throw new InvalidIdException("user", customerId);
        this.package_type = package_type;
        this.minutes = minutes;
        this.sms = sms;
        this.internet = internet;
    }

    public boolean checkCorrectId()
    {
        if(customerId.length() != 7)
            return false;
        for(int i = 0; i<customerId.length(); i++)
            if(customerId.charAt(i) < '0' || customerId.charAt(i)>'9')
                return false;

        return true;
    }

    public double getPrice()
    {
        if(package_type == Type.M)
        {
            basic_price = 750;
            int free_minutes = 150, free_sms = 60, free_internet = 10;
            int minutes_cost = 4, sms_cost = 4, internet_cost = 20;

            return Math.max((minutes - free_minutes), 0)*minutes_cost+
                    Math.max(sms-free_sms, 0) * sms_cost+
                    Math.max(internet - free_internet, 0)*internet_cost +
                    basic_price;
        }
        else
            basic_price = 500;
        int free_minutes = 100, free_sms = 50, free_internet = 5;
        int minutes_cost = 5, sms_cost = 6, internet_cost = 25;
        return Math.max((minutes - free_minutes), 0)*minutes_cost+
                Math.max(sms-free_sms, 0) * sms_cost+
                Math.max(internet - free_internet, 0)*internet_cost +
                basic_price;
        //dont forget to refactor this
    }

    public double getTax(){
        if(package_type == Type.M)
            return 0.07;
        return 0.04;
    }


}

class SalesRep{
    private String salesRepId;
    ArrayList<CustomerBill> bills;

    public SalesRep(String line) throws InvalidIdException {
        this.bills = new ArrayList<>();
        String[] lines = line.split("\\s+");
//        System.out.println(lines.length);
        this.salesRepId = lines[0];
        if(!checkCorrectId())
            throw new InvalidIdException("sales rep", salesRepId);
        for(int i = 1; i<lines.length; i+=5)
        {
//            System.out.println(lines[i] + ", "+lines[i+1]+ ", "+lines[i+2]+ ", "+lines[i+3]+ ", "+lines[i+4]);
            try{
               CustomerBill temp =  new CustomerBill(lines[i],Type.valueOf(lines[i+1]),
                        Integer.parseInt(lines[i+2]), Integer.parseInt(lines[i+3]),
                        Double.parseDouble(lines[i+4]));
               bills.add(temp);

            }catch(InvalidIdException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean checkCorrectId()
    {
        if(salesRepId.length() != 3)
            return false;
        for(int i = 0; i<salesRepId.length(); i++)
            if(salesRepId.charAt(i) < '0' || salesRepId.charAt(i)>'9')
                return false;

            return true;
    }

    public double total_commission(){
        double total = 0;
        for(int i = 0; i<this.bills.size();i++)
        {
            total+=this.bills.get(i).getPrice()*this.bills.get(i).getTax();
        }
        return total;
    }
    public double minBill()
    {
       return bills.stream().mapToDouble(CustomerBill::getPrice).min().getAsDouble();
    }

    public double maxBill()
    {
        return bills.stream().mapToDouble(CustomerBill::getPrice).max().getAsDouble();
    }

    public double averageBill()
    {
        return bills.stream().mapToDouble(CustomerBill::getPrice).sum() / bills.size();
    }
    @Override
    public String toString() {
        return String.format("%s Count: %d Min: %.2f Average: %.2f Max: %.2f Commission: %.2f",
                            salesRepId, bills.size(), minBill(), averageBill(), maxBill(), total_commission());
    }
}

class MobileOperator{
    ArrayList<SalesRep> salesReps;

    public MobileOperator() {
        this.salesReps = new ArrayList<SalesRep>();
    }

    public void readSalesRepData (InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        salesReps = br.lines()
//                    .map((line) -> {
//                                try {
//                                    return new SalesRep(line);
//                                } catch (InvalidIdException e) {
//                                    System.out.println(e.getMessage());
//                                }
//                            }
//                    ).collect(Collectors.toCollection(ArrayList::new));

        String singleLine = new String();
        while((singleLine = br.readLine())!=null)
        {
//            System.out.println(singleLine);
            try{
                SalesRep temp = new SalesRep(singleLine);
                salesReps.add(temp);
            }catch(InvalidIdException e)
            {
                System.out.println(e.getMessage());
            }
        }
        br.close();

    }

    public void printSalesReport (OutputStream os)
    {
        PrintWriter pw = new PrintWriter(os);
        for(int i = 0; i<salesReps.size(); i++)
            pw.println(salesReps.get(i).toString());

        pw.close();
    }
}

class InvalidIdException extends Exception{
    public InvalidIdException(String type, String id) {
        super(String.format("%s is not a valid %s ID", id, type));
    }
}