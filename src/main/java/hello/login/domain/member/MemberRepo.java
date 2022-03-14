package hello.login.domain.member;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepo {

    private static Map<Long,Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String id){
    /*    List<Member> all = findAll();
        for (Member member : all) {
            if(member.getLoginId().equals(id)){
                return Optional.of(member);
            }
        }
        return Optional.empty();*/

        return findAll().stream()
                .filter(m -> m.getLoginId().equals(id))
                .findFirst();
    }

}
