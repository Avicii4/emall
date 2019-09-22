<%--
  Created by IntelliJ IDEA.
  User: 25146
  Date: 2019/9/19
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>welcome</title>
    <style type="text/css">

/*        html {*/
/*            height: 100%;*/
/*        }*/

/*        body {*/
/*            background-image: url(11.jpeg);*/
/*            background-size: 100% 100%;*/
/*            padding: 0;*/
/*            margin: 0;*/
/*        }*/

        @font-face {
            font-family: "MAILR___";
            src: url("MAILR___.TTF");
        }

        p {
            font-family: MAILR___;
            font-size: 70px;
        }

        .blur {
            filter: url(blur.svg#blur); /* FireFox, Chrome, Opera */
            -webkit-filter: blur(5px); /* Chrome, Opera */
            -moz-filter: blur(5px);
            -ms-filter: blur(5px);
            filter: blur(5px);
            filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=5, MakeShadow=false); /* IE6~IE9 */
        }

        .hvcenter {
            position: absolute;
            top: 15%;
            left: 10%;
        }
    </style>
    <script type="text/javascript">
        window.onload = CurrentTime;

        function CurrentTime() {
            var now = new Date();
            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日
            var hh = now.getHours();            //时
            var mm = now.getMinutes();          //分

            var clock = year + " - ";
            if (month < 10)
                clock += "0";
            clock += month + " - ";
            if (day < 10)
                clock += "0";
            clock += day + "&nbsp;&nbsp;&nbsp;";
            if (hh < 10)
                clock += "0";
            clock += hh + " : ";
            if (mm < 10)
                clock += '0';
            clock += mm;
            document.getElementById("time").innerHTML = clock;
            document.getElementById("sayhi").innerHTML = "tomcat one";
            document.getElementById("myimg").src="http://cdn.duitang.com/uploads/item/201202/06/20120206171818_YG4en.png;

        }
    </script>
</head>
<body>
<div id="bg_pic" style="position:absolute; width:100%; height:100%; z-index:-1">
    <img id="myimg" src="morning.jpg" alt="backgroundpicture" class="blur" width="100%" height="100%">
</div>
<div id="" style="width:1000px; height:500px;  text-align:center" class="hvcenter">
    <p id="sayhi"></p>
    <p id="time"></p>
</div>
</body>
</html>
