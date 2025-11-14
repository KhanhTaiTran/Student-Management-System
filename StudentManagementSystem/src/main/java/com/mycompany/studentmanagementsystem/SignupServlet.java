package com.mycompany.studentmanagementsystem;

import com.mycompany.studentmanagementsystem.dao.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SignupServlet", urlPatterns = {"/signup"})
public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserDAO dao = new UserDAO();
        try {
            if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                request.setAttribute("error", "All fields are required.");
                request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
                return;
            }

            if (dao.usernameExists(username)) {
                request.setAttribute("error", "Username already exists.");
                request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
                return;
            }

            if (dao.emailExists(email)) {
                request.setAttribute("error", "Email already registered.");
                request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
                return;
            }

            boolean ok = dao.createUser(username, password, email, "student");
            if (ok) {
                request.setAttribute("success", "Account created. Please sign in.");
                request.getRequestDispatcher("/WEB-INF/jsp/login_page.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Could not create account. Try again.");
                request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
        }
    }

}
