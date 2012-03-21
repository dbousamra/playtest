
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._
/**/
object createForm extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Form[Model],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(modelForm: Form[Model]):play.api.templates.Html = {
        _display_ {import helper._

implicit def /*5.2*/implicitFieldConstructor/*5.26*/ = {{ FieldConstructor(twitterBootstrapInput.f) }};
Seq(format.raw/*1.26*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*5.75*/(""" 

"""),_display_(Seq(/*7.2*/main/*7.6*/ {_display_(Seq(format.raw/*7.8*/("""
    
    <h1>Add a model</h1>
    
    """),_display_(Seq(/*11.6*/form(routes.Application.save())/*11.37*/ {_display_(Seq(format.raw/*11.39*/("""
        
        <fieldset>
        
            """),_display_(Seq(/*15.14*/inputText(modelForm("name"), '_label -> "Car model name"))),format.raw/*15.71*/("""
            """),_display_(Seq(/*16.14*/inputText(modelForm("introduced"), '_label -> "Introduced date"))),format.raw/*16.78*/("""
            """),_display_(Seq(/*17.14*/inputText(modelForm("discontinued"), '_label -> "Discontinued date"))),format.raw/*17.82*/("""

            """),_display_(Seq(/*19.14*/select(
                modelForm("make"), 
                Make.options, 
                '_label -> "Make", '_default -> "-- Choose a Make --",
                '_showConstraints -> false
            ))),format.raw/*24.14*/("""
            
            """),_display_(Seq(/*26.14*/select(
                modelForm("aspiration"), 
                Aspiration.options, 
                '_label -> "Aspiration", '_default -> "-- Choose an aspiration type --",
                '_showConstraints -> false
            ))),format.raw/*31.14*/("""
            

        </fieldset>
        
        <div class="actions">
            <input type="submit" value="Create this model" class="btn primary"> or 
            <a href=""""),_display_(Seq(/*38.23*/routes/*38.29*/.Application.list())),format.raw/*38.48*/("""" class="btn">Cancel</a> 
        </div>
        
    """)))})),format.raw/*41.6*/("""
    
""")))})))}
    }
    
    def render(modelForm:Form[Model]) = apply(modelForm)
    
    def f:((Form[Model]) => play.api.templates.Html) = (modelForm) => apply(modelForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Mar 21 14:16:00 EST 2012
                    SOURCE: C:/play-2.0/samples/scala/playtest/app/views/createForm.scala.html
                    HASH: eb1166d5d5cb4296d5b994455acd902c9a517984
                    MATRIX: 515->1|624->46|656->70|730->25|758->44|786->119|819->123|830->127|863->129|934->170|974->201|1009->203|1091->254|1170->311|1215->325|1301->389|1346->403|1436->471|1482->486|1706->688|1764->715|2018->947|2229->1127|2244->1133|2285->1152|2371->1207
                    LINES: 19->1|22->5|22->5|23->1|25->4|26->5|28->7|28->7|28->7|32->11|32->11|32->11|36->15|36->15|37->16|37->16|38->17|38->17|40->19|45->24|47->26|52->31|59->38|59->38|59->38|62->41
                    -- GENERATED --
                */
            