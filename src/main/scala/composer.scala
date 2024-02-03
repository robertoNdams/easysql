//> using 3.3.1 

package easy


trait Query:
  def sql(): String

case class CreateDelta(table: String, tableClauses: Option[List[String]], asQuery: String) extends Query :
  override def sql() : String = 
    "sdqkfqmsfmk"
case class Select(table: String, columns: List[String], conditions: Option[List[String]], timeTravel: Option[List[String]]) extends Query :
  override def sql() : String = 
    "sdqkfqmsfmk"
case class DeepSelect(table: Select, columns: List[String]) extends Query :
  override def sql() : String = 
    "sdqkfqmsfmk"
case class Upsert() extends Query :
  override def sql() : String = 
    "sdqkfqmsfmk"
case class Display() extends Query :
  override def sql() : String = 
    "sdqkfqmsfmk"
case class Delete(table: String, conditions: Option[List[String]]) extends Query :
  override def sql() : String = 
    "sdqkfqmsfmk"

case class FreeQuery(query: String ) extends Query: 
  override def sql(): String = query 

val caseList : List[Class[_]] = List(  classOf[FreeQuery],
                                      //  classOf[CreateDelta],
                                      //  classOf[Select],
                                      //  classOf[DeepSelect],
                                      //  classOf[Upsert],
                                      //  classOf[Display],
                                      //  classOf[Delete]
                                      )


