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
        val Some(user) = User.findById(id)
        user.name must equalTo("Bob Sacamano")
        user.id.get must equalTo(id)
      }
    }

    "be retrieved by email" in {
      running(FakeApplication()) {
        val email = "bob@gmail.com"
        val Some(user) = User.findByEmail(email)
        user.name must equalTo("Bob Sacamano")
        user.email must equalTo(email)
      }
    }

    "using findAll method contain" in {
      running(FakeApplication()) {
        val users = User.findAll
        val userOne = User.findById(1).get
        val userTwo = User.findById(2).get
        users must contain(userOne)
        users must contain(userTwo)
      }
    }

    "be authenticated" in {
      running(FakeApplication()) {
        val userOne = User.findById(1).get
        val returnedUser = User.authenticate(userOne.email, userOne.password).get
        returnedUser.email must equalTo(userOne.email)
        returnedUser.password must equalTo(userOne.password)
      }
    }

    "be created" in {
      running(FakeApplication()) {
        val userToBeCreated = User(Id(1), "Foo@bar.com", "Foo", "Pass")
        User.create(userToBeCreated)
        val userAfterCreated = User.findByEmail("Foo@bar.com").get
        userAfterCreated.email must equalTo("Foo@bar.com")
        userAfterCreated.password must equalTo("Pass")
        userAfterCreated.name must equalTo("Foo")
      }
    }

    "be updated" in {
      running(FakeApplication()) {
        val id = 1
        val oldUser = User.findById(id).get
        val newUser = User(Id(id), "Foo@bar.com", "Foo", "Pass")
        User.update(id, newUser)
        val changedUser = User.findById(id).get
        changedUser.name must equalTo(newUser.name)
        changedUser.password must equalTo(newUser.password)
      }

    }

  }
}