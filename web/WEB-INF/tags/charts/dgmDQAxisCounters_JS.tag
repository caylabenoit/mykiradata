<%@tag description="DGM Counter Tag" pageEncoding="UTF-8" import="com.joy.Joy, com.joy.common.JoyParameter, com.joy.charts.gaugejs.ChartCounterData, com.joy.mvc.ActionForm, com.joy.C, com.joy.mvc.formbean.JoyFormMatrix, com.joy.mvc.formbean.JoyFormVector" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="dqaxis"%>

<%
    try {
        ActionForm actionform = (ActionForm)Joy.CURRENT_ACTION(request);
        JoyFormMatrix matrix = (JoyFormMatrix)actionform.getMatrix(dqaxis); 

        for (JoyFormVector axis : matrix.getMatrix()) {
            ChartCounterData myChart = (ChartCounterData)axis.getVectorObject("COUNTER_OBJECT");
%>
displayGauge('canvas-<%= myChart.getCode() %>', '<%= myChart.getColor() %>', <%= myChart.getValue() %>);
<%      } 
    } catch (Exception e) { 
%>
    <!-- Error !! -->
<%
    }
%>
