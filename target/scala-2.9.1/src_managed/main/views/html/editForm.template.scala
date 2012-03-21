
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
object editForm extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[Long,Form[Model],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(id: Long, modelForm: Form[Model]):play.api.templates.Html = {
        _display_ {import helper._

implicit def /*5.2*/implicitFieldConstructor/*5.26*/ = {{ FieldConstructor(twitterBootstrapInput.f) }};
Seq(format.raw/*1.36*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*5.75*/("""

"""),_display_(Seq(/*7.2*/main/*7.6*/ {_display_(Seq(format.raw/*7.8*/("""
    
    <h1>Edit model</h1>
    
    """),_display_(Seq(/*11.6*/form(routes.Application.update(id))/*11.41*/ {_display_(Seq(format.raw/*11.43*/("""
        
        <fieldset>
            """),_display_(Seq(/*14.14*/inputText(modelForm("name"), '_label -> "Model name"))),format.raw/*14.67*/("""
	        """),_display_(Seq(/*15.11*/select(
	                modelForm("make"), 
	                Make.options, 
	                '_label -> "Make", '_default -> "-- Choose a make --",
	                '_showConstraints -> false
	            ))),format.raw/*20.15*/("""
	        """),_display_(Seq(/*21.11*/select(
                modelForm("aspiration"), 
                Aspiration.options, 
                '_label -> "Aspiration", '_default -> "-- Choose an aspiration type --",
                '_showConstraints -> false
            ))),format.raw/*26.14*/("""
        
            """),_display_(Seq(/*28.14*/inputText(modelForm("introduced"), '_label -> "Introduced date"))),format.raw/*28.78*/("""
            """),_display_(Seq(/*29.14*/inputText(modelForm("discontinued"), '_label -> "Discontinued date"))),format.raw/*29.82*/("""
        </fieldset>
        
        <div class="actions">
            <input type="submit" value="Save this model" class="btn primary"> or 
            <a href=""""),_display_(Seq(/*34.23*/routes/*34.29*/.Application.list())),format.raw/*34.48*/("""" class="btn">Cancel</a> 
        </div>
        
    """)))})),format.raw/*37.6*/("""
    
    """),_display_(Seq(/*39.6*/form(routes.Application.delete(id), 'class -> "topRight")/*39.63*/ {_display_(Seq(format.raw/*39.65*/("""
        <input type="submit" value="Delete this model" class="btn danger">
    """)))})),format.raw/*41.6*/("""
    
""")))})))}
    }
    
    def render(id:Long,modelForm:Form[Model]) = apply(id,modelForm)
    
    def f:((Long,Form[Model]) => play.api.templates.Html) = (id,modelForm) => apply(id,modelForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Mar 21 14:16:00 EST 2012
                    SOURCE: C:/play-2.0/samples/scala/playtest/app/views/editForm.scala.html
                    HASH: cec9385f6b455314c72c8f5da133a10bfdd8138f
                    MATRIX: 518->1|637->56|669->80|743->35|771->54|799->129|831->132|842->136|875->138|945->178|989->213|1024->215|1097->257|1172->310|1214->321|1443->528|1485->539|1739->771|1793->794|1879->858|1924->872|2014->940|2209->1104|2224->1110|2265->1129|2351->1184|2392->1195|2458->1252|2493->1254|2605->1335
                    LINES: 19->1|22->5|22->5|23->1|25->4|26->5|28->7|28->7|28->7|32->11|32->11|32->11|35->14|35->14|36->15|41->20|42->21|47->26|49->28|49->28|50->29|50->29|55->34|55->34|55->34|58->37|60->39|60->39|60->39|62->41
                    -- GENERATED --
                */
            