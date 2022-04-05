<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <title>${hosting.title} - MightTravel</title> <!-- El título cambiará según la casa seleccionada -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/details.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shared.css">
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
        <section class="basicinfocontainer backgrounddiv">
            <h1>${hosting.title}</h1>
            <h5>Categorias:
            <c:forEach var="Category" items="${CheckedCategoriesList}" varStatus="loopStatus">${Category.name}<c:if test="${!loopStatus.last}"> | </c:if></c:forEach>
            </h5>
            <img id="imagedetails" alt="HouseImage" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/img/hostingNotFound.png'" src="${hosting.image}">
        </section>
        <section class="detailscontainer">
            <div class="infodiv backgrounddiv">
                <h3>Descripción:</h3>
                ${hosting.description}
                <hr>
                <h3>Servicios:</h3>
                <table class="rowtable">
                    <tr>
                        <th>Servicio</th>
                        <th>Disponible</th>
                    </tr>
                    <c:forEach var="Service" items="${ServicesList}">
	                    <tr>
	                        <td>${Service.name}</td>
	                        <td>Sí</td>
	                    </tr>
                    </c:forEach>
                </table>
                <hr>
                <h3>Contacto:</h3>
                <b>Teléfono:</b> ${hosting.telephone}
                <br>
                <b>Correo:</b> ${hosting.contactEmail}

            </div>
            <div class="pricediv">
                <div class="backgrounddiv">
                    <h2><c:choose><c:when test="${hosting.price==null || hosting.price==0}">--</c:when><c:otherwise>${hosting.price }</c:otherwise></c:choose>€/noche</h2>
                    <h4><c:choose><c:when test="${hosting.available!=1 }">Reservado</c:when><c:otherwise>Disponible</c:otherwise></c:choose></h4>
                    <h4>&#9733; ${hosting.likes} likes - <form method="post" style="display:inline-block" action="AddHostingFavoritesServlet.do"><input type="hidden" name="hid" value="${hosting.id}"></input><a href="#" onclick="this.parentNode.submit()" class="add<c:choose><c:when test="${favorite==false}">fav">&#9825; Añadir a</c:when><c:otherwise>edfav">&#9825; Quitar de</c:otherwise></c:choose> favoritos</a></form></h4>
                    <hr>
                    <h3>Reserva:</h3>
                    <form>
                        <label for="fllegada">Llegada:</label>
                        <input <c:if test="${hosting.available!=1 }">disabled="disabled"</c:if> type="date" id="fllegada" class="freserva" required>
                        <label for="fsalida">Salida:</label>
                        <input <c:if test="${hosting.available!=1 }">disabled="disabled"</c:if> type="date" id="fsalida" class="freserva" required>
                        <label for="fhuesped">Huéspedes:</label>
                        <input <c:if test="${hosting.available!=1 }">disabled="disabled"</c:if> type="number" id="fhuesped" class="freserva" step=1 min=1 value=1 required>
                        <button class="submitbutton<c:if test="${hosting.available!=1 }"> disabledsubmitbutton" disabled="disabled" title="No es posible reservar ahora mismo</c:if>" type="submit" id="reservabutton">Reserva</button>
                    </form>
                </div>
            </div>
        </section>
    </div>
</body>

</html>