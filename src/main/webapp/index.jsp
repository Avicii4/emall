<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<body>
<h2>===EMALL===</h2>

SpringMVC文件上传
<form name="form1" action="/emall_war/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="SpringMVC文件上传" />
</form>


富文本图片上传
<form name="form2" action="/emall_war/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本图片上传" />
</form>


</body>
</html>
