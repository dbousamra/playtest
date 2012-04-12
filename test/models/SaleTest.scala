package models

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import anorm.Id
import models.Sales._

class SaleTest extends SpecificationWithJUnit {

  //  val sampleFixture = Sale

  "User model" should {

    "be retrieved by id" in {
      running(FakeApplication()) {
        val sale = Sales.showSaleById(1)
        sale._1.id must equalTo(1)
        sale._1.mileage must equalTo("202239")
      }
    }
    "be retrieved by user id" in {
      running(FakeApplication()) {
        val sale = Sales.listSalesByUserId(1)
        1 must equalTo(1)
      }
    }
    
    
    
  }
}