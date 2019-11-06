import java.awt.geom.Line2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class CreateDataSet{

  //DRAW KMEANS TESTCASE
  public static double[][] kmeansTest(int k, int range, int num){

    double[][] pivot = new double[k][2];
    for(int i=0;i<k;i++){
      Random seedRandom = new Random(System.nanoTime());
      pivot[i][0] = seedRandom.nextDouble()+seedRandom.nextInt(range);
      pivot[i][1] = seedRandom.nextDouble()+seedRandom.nextInt(range);
    }
    double[][] res = new double[num][2];
    for(int i=0;i<k;i++){
      res[i][0] = pivot[i][0];
      res[i][1] = pivot[i][1];
    }
    //https://blog.csdn.net/sinat_31124611/article/details/80095849
    for(int i = k;i<num;i++){
      Random rand = new Random();
      int theta = rand.nextInt(361);
      int R = rand.nextInt(40);
      double r = Math.sqrt(rand.nextDouble())*R;
      res[i][0] = r*sin(theta) + pivot[rand.nextInt(k)][0];
      res[i][1] = r*cos(theta) + pivot[rand.nextInt(k)][1];
    }
//    int j = k;
//    for(int i=0;i<k;i++){
//      int count = 0;
//      while(count<90){
//        Random seedRandom = new Random(System.nanoTime());
//        int theta = seedRandom.nextInt(361);
//        int R = 70;
//        double r = Math.sqrt(seedRandom.nextDouble())*R;
//        res[j][0] = r*sin(theta) + pivot[i][0];
//        res[j++][1] = r*cos(theta) + pivot[i][1];
//        count++;
//      }


    return res;

  }
  //DRAW LINEAR TESTCASE
  public static double[][] linearRegTest(double startX,double startY, double endX,
                                         double endY, int range,
                                         int num){
    double [][] res = new double[num][2];
    int k = 0;
    Line2D t = new Line2D.Double(startX,startY,endX,endY);
    for(int i=0;i<num;i++){
      Random rand = new Random();
      double x = rand.nextDouble()+rand.nextInt(range);
      rand = new Random();
      double y = rand.nextDouble()+rand.nextInt(range);
      if(t.ptLineDist(x,y)<40){
        res[i][0] = x;
        res[i][1] = y;
        k++;
      }
    }
    double[][] p = new double[k][2];
    for(int i = 0;i<k;i++){
      if(res[i][0]!=0){
        p[i][0] = res[i][0];
        p[i][1] = res[i][1];
      }
    }


    return p;
  }
  //DRAW RANDOM TESTCASE
  public static double[][] randomTest(int range,int num){

    double[][] res = new double[num][2];
    for(int i=0; i<num; i++) {
      Random rand = new Random();
      res[i][0] = rand.nextInt(range+1);
      rand = new Random(System.nanoTime());
      res[i][1] = rand.nextInt(range+1);
    }
    return res;
  }

  public static void writeFileContext(double[][] strings, String path) throws Exception {
    File file = new File(path);
    if (!file.isFile()) {
      file.createNewFile();
    }
    BufferedWriter writer = new BufferedWriter(new FileWriter(path));
    for (double[] l:strings){
      writer.write(String .format("%.2f",l[0])+" "+String .format("%.2f",l[1])+ "\r\n");
    }
    writer.close();
  }


  public static void main(String[] args) throws Exception {
    double[][] a= kmeansTest(4,400,100);
    writeFileContext(a,"src\\data\\1.txt");
  }

}