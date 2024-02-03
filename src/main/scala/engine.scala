//> using 3.3.1 

package easy

import java.nio.file.{Paths,Files, Path}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import java.util
import collection.JavaConverters._


case class SparkCredentialsError(message : String) extends Exception(message) :
   override def fillInStackTrace(): Throwable =
      this   

class Config :
/* 
   Contains all the configuration applied through the yaml file 
   and control over the credential file
 */   

   def existsPath(filePath: String): String = 
      val path = Paths.get(filePath)
      if Files.exists(path) then path.toString()
      else
         val message = s"spark path file: $path does not exits"
         throw new SparkCredentialsError(message)

   def checkCreds(creds : String) : String = 
      /*
         Checks if a spark credentials is there and return a path
         Args:
            creds
      */
      if sys.env.contains("SPARK_CREDENTIALS") then 
         val credentials = sys.env.get("SPARK_CREDENTIALS").get
         existsPath(credentials)
      else
         existsPath(creds)

   def sparkFromMap(mapConfig: Map[String, Any], name: String = "" ): SparkSession = 

      var spark: SparkSession.Builder = null

      if mapConfig.contains("spark") then 
         val conf = mapConfig("spark").asInstanceOf[util.LinkedHashMap[String, Any]].asScala.toMap
         for (propName, propValue) <- conf do
            if propName != "appName" then
               if spark == null then
                  spark = SparkSession.builder.config(propName, propValue.toString())
               else
                  spark.config(propName, propValue.toString())
         if name.isEmpty() then
            spark.appName(conf("appName").toString())
         else 
            spark.appName(name)
      
      else 
         println(s"config $mapConfig must start with spark: ")
      
      spark.getOrCreate

   def sparkFromConf(yamlFile: String, name: String): SparkSession = 

      val configData = readYamFile(yamlFile)
      println(configData)

      sparkFromMap(mapConfig = configData, name = name)
      


class  SparkEngine(name: String = "", sparkCreds: Option[String] = None ) extends Config:

   val spark = sparkFromConf(yamlFile = checkCreds(sparkCreds.get), name = name)

   def query(sqlText: String) : DataFrame = spark.sql(sqlText = sqlText)
   

