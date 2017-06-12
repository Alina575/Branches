package ru.kpfu.itis.javalab.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import ru.kpfu.itis.javalab.rest.DBConnection;

public class HelloRest extends HttpServlet {

    public static final String PARAMETER_USERNAME = "username";
    public static final String PARAMETER_EMAIL = "email";
    public static final String PARAMETER_PHONE = "phone";

    private String createMessageTemplate;

    @Override
    public void init() throws ServletException {
        Properties props = new Properties();
        try (InputStream in = getClass().getResourceAsStream("/messages.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new ServletException("Error reading 'messages.properties'", e);
        }
        createMessageTemplate = props.getProperty("create.message", "User %s created.");
    }

    /*
    //FIXME: send error when name
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter(PARAMETER_USERNAME);
        String email = req.getParameter(PARAMETER_EMAIL);
        String phone = req.getParameter(PARAMETER_PHONE);

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (id, name, email, phone) " +
                    "VALUES((SELECT COALESCE(max(id),30) FROM users)+1, ?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.execute();
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        String message = String.format(createMessageTemplate, username);
        resp.setContentType("text/plain; charset=utf8");
        resp.getWriter().write(message);
    }

     /*

    ?username=' + encodeURIComponent(this.state.username)
            + '&email=' + encodeURIComponent(this.state.email)
            + '&phone=' + encodeURIComponent(this.state.phone)
 */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter(PARAMETER_USERNAME);
        String email = req.getParameter(PARAMETER_EMAIL);
        String phone = req.getParameter(PARAMETER_PHONE);
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (id, name, email, phone) " +
                    "VALUES((SELECT COALESCE(max(id),30) FROM users)+1, ?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.execute();
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String message = String.format(createMessageTemplate, username);
        resp.setContentType("text/plain; charset=utf8");
        resp.getWriter().write(message);
    }


}
