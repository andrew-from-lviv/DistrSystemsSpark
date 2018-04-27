import org.apache.spark.graphx.lib.UDFTriangle
import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}
import org.apache.spark.{SparkConf, SparkContext}


object UDFTrianglesMain {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Count Triangles")
    val sc = new SparkContext(conf)
    val graph = GraphLoader.edgeListFile(sc, args(0), true).partitionBy(PartitionStrategy.RandomVertexCut)
    val triCounts = UDFTriangle.run(graph).vertices
    val n = triCounts.map(_._2).reduce(_ + _)/ 3 // adjust output
    println(s"N=: $n")

    //// spark-submit --class UDFTrianglesMain --master local target/AK_DB-1.0-SNAPSHOT.jar followers.txt
  }
}