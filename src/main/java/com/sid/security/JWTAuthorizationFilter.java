package com.sid.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        setResponseHeaders(response);

        if(request.getMethod().equals("OPTIONS"))
            response.setStatus(HttpServletResponse.SC_OK);
        else {
            String jwt = request.getHeader(SecurityParameters.HEADER);
            if(jwt == null || !jwt.startsWith(SecurityParameters.TOKEN_PREFIX)){
                filterChain.doFilter(request, response);
                return;
            }

            String encodedSecret = new String(Base64.getEncoder().encode(SecurityParameters.SECRET.getBytes()));

            Claims claims = Jwts.parser()
                    .setSigningKey(encodedSecret)
                    .parseClaimsJws(jwt.substring(SecurityParameters.TOKEN_PREFIX.length()))
                    .getBody();

            String username = claims.getSubject();

            ArrayList<Map<String, String>> roles;
            roles = (ArrayList<Map<String, String>>) claims.get(SecurityParameters.CLAIMS_NAME);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.get("authority"))));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);

        }
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type," +
                "Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Authorization," +
                "Access-Control-Allow-Credentials");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    }
}
