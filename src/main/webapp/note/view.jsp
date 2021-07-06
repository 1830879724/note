<%--
  Created by IntelliJ IDEA.
  User: cjw
  Date: 2021/7/6
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div class="col-md-9">


        <div class="data_list">
            <div class="data_list_title">
                <span class="glyphicon glyphicon-cloud-upload"></span>&nbsp;修改云记
            </div>
            <div class="container-fluid">
                <div class="container-fluid">
                    <div class="row" style="padding-top: 20px;">
                        <div class="col-md-12">
                            <!--判断类型列表是否为空如果为空提示用户先添加类型-->
                            <c:if  test="${empty typeList}">
                                <h2>暂未查询到云记类型</h2>
                                <h4><a href="type?actionName=list">添加类型</a></h4>
                            </c:if>
                            <c:if  test="${!empty typeList}">
                            <form class="form-horizontal" method="post" action="note">
                                <div class="form-group">
                                    <label for="typeId" class="col-sm-2 control-label">类别:</label>
                                    <div class="col-sm-8">
                                        <select id="typeId" class="form-control" name="typeId">
                                            <option value="-1">请选择云记类别...</option>
                                            <c:forEach var="item" items="${typeList}">
                                                <option value="${item.typeId}">${item.typeName}</option>
                                            </c:forEach>
                                            <option value="2">技术</option>


                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" name="noteId" value="28">
                                    <input type="hidden" name="act" value="save">
                                    <label for="title" class="col-sm-2 control-label">标题:</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="title" id="title" placeholder="云记标题" value="">
                                    </div>
                                </div>

                                <div class="form-group">
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-4 col-sm-4">
                                        <input type="submit" class="btn btn-primary" onclick="return saveNote();" value="保存">
                                        <font id="error" color="red"></font>
                                    </div>
                                </div>
                            </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(function(){
                    //UE.getEditor('noteEditor');
                    var editor = new UE.ui.Editor({initialFrameHeight:'100%',initialFrameWidth:'100%'});
                    editor.render("noteEditor");
                }
            );
            //验证
            function saveNote(){
                //验证非空

                return true;
            }
        </script>

