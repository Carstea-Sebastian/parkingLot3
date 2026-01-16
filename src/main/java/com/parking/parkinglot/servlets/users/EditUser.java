package com.parking.parkinglot.servlets.users;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.UsersBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Users");
            return;
        }

        Long userId = Long.parseLong(idParam);
        UserDto user = usersBean.findById(userId);

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Users");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/pages/users/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("user_id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Users");
            return;
        }

        Long userId = Long.parseLong(idParam);
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        usersBean.updateUser(userId, username, email, password);

        response.sendRedirect(request.getContextPath() + "/Users");
    }
}