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

        // count 计数
        System.out.println("refund info number: " + refundInfos.count());

        // filter & map 过滤和映射转换
        JavaRDD<RefundInfo> filtered = refundInfos.filter(refundInfo -> refundInfo.getRealPrice() >= 10);
        System.out.println("realPrice > 10: " + filtered.collect().stream().map(RefundInfo::getOrderNo).collect(Collectors.joining(",")));

        // 最好在 RDD 里直接做完过滤和映射转换，在 collect 中进行收集和展示
        JavaRDD<String> filterAndMapped = refundInfos.filter(refundInfo -> refundInfo.getRealPrice() >= 10).map(RefundInfo::getOrderNo);
        System.out.println("realPrice > 10 222: " + String.join(",", filterAndMapped.collect()));

        // groupBy 分组
        JavaPairRDD<String, Iterable<RefundInfo>> grouped = refundInfos.groupBy(RefundInfo::getType);

        // 持久化，避免前面多次 RDD 重复解析和转换
        grouped.persist(StorageLevel.MEMORY_ONLY());

        // 对 Map 的每个 value 进行转换 （分组的退款总金额）
        JavaPairRDD<String, Double> groupedRealPaySumRDD = grouped.mapValues(info -> StreamSupport.stream(info.spliterator(), false).mapToDouble(RefundInfo::getRealPrice).sum());

        System.out.println("groupedRealPaySum: " + groupedRealPaySumRDD.collectAsMap());

        // 对 Map 的每个 value 进行转换 （分组的退款计数）
        JavaPairRDD<String, Long> groupedNumberRDD = grouped.mapValues(info -> StreamSupport.stream(info.spliterator(), false).count());

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
