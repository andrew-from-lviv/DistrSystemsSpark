import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}
import org.apache.spark.{SparkConf, SparkContext}

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Count Triangles")
    val sc = new SparkContext(conf)
    val graph = GraphLoader.edgeListFile(sc, args(0), true).partitionBy(PartitionStrategy.RandomVertexCut)
    val triCounts = graph.triangleCount().vertices
    val n = triCounts.map(_._2).reduce(_ + _)/ 3 // adjust output

    println(s"N=: $n")

    // spark-submit --class Main --master local target/AK_DB-1.0-SNAPSHOT.jar followers.txt

  }
}