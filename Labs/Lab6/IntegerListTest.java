

import java.lang.reflect.Array;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

class IntegerList{

    private ArrayList<Integer> listIntegers;

   public IntegerList() {
        listIntegers = new ArrayList<Integer>();
    }

    public IntegerList(Integer... numbers)
    {
        listIntegers = Arrays.stream(numbers).collect(Collectors.toCollection(ArrayList::new));
    }

    public void add(int el, int idx)
    {
        if(idx<listIntegers.size() && idx>=0)
            listIntegers.add(idx, el);
        else
        {
            for(int i = listIntegers.size(); i<idx; i++)
                listIntegers.add(0);
            listIntegers.add(el);


        }
    }

    public int remove(int idx)
    {
        return listIntegers.remove(idx);
    }

    public void set(int el, int idx)
    {
        listIntegers.set(idx, el);
    }

    public int get(int idx)
    {
        return listIntegers.get(idx);

    }

    public int size(){
       return listIntegers.size();
    }

    public int count(int el)
    {
        int n = 0;
        for(int i = 0; i<size(); i++)
            if(listIntegers.get(i) == el)
                n++;

            return n;
    }

    public int contains(int el)
    {
        return listIntegers.indexOf(el);
    }

    public void removeDuplicates()
    {
        IntegerList il = new IntegerList();
        for(int i = 0; i<size(); i++)
        {
            int indexOfElement = il.contains(listIntegers.get(i));
            if(indexOfElement != -1)
                il.remove(indexOfElement);
            il.add(listIntegers.get(i), il.size());
        }
        listIntegers = il.listIntegers;
    }

    public int sumFirst(int k)
    {
        int sum = 0;
        for(int i = 0; i<Math.min(k, size()); i++)
            sum+=listIntegers.get(i);
        return sum;
    }

    public int sumLast(int k)
    {
        return listIntegers.stream().skip(size() - Math.min(k, size())).mapToInt(e ->e).sum();
    }

    public void shiftRight(int index, int count) {
        validateIndex(index);
        int shiftIndex = (index + count) % listIntegers.size();
        Integer element = listIntegers.remove(index);
        listIntegers.add(shiftIndex, element);
    }

    public boolean validateIndex(int index) {
        if (index < 0 || index > listIntegers.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return true;
    }
    public void shiftLeft(int index, int count) {
        validateIndex(index);
        int shiftIndex = (index - count) % listIntegers.size();
        if (shiftIndex < 0) {
            shiftIndex = listIntegers.size() + shiftIndex;
        }
        Integer element = listIntegers.remove(index);
        listIntegers.add(shiftIndex, element);
    }

    public IntegerList addValue(int value)
    {

//        return new IntegerList((Integer[])listIntegers.stream().map(element ->element+value).collect(Collectors.toCollection(Array::new)));
        IntegerList il = new IntegerList();
        for(int i = 0; i<size(); i++)
            il.add(listIntegers.get(i) + value, il.size());

        return il;
    }
}

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
