//> using 3.3.1

package easy

import scala.io.Source
import org.yaml.snakeyaml.Yaml
import java.util
import java.io.File
import collection.JavaConverters._
import scopt.OParser


/* 
Lire un fichier yaml 

 */
def readYamFile(yamlFile: String) : Map[String, Any] = 
   val yamlStr = Source.fromFile(File(yamlFile)).getLines().mkString("\n") 
   val configData = new Yaml().load(yamlStr)
                  .asInstanceOf[util.Map[String, Any]].asScala.toMap
   configData

def defaultQuery : String = 
   println("This is not recognized. Will show databases instead..")
   "Show databases"




case class ConfigArgs(yamlQuery: String = "", sparkCreds: Option[String] = None)

object ArgsParser :

   val parser: OParser[Unit, ConfigArgs] = 
      val builder = OParser.builder[ConfigArgs]
      import builder._

      OParser.sequence(
         programName("EasySQL"),
         head("EasySQL", "1.0"),
         opt[String]('y', "yamlquery")
         .required()
         .action((value, config) => config.copy(yamlQuery = value))
         .text("YAML query (required)"),
         opt[String]('s', "sparkcreds")
         .optional()
         .action((value, config) => config.copy(sparkCreds = Some(value)))
         .text("Spark credentials (optional)"),
      )
   
   def parse(args: Array[String]): Option[ConfigArgs] = 
      OParser.parse(parser, args, ConfigArgs())
  
