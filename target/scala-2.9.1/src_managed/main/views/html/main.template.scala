
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
object main extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(content: Html):play.api.templates.Html = {
        _display_ {

Seq(format.raw/*1.17*/("""

<!DOCTYPE html>
<html>
    <head>
        <title>App that probably won't be completed</title>
        <link rel="stylesheet" type="text/css" media="screen" href=""""),_display_(Seq(/*7.70*/routes/*7.76*/.Assets.at("stylesheets/bootstrap.min.css"))),format.raw/*7.119*/(""""> 
        <link rel="stylesheet" type="text/css" media="screen" href=""""),_display_(Seq(/*8.70*/routes/*8.76*/.Assets.at("stylesheets/main.css"))),format.raw/*8.110*/(""""> 
    </head>
    <body>
        
        <header class="topbar">
            <h1 class="fill">
                <a href=""""),_display_(Seq(/*14.27*/routes/*14.33*/.Application.index())),format.raw/*14.53*/("""">
                   App that probably won't be completed
                </a>
            </h1>
        </header>
        
        <section id="main">
            """),_display_(Seq(/*21.14*/content)),format.raw/*21.21*/("""
        </section>
        
    </body>
</html>"""))}
    }
    
    def render(content:Html) = apply(content)
    
    def f:((Html) => play.api.templates.Html) = (content) => apply(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Mar 21 14:16:00 EST 2012
                    SOURCE: C:/play-2.0/samples/scala/playtest/app/views/main.scala.html
                    HASH: b49ec5c4fd2b7f4285ab7a25a0954f1f446bc081
                    MATRIX: 502->1|589->16|784->181|798->187|863->230|966->303|980->309|1036->343|1191->467|1206->473|1248->493|1445->659|1474->666
                    LINES: 19->1|22->1|28->7|28->7|28->7|29->8|29->8|29->8|35->14|35->14|35->14|42->21|42->21
                    -- GENERATED --
                */
            