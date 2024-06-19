package roomescape.member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.member.dto.LoginMemberRequestDto;
import roomescape.member.dto.MemberResponseDto;
import roomescape.member.dto.TokenResponseDto;

@RestController
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final AuthService authService;

    public MemberController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginMemberRequestDto loginMemberRequestDto, HttpServletResponse response) {
        log.info("loginMemberID :" + loginMemberRequestDto.getEmail());
        TokenResponseDto tokenResponseDto = authService.createToken(loginMemberRequestDto);

        Cookie cookie = new Cookie("token", tokenResponseDto.getAccessToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/check")
    public ResponseEntity<MemberResponseDto> getMember(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = extractTokenFromCookie(cookies);
        MemberResponseDto memberResponseDto = authService.findMemberName(token);
        log.info("로그인 계정의 Name : " + memberResponseDto.getName());
        return ResponseEntity.ok().body(memberResponseDto);
    }

    private String extractTokenFromCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                return cookie.getValue();
            }
        }
        throw new AuthorizationException("토큰 정보가 없습니다.");
    }
}
