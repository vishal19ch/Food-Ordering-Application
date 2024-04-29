package com.zosh.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.util.List;
@Slf4j

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt=request.getHeader(JwtConstant.JWT_HEADER);

        if(jwt!=null) {
            jwt = jwt.substring(7);
            logger.info("before execution");
            try {
                log.info("aahkahdckhskkshksdkskkshkhskh");
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                log.info("step2");
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                log.info("step3");
                String email = String.valueOf(claims.get("email"));
                log.info("step4");

                String authorities = String.valueOf((claims.get("authorities")));
                log.info("step5");
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                log.info("step6");
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
                log.info("step7");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("step8");

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token");

            }
        }
        filterChain.doFilter(request,response);

    }
}
