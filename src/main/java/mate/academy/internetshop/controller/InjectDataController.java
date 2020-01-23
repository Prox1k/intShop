package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InjectDataController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName("user");
        user.setSurname("userSur");
        user.addRole(Role.of("USER"));
        user.setLogin("user");
        user.setPassword("user");
        userService.create(user);

        User admin = new User();
        admin.setName("admin");
        admin.setSurname("adminSur");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("admin");
        admin.setPassword("admin");
        userService.create(admin);

        resp.sendRedirect(req.getContextPath() + "/servlet/mainMenu");
    }
}

