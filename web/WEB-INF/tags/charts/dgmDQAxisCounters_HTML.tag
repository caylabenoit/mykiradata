<%@tag description="DGM Counter Tag" pageEncoding="UTF-8" import="com.joy.Joy, com.joy.common.JoyParameter, com.joy.charts.gaugejs.ChartCounterData, com.joy.mvc.ActionForm, com.joy.C, com.joy.mvc.formbean.JoyFormMatrix, com.joy.mvc.formbean.JoyFormVector" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="dqaxis"%>
<%@attribute name="trends"%>
<%
    try {
        ActionForm actionform = (ActionForm)Joy.CURRENT_ACTION(request);
        JoyFormMatrix matrix = (JoyFormMatrix)actionform.getMatrix(dqaxis); 
        int i = matrix.getMatrix().size();
        
        if (i > 0) {
%>
	<div class="table-responsive">
		<table class="table"><tbody><tr>
<%
            for (JoyFormVector axis : matrix.getMatrix()) {
                ChartCounterData myChart = (ChartCounterData)axis.getVectorObject("COUNTER_OBJECT");
%>
                                <TH style="text-align:center;"><canvas width=100 height=70 id="canvas-<%= myChart.getCode() %>"></canvas><br><%= myChart.getLabel() %></TH>
<%          } %>
                            </tr>
                            <tr>
<%          for (JoyFormVector axis : matrix.getMatrix()) {
                ChartCounterData myChart = (ChartCounterData)axis.getVectorObject("COUNTER_OBJECT");
%>
                                <td style="text-align:center;">
                                    <span style="font-size: 18px; font-style:  italic"><%= myChart.getValue()%>%</span>
                                </td>
<%
        }
%>
                            </TR>
                    <TR>
<%
        JoyFormMatrix matrixtrends = (JoyFormMatrix)actionform.getMatrix(trends); 
        JoyParameter glypheParam;
        String myglyphe = "";
        String mycolor = "";
        
        for (JoyFormVector axis : matrixtrends.getMatrix()) {
            if (axis.getVectorValue("TREND").equalsIgnoreCase("UP")) {
               glypheParam = Joy.PARAMETERS().getParameter("ApplicationIconsBSGlyphe").get("trend-up");
               mycolor = "style=\"color:" + Joy.RGBA(Joy.PARAMETERS().getParameter("ColorGood").getValue().toString(), "1") + "\"";
            } else if (axis.getVectorValue("TREND").equalsIgnoreCase("DOWN")) {
               glypheParam = Joy.PARAMETERS().getParameter("ApplicationIconsBSGlyphe").get("trend-down");
               mycolor = "style=\"color:" + Joy.RGBA(Joy.PARAMETERS().getParameter("ColorBad").getValue().toString(), "1") + "\"";
            } else if (axis.getVectorValue("TREND").equalsIgnoreCase("EQUAL")) {
               glypheParam = Joy.PARAMETERS().getParameter("ApplicationIconsBSGlyphe").get("trend-stable");
               mycolor = "style=\"color:" + Joy.RGBA(Joy.PARAMETERS().getParameter("ColorNoMove").getValue().toString(), "1") + "\"";
            } else {
               glypheParam = Joy.PARAMETERS().getParameter("ApplicationIconsBSGlyphe").get("trend-new");
               mycolor = "style=\"color:" + Joy.RGBA(Joy.PARAMETERS().getParameter("ColorNoMove").getValue().toString(), "1") + "\"";
            }
            if (glypheParam != null) 
                myglyphe = (String)glypheParam.getValue();
%>
            <TD style="text-align:center;">
                <DIV class="bloctendance_prev">Previous: <%= axis.getVectorValue("PREV") %></DIV> 
                <DIV class="bloctendance_last">Variation: <%= axis.getVectorValue("TREND_SCORE") %>%</DIV> 
                <i class="fa <%= myglyphe %> fa-fw fa-4x" <%= mycolor %>></i>&nbsp;
            </TD>
<%
        }
%>                                
                </TR>
			</tbody>
		</table>
	</div>
<%
        } else {
%>
No data to display.
<%
}
    } catch (Exception e) { 
%>
    <!-- Error !! -->
<%
    }
%>
