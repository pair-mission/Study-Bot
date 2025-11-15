package domain.member;

import java.util.List;

public interface MemberRepository {
    void save(Member member);

    List<Member> findAll();

    Boolean existsBy(String nickname);
}
