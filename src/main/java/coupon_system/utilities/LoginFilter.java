package coupon_system.utilities;

import coupon_system.entities.Token;
import coupon_system.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter("/*")
public class LoginFilter implements Filter {

    private TokenRepository tokenRepository;

    @Autowired
    public LoginFilter(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURL().toString();
        if (!path.contains("findByNameAndPassword")) {
            if (!path.contains("registration")) {
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if ("auth".equals(c.getName())) {
                            Token token = tokenRepository.findByToken(c.getValue());
                            if (token == null) {
                                res.sendRedirect("http://localhost:4200");
                                return;
                            }
                        }
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
