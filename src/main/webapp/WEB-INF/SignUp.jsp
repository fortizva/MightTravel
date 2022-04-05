<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <title>Registro - MightTravel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountstyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shared.css">
</head>

<body>
    <div id="signupdiv" class="accountdiv backgrounddiv">
        <a href="ExploreHostingsServlet.do" class="nounderline"><img alt="Logo" src="${pageContext.request.contextPath}/img/logo.png" class="accountlogo"></a>
        <h1>¡Bienvenid@ a MightTravel!</h1>
        <br class="separator">
        <p style="color:red;">${messages}</p>
        <form class="accountform" method="post" action="SignUpCheckServlet.do">
            <label for="fname" class="lsignup"><b>Usuario</b></label>
            <input type="text" id="fname" class="faccount" name="username" autofocus required>
            <br class="separator">
            <label for="femail" class="lsignup"><b>Email</b></label>
            <input type="email" id="femail" class="faccount" name="email" required>
            <br class="separator">
            <label for="fpassword" class="lsignup"><b>Password</b></label>
            <input type="password" id="fpassword" class="faccount" name="password" minlength="8"
                pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required>
            <label id="lpasswordrules">*La contraseña debe contener minúsculas, mayúsculas, números y debe tener al
                menos 8 caracteres.</label>
            <button type="submit" class="submitbutton accountbutton">Registro</button>
        </form>
        <br>
        <form method="post" action="LoginServlet.do">¿Ya tiene cuenta? <a href="#" onclick="this.parentNode.submit()">Inicie sesión</a>.</form>
    </div>
</body>

</html>