package models

case class Engine(
    position: Option[String], 
    cc: Option[Int], 
    cylinders: Option[Int], 
    engineType: Option[String], 
    valves: Option[Int]
    )