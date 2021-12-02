

import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n)
                .forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {
    /**
     * Roman to decimal converter
     *
     * @param n number in decimal format
     * @return string representation of the number in Roman numeral
     */
    public static String toRoman(int n) {
        // your solution here

        StringBuilder stringBuilder = new StringBuilder();
        String[] roman_numbers = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] normal_numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        int temp_number;
        for(int i = 0; i<normal_numbers.length; i++)
        {
            if(n>=normal_numbers[i]){
                temp_number = n/normal_numbers[i];
                n-=temp_number*normal_numbers[i];

                while(temp_number>0)
                {
                    stringBuilder.append(roman_numbers[i]);
                    temp_number--;
                }
            }
        }
        return stringBuilder.toString();
    }

}
