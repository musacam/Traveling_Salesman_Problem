import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import static java.lang.Integer.MAX_VALUE;

public class divide_and_conquer
{

    /*-------------------------TANIMLAMALAR--------------------------*/

    static ArrayList<Double> uzakliklar = new ArrayList();
    static ArrayList<Integer> xpoint = new ArrayList();
    static ArrayList<Integer> ypoint = new ArrayList();

    /*-------------------------TANIMLAMALAR--------------------------*/

    public static int[] x_coordinates() {

        int x,y;
        int[] arr_x = new int[48];
        int[] arr_y = new int[48];

        try {
            File myObj = new File("att48_xy.txt");
            Scanner myReader = new Scanner(myObj);



            int k=0;
            while (myReader.hasNext() && k!=48) {
                for(int j=0; j<1; j++) {
                    x = myReader.nextInt(); //1. k�s�m� x e  al�yor sonra next ama github bunu 1. �ehir olarak al�yor
                    y = myReader.nextInt();
                    arr_x[k] = x;
                    arr_y[k] = y;
                }
                myReader.nextLine();
                k++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return arr_x;


    }
    public static int[] y_coordinates() {

        int x,y;
        int[] arr_x = new int[48];
        int[] arr_y = new int[48];

        try {
            File myObj = new File("att48_xy.txt");
            Scanner myReader = new Scanner(myObj);



            int k=0;
            while (myReader.hasNext() && k!=48) {
                for(int j=0; j<1; j++) {
                    x = myReader.nextInt();
                    y = myReader.nextInt();
                    arr_x[k] = x;
                    arr_y[k] = y;
                }
                myReader.nextLine();
                k++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return arr_y;
    }
    public static double[][] distances2() {

        int[] arr_x = x_coordinates();
        int[] arr_y= y_coordinates();
        double[][] mesafe = new double[48][48];
        for(int i = 0; i<48; i++) {
            for(int j=0; j<48; j++) {
                if(i<j) {
                    mesafe[i][j] = (Math.sqrt((arr_x[i] - arr_x[j]) * (arr_x[i] - arr_x[j]) + (arr_y[i] - arr_y[j]) * (arr_y[i] - arr_y[j])));
                }
                else
                    mesafe[i][j] = Double.MAX_VALUE;
            }
        }
        return mesafe;

    }
    public static double getMinValue(double[][] numbers) {

        int ilk=0;
        int iki=0;
        double minValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {
            for (int i = 0; i < numbers[j].length; i++) {
                if (numbers[j][i] < minValue ) {
                    minValue = numbers[j][i];
                    ilk = j;
                    iki = i;
                }
            }
        }
        xpoint.add(ilk);
        ypoint.add(iki);
        //    numbers[ilk][iki] = Double.MAX_VALUE;
        return minValue;
    }
    public static double fark(Vector<Integer> number){
        int min = MAX_VALUE;
        int max = 0;
        for (int i = 0; i < number.size(); i++) {
            if(number.get(i) < min){
                min = number.get(i);
            }
            if(number.get(i) > max){
                max = number.get(i);
            }
        }
        return max-min;
    }

    public static Vector<Vector<Points>> splitting(Vector<Points> allpoints){
        Vector<Integer> xPoints = new Vector<>();
        Vector<Integer> xPoints2 = new Vector<>();
        Vector<Integer> yPoints = new Vector<>();
        Vector<Integer> yPoints2 = new Vector<>();
        Vector<Points> firsthalf = new Vector<>();
        Vector<Points> secondhalf = new Vector<>();
        Vector<Points> joined = new Vector<>();
        Vector<Vector<Points>> new_vector = new Vector<>();
        int index = 0;
        int smallestx = allpoints.get(0).getX();
        int smallesty = allpoints.get(0).getY();
        int length = allpoints.size();
        for (Points allpoint : allpoints) {
            xPoints.add(allpoint.getX());
            yPoints.add(allpoint.getY());
        }
        double farkx = fark(xPoints);
        double farky = fark(yPoints);
        int tempy = 0;
        int tempx = 0;
        if(farkx>farky){
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < allpoints.size(); j++) {
                    if(allpoints.size() == 1){
                        index = j;
                    }
                    else if (allpoints.get(j).getX() <= smallestx){
                        smallestx = allpoints.get(j).getX();
                        tempy = allpoints.get(j).getY();
                        index = j;
                    }
                }
                xPoints2.add(smallestx);
                yPoints2.add(tempy);
                joined.add(allpoints.get(index));
                allpoints.remove(index);
                smallestx = xPoints.firstElement();
            }
        }
        else {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < allpoints.size(); j++) {
                    if (allpoints.size() == 1) {
                        index = j;
                    } else if (allpoints.get(j).getY() <= smallesty) {
                        index = j;
                        smallesty = allpoints.get(j).getY();
                        tempx = allpoints.get(j).getX();
                    }
                }
                xPoints2.add(tempx);
                yPoints2.add(smallesty);
                joined.add(allpoints.get(index));
                allpoints.remove(index);
                smallesty = yPoints.firstElement();
            }
        }

        for (int i = 0; i < joined.size()/2; i++) {
            firsthalf.add(joined.get(i));
        }

        for (int i = joined.size()/2; i < joined.size(); i++) {
            secondhalf.add(joined.get(i));
        }
        new_vector.add(firsthalf);
        new_vector.add(secondhalf);
        return new_vector;

    }

