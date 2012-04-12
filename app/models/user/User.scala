package models
import play.api.db._
import play.api.Play.current
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.KeyedEntity

class User (
  val id: Long = 0,
  val email: String,
  val name: String,
  val password: String) extends KeyedEntity[Long] {
  def this() = this(0, "", "", "")
}

object Users extends Schema {

  val users = table[User]
  
  on(users)(user => declare(
      user.id is (autoIncremented),
      user.email is (unique)
    ))

  def findByEmail(email: String) = inTransaction {
    from(users)(u => where(u.email === email) select u).headOption
  }

  def findById(id: Long) = inTransaction {
    from(users)(u => where(u.id === id) select u).headOption
  }
  def findAll = inTransaction {
    from(users)(select(_)).toList
  }

  def authenticate(email: String, password: String): Option[User] = inTransaction {
    from(users) ( u =>
      where(u.email === email and u.password === password)
      select(u)
    ).headOption
  }

  def create(user: User) = inTransaction {
    users.insert(user)
  }

  def update(id: Long, newUser: User) = inTransaction {
    users.update ( u => 
      where(u.id === id) set (u.password := newUser.password, u.name := newUser.name, u.email := newUser.email))
  }
}