import java.io.File;
import java.util.*;

public class nearest_neighbor {

    static ArrayList<Integer> rotation = new ArrayList();

    public static double nearest_neighbor_algo(Points start, Points current, Vector<Points> temp)    {
        if (temp.size() > 1) {
            rotation.add(current.getIndex());
            temp.remove(current);
            double minimum = Integer.MAX_VALUE;
            int x = 0;
            for (Points p : temp) {
                if (distance(current,p) < minimum) {
                    minimum = distance(current,p);                          //Chosen minimum distance between current source point and the destionation point
                    x = temp.indexOf(p);                                    //x = Destination point
                }
            }
            return minimum + nearest_neighbor_algo(start, temp.get(x), temp);
        } else {
            rotation.add(current.getIndex());
            rotation.add(start.getIndex());
            return distance(current, start);
        }
    }

    public static double distance(Points c, Points d){
        return Math.sqrt((c.getX() - d.getX()) * (c.getX() - d.getX()) + (c.getY() - d.getY()) * (c.getY() - d.getY()));
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        File myObj = new File("att48_xy.txt");
        Scanner sc = new Scanner(myObj);
        Vector<Points> points = new Vector();
        for (int i = 0; i < 48; i++){
            points.add(new Points(sc.nextInt(), sc.nextInt(), i+1));
        }
        Vector<Points> temp = points;
        System.out.println("Total distance is : " + (int) nearest_neighbor_algo(temp.get(1),temp.get(1),temp) + " km");
        System.out.println();
        System.out.println("Route is : " + rotation);
        System.out.println();
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed);
    }
}