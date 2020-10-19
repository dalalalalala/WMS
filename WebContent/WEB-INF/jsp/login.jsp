<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <!-- 这是JSP的语法,包含其他资源 -->
    <jsp:include page="/WEB-INF/jsp/common/common.jsp"/>
</head>
<body class="hold-transition login-page" > 
<!-- style='background-image: url("background.jpg");' -->

<div class="login-box">
  <div class="login-logo">
    <a href="../../index2.html"><b>仓库管理</b>系统</a>
  </div>
  
  <div class="login-box-body">
    <p class="login-box-msg">请输入账号密码</p>

    <form action="" method="post" id="login-form">
      <div class="form-group has-feedback">
        <input type="text" id="loginName" class="form-control" placeholder="账号">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" id="password" class="form-control" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
      </div>
    </form>
  </div>
</div>

<script type="text/javascript">
 
    $(function(){
        //JSON对象
        var json = {};  //这就是json对象,因为这是js语法，所有你说这是js对象也没问题
        json.name="zs";          //给json对象的name属性赋值,如果name属性不存在,会添加该属性
        
        console.log(json); 
        console.log(json.name);  //获取json对象的name属性值
        
        var jsonStr = '{"name":"zs"}';  //这是json字符串,提交给服务器的时候用的是json字符串
        console.log(jsonStr);
        
        //JSON对象--->json字符串
        //JSON.stringify();
    	
    	$("#login-form").on("submit",function(){
    		//ajax 提交登录参数
    		
    		var loginName = $("#loginName").val();
    		var password = $("#password").val();
    		
    		var param = "loginName="+loginName+"&password="+password;
    		$.ajax({
    			type:"post",
    			url:"/WMS/login",
    			data:param,
    			success:function(responseData){  //JSON对象
    				//服务器响应回来的数据
    				if(responseData.success){
    					//去到主界面
    					window.location.href="/WMS";
    				}else{
    					alert(responseData.msg);
    				}
    			}
    		});
    		return false;
    	});
    });
</script>

</body>
</html>
