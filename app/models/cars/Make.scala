package models

import play.api.db._
import play.api.Play.current
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column

class Make(
  val id: Long,
  val name: String) {
  def this() = this(0, "")
}

object Makes extends Schema {

  val makes = table[Make]

  def options: Seq[(String, String)] = inTransaction {
    from(makes)(m => select(m.id.toString, m.name) orderBy m.name).toList
  }

  def findById(id: Long): Make = inTransaction {
    from(makes)(m =>
      where(m.id === id)
        select (m)).head
  }

}

