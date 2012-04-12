package models

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import anorm.Id

class UserTest extends SpecificationWithJUnit {

  "User model" should {

    "be retrieved by id" in {
      running(FakeApplication()) {
        val id = 1
        val Some(user) = Users.findById(id)
        user.name must equalTo("Bob Sacamano")
        user.id must equalTo(id)
      }
    }

    "be retrieved by email" in {
      running(FakeApplication()) {
        val email = "bob@gmail.com"
        val Some(user) = Users.findByEmail(email)
        user.name must equalTo("Bob Sacamano")
        user.email must equalTo(email)
      }
    }

    "using findAll method contain" in {
      running(FakeApplication()) {
        val users = Users.findAll
        val userOne = Users.findById(1).get
        val userTwo = Users.findById(2).get
        println(users)
        users must contain(userOne)
        users must contain(userTwo)
      }
    }

    "be authenticated" in {
      running(FakeApplication()) {
        val userOne = Users.findById(1).get
        val returnedUser = Users.authenticate(userOne.email, userOne.password).get
        returnedUser.email must equalTo(userOne.email)
        returnedUser.password must equalTo(userOne.password)
      }
    }

    "be created" in {
      running(FakeApplication()) {
        val userToBeCreated = new User(111, "Foo@bar.com", "Foo", "Pass")
        Users.create(userToBeCreated)
        val userAfterCreated = Users.findByEmail("Foo@bar.com").get
        userAfterCreated.email must equalTo("Foo@bar.com")
        userAfterCreated.password must equalTo("Pass")
        userAfterCreated.name must equalTo("Foo")
      }
    }

    "be updated" in {
      running(FakeApplication()) {
        val id = 1
        val oldUser = Users.findById(id).get
        val newUser = new User(id, "Foo@bar.com", "Foo", "Pass")
        Users.update(id, newUser)
        val changedUser = Users.findById(id).get
        changedUser.name must equalTo(newUser.name)
        changedUser.password must equalTo(newUser.password)
      }
    }

  }
}