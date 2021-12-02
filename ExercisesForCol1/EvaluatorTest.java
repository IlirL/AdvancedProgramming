import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EvaluatorTest {

    private static class Student implements Comparable<Student>{
        String id;
        Double average;
        int year;

        Student(String id, Double average, int year) {
            this.id = id;
            this.average = average;
            this.year = year;
        }

        public static Student createInstance (String input) {
            String [] parts = input.split("\\s+");
            Double average = Double.parseDouble(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return new Student(parts[0], average, year);
        }

        @Override
        public int compareTo(Student student) {
            int compResult = Double.compare(this.average, student.average);
            if (compResult==0)
                return Integer.compare(this.year, student.year);
            else
                return compResult;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String [] parts = line.split("\\s+");
            String operator = parts[2];

            if (parts[0].equals("1")) { //Integers
                Integer left = Integer.valueOf(parts[1]);
                Integer right = Integer.valueOf(parts[3]);
                System.out.println(Evaluator.evaluateExpression(left,right,operator));

            }
            else if (parts[0].equals("2")) { //Double
                Double left = Double.valueOf(parts[1]);
                Double right = Double.valueOf(parts[3]);
                System.out.println(Evaluator.evaluateExpression(left,right,operator));
            }
            else if (parts[0].equals("3")) { //Characters
                Character left = parts[1].charAt(0);
                Character right = parts[3].charAt(0);
                System.out.println(Evaluator.evaluateExpression(left,right,operator));
            }
            else if (parts[0].equals("4")) { //String
                System.out.println(Evaluator.evaluateExpression(parts[1],parts[3],operator));
            }
            else { // Students
                operator = parts[3];
                String sInfo1 = Arrays.stream(parts).limit(3).collect(Collectors.joining(" "));
                String sInfo2 = Arrays.stream(parts).skip(4).limit(3).collect(Collectors.joining(" "));
                Student s1 = Student.createInstance(sInfo1);
                Student s2 = Student.createInstance(sInfo2);
                System.out.println(Evaluator.evaluateExpression(s1,s2,operator));
            }
        }

    }
}

 interface IEvaluator<T extends Comparable<T>>{
    public boolean evaluate (T a, T b);
}


class EvaluatorBuilder<T extends Comparable<T>>{


    public static <T extends Comparable<T>> IEvaluator build(String operator){
        if(operator.equals(">"))
        {
            IEvaluator<T> temp = (T x,T y)-> {return x.compareTo(y)>0;};
            return temp;
        }
        else if(operator.equals("=="))
        {
            IEvaluator<T> temp = (T x,T y)->{return x.compareTo(y)==0;};
            return temp;
        }
        else if(operator.equals("!="))
        {
            IEvaluator<T> temp = (T x,T y)->{return x.compareTo(y)!=0;};
            return temp;
        }
        else if(operator.equals("<"))
        {
            IEvaluator<T> temp = (T x,T y)->{return x.compareTo(y)<0;};
            return temp;
        }

        return null;

    }
}
//static boolean evaluateExpression (T left, T right, String operator) -
class Evaluator<T extends Comparable<T>>{
   public static <T extends  Comparable<T>> boolean evaluateExpression(T left, T right, String operator)
   {
        return EvaluatorBuilder.build(operator).evaluate(left, right);
   }
}