<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Edit Car">
    <h1>Edit Car</h1>
    <form method="POST" action="${pageContext.request.contextPath}/EditCar">
        <input type="hidden" name="car_id" value="${car.id}">

        <div class="mb-3">
            <label for="license_plate" class="form-label">License Plate</label>
            <input type="text" class="form-control" id="license_plate" name="license_plate" value="${car.licensePlate}" required>
        </div>

        <div class="mb-3">
            <label for="parking_spot" class="form-label">Parking Spot</label>
            <input type="text" class="form-control" id="parking_spot" name="parking_spot" value="${car.parkingSpot}" required>
        </div>

        <div class="mb-3">
            <label for="owner_id" class="form-label">Owner</label>
            <select class="form-control" id="owner_id" name="owner_id" required>
                <c:forEach var="user" items="${users}">
                    <option value="${user.id}" ${car.ownerName eq user.username ? 'selected' : ''}>${user.username}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Save Changes</button>
        <a href="${pageContext.request.contextPath}/Cars" class="btn btn-secondary">Cancel</a>
    </form>
</t:pageTemplate>