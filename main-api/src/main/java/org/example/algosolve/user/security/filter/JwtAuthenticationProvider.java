package org.example.algosolve.user.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.algosolve.user.security.filter.Result;

public interface JwtAuthenticationProvider {
    Result authenticationToken(HttpServletRequest request, HttpServletResponse response);
}
