package org.example.algosolve.user.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.global.exception.response.ErrorCode;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class JwtAuthenticationExceptionProvider implements JwtAuthenticationProvider{

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public Result authenticationToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            return jwtAuthenticationProvider.authenticationToken(request,response);
        } catch (AuthenticationException authenticationException) {
            ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
            return new Result(errorCode, authenticationException.getMessage(), request.getRequestURI(), authenticationException);
        } catch (ExpiredJwtException e) {
            ErrorCode errorCode = ErrorCode.EXPIRED_TOKEN;
            return new Result(errorCode, "만료된 토큰입니다.", request.getRequestURI(), e);
        } catch (UnsupportedJwtException | SignatureException | MalformedJwtException e) {
            ErrorCode code = ErrorCode.INVALID_TOKEN;
            return new Result(code, "토큰이 손상 되었거나 알 수 없습니다", request.getRequestURI(), e);
        } catch (IllegalArgumentException e) {
            ErrorCode code = ErrorCode.BAD_REQUEST;
            return new Result(code, e.getMessage(), request.getRequestURI(),e);
        }
    }
}
