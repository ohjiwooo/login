package hello.login.web.session;



import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
//세션관리
@Component
public class SessionManager {


    public static final String SESSION_COOKIE_NAME = "Session_cookie_name";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();//동시성 이슈가 있을때 사용

    /**
     * 세션생성
     */

    public void createSession(Object value, HttpServletResponse response) {

        //세션 id 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    /**
     * 세션 조회
     */

    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);

    }

    /**
     * 세션 만료
     */

    public void expire(HttpServletRequest request){
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);
        if(cookie != null){
            sessionStore.remove(cookie.getValue());
        }
    }
}