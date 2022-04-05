<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <title>Iniciar sesión - MightTravel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountstyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shared.css">
</head>

<body>
    <div id="logindiv" class="accountdiv backgrounddiv">
        <a href="ExploreHostingsServlet.do" class="nounderline"><img alt="Logo" src="${pageContext.request.contextPath}/img/logo.png" class="accountlogo"></a>
        <h1>¡Bienvenid@ a MightTravel!</h1>
        <br class="separator">
        <p style="color:red;">${messages}</p>
        <form class="accountform" method="post" action="LoginCheckServlet.do">
            <label for="fusername" class="llogin"><b>Usuario</b></label>
            <input type="text" id="fusername" class="faccount" name="username" autofocus required>
            <br class="separator">
            <label for="fpassword" class="llogin"><b>Contraseña</b></label>
            <input type="password" id="fpassword" class="faccount" name="password" required>
            <button type="submit" class="submitbutton accountbutton">Login</button>
        </form>
        <br>
        <form method="post" action="SignUpServlet.do" >Aún no tiene cuenta? <a href="#" onclick="this.parentNode.submit()">Regístrese</a>.</form>
    </div>
</body>

</html>