    public static Vector<Points> combineTwoRoutes(Vector<Points> points1, Vector<Points> points2) {
        Vector<Points> new_new_vector = new Vector<>();
        new_new_vector.setSize(points1.size() + points2.size());
        new_new_vector = points1;
        new_new_vector.addAll(points2);
        return new_new_vector;
    }

    public static Vector<Points> conquer(Vector<Points> all, int x){
        if (all.size()>x){
            Vector<Vector<Points>> merge = splitting(all);
            Vector<Points> half1 = merge.firstElement();        //Her iki parçaya ayırma işlemi için vektörlerden oluşan vektörün içindeki ilk element yani ilk parça
            Vector<Points> half2 = merge.lastElement();         //Her iki parçaya ayırma işlemi için vektörlerden oluşan vektörün içindeki ikinci element yani ikinci parça
            return combineTwoRoutes(conquer(half1, x), conquer(half2,x));
        }
        else{
            return all;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        File file = new File("att48_xy.txt");
        Scanner sc = new Scanner(file);
        Vector<Points> points = new Vector<>();
        for (int i = 0; i < 48; i++){
            points.add(new Points(sc.nextInt(), sc.nextInt(), i+1));
        }

        Vector<Points> deneme = new Vector<>();
        for (int i = 0; i < 48; i++) {
            deneme.add(points.get(i));
        }

        //Method çağırıldı.
        Vector<Points> last;
        last = conquer(deneme,3);

        //Rotayı yazdırma yeri
        for (int i = 0; i < 48; i++) {
            System.out.print(last.get(i).getIndex() + "->");
        }
        System.out.println(last.get(0).getIndex());

        //Son iki uzaklığı hesaplama
        double[][] graph3;
        graph3 = distances2();
        for (int i = 0; i < 1128; i++) {
            uzakliklar.add(getMinValue(graph3));
        }
        double totalroute = 0;
        int i=0;
        while(i<47) {
            if (last.get(i).getIndex() < last.get(i + 1).getIndex()) {
                totalroute += graph3[last.get(i).getIndex() - 1][last.get(i + 1).getIndex() - 1];
            } else {
                totalroute += graph3[last.get(i + 1).getIndex() - 1][last.get(i).getIndex() - 1];
            }
            i++;
        }
        System.out.println();
        System.out.println("Total route is : " + (int)totalroute + " km.");
        System.out.println();
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed);
    }
}
