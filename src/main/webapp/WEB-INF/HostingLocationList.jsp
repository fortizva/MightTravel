<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <title>Explorar - MightTravel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/location.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shared.css">
</head>

<body>
    <!-- Barra de navegación -->
    <div class="navbar">
        <a href="ExploreHostingsServlet.do" class="nounderline bannerLogoLink"><img alt="Logo" src="${pageContext.request.contextPath}/img/logoWhite.png" class="bannerlogo"></a>
        <a href="ExploreHostingsServlet.do" class="bannertitle">MightTravel</a>
        <form class="searchform" method="get" action="HostingLocationListServlet.do">
            <input type="text" placeholder="Buscar" name="q" class="fbannersearch" value="${q}">
            <select name="category" class="fbannerselect">
            	<option value="">Categoría</option>
            	<c:forEach var="Category" items="${Categories}">
            		<option value="${Category.id}"<c:if test="${not empty category && Category.id==category}"> selected</c:if>>${Category.name}</option>
            	</c:forEach> 
            </select>
            <select name="availability" class="fbannerselect">
            	<option value="">Disponibilidad</option>
           		<option value="1"<c:if test="${availability==1}"> selected</c:if>>Sólo disponibles</option>
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


    <div class="maindiv" style="background-color:blue; height:100%;">
        <div class="houselist split">
            <h2>${HostingsList.size()} alojamientos encontrados.</h2>
            <c:forEach var="Hosting" items="${HostingsList}">
            <hr>
	            <a href="HostingDetailsServlet.do?id=${Hosting.id}" class="nounderline">
	                <div class="houselistentry">
	                    <img alt="HousePreview"  onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/img/hostingNotFound.png'" src="${Hosting.image}">
	                    <div>
	                        <h3>${Hosting.title}</h3>
	                        <i>&#9733; ${Hosting.likes} likes</i>
	                    </div>
	                </div>
	            </a>
            </c:forEach>            
        </div>
        <div class="houselistmapdiv split">
            <iframe class="houselistmap"
                src="https://www.google.com/maps/embed/v1/search?key=${MAPS_API_KEY}&q=${Location}"
                allowfullscreen=""></iframe>
        </div>
    </div>
</body>

</html>