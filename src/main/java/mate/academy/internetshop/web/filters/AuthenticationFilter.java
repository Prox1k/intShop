package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

import org.apache.log4j.Logger;

public class AuthenticationFilter implements Filter {
    private static Logger logger = Logger.getLogger(AuthenticationFilter.class);

    @Inject
    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        if (session == null) {
            processUnAuthenticated(req, resp);
            return;
        }
        String userToken = (String) req.getSession(true).getAttribute("token");
        if (userToken == null) {
            logger.info("session without UserToken");
            processUnAuthenticated(req, resp);
            return;
        }
        Optional<User> user = userService.getByToken(userToken);
        if (user.isPresent()) {
            logger.info("User " + user.get().getLogin() + " was authenticated.");
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        logger.info("User was not authenticated");
        processUnAuthenticated(req, resp);
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
