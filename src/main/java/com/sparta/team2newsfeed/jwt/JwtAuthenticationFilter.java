package com.sparta.team2newsfeed.jwt;

import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.team2newsfeed.service.UserDetailsService;
import com.sparta.team2newsfeed.dto.StatusResponseDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter : 요청이 한번 올 때 마다 필터를 태우겠다.

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 토큰 뽑아오기
        String token = jwtUtil.resolveToken(request);

        if(Objects.nonNull(token)){
            if(jwtUtil.validateToken(token)){
                Claims info = jwtUtil.getUserInfoFromToken(token);
                // 인증정보에 유저정보 (username) 넣기
                // username -> user 조회 -> userDetails 에 담고 -> authentication 의 principal 에 답고
                String username = info.getSubject();
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                // 보안 관련된 컨텍스트 홀더를 들고있음
                UserDetails userDetails = userDetailsService.getUserDetails(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                // 3번째 값으로 Authorities 값을 넣어줘야 정상적으로 인증이 완료 되었다고 뒤에 username password 필터에서 인식하도록 되어있음
                // -> securityContext 에 담고
                context.setAuthentication(authentication);
                // -> SecurityContextHolder 에 담고
                SecurityContextHolder.setContext(context);
                // -> 이제 @AuthenticationPrincipal 로 조회할 수 있음

            }else {
                // 인증정보가  존재하지 않을때
                StatusResponseDto statusResponseDto = new StatusResponseDto("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(statusResponseDto));
                // write 타입은 스트링 이므로 ,objectMapper 를 통해 맵핑 해서 String 으로변환
                return;
                // 더 필터를 타지 않고 에러응답을 리턴시킴
            }
        }

        filterChain.doFilter(request,response);
    }

}