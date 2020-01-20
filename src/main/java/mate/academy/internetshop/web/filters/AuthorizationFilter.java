package mate.academy.internetshop.web.filters;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

public class AuthorizationFilter  implements Filter {
    @Inject
    private static UserService userService;

    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/getAllUsers", ADMIN);
        protectedUrls.put("/servlet/deleteUser", ADMIN);
        protectedUrls.put("/servlet/deleteItem", ADMIN);
        protectedUrls.put("/servlet/addItem", ADMIN);
        protectedUrls.put("/servlet/showAllItems", USER);
        protectedUrls.put("/servlet/addItemToBucket", USER);
        protectedUrls.put("/servlet/showBucket", USER);
        protectedUrls.put("/servlet/showOrder", USER);
        protectedUrls.put("/servlet/deleteOrder", USER);
        protectedUrls.put("/servlet/complete", USER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String userToken = (String) req.getSession(true).getAttribute("token");
        Role.RoleName roleName = protectedUrls.get(req.getServletPath());
        if (roleName == null) {
            processAuthenticated(chain, req, resp);
            return;
        }
        User user = userService.getByToken(userToken).get();
        if (verifyRole(user, roleName)) {
            processAuthenticated(chain, req, resp);
        } else {
            processDenied(req, resp);
        }
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }

    private void processAuthenticated(FilterChain chain,
                                      HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
