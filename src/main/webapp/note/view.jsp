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
                                <%--设置隐藏域用来存放用户行为的actionName--%>
                                <input type="hidden" name="actionName" value="addOrUpdate">
                                <div class="form-group">
                                    <label for="typeId" class="col-sm-2 control-label">类别:</label>
                                    <div class="col-sm-8">
                                        <select id="typeId" class="form-control" name="typeId">
                                            <option value="">请选择云记类别...</option>
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
                                    <label for="title" class="col-sm-2 control-label">内容:</label>
                                    <div class="col-sm-8">
                                       <!--准备容器加载富文本编辑器-->
                                        <textarea id="content" name="content"></textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-4 col-sm-4">
                                        <input type="submit" class="btn btn-primary" onclick="return checkForm();" value="保存">
                                       &nbsp;<span id="msg" style="font-size: 12px;color: red"></span>
                                    </div>
                                </div>
                            </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <script>
            // $(function(){
            //         //UE.getEditor('noteEditor');
            //         var editor = new UE.ui.Editor({initialFrameHeight:'100%',initialFrameWidth:'100%'});
            //         editor.render("noteEditor");
            //     }
            // );
            var ue;
            $(function (){
                //加载富文本编辑器
                 ue = UE.getEditor('content');
            })

            /**
             * 表单校验
                 1. 获取表单元素的值
                     获取下拉框选中的选项  .val()
                     获取文本框的值       .val()
                     获取富文本编辑器的内容
                         ue.getContent() 获取富文本编辑器的内容（包含html标签）
                         ue.getContentTxt() 获取富文本编辑器的纯文本内容（不包含html标签）
                 2. 参数的非空判断
                    如果为空，提示用户，并return fasle
                 3. 如果参数不为空，则return true，提交表单
             */

            function checkForm(){
                //1. 获取表单元素的值
                //获取下拉框选中的选项  .val()
                var  typeId = $('#typeId').val();
                //获取文本框的值       .val()
                var title=$('#title').val();
                //获取富文本编辑器的内容
                var content=ue.getContent();
                //2. 参数的非空判断
                if (isEmpty(typeId)){
                    $('#msg').html("请选择类型");
                    return false;
                }
                if (isEmpty(title)){
                    $('#msg').html("云记标题不能为空");
                    return false;
                }
                if (isEmpty(content)){
                    $('#msg').html("云记内容不能为空");
                    return false;
                }
            }
        </script>

