package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager=new SessionManager();

    @Test
    void createSession() {

        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member,response);

        //요청에 응답 쿠키
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션조회
        Object session = sessionManager.getSession(request);
        Assertions.assertThat(session).isEqualTo(member);

        //세션만료
        sessionManager.expire(request);
        Object session2 = sessionManager.getSession(request);
        Assertions.assertThat(session2).isNull();;

    }

    @Test
    void getSession() {
    }

    @Test
    void expire() {
    }
}