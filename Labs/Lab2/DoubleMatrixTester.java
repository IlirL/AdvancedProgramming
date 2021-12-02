import java.text.DecimalFormat;
import java.util.Arrays;
import java.io.InputStream;
import java.util.Scanner;
import java.io.ByteArrayInputStream;


class InvalidColumnNumberException extends Exception {
    public InvalidColumnNumberException(){
        super("Invalid column number");
    }
}

class InvalidRowNumberException extends Exception {
    public InvalidRowNumberException(){
        super("Invalid row number");
    }
}

class InsufficientElementsException extends Exception {
    public InsufficientElementsException(){
        super("Insufficient number of elements");
    }
}


class DoubleMatrix {
    private final int columns;
    private final int rows;
    private final double[][] matrix;
    private final double[] matrixArr;

    public DoubleMatrix(double a[], int m, int n) throws InsufficientElementsException{
        if (a.length < m*n)
            throw new InsufficientElementsException();
        rows = m;
        columns = n;
        matrix = new double[rows][columns];
        matrixArr = Arrays.copyOfRange(a,a.length-rows*columns,a.length);    //kopiraj gi poslednite m*n broevi

        int counter = 0;
        for (int i=rows-1; i>=0; i--){
            for (int j=columns-1; j>=0; j--){
                matrix[i][j] = matrixArr[matrixArr.length-1-counter];
                counter++;
            }
        }
    }

    public String getDimensions(){
        return "[" + rows + " x " + columns + "]";
    }

    public int rows(){
        return rows;
    }

    public int columns(){
        return columns;
    }


    public double maxElementAtRow(int row) throws InvalidRowNumberException{
        row--;       //vo zadacata veli deka row e vo rang [1,m]
        if (row < 0 || row >= rows)
            throw new InvalidRowNumberException();
        double maxElement = matrix[row][0];   // ne moze maxElement = 0 bidejki rabotime i so negativni broevi
        for (int i=1; i<columns; i++){
            if (matrix[row][i] > maxElement)
                maxElement = matrix[row][i];
        }
        return maxElement;
    }

    public double maxElementAtColumn(int column) throws InvalidColumnNumberException{
        column--;    //vo zadacata veli deka column e vo rang [1,n]
        if (column < 0 || column >= columns)
            throw new InvalidColumnNumberException();
        double maxElement = matrix[0][column];         // ne moze double maxElement = 0; bidejki rabotime i so negativni br
        for (int i=1; i<rows; i++){
            if (matrix[i][column] > maxElement)
                maxElement = matrix[i][column];
        }
        return maxElement;
    }


    public double sum(){
        double sum = 0;
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public double[] toSortedArray(){
        // pomnozi ja celata niza so -1, sortiraj ja vo rastecki redosled, pomnozi ja pak so -1
        // Arrays.sort(a, Collections.reverseOrder()); ne raboti so primitivni tipovi kako int, double..

        double[] sorted =  Arrays.copyOf(matrixArr,matrixArr.length);
        for (int i=0; i<sorted.length; i++){
            sorted[i] *= -1;
        }
        Arrays.sort(sorted);
        for (int i=0; i<sorted.length; i++){
            sorted[i] *= -1;
        }
        return sorted;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(matrix);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DoubleMatrix other = (DoubleMatrix)obj;
        if (!Arrays.deepEquals(matrix, other.matrix))
            return false;
        return true;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.00");
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns-1; j++){
                sb.append(df.format(matrix[i][j])).append("\t");
            }
            sb.append(df.format(matrix[i][columns-1]));
            if (i != rows-1)
                sb.append("\n");
        }
        return sb.toString();
    }
}


class MatrixReader {
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException {
        Scanner sc = new Scanner(input);
        int m = sc.nextInt();
        int n = sc.nextInt();
        double[] arr = new double[m*n];
        /* for (int i=0; i<m; i++){
            arr[i] = sc.nextDouble();
        }*/
        int i=0;
        while (sc.hasNextDouble()){
            arr[i++] = sc.nextDouble();
        }
        return new DoubleMatrix(arr, m, n);
    }
}


public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode()&&f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}