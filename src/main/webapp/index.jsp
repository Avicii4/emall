<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<body>
<h2>===EMALL===</h2>
<h3>This is Tomcat 1</h3>
<h3>This is Tomcat 1</h3>

SpringMVC文件上传
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="SpringMVC文件上传" />
</form>


富文本图片上传
<form name="form2" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本图片上传" />
</form>


</body>
</html>
