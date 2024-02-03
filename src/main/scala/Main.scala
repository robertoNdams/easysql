//> using scala 3.3.1

package easy

import scopt.OParser

 
def main(args: Array[String]): Unit =

  /* Command line Parser */
  val config: ConfigArgs = ArgsParser.parse(args = args ) match
    case Some(config) => 
      // Use config.yamlQuery and config.sparkCreds in your application logic
      println(s"config is ${config.sparkCreds} ${config.yamlQuery}")
      config
    case None => ???

  println("I was compiled by Scala 3.3.1 :")
  val engine = SparkEngine(sparkCreds = config.sparkCreds)
  val element : Map[String, Any] = readYamFile(config.yamlQuery)

  val sqlQuery: String = selectMatch(data = element) match
    case Some(value) => value.sql()
    case None => defaultQuery
  println(s"Query to run : $sqlQuery")


  val arrayQuery: Array[String] = sqlQuery.split(";")

  arrayQuery match
    case a if a.length == 1 =>  engine.query(sqlText = arrayQuery(0)).show()
    case a if a.length > 1 => arrayQuery.foreach(engine.query(_).show())
    
  
  

