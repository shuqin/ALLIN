package zzz.spark;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BasicSpark {

  public static void main(String[] args) {

    JavaSparkContext sc = buildSparkContext();
    JavaRDD<String> rdd = sc.hadoopFile("/Users/shuqin/Downloads/refund.csv", TextInputFormat.class,
        LongWritable.class, Text.class).map(pair -> new String(pair._2.getBytes(), 0, pair._2.getLength(), "GBK"));

    // 读取并解析数据，转换为容易理解的对象
    JavaRDD<RefundInfo> refundInfos = rdd.map(BasicSpark::parseLine).map(RefundInfo::from);

    // 持久化，避免前面多次 RDD 重复解析和转换
    refundInfos.persist(StorageLevel.MEMORY_ONLY());

    System.out.println("refund info number: " + refundInfos.count());

    JavaRDD<RefundInfo> filtered = refundInfos.filter(refundInfo -> refundInfo.getRealPrice() >=10 );
    System.out.println("realPrice > 10: " + filtered.collect().stream().map(RefundInfo::getOrderNo).collect(Collectors.joining()));

    JavaPairRDD<String, Iterable<RefundInfo>> grouped = refundInfos.groupBy(RefundInfo::getType);

    JavaPairRDD<String, Double> groupedRealPaySumRDD = grouped.mapValues(info -> StreamSupport.stream(info.spliterator(),false).mapToDouble(RefundInfo::getRealPrice).sum());

    System.out.println("groupedRealPaySum: " + groupedRealPaySumRDD.collectAsMap());

    JavaPairRDD<String, Long> groupedNumberRDD = grouped.mapValues(info -> StreamSupport.stream(info.spliterator(),false).count());

    System.out.println("groupedNumber: " + groupedNumberRDD.collectAsMap());
  }


  public static JavaSparkContext buildSparkContext() {
    SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("learningSparkInJava")
                                         .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
    return new JavaSparkContext(sparkConf);
  }

  public static String[] parseLine(String line) {
    try {
      CSVReader reader = new CSVReader(new StringReader(line));
      return reader.readNext();
    } catch (IOException e) {
      return new String[0];
    }
  }
}
