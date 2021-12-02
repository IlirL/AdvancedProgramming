import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class PizzaItem implements Item{
    private String type;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if(!type.equals("Standard")&&!type.equals("Pepperoni") && !type.equals("Vegetarian"))
            throw new InvalidPizzaTypeException();
        this.type = type;
    }

    @Override
    public int getPrice(){
        if(type.equals("Standard"))
            return 10;
        if(type.equals("Pepperoni"))
            return 12;

        return 8;


    }

    @Override
    public String getType() {
        return type;
    }
}

class OrderLockedException extends Exception{
    public OrderLockedException() {
        super("OrderLockedException");
    }
}
class Order {
    List<Item> items;
    List<Integer> amounts;
    boolean isLocked;

    public Order() {
        items = new ArrayList<Item>();
        amounts = new ArrayList<Integer>();
        isLocked = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if(count > 10)
            throw new ItemOutOfStockException(item);
        if(isLocked)
            throw new OrderLockedException();

        for(int i = 0; i<items.size(); i++)
            if(items.get(i).getType().equals(item.getType()))
            {
                items.set(i, item);
                amounts.set(i, count);
                return;
            }
        items.add(item);
        amounts.add(count);
    }

    public int getPrice() {
        int total_price = 0;
        for(int i = 0; i<items.size(); i++)
        {

            total_price+=items.get(i).getPrice() * amounts.get(i);

        }

        return total_price;
    }

    public void displayOrder()
    {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<items.size(); i++){
            sb.append(String.format("%3d.%-15sx%2d%5d$\n", i+1, items.get(i).getType(), amounts.get(i),
                    items.get(i).getPrice()*amounts.get(i)));
        }
        sb.append(String.format("%-22s%5d$","Total:",getPrice()));
        System.out.println(sb.toString());
    }

    public void removeItem(int idx) throws OrderLockedException, ArrayIndexOutOfBоundsException {
        if(idx >= items.size() && idx <0)
            throw new ArrayIndexOutOfBоundsException(idx);
        if(isLocked)
            throw new OrderLockedException();

        items.remove(idx);
    }

    public void lock() throws EmptyOrder {
        if(items.size() == 0)
            throw new EmptyOrder();
        isLocked = true;
    }
}

class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(Item item) {
        super("ItemOutOfStockException");
    }
}

interface Item {
    int getPrice();
    String getType();
}

class InvalidPizzaTypeException extends Exception{

    public InvalidPizzaTypeException() {
        super("InvalidPizzaTypeException");
    }
}

class InvalidExtraTypeException extends Exception{
    public InvalidExtraTypeException() {
        super("InvalidExtraTypeException");
    }
}

class ExtraItem implements  Item{
    private String type;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if(!type.equals("Coke") && !type.equals("Ketchup"))
            throw new InvalidExtraTypeException();
        this.type = type;
    }

    @Override
    public int getPrice() {
        if(type.equals("Ketchup"))
            return 3;

        return 5;
    }

    @Override
    public String getType() {
        return type;
    }
}
class EmptyOrder extends Exception{
    public EmptyOrder() {
        super("EmptyOrder");
    }
}
class ArrayIndexOutOfBоundsException extends Exception{
    public ArrayIndexOutOfBоundsException(int idx) {
        super("ArrayIndexOutOfBоundsException");
    }
}


public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}