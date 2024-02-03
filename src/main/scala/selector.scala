//> using 3.3.1

package easy

trait MapType[T] {
  def validate(map: Map[String, Any]): Option[T]
}

implicit val freeQueryMapType: MapType[FreeQuery] = new MapType[FreeQuery] {
  def validate(map: Map[String, Any]): Option[FreeQuery] = {
    for {
      sql <- map.get("query").collect { case s: String => if s.nonEmpty then s else null }
    } yield new FreeQuery(sql)
  }
}


object MapType :
  private val mapTypes: Map[Class[_], MapType[_]] = Map(
    classOf[FreeQuery] -> freeQueryMapType
  )
  def apply[T](clazz: Class[T]): MapType[T] =
    mapTypes.getOrElse(clazz, throw new IllegalArgumentException(s"No MapType found for class $clazz")).asInstanceOf[MapType[T]]


def validateMapDynamic[T](map: Map[String, Any], clazz: Class[T]): Option[T] =
  MapType(clazz).validate(map)
  


def selectMatch(data: Map[String, Any]): Option[Query] = 
  var result = for (clazz <- caseList) yield validateMapDynamic(data, clazz)
  result = result.filter(_ != None)
  val resultOption = result.flatten.head
  val target = resultOption match 
    case a: FreeQuery =>  Some(resultOption.asInstanceOf[FreeQuery])
    case _ => None
  
  target
  
