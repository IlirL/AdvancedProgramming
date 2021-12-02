package Lab2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

class ObjectCanNotBeMovedException extends Exception {

    public ObjectCanNotBeMovedException(int pointX, int y) {
        super(String.format("Point (%d,%d) is out of bounds", pointX, y));

    }
}

class MovableObjectNotFittableException extends Exception {

    public MovableObjectNotFittableException(int x, int y, int r) {
        super(String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection", x, y, r));
    }
}

interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;

    void moveDown() throws ObjectCanNotBeMovedException;

    void moveLeft() throws ObjectCanNotBeMovedException;

    void moveRight() throws ObjectCanNotBeMovedException;

    int getCurrentXPosition();

    int getCurrentYPosition();

    String getTypeInstance();

    Movable moveInDirection(String direction) throws ObjectCanNotBeMovedException;

    int getRadius();
}

class MovablePoint implements Movable{
    private int pointX;
    private int pointY;
    private int xSpeed;
    private int ySpeed;

    public MovablePoint(int pointX, int pointY, int xSpeed, int ySpeed) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)", this.pointX, this.pointY);
    }


    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if(pointY+ySpeed > MovablesCollection.x_MAX)
            throw new ObjectCanNotBeMovedException(pointX, pointY + ySpeed);
        pointY = pointY + ySpeed;

    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (pointY - ySpeed < 0)
            throw new ObjectCanNotBeMovedException(pointX, pointY - ySpeed);
        pointY = pointY - ySpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (pointX - xSpeed < 0)
            throw new ObjectCanNotBeMovedException(pointX - xSpeed, pointY);
        pointX = pointX - xSpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (pointX + xSpeed > MovablesCollection.x_MAX)
            throw new ObjectCanNotBeMovedException(pointX + xSpeed, pointY);
        pointX = pointX + xSpeed;
    }

    @Override
    public int getCurrentXPosition() {
        return pointX;
    }

    @Override
    public int getCurrentYPosition() {
        return pointY;
    }

    @Override
    public String getTypeInstance() {
        return "MovablePoint";
    }

    @Override
    public Movable moveInDirection(String direction) throws ObjectCanNotBeMovedException {
        if (direction.equals("UP")) {
            moveUp();
        } else if (direction.equals("DOWN")) {
            moveDown();
        } else if (direction.equals("LEFT")) {
            moveLeft();
        } else if (direction.equals("RIGHT")) {
            moveRight();
        }
        return this;
    }

    @Override
    public int getRadius() {
        return 0;
    }
}

class MovableCircle implements Movable{
    private int radius;
    private MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }
    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d\n", this.center.getCurrentXPosition(), this.center.getCurrentYPosition(), radius);
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        center.moveUp();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        center.moveDown();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        center.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        center.moveRight();
    }

    @Override
    public int getCurrentXPosition() {
        return center.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return center.getCurrentYPosition();
    }

    public int getRadius() {
        return radius;
    }
    @Override
    public String getTypeInstance() {
        return "MovableCircle";
    }
    @Override
    public Movable moveInDirection(String direction) throws ObjectCanNotBeMovedException {
        if (direction.equals("UP")) {
            moveUp();
        } else if (direction.equals("DOWN")) {
            moveDown();
        } else if (direction.equals("LEFT")) {
            moveLeft();
        } else if (direction.equals("RIGHT")) {
            moveRight();
        }
        return this;
    }

}

class MovablesCollection{
    private List<Movable> movables;
    public static int x_MAX = 0;
    public static int y_MAX = 0;

    public MovablesCollection(int x_MAX, int y_MAX) {
        this.x_MAX = x_MAX;
        this.y_MAX = y_MAX;
        this.movables = new ArrayList<>();
    }

    public static void setX_MAX(int x_MAX) {
        MovablesCollection.x_MAX = x_MAX;
    }

    public static void setY_MAX(int y_MAX) {
        MovablesCollection.y_MAX = y_MAX;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException
    {
        if(m.getTypeInstance().equals("MovablePoint")){
            if(m.getCurrentXPosition()>=0 && m.getCurrentXPosition() <=x_MAX
            && m.getCurrentYPosition() >=0 && m.getCurrentYPosition()<=y_MAX)
            {
                movables.add(m);
            }else  throw new MovableObjectNotFittableException(m.getCurrentXPosition(),m.getCurrentYPosition(),0);

        }else if(m.getTypeInstance().equals("MovableCircle")){
            if(m.getCurrentXPosition() - m.getRadius() >=0 && m.getCurrentXPosition() + m.getRadius() <= x_MAX
                    && m.getCurrentYPosition() - m.getRadius() >=0 && m.getCurrentYPosition() + m.getRadius() <= y_MAX){
                movables.add(m);
        }else throw new MovableObjectNotFittableException(m.getCurrentXPosition(),m.getCurrentYPosition(),m.getRadius());
    }


}

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

public class CirclesTest {

    public static void main(String[] args) {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) { //point
                collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
            }

        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());


    }


}

