
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
object list extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template4[Page[scala.Tuple3[Model, Option[Make], Option[Aspiration]]],Int,String,play.api.mvc.Flash,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(currentPage: Page[(Model, Option[Make], Option[Aspiration])], currentOrderBy: Int, currentFilter: String)(implicit flash: play.api.mvc.Flash):play.api.templates.Html = {
        _display_ {
def /*16.2*/header/*16.8*/(orderBy: Int, title: String):play.api.templates.Html = {_display_(

Seq(format.raw/*16.41*/("""
    <th class="col"""),_display_(Seq(/*17.20*/orderBy)),format.raw/*17.27*/(""" header """),_display_(Seq(/*17.36*/if(scala.math.abs(currentOrderBy) == orderBy){/*17.83*/{if(currentOrderBy < 0) "headerSortDown" else "headerSortUp"}})),format.raw/*17.144*/("""">
        <a href=""""),_display_(Seq(/*18.19*/link(0, Some(orderBy)))),format.raw/*18.41*/("""">"""),_display_(Seq(/*18.44*/title)),format.raw/*18.49*/("""</a>
    </th>
""")))};def /*6.2*/link/*6.6*/(newPage: Int, newOrderBy: Option[Int] = None) = {{
    routes.Application.list(newPage, newOrderBy.map { orderBy =>
        if(orderBy == scala.math.abs(currentOrderBy)) -currentOrderBy else orderBy
    }.getOrElse(currentOrderBy), currentFilter)
    
}};
Seq(format.raw/*1.144*/("""

"""),format.raw/*5.42*/("""
"""),format.raw/*11.2*/("""

"""),format.raw/*15.37*/("""
"""),format.raw/*20.2*/("""

"""),_display_(Seq(/*22.2*/main/*22.6*/ {_display_(Seq(format.raw/*22.8*/("""
    
    <h1>"""),_display_(Seq(/*24.10*/Messages("models.list.title", currentPage.total))),format.raw/*24.58*/("""</h1>

    """),_display_(Seq(/*26.6*/flash/*26.11*/.get("success").map/*26.30*/ { message =>_display_(Seq(format.raw/*26.43*/("""
        <div class="alert-message warning">
            <strong>Done!</strong> """),_display_(Seq(/*28.37*/message)),format.raw/*28.44*/("""
        </div>
    """)))})),format.raw/*30.6*/("""

    <div id="actions">
        
        """),_display_(Seq(/*34.10*/helper/*34.16*/.form(action=routes.Application.list())/*34.55*/ {_display_(Seq(format.raw/*34.57*/("""
            <input type="search" id="searchbox" name="f" value=""""),_display_(Seq(/*35.66*/currentFilter)),format.raw/*35.79*/("""" placeholder="Filter by model name...">
            <input type="submit" id="searchsubmit" value="Filter by name" class="btn primary">
        """)))})),format.raw/*37.10*/("""
        
        <a class="btn success" id="add" href=""""),_display_(Seq(/*39.48*/routes/*39.54*/.Application.create())),format.raw/*39.75*/("""">Add a new model</a>
        
    </div>
    
    """),_display_(Seq(/*43.6*/Option(currentPage.items)/*43.31*/.filterNot(_.isEmpty).map/*43.56*/ { models =>_display_(Seq(format.raw/*43.68*/("""
        
        <table class="models zebra-striped">
            <thead>
                <tr>
                	"""),_display_(Seq(/*48.19*/header(2, "Make"))),format.raw/*48.36*/("""
                    """),_display_(Seq(/*49.22*/header(8, "Model name"))),format.raw/*49.45*/("""
                    """),_display_(Seq(/*50.22*/header(6, "Aspiration"))),format.raw/*50.45*/("""
                    """),_display_(Seq(/*51.22*/header(3, "Introduced"))),format.raw/*51.45*/("""
                    """),_display_(Seq(/*52.22*/header(4, "Discontinued"))),format.raw/*52.47*/("""
                </tr>
            </thead>
            <tbody>
				
                """),_display_(Seq(/*57.18*/models/*57.24*/.map/*57.28*/ {/*58.21*/case (model, make, aspiration) =>/*58.54*/ {_display_(Seq(format.raw/*58.56*/("""
                        <tr>
                            <td><a href=""""),_display_(Seq(/*60.43*/routes/*60.49*/.Application.edit(model.id.get))),format.raw/*60.80*/("""">"""),_display_(Seq(/*60.83*/model/*60.88*/.name)),format.raw/*60.93*/("""</a></td>
                            <td>
                                """),_display_(Seq(/*62.34*/make/*62.38*/.map(_.name).getOrElse/*62.60*/ {_display_(Seq(format.raw/*62.62*/(""" <em>-</em> """)))})),format.raw/*62.75*/("""
                            </td>
                            <td>
                                """),_display_(Seq(/*65.34*/aspiration/*65.44*/.map(_.aspType).getOrElse/*65.69*/ {_display_(Seq(format.raw/*65.71*/(""" <em>-</em> """)))})),format.raw/*65.84*/("""
                            </td>
                            <td>
                                """),_display_(Seq(/*68.34*/model/*68.39*/.introduced.map(_.format("dd MMM yyyy")).getOrElse/*68.89*/ {_display_(Seq(format.raw/*68.91*/(""" <em>-</em> """)))})),format.raw/*68.104*/("""
                            </td>
                            <td>
                                """),_display_(Seq(/*71.34*/model/*71.39*/.discontinued.map(_.format("dd MMM yyyy")).getOrElse/*71.91*/ {_display_(Seq(format.raw/*71.93*/(""" <em>-</em> """)))})),format.raw/*71.106*/("""
                            </td>                            
                            
                        </tr>
                    """)))}})),format.raw/*76.18*/("""

            </tbody>
        </table>

        <div id="pagination" class="pagination">
            <ul>
                """),_display_(Seq(/*83.18*/currentPage/*83.29*/.prev.map/*83.38*/ { page =>_display_(Seq(format.raw/*83.48*/("""
                    <li class="prev">
                        <a href=""""),_display_(Seq(/*85.35*/link(page))),format.raw/*85.45*/("""">&larr; Previous</a>
                    </li> 
                """)))}/*87.18*/.getOrElse/*87.28*/ {_display_(Seq(format.raw/*87.30*/("""
                    <li class="prev disabled">
                        <a>&larr; Previous</a>
                    </li>
                """)))})),format.raw/*91.18*/("""
                <li class="current">
                    <a>Displaying """),_display_(Seq(/*93.36*/(currentPage.offset + 1))),format.raw/*93.60*/(""" to """),_display_(Seq(/*93.65*/(currentPage.offset + models.size))),format.raw/*93.99*/(""" of """),_display_(Seq(/*93.104*/currentPage/*93.115*/.total)),format.raw/*93.121*/("""</a>
                </li>
                """),_display_(Seq(/*95.18*/currentPage/*95.29*/.next.map/*95.38*/ { page =>_display_(Seq(format.raw/*95.48*/("""
                    <li class="next">
                        <a href=""""),_display_(Seq(/*97.35*/link(page))),format.raw/*97.45*/("""">Next &rarr;</a>
                    </li> 
                """)))}/*99.18*/.getOrElse/*99.28*/ {_display_(Seq(format.raw/*99.30*/("""
                    <li class="next disabled">
                        <a>Next &rarr;</a>
                    </li>
                """)))})),format.raw/*103.18*/("""
            </ul>
        </div>
        
    """)))}/*107.6*/.getOrElse/*107.16*/ {_display_(Seq(format.raw/*107.18*/("""
        
        <div class="well">
            <em>Nothing to display</em>
        </div>
        
    """)))})),format.raw/*113.6*/("""

        
""")))})),format.raw/*116.2*/("""

            """))}
    }
    
    def render(currentPage:Page[scala.Tuple3[Model, Option[Make], Option[Aspiration]]],currentOrderBy:Int,currentFilter:String,flash:play.api.mvc.Flash) = apply(currentPage,currentOrderBy,currentFilter)(flash)
    
    def f:((Page[scala.Tuple3[Model, Option[Make], Option[Aspiration]]],Int,String) => (play.api.mvc.Flash) => play.api.templates.Html) = (currentPage,currentOrderBy,currentFilter) => (flash) => apply(currentPage,currentOrderBy,currentFilter)(flash)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Mar 21 14:16:00 EST 2012
                    SOURCE: C:/play-2.0/samples/scala/playtest/app/views/list.scala.html
                    HASH: 0b25371d2c33774d4e7a5f77e064c58197204f1b
                    MATRIX: 587->1|790->642|804->648|896->681|947->701|976->708|1016->717|1071->764|1156->825|1208->846|1252->868|1286->871|1313->876|1351->272|1362->276|1642->143|1671->270|1699->530|1729->640|1757->892|1790->895|1802->899|1836->901|1882->916|1952->964|1994->976|2008->981|2036->1000|2082->1013|2194->1094|2223->1101|2275->1122|2349->1165|2364->1171|2412->1210|2447->1212|2544->1278|2579->1291|2756->1436|2844->1493|2859->1499|2902->1520|2984->1572|3018->1597|3052->1622|3097->1634|3242->1748|3281->1765|3334->1787|3379->1810|3432->1832|3477->1855|3530->1877|3575->1900|3628->1922|3675->1947|3792->2033|3807->2039|3820->2043|3831->2067|3873->2100|3908->2102|4011->2174|4026->2180|4079->2211|4113->2214|4127->2219|4154->2224|4261->2300|4274->2304|4305->2326|4340->2328|4385->2341|4517->2442|4536->2452|4570->2477|4605->2479|4650->2492|4782->2593|4796->2598|4855->2648|4890->2650|4936->2663|5068->2764|5082->2769|5143->2821|5178->2823|5224->2836|5400->2997|5555->3121|5575->3132|5593->3141|5636->3151|5740->3224|5772->3234|5857->3300|5876->3310|5911->3312|6081->3450|6185->3523|6231->3547|6267->3552|6323->3586|6360->3591|6381->3602|6410->3608|6485->3652|6505->3663|6523->3672|6566->3682|6670->3755|6702->3765|6783->3827|6802->3837|6837->3839|7004->3973|7071->4021|7091->4031|7127->4033|7265->4139|7309->4151
                    LINES: 19->1|21->16|21->16|23->16|24->17|24->17|24->17|24->17|24->17|25->18|25->18|25->18|25->18|27->6|27->6|33->1|35->5|36->11|38->15|39->20|41->22|41->22|41->22|43->24|43->24|45->26|45->26|45->26|45->26|47->28|47->28|49->30|53->34|53->34|53->34|53->34|54->35|54->35|56->37|58->39|58->39|58->39|62->43|62->43|62->43|62->43|67->48|67->48|68->49|68->49|69->50|69->50|70->51|70->51|71->52|71->52|76->57|76->57|76->57|76->58|76->58|76->58|78->60|78->60|78->60|78->60|78->60|78->60|80->62|80->62|80->62|80->62|80->62|83->65|83->65|83->65|83->65|83->65|86->68|86->68|86->68|86->68|86->68|89->71|89->71|89->71|89->71|89->71|93->76|100->83|100->83|100->83|100->83|102->85|102->85|104->87|104->87|104->87|108->91|110->93|110->93|110->93|110->93|110->93|110->93|110->93|112->95|112->95|112->95|112->95|114->97|114->97|116->99|116->99|116->99|120->103|124->107|124->107|124->107|130->113|133->116
                    -- GENERATED --
                */
            