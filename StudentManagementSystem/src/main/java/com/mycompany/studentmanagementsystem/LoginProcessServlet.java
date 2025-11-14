package com.mycompany.studentmanagementsystem;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginProcessServlet", urlPatterns = {"/login"})
public class LoginProcessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login_page.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login_page.jsp");
            rd.forward(request, response);
            return;
        }

        boolean authenticated = "admin".equals(username) && "admin".equals(password);

        if (!authenticated) {
            request.setAttribute("error", "Invalid username or password.");
            request.setAttribute("username", username);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login_page.jsp");
            rd.forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", username);

        if (remember != null && !remember.isEmpty()) {
            Cookie c = new Cookie("remember_user", username);
            c.setMaxAge(60 * 60 * 24 * 30);
            c.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            response.addCookie(c);
        } else {
            Cookie c = new Cookie("remember_user", "");
            c.setMaxAge(0);
            c.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            response.addCookie(c);
        }

        String context = request.getContextPath();
        response.sendRedirect(context + "/");
    }

}
