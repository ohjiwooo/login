package hello.login.domain.login;


import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepo memberRepo;

    public Member login(String id, String pass){
       /* Optional<Member> findMember = memberRepo.findByLoginId(id);
        Member member = findMember.get(); // optional이라서
        if(member.getPassword().equals(pass)){
            return member;
        }return null;
        */
        return memberRepo.findByLoginId(id).filter( m -> m.getPassword().equals(pass))
                .orElse(null);

    }
}
