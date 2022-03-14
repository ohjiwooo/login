package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepo memberRepo;

    //@GetMapping("/")
    public String home() {
        return "home";
    }


    //로그인하고 정보를 띄워주자~~
    @GetMapping("/")
    public String homeLogin(@CookieValue(name="memberId",required = false) Long memberId, Model model){
        if(memberId==null){
            return "home";
        }
        Member member = memberRepo.findById(memberId);
        if(member==null){
            return "home";
        }

        model.addAttribute("member",member);
        return "loginHome";
    }
}