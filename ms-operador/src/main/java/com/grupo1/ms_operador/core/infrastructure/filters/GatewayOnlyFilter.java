package com.grupo1.ms_operador.core.infrastructure.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GatewayOnlyFilter implements Filter {

    @Value("${security.gateway.secret}")
    private String gatewaySecret;

    @Value("${security.swagger.endpoint}")
    private String swaggerEndpoint;

    @Value("${security.swagger.config-endpoint}")
    private String swaggerConfigEndpoint;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String secretHeader = httpRequest.getHeader("X-Gateway-Secret");

        // Permitir acceso a Swagger
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.startsWith(swaggerEndpoint) || requestURI.startsWith(swaggerConfigEndpoint)) {
            chain.doFilter(request, response);
            return;
        }

        // Bloquea la petición si no contiene el header correcto
        if (secretHeader == null || !secretHeader.equals(gatewaySecret)) {
            throw new SecurityException("Acceso denegado. Debes pasar por el API Gateway.");
        }

        chain.doFilter(request, response);
    }
}
