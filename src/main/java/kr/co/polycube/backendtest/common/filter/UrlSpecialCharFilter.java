package kr.co.polycube.backendtest.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

// Url에 허용되지 않은 특수문자가 있는지 검사하는 필터
@Component
public class UrlSpecialCharFilter implements Filter {

    private static final String ALLOWED_PATTERN = "^[a-zA-Z0-9가-힣?&=:/._-]*$"; // 허용 문자

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestUri = URLDecoder.decode(req.getRequestURI(), StandardCharsets.UTF_8);
        String queryString = req.getQueryString();
        String fullUrl = requestUri;

        // 쿼리스트링이 존재하는 경우
        if (queryString != null) {
            fullUrl += "?" + URLDecoder.decode(queryString, StandardCharsets.UTF_8);
        }

        // 허용되지 않는 특수문자가 포함된 경우
        if (!fullUrl.matches(ALLOWED_PATTERN)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            res.getWriter().write("{\"reason\": \"허용되지 않는 특수문자가 포함되어 있습니다.\"}");
            res.getWriter().flush();
            return;
        }

        // 다음 필터 또는 컨트롤러로 요청 전달
        chain.doFilter(request, response);
    }
}

