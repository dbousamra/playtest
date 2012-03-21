
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
object twitterBootstrapInput extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[helper.FieldElements,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(elements: helper.FieldElements):play.api.templates.Html = {
        _display_ {

Seq(format.raw/*1.34*/("""

"""),format.raw/*5.52*/("""
<div class="clearfix """),_display_(Seq(/*6.23*/if(elements.hasErrors)/*6.45*/ {_display_(Seq(format.raw/*6.47*/("""error""")))})),format.raw/*6.53*/("""">
    <label for=""""),_display_(Seq(/*7.18*/elements/*7.26*/.id)),format.raw/*7.29*/("""">"""),_display_(Seq(/*7.32*/elements/*7.40*/.label)),format.raw/*7.46*/("""</label>
    <div class="input">
        """),_display_(Seq(/*9.10*/elements/*9.18*/.input)),format.raw/*9.24*/("""
        <span class="help-inline">"""),_display_(Seq(/*10.36*/elements/*10.44*/.infos.mkString(", "))),format.raw/*10.65*/("""</span> 
    </div>
</div>
"""))}
    }
    
    def render(elements:helper.FieldElements) = apply(elements)
    
    def f:((helper.FieldElements) => play.api.templates.Html) = (elements) => apply(elements)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Mar 21 14:16:00 EST 2012
                    SOURCE: C:/play-2.0/samples/scala/playtest/app/views/twitterBootstrapInput.scala.html
                    HASH: 69ac1da3a5c9505436ae5a8eb5dc1c480cf7a34a
                    MATRIX: 535->1|639->33|668->190|721->213|751->235|785->237|822->243|872->263|888->271|912->274|945->277|961->285|988->291|1060->333|1076->341|1103->347|1170->383|1187->391|1230->412
                    LINES: 19->1|22->1|24->5|25->6|25->6|25->6|25->6|26->7|26->7|26->7|26->7|26->7|26->7|28->9|28->9|28->9|29->10|29->10|29->10
                    -- GENERATED --
                */
            