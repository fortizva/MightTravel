<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">

<head>
<title>Explorar - MightTravel</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/userpanel.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/shared.css">
</head>

<body>
	<!-- Barra de navegación -->
    <div class="navbar">
        <a href="ExploreHostingsServlet.do" class="nounderline bannerLogoLink"><img alt="Logo" src="${pageContext.request.contextPath}/img/logoWhite.png" class="bannerlogo"></a>
        <a href="ExploreHostingsServlet.do" class="bannertitle">MightTravel</a>
        <form class="searchform" method="get" action="HostingLocationListServlet.do">
            <input type="text" placeholder="Buscar" name="q" class="fbannersearch">
            <select name="category" class="fbannerselect">
            	<option value="">Categoría</option>
            	<c:forEach var="Category" items="${Categories}">
            		<option value="${Category.id}">${Category.name}</option>
            	</c:forEach> 
            </select>
            <select name="availability" class="fbannerselect">
            	<option value="">Disponibilidad</option>
           		<option value="1">Sólo disponibles</option>
            </select>
            <button type="submit" class="submitbutton bannersearchbutton">Buscar</button>
        </form>
        <c:choose>
        <c:when test="${user==null}">
	        <form action="LoginServlet.do" method="POST" class="banneraccount">
	        	<button type="submit" class="submitbutton banneraccount">Login</button>
	        </form>
        </c:when>
        <c:otherwise>
            <form action="LogoutServlet.do" method="POST" class="banneraccount">
        		<button type="submit" class="submitbutton banneraccount">Logout</button>
       		</form>
        </c:otherwise>
        </c:choose>
    </div>


	<div class="maindiv">
		<section class="userpanelcontainer backgrounddiv">
			<h1>Datos de usuario:</h1>
			<form class="panelEditForm" method="post" action="UserEditServlet.do">
				<p style="color:red;">${messages}</p>
				<label for="fname" class="lsignup">
				<b>Usuario: ${user.username}</b></label>
				<input type="text" id="fname" class="faccount" name="username"> <br class="separator">
				<label for="femail" class="lsignup"><b>Email: ${user.email}</b></label>
				<input type="email" id="femail" class="faccount" name="email">
				<br class="separator">
				<label for="fpassword" class="lsignup"><b>Password</b></label>
				<input type="password" id="fpassword" class="faccount" name="password" minlength="8" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}">
				<br class="separator">
				<button type="submit" class="submitbutton accountbutton">Guardar</button>
				<label id="lpasswordrules">*La contraseña debe contener	minúsculas, mayúsculas, números y debe tener al menos 8 caracteres.</label>
			</form>
			<form class="panelEditForm" method="post" action="UserEditServlet.do">
				<input type="hidden" value="true" name="deleteaccount">
				<button type="submit" class="submitbutton accountbutton">Eliminar cuenta</button>
			</form>
		</section>
		<br>
		<section class="backgrounddiv">
			<h1>Favoritos:</h1>
			<section class="housecelllist">
				<!-- Mostrar listado de casas -->
				<c:forEach var="Hosting" items="${FavoritesList}">
					<div class="housecell">
						<a href="HostingDetailsServlet.do?id=${Hosting.id}"
							class="nounderline"><img alt="HousePreview"
							onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/img/hostingNotFound.png'"
							src="${Hosting.image}"></a>
						<div class="housecellinfo">
							<i>&#9733; ${Hosting.likes} likes</i> <br> <a
								href="HostingDetailsServlet.do?id=${Hosting.id}"><b>${Hosting.title}</b></a>
						</div>
					</div>
				</c:forEach>
			</section>
		</section>
		<br>
		<section class="backgrounddiv">
			<h1>Publicaciones:</h1><form action="AddHostingServlet.do">
				<input type="hidden" value="true" name="new">
				<button type="submit" class="submitbutton">Añadir</button>
			</form>
			<section class="housecelllist">
				<!-- Mostrar listado de casas -->
				<c:forEach var="Hosting" items="${HostingsList}">
					<div class="housecell">
						<a href="HostingDetailsServlet.do?id=${Hosting.id}"
							class="nounderline"><img alt="HousePreview"
							onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/img/hostingNotFound.png'"
							src="${Hosting.image}"></a>
						<div class="housecellinfo housecellinfoedit">
							<div class="houseinfotitle">
								<i>&#9733; ${Hosting.likes} likes</i> <br> <a
									href="HostingDetailsServlet.do?id=${Hosting.id}"><b>${Hosting.title}</b></a>
							</div>
							<div class="houseinfoedit">
								<form method="post" action="EditHostingServlet.do">
									<input type="hidden" name="hid" value="${Hosting.id}"></input>
									<a href="#" onclick="this.parentNode.submit()">Editar &#9998;</a>
								</form>
								<form method="post" action="HostingUpdateServlet.do">
									<input type="hidden" name="hid" value="${Hosting.id}"></input>
									<input type="hidden" name="deletehosting" value="true"></input>
									<a href="#" onclick="this.parentNode.submit()">Eliminar &#10799;</a>
								</form>
							</div>
						</div>
					</div>
				</c:forEach>
			</section>
		</section>
	</div>
</body>

</html>