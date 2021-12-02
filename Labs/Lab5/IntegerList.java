import java.util.*;
import java.util.stream.Collectors;

class IntegerList {

    ArrayList<Integer> arrayList;

    IntegerList() {
        arrayList = new ArrayList<>();
    }

    /* конструктор коj креира листа што ги содржи елементите numbers во истиот редослед во кој тие се појавуваат во низата.     */
    IntegerList(Integer... numbers) {
        arrayList = new ArrayList<>(Arrays.asList(numbers));
    }

    /* го додава елементот на соодветниот индекс. */
    /*Доколку има други елементи после таа позиција истите се поместуваат на десно за едно место
    (нивните индекси им се зголемуваат за 1).
    Доколку idx е поголемо од сегашната големина на листата ја зголемуваме листата и
    сите нови елементи ги иницијалираме на нула (освен тој на позиција idx кој го поставуваме на el).*/
    public void add(int element, int index) {
        if (index > arrayList.size()) {
            int length = index - arrayList.size();
            for (int i = 0; i < length; i++) {
                arrayList.add(0);
            }
            arrayList.add(element);
        } else {
            arrayList.add(index, element);
        }

    }

    /* Ако индексот е негативен или поголем од тековната големина на листата фрламе исклучок ArrayIndexOutOfBoundsException.*/
    public boolean validateIndex(int index) {
        if (index < 0 || index > arrayList.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return true;
    }

    public int remove(int index) {
        validateIndex(index);
        return arrayList.remove(index);
    }

    public void set(int element, int index) {
        validateIndex(index);
        arrayList.set(index, element);
    }

    public int get(int index) {
        validateIndex(index);
        return arrayList.get(index);
    }

    public int size() {
        return arrayList.size();
    }

    public int count(int element) {
        return (int) arrayList.stream().filter(integer -> integer.equals(element)).count();
    }

    public void removeDuplicates() {
        for (int i = 0; i < arrayList.size(); i++) {
            //System.out.println(arrayList.toString());
            Integer integer = arrayList.get(i);
            for (int j = 0; j < i; j++) {
                if (integer.equals(arrayList.get(j))) {
                    arrayList.remove(j);
                    i--;
                    break;
                }
            }
        }
    }

    private int validateCount(int count) {
        if (count > arrayList.size()) {
            return arrayList.size();
        }
        return count;
    }

    public int sumFirst(int count) {
        count = validateCount(count);
        /*int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += arrayList.get(i);
        }
        return sum;*/
        return arrayList.stream()
                .limit(count)
                .mapToInt(Integer::valueOf)
                .sum();

    }

    public int sumLast(int count) {
        count = validateCount(count);
        /*int size = arrayList.size();
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += arrayList.get(size - i - 1);
        }
        return sum;*/
        return arrayList.stream()
                .skip(arrayList.size() - count)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public void shiftRight(int index, int count) {
        validateIndex(index);
        int shiftIndex = (index + count) % arrayList.size();
        Integer element = arrayList.remove(index);
        arrayList.add(shiftIndex, element);
    }

    public void shiftLeft(int index, int count) {
        validateIndex(index);
        int shiftIndex = (index - count) % arrayList.size();
        if (shiftIndex < 0) {
            shiftIndex = arrayList.size() + shiftIndex;
        }
        Integer element = arrayList.remove(index);
        arrayList.add(shiftIndex, element);
    }

    public IntegerList addValue(int value) {
        /*IntegerList integerList = new IntegerList();
        for (int i = 0; i < this.arrayList.size(); i++) {
            integerList.add(this.arrayList.get(i) + value, i);
        }
        return integerList;*/
        return new IntegerList(
                arrayList.stream()
                        .map(objectInteger -> new Integer(objectInteger + value))
                        .collect(Collectors.toCollection(ArrayList::new)) //.collect(Collectors.toList()))
        );
    }

    IntegerList(List<Integer> list) {
        this.arrayList = (ArrayList<Integer>) list;
    }
}

/*
Со користење на ArrayList или LinkedList сакаме да развиеме класа за работа со листи од цели броеви IntegerList.
Листата ги има следните вообичаени методи:
 
IntegerList() - конструктор кој креира празна листа.
 
 
 
remove(int idx):int - го отстранува елементот на дадена позиција од листата и истиот го враќа. Доколку после таа позиција има други елементи истите се поместуваат во лево (нивните индекси се намалуваат за 1).
set(int el, int idx) - го поставува елементот на соодветната позиција.
get(int idx):int - го враќа елементот на соодветната позиција.
size():int - го враќа бројот на елементи во листата.
Освен овие методи IntegerList треба да нуди и неколку методи згодни за работа со цели броеви:
 
count(int el):int - го враќа бројот на појавувања на соодветниот елемент во листата.
removeDuplicates() - врши отстранување на дупликат елементите од листата. Доколку некој елемент се сретнува повеќе пати во листата ја оставаме само последната копија од него. Пр: 1,2,4,3,4,5. -> removeDuplicates() -> 1,2,3,4,5
sumFirst(int k):int - ја дава сумата на првите k елементи.
sumLast(int k):int - ја дава сумата на последните k елементи.
shiftRight(int idx, int k) - го поместува елементот на позиција idx за k места во десно. При поместувањето листата ја третираме како да е кружна. Пр: list = [1,2,3,4]; list.shiftLeft(1,2); list = [1,3,4,2] - (листата е нула индексирана така да индексот 1 всушност се однесува на елементот 2 кој го поместуваме две места во десно) list = [1,2,3,4]; list.shiftLeft(2, 3); list = [1,3,2,4] - елементот 3 го поместуваме 3 места во десно. По две поместувања стигнуваме до крајот на листата и потоа продолжуваме да итерираме од почетокот на листата уште едно место и овде го сместуваме.
shiftLeft(int idx , int k) - аналогно на shiftRight.
addValue(int value):IntegerList - враќа нова листа каде елементите се добиваат од оригиналната листа со додавање на value на секој елемент. Пр: list = [1,4,3]; addValue(5) -> [6,9,8]
Забелешка која важи за сите методи освен add:
 
 */
 class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test standard methods
            int subtest = jin.nextInt();
            if (subtest == 0) {
                IntegerList list = new IntegerList();
                while (true) {
                    int num = jin.nextInt();
                    if (num == 0) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if (num == 1) {
                        list.remove(jin.nextInt());
                    }
                    if (num == 2) {
                        print(list);
                    }
                    if (num == 3) {
                        break;
                    }
                }
            }
            if (subtest == 1) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for (int i = 0; i < n; ++i) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if (k == 1) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if (num == 1) {
                    list.removeDuplicates();
                }
                if (num == 2) {
                    print(list.addValue(jin.nextInt()));
                }
                if (num == 3) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
        if (k == 2) { //test shiftRight, shiftLeft, sumFirst, sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if (num == 1) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if (num == 2) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if (num == 3) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if (il.size() == 0) System.out.print("EMPTY");
        for (int i = 0; i < il.size(); ++i) {
            if (i > 0) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}