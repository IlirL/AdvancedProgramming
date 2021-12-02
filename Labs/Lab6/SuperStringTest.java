import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

class SuperString {

    private LinkedList<StringOrder> list;

    public SuperString() {
        this.list = new LinkedList<>();
    }

    public void append(String s) {
        this.list.addLast(new StringOrder(s, this.list.size()));
    }

    public void insert(String s) {
        this.list.addFirst(new StringOrder(s, this.list.size()));
    }

    public boolean contains(String s) {
        return this.toString().contains(s);
    }

    @Override
    public String toString() {
        String s = "";
        Iterator<StringOrder> it = this.list.iterator();
        while(it.hasNext())
            s += it.next();
        return s;
    }

    public void reverse() {
        Collections.reverse(list);
        Iterator<StringOrder> it = this.list.iterator();
        while(it.hasNext())
            it.next().reverseString();
    }

    public void removeLast(int k) {
        int size = this.list.size();
        Iterator<StringOrder> it = this.list.iterator();
        LinkedList<StringOrder> newList = new LinkedList<>();
        while(it.hasNext()) {
            StringOrder curr = it.next();
            if(curr.getOrder() < size-k)
                newList.add(curr);
        }
        this.list = newList;
    }
}

class StringOrder {

    private String s;
    private int in;

    public StringOrder(String s, int in) {
        super();
        this.s = s;
        this.in = in;
    }

    public int getOrder() {
        return in;
    }

    public void reverseString() {
        this.s = new StringBuilder(this.s).reverse().toString();
    }

    @Override
    public String toString() {
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StringOrder other = (StringOrder) obj;
        if (in != other.in)
            return false;
        if (s == null) {
            if (other.s != null)
                return false;
        } else if (!s.equals(other.s))
            return false;
        return true;
    }
}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}