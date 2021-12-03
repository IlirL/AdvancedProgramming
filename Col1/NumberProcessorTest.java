import java.util.*;
import java.util.stream.Collectors;

interface NumberProcessor<R, T extends Number>{
        R compute(ArrayList<T> numbers);
        }

class Numbers<T extends  Number> {
        //TODO add fields
        ArrayList<T> numbers;
        //TODO constructor

    public Numbers(ArrayList<T> elements) {
        this.numbers = elements;
    }

    public ArrayList<T> getNumbers() {
        return numbers;
    }

    void process(NumberProcessor<?, T> processor) {
        System.out.println(processor.compute(numbers));
        }
        }

public class NumberProcessorTest<T extends Number> {

    public static void main(String[] args) {

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<Double> doubleArrayList = new ArrayList<>();

        int countOfIntegers;
        Scanner sc = new Scanner(System.in);
        countOfIntegers = sc.nextInt();
        while (countOfIntegers>0){
            integerArrayList.add(sc.nextInt());
            --countOfIntegers;
        }

        int countOfDoubles;
        countOfDoubles = sc.nextInt();
        while(countOfDoubles>0){
            doubleArrayList.add(sc.nextDouble());
            --countOfDoubles;
        }

        Numbers<Integer> integerNumbers = new Numbers<>(integerArrayList);

        //TODO first processor
        NumberProcessor<Integer, Integer> firstProcessor = (integerArrayList1)->{
            return integerArrayList1.stream().filter(number -> number<0)
                    .collect(Collectors.toCollection(ArrayList::new))
                    .size();
        };
        System.out.println("RESULTS FROM THE FIRST NUMBER PROCESSOR");
        integerNumbers.process(firstProcessor);

        //TODO second processor
        NumberProcessor<String, Integer> secondProcessor =(integerArrayList1)->{

            double maxNumber = Collections.max(integerArrayList1).doubleValue();
            double minNumber = Collections.min(integerArrayList1).doubleValue();
            double average = integerArrayList1.stream().mapToInt(i ->i).sum();
                    average = average / integerArrayList1.size();
                 return   String.format("Count: %d Min: %.2f Average: %.2f Max: %.2f", integerArrayList1.size()
                            , minNumber, average, maxNumber);
        };
        System.out.println("RESULTS FROM THE SECOND NUMBER PROCESSOR");
        integerNumbers.process(secondProcessor);

        Numbers<Double> doubleNumbers = new Numbers<>(doubleArrayList);

        //TODO third processor
        NumberProcessor<ArrayList<Double>, Double> thirdProcessor = (doubleArrayList1)->{
//            doubleArrayList1 = doubleArrayList1.stream()
//                    .sorted()
//                    .collect(Collectors.toCollection(ArrayList::new));
            Collections.sort(doubleArrayList1);
            return doubleArrayList1;
        };

        System.out.println("RESULTS FROM THE THIRD NUMBER PROCESSOR");
        doubleNumbers.process(thirdProcessor);

        //TODO fourth processor
        NumberProcessor<Double, Double> fourthProcessor = (doubleArrayList1)->{
//            Collections.sort(doubleArrayList1);
            if(doubleArrayList1.size()%2 == 0)
            {
                return (doubleArrayList1.get(doubleArrayList1.size() / 2)
                        + doubleArrayList1.get((doubleArrayList1.size()/2)-1))/2;
            }
            else
            {
                return doubleArrayList1.get(doubleArrayList1.size() / 2);
            }
        };
        System.out.println("RESULTS FROM THE FOURTH NUMBER PROCESSOR");
        doubleNumbers.process(fourthProcessor);

    }

}
