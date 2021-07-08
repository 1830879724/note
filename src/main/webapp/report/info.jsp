<%--
  Created by IntelliJ IDEA.
  User: cjw
  Date: 2021/7/8
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon-signal"></span>&nbsp;数据报表 </div>
        <div class="container-fluid">
            <div class="row" style="padding-top: 20px;">
                <div class="col-md-12">
                <%-- 柱状图容器--%>
                <div id="monthChart" style="height: 500px">

                </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%----%>

<script type="text/javascript" src="statics/echarts/echarts.min.js"></script>
<script type="text/javascript">
    /**
     * 通过月份查询云记数量
     */
   $.ajax({
       type:"get",
       url:"report",
       data:{
           actionName:"month",
       },
       success:function (result){
                console.log(result)
          if (result.code == 1){
              //通过月份查询数量
              var monthArray=result.result.monthArray;
              var dataArray=result.result.dataArray;
              //加载柱状图
              loadMonthChart(monthArray,dataArray);
          }
       }
   })
            /**
             * 加载柱状图
             */
         function loadMonthChart(monthArray,dataArray){
             // 基于准备好的dom，初始化echarts实例
             var myChart = echarts.init(document.getElementById('monthChart'));

             var dataAxis = monthArray;
             var data = dataArray;
             var yMax = 30;
             var dataShadow = [];

             for (var i = 0; i < data.length; i++) {
                 dataShadow.push(yMax);
             }

             var option = {
                 title: {
                     text: '按月统计',
                     subtext: '通过月份查询对应数量',
                     left:'center'
                 },
                 tooltip:{},

                 xAxis: {
                     data: dataAxis,
                     axisTick: {
                         show: false
                     },
                     axisLine: {
                         show: false
                     }
                 },
                 yAxis: {
                     axisLine: {
                         show: false
                     },
                     axisTick: {
                         show: false
                     },
                     axisLabel: {
                         textStyle: {
                             color: '#999'
                         }
                     }
                 },
                 dataZoom: [
                     {
                         type: 'inside'
                     }
                 ],
                 series: [
                     {
                         type: 'bar',
                         showBackground: true,
                         data: data,
                         itemStyle: {
                             color: new echarts.graphic.LinearGradient(
                                 0, 0, 0, 1,
                                 [
                                     {offset: 0, color: '#83bff6'},
                                     {offset: 0.5, color: '#188df0'},
                                     {offset: 1, color: '#188df0'}
                                 ]
                             )
                         },
                         emphasis: {
                             itemStyle: {
                                 color: new echarts.graphic.LinearGradient(
                                     0, 0, 0, 1,
                                     [
                                         {offset: 0, color: '#2378f7'},
                                         {offset: 0.7, color: '#2378f7'},
                                         {offset: 1, color: '#83bff6'}
                                     ]
                                 )
                             }
                         },

                     }
                 ]
             };
             // 使用刚指定的配置项和数据显示图表。
             myChart.setOption(option);
 }
</script>