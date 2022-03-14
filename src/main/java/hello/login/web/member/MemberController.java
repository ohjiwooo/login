package hello.login.web.member;


import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepo memberRepo;

    @GetMapping("/add")
    private String addForm(@ModelAttribute Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/addMemberForm";
        }

        memberRepo.save(member);
        return "redirect:/";
    }
}
