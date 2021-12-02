
import java.sql.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.stream.Collectors;

class ArrayIndexOutOfBoundsException extends Exception{
    public ArrayIndexOutOfBoundsException() {
        super("ArrayIndexOutOfBoundsException ");
    }
}

class ResizableArray<T>{

    protected T[] elements;
    protected int maxSize;
    protected int number_element;

    public ResizableArray() {
//        this.maxSize = 1000;
//        this.number_element = 0;
//        this.elements = (T[]) new Object[maxSize];
        this.maxSize = 10;
        this.number_element = 0;
        this.elements = (T[]) new Object[maxSize];
    }

    public void addElement(T element)
    {
//        if(number_element == maxSize)
//        {
//            this.maxSize*=2;
//            this.elements = Arrays.copyOf(this.elements, maxSize*2);
//        }
//
//        this.elements[number_element++] = element;
        if(number_element==maxSize){
            maxSize = maxSize * 2;
            T[] temp = (T[]) new Object[maxSize];
            for (int i = 0; i < elements.length; i++) {
                temp[i] = elements[i];
            }
            elements = temp;
        }
        elements[number_element++] = element;
    }

    public boolean removeElement(T element)
    {
//        for(int i = 0; i<number_element; i++)
//        {
//            if(this.elements[i].equals(element))
//            {
//                for(int j = i+1; j<number_element; j++)
//                {
//                    this.elements[j-1] = this.elements[j];
//                }
//                number_element--;
//                if(number_element < maxSize/2)
//                {
//                    this.maxSize/=2;
//                    this.elements = Arrays.copyOf(this.elements, maxSize/2);
//                }
//                return true;
//            }
//        }
//        return false;

        int index = this.findElement(element);
        if (index == -1)
            return false;
        for (int i=index; i<number_element-1;++i) {
            elements[i] = elements[i+1];
        }
        --number_element;
        return true;
    }


    private int findElement(T element) {
        for (int i = 0; i < number_element; i++) {
            if(element.equals(elements[i]))
                return i;
        }
        return -1;
    }

    public boolean contains(T element){
        for(int i = 0; i<number_element; i++)
        {
            if(this.elements[i].equals(element))
                return true;
        }
        return false;
    }

    public Object[] toArray()
    {
        return Arrays.stream(elements).toArray();
    }


    boolean isEmpty()
    {
        return number_element==0;
    }

    public int count(){
        return number_element;
    }
    public T elementAt(int idx) throws ArrayIndexOutOfBoundsException {
        if(idx<0 && idx > number_element)
            throw new ArrayIndexOutOfBoundsException();
        return elements[idx];
    }

//    <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src)
    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src) throws ArrayIndexOutOfBoundsException {
        for(int i = 0; i<src.count(); i++)
        {
            dest.addElement(src.elementAt(i));
        }

    }

}

class IntegerArray extends  ResizableArray<Integer>{

    public IntegerArray(){
        super();
    }

    public double sum() throws ArrayIndexOutOfBoundsException {
        double total = 0;
        for(int i = 0; i<count(); i++)
        {
            total+=elementAt(i);
        }
        return total;
    }

    public double mean() throws ArrayIndexOutOfBoundsException {
        return sum() / count();
    }

    public int countNonZero() throws ArrayIndexOutOfBoundsException {
        int numbers = 0;
        for(int i = 0; i<count(); i++)
            if(elementAt(i) != 0)
                numbers++;

            return numbers;
    }

    public IntegerArray distinct() throws ArrayIndexOutOfBoundsException {
//        IntegerArray ia = new IntegerArray();
//        Arrays.stream(this.elements).distinct().forEach(number -> ia.addElement(number));
//
//        return ia;
        IntegerArray ia = new IntegerArray();
        for (int i = 0; i < count(); i++) {
            if(ia.contains(this.elementAt(i)))
                continue;
            ia.addElement(this.elementAt(i));
        }
        return ia;
    }

    public IntegerArray increment(int offset) throws ArrayIndexOutOfBoundsException{
//        IntegerArray ia = new IntegerArray();
//        Arrays.stream(this.elements).map(element -> element+offset).forEach(element -> ia.addElement(element));
//        return ia;
        IntegerArray nova = new IntegerArray();
        for (int i = 0; i < count(); i++) {
            nova.addElement(this.elementAt(i) + offset);
        }
        return nova;
    }
}

public class ResizableArrayTest {

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if ( test == 0 ) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while ( jin.hasNextInt() ) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if ( test == 1 ) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for ( int i = 0 ; i < 4 ; ++i ) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if ( test == 2 ) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while ( jin.hasNextInt() ) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if ( a.sum() > 100 )
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if ( test == 3 ) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for ( int w = 0 ; w < 500 ; ++w ) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k =  2000;
                int t =  1000;
                for ( int i = 0 ; i < k ; ++i ) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for ( int i = 0 ; i < t ; ++i ) {
                    a.removeElement(k-i-1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}

