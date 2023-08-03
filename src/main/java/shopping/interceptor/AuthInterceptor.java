package shopping.interceptor;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;
import shopping.utils.JwtProvider;

public class AuthInterceptor implements HandlerInterceptor {

    public static final String BEARER_TOKEN_TYPE = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_ID = "userId";
    private final JwtProvider jwtProvider;

    public AuthInterceptor(final JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) {

        final String header = request.getHeader(AUTHORIZATION);
        validateAuthorizationHeader(header);
        validateTokenType(header);

        String token = header.substring(BEARER_TOKEN_TYPE.length());
        validateToken(token);

        Long userId = Long.valueOf(jwtProvider.parseToken(token));
        request.setAttribute(USER_ID, userId);
        return true;
    }

    private void validateToken(final String token) {
        if (!jwtProvider.validate(token)) {
            throw new ShoppingException(ErrorCode.INVALID_TOKEN);
        }
    }

    private void validateAuthorizationHeader(final String header) {
        if (Objects.isNull(header)) {
            throw new ShoppingException(ErrorCode.NO_AUTHENTICATION_HEADER);
        }
    }

    private void validateTokenType(final String header) {
        if (!header.startsWith(BEARER_TOKEN_TYPE)) {
            throw new ShoppingException(ErrorCode.INVALID_TOKEN_TYPE);
        }
    }
}
