import java.util.*;
import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static java.lang.Double.MAX_VALUE;

public class greedy_algorithm {

    /*-------------------------TANIMLAMALAR--------------------------*/

    static ArrayList<Double> uzakliklar = new ArrayList();
    static ArrayList<Integer> xpoint = new ArrayList();
    static ArrayList<Integer> ypoint = new ArrayList();
    static ArrayList<LinkedList<Integer>> points = new ArrayList<>();

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
                    mesafe[i][j] = MAX_VALUE;
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
        numbers[ilk][iki] = MAX_VALUE;
        return minValue;
    }
    public static void giveCityIndex(ArrayList<LinkedList<Integer>> a) {
        for(int i=0; i<a.size(); i++) {
            for(int j=0; j<a.get(i).size(); j++)
                System.out.print((a.get(i).get(j)+1) + " ");

        }
    }
    public static LinkedList<Integer> reverseList(LinkedList<Integer> p) {  //head alacak
        LinkedList<Integer> rev= new LinkedList<>();
        for(int i = 0; i<p.size(); i++) {
            rev.add(p.get(p.size()-1-i));
        }

        return rev;
    }

    public static void greedy() {

        int[] kacincicizgi = new int[48];       //Ka��nc� s�radaki i�lem
        int cizgicount = 1;                     //Yukar�daki arraye atanacak olan integer
        int[] kackomsusuvar = new int[48];      //Ka� �ehirle ba� yapm��
        int iterator = 0;                       //Uzakl�klar ve xpoint ypoint arraylerinden s�rekli bir yana kayma integer�
        int totalcizgi = 0;                     //48 �ehir g�n�n sonunda 48 �izgi olu�turur. Onun count'u
        int ilk;                                //Her iterationdaki xpointteki say�
        int ikinci;                             //Her iterationdaki ypointteki say�
        double totalroute = 0;                  //Toplam mesafe
        double minimum;                         //Her iterationda uzakl�klardan ald��� mesafe
        double[][] dist = new double[48][48];
        dist = distances2();

        ArrayList<Integer> lasttwo = new ArrayList<>();     //46 tane �ehir iki�erli ba�l� iki tanesi ba�lang�� ve biti� o ikisini bulduktan sonra ekledi�imiz yer.

        while (totalcizgi < 48) {
            minimum = uzakliklar.get(iterator);
            ilk = xpoint.get(iterator);
            ikinci = ypoint.get(iterator);
            int a = kacincicizgi[ilk];
            int b = kacincicizgi[ikinci];

            if (kackomsusuvar[ilk] == 0 && kackomsusuvar[ikinci] == 0) {
                LinkedList<Integer> p= new LinkedList<>();
                p.add(ilk);
                p.add(ikinci);
                kacincicizgi[ilk] = cizgicount;
                kacincicizgi[ikinci] = cizgicount;
                kackomsusuvar[ilk]++;
                kackomsusuvar[ikinci]++;
                totalroute += minimum;
                cizgicount++;
                iterator++;
                totalcizgi++;
                points.add(p);
            }

            else if (kackomsusuvar[ilk] < 2 && kackomsusuvar[ikinci] < 2) {
                if (kackomsusuvar[ikinci] == 0 && kackomsusuvar[ilk] == 1) {
                    for (int i = 0; i < 48; i++) {
                        if (kacincicizgi[i] == a) {
                            kacincicizgi[i] = cizgicount;
                        }
                    }
                    for(int i=0; i<points.size(); i++) {
                        if(points.get(i).indexOf(ilk)==0)
                            points.get(i).addFirst(ikinci);
                        else if(points.get(i).indexOf(ilk)==points.get(i).size()-1)
                            points.get(i).add(ikinci);
                    }
                    kacincicizgi[ikinci] = cizgicount;
                    totalroute += minimum;
                    kackomsusuvar[ilk]++;
                    kackomsusuvar[ikinci]++;
                    cizgicount++;
                    iterator++;
                    totalcizgi++;
                }

                else if(kackomsusuvar[ilk] == 0 && kackomsusuvar[ikinci] == 1) {
                    for (int i = 0; i < 48; i++) {
                        if (kacincicizgi[i] == b) {
                            kacincicizgi[i] = cizgicount;
                        }
                    }
                    for(int i=0; i<points.size(); i++) {
                        if(points.get(i).indexOf(ikinci)==0)
                            points.get(i).addFirst(ilk);
                        if(points.get(i).indexOf(ikinci)==points.get(i).size()-1)
                            points.get(i).add(ilk);
                    }
                    kacincicizgi[ilk] = cizgicount;
                    totalroute += minimum;
                    kackomsusuvar[ilk]++;
                    kackomsusuvar[ikinci]++;
                    cizgicount++;
                    iterator++;
                    totalcizgi++;
                }

                else if(kackomsusuvar[ilk] == 1 && kackomsusuvar[ikinci] == 1) {
                    if (a == b) {
                        if (totalcizgi == 47) {
                            points.get(0).add(points.get(0).getFirst());
                            for (int i = 0; i < 48; i++) {
                                if (kackomsusuvar[i] == 1) {
                                    lasttwo.add(i);
                                }
                            }
                            totalroute += minimum;
                            break;     //İşimiz bittiği için hiçbir şeyi updatelemeden tüm whiledan çıkabiliriz.
                        } else {
                            iterator++;
                        }
                    }
                    else{
                        for (int i = 0; i < 48; i++) {
                            if (kacincicizgi[i] == b || kacincicizgi[i] == a) {
                                kacincicizgi[i] = cizgicount;
                            }
                        }
                        for(int i=0; i<points.size(); i++) {
                            for(int j=0; j<points.size(); j++) {
                                if(points.get(i).indexOf(ilk)==0 && points.get(j).indexOf(ikinci)==0) {  //her ikisi ba�ta
                                    LinkedList<Integer> x =reverseList(points.get(i));
                                    points.get(i).clear();
                                    points.get(i).addAll(x);
                                    points.get(i).addAll(points.get(j));
                                    points.remove(points.get(j));
                                }
                                else if(points.get(i).indexOf(ilk)==0 && points.get(j).indexOf(ikinci)==points.get(j).size()-1) {  //1.ba�ta 2.sonda
                                    points.get(j).addAll(points.get(i));
                                    points.remove(points.get(i));
                                }
                                else if(points.get(i).indexOf(ilk)==points.get(i).size()-1 && points.get(j).indexOf(ikinci)==0) {  //1.sonda 2.ba�ta
                                    points.get(i).addAll(points.get(j));
                                    points.remove(points.get(j));
                                }
                                else if(points.get(i).indexOf(ilk)==points.get(i).size()-1 && points.get(j).indexOf(ikinci)==points.get(j).size()-1) {  //1.sonda 2.sonda
                                    LinkedList<Integer> x =reverseList(points.get(j));
                                    points.get(j).clear();
                                    points.get(j).addAll(x);
                                    points.get(i).addAll(points.get(j));
                                    points.remove(points.get(j));
                                }
                            }
                        }
                        totalroute += minimum;
                        kackomsusuvar[ilk]++;
                        kackomsusuvar[ikinci]++;
                        cizgicount++;
                        iterator++;
                        totalcizgi++;
                    }
                }
            }
            else {
                iterator++;
            }
        }
        System.out.println("Total route is : " + (int)totalroute + " km.");
    }

    public static void main(String[] args) {

        double[][] graph3;
        graph3 = distances2();
        for (int i = 0; i < 1128; i++) {
            uzakliklar.add(getMinValue(graph3));
        }
        greedy();
        giveCityIndex(points);

    }
}


