package com.ptak.Moonshiner.Security.filter;

import com.ptak.Moonshiner.Production.Model.Production;
import com.ptak.Moonshiner.Security.Service.MyUserDetailsService;
import com.ptak.Moonshiner.Security.model.TokenValidateRequest;
import com.ptak.Moonshiner.Security.model.TokenValidateResponse;
import com.ptak.Moonshiner.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    RestTemplate restTemplate;

    @Value("${authentication.service.name}")
    private String authenticationServiceName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;

        if (authorizationHeader != null) {
            username = jwtUtil.extractUsername(authorizationHeader);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            TokenValidateResponse tokenValidateResponse = validateTokenByAuthorizationService(authorizationHeader, username);
            UserDetails userDetails = tokenValidateResponse.getUser();
            if (tokenValidateResponse.isValid()) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    private TokenValidateResponse validateTokenByAuthorizationService(String token, String username) {
        Map parameters = new HashMap<String, TokenValidateRequest>();
        parameters.put("tokenValidateRequest", new TokenValidateRequest(username, token));
        TokenValidateRequest tokenValidateRequest = new TokenValidateRequest(username, token);
       /* TokenValidateResponse tokenValidateResponse = webClientBuilder.build()
                .post()
                .uri("http://" + authenticationServiceName + "/security/validate/")
                .body(Mono.just(new TokenValidateRequest(username, token)), TokenValidateRequest.class)
                .retrieve()
                .bodyToMono(TokenValidateResponse.class)
                .block();*/

        TokenValidateResponse tokenValidateResponse= restTemplate.postForObject("http://" + authenticationServiceName + "/security/validate",tokenValidateRequest, TokenValidateResponse.class);
      /*  (builder -> builder.scheme("http")
                .host("authenticationServiceName").path("security/validate")
                .queryParam("tokenValidateRequest", new TokenValidateRequest(username,token)))*/
        return tokenValidateResponse;
    }

}
