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
	href="${pageContext.request.contextPath}/css/edithostingpanel.css">
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
            <form action="UserPanelServlet.do" method="POST" class="banneraccount">
        		<button type="submit" class="submitbutton banneraccount">Panel</button>
       		</form>
        </c:otherwise>
        </c:choose>
    </div>


	<div class="maindiv">
		<section class="userpanelcontainer backgrounddiv">
			<h1>Datos de hosting:</h1>
			<form class="panelEditForm" method="post" action="HostingUpdateServlet.do">
				<input type="hidden" name="hid" value="${Hosting.id}"></input>
				<label for="ftitle" class="ledithosting"><b>Título:</b></label>
				<input type="text" id="ftitle" class="ftitle" name="title" value="${Hosting.title}" required>
				<br class="separator">
				<label for="fdescription" class="ledithosting"><b>Descripción:</b></label>
				<textarea id="fdescription" class="fdescription" name="description" rows="8" cols="30"required>${Hosting.description}</textarea>
				<br class="separator">
				<label for="ftelephone" class="ledithosting"><b>Teléfono:</b></label>
				<input type="text" id="ftelephone" class="ftelephone" name="telephone" value="${Hosting.telephone}" required>
				<br class="separator">
				<label for="flocation" class="ledithosting"><b>Ubicación:</b></label>
				<textarea id="flocation" class="flocation" name="location" required>${Hosting.location}</textarea>
				<br class="separator">
				<label for="femail" class="ledithosting"><b>Email de contacto:</b></label>
				<input type="email" id="femail" class="femail" name="email" value="${Hosting.contactEmail}" required>
				<br class="separator">
				<label for="fimage" class="ledithosting"><b>Imagen (URL):</b></label>
				<input type="text" id="fimage" class="fimage" name="image" value="${Hosting.image}">
				<br class="separator">
				<label for="favailable" class="ledithosting"><b>Disponibilidad:</b></label>
				<select name="available">
					<option value="1" <c:if test="${Hosting.available==1}">selected="selected"</c:if>>Disponible</option>
					<option value="0" <c:if test="${Hosting.available!=1}">selected="selected"</c:if>>Reservado</option>
				</select>
				<br class="separator">
				<label for="fprice" class="ledithosting"><b>Precio:</b></label>
				<input type="number" id="fprice" class="fprice" name="price" value="${Hosting.price}" min="0" required>
				<br class="separator">
				<label class="ledithosting"><b>Servicios:</b></label>
				<div class="checkboxlist">
			    	<c:forEach var="Service" items="${ServicesList}">
			    		<div>
				    		<input type="checkbox" id="srv${Service.id}" name="srv${Service.id}" value="1"<c:if test="${CheckedServicesList.contains(Service.id)}"> checked="checked"</c:if>>
				    		<label for="srv${Service.id}">${Service.name}</label>
				    	</div>
			    	</c:forEach>
			    </div>
			    <br class="separator">
			    <br class="separator">
				<label class="ledithosting"><b>Categorías:</b></label>
				<div class="checkboxlist">
			    	<c:forEach var="Category" items="${Categories}">
			    		<div>
				    		<input type="checkbox" id="srv${Category.id}" name="cat${Category.id}" value="1"<c:if test="${CheckedCategoriesList.contains(Category.id)}"> checked="checked"</c:if>>
				    		<label for="cat${Category.id}">${Category.name}</label>
				    	</div>
			    	</c:forEach>
			    </div>
				<button type="submit" class="submitbutton fullwidthbutton">Guardar</button>
			</form>
		</section>
	</div>
</body>

</html>