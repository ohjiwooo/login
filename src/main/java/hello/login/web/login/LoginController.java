package hello.login.web.login;


import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm){
        return "login/loginForm";
    }

    @PostMapping("/login")
    /*public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        //global 오류는 코드로 처리하는게 깔끔함
        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }
        //쿠키처리~~ 시간정보를 주지않음으로써 세션쿠키로 설정

        Cookie cookie = new Cookie("memberId", String.valueOf(loginMember.getId()));//long -> string
        response.addCookie(cookie);

        return "redirect:/";
    }*/
/*
    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        //global 오류는 코드로 처리하는게 깔끔함
        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }
        //쿠키처리~~ 시간정보를 주지않음으로써 세션쿠키로 설정

        sessionManager.createSession(loginMember,response);
        return "redirect:/";
    }*/
    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        //global 오류는 코드로 처리하는게 깔끔함
        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }
        //세션이 있으면 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }

    @PostMapping("/logout")
    /*public String logout(HttpServletResponse response){
        response.addCookie(getCookie());
        return "redirect:/";

    }
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";

    }*/
    public String logoutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false); // 가저오는데 없으면 만들지마
        if(session!=null){
         session.invalidate(); //있으면 없애
        }
        return "redirect:/";
    }


    private Cookie getCookie() {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        return cookie;
    }
}
