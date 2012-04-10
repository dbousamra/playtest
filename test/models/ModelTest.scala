package models
import org.specs2.mutable._
import java.sql.Date;
import play.api.test._
import play.api.test.Helpers._
import anorm.Id

class ModelTest extends SpecificationWithJUnit {

  val sampleFixture = ModelUnified(
    Id(1),
    "Clio",
    Some(Date.valueOf("2004-01-01")),
    Some("Sport 182"),
    Some(5),
    Some(3),
    Some("Hatchback"),
    Make(Id(1), "Renault"),
    Engine(Id(1), "Front", 1998, 4, "Inline", 16),
    Drivetrain(Id(1), "AWD", "Manual"),
    Dimensions(Id(1), Some(2220), Some(4400), Some(2200), Some(2222), Some(1922)),
    Economy(Id(1), Some(22), Some(27), Some(65), Some(65)),
    Performance(Id(1), Some(182), Some(6500), Some(200), Some(5400)))

  "Model model" should {
    "be retrieved by id and have corresponding id" in {
      running(FakeApplication()) {
        val model = Model.findById(1).get;
        println(model)
        model.name must equalTo(sampleFixture.name)
      }
    }
  }
  
//  "Model model" should {
//    "be created from model object" in {
//      running(FakeApplication()) {
//        val newModel = sampleFixture.copy(id=Id(2))
//        Model.insert(newModel)
//        
//        
//        
//      }
//    }
//    
//  }

}