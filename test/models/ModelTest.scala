package models
import org.specs2.mutable._
import java.sql.Date;
import play.api.test._
import play.api.test.Helpers._
import anorm.Id

class ModelTest extends SpecificationWithJUnit {

  val sampleFixture = ModelUnified(
    1,
    "Clio",
    Some(Date.valueOf("2004-01-01")),
    Some("Sport 182"),
    Some(5),
    Some(3),
    Some("Hatchback"),
    new Make(1, "Renault"))

  "Model model" should {
    "be retrieved by id and have corresponding id" in {
      running(FakeApplication()) {
        val model = Models.findById(1).get;
        println(model)
        model.name must equalTo(sampleFixture.name)
      }
    }
  }
}