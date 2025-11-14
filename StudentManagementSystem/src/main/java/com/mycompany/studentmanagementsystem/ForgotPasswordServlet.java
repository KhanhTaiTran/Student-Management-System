package com.mycompany.studentmanagementsystem;

import com.mycompany.studentmanagementsystem.dao.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/forgot_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("new_password");

        UserDAO dao = new UserDAO();
        try {
            if (email == null || newPassword == null || email.isEmpty() || newPassword.isEmpty()) {
                request.setAttribute("error", "Email and new password are required.");
                request.getRequestDispatcher("/WEB-INF/jsp/forgot_password.jsp").forward(request, response);
                return;
            }

            if (!dao.emailExists(email)) {
                request.setAttribute("error", "Email not found.");
                request.getRequestDispatcher("/WEB-INF/jsp/forgot_password.jsp").forward(request, response);
                return;
            }

            boolean ok = dao.resetPasswordByEmail(email, newPassword);
            if (ok) {
                request.setAttribute("success", "Password reset. Please sign in.");
                request.getRequestDispatcher("/WEB-INF/jsp/login_page.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Could not reset password. Try again.");
                request.getRequestDispatcher("/WEB-INF/jsp/forgot_password.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/forgot_password.jsp").forward(request, response);
        }
    }

}
