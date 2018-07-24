package spark.locator

import org.apache.spark.sql.SparkSession

object LocatorApp extends App {
  val sparkSession = SparkSession.builder.master("local[*]").appName("Locator").getOrCreate()
  sys.addShutdownHook(sparkSession.stop)

  import AreaOfInterest._
  val areasOfInterest = sparkSession
    .read
    .option("header", true)
    .schema(areaOfInterestStructType)
    .csv("./data/areas_of_interest.txt")
    .as[AreaOfInterest]
    .collect
    .toSet

  import Location._
  val locations = sparkSession
    .readStream
    .option("header", true)
    .schema(locationStructType)
    .csv("./data/person")
    .as[Location]

}