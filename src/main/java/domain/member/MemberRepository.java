package domain.member;

import java.util.List;

public interface MemberRepository {
    void save(Member member);

    List<Member> findAll();

    Member findByNickName(String nickname);

    Member findById(long id);

    Boolean existsBy(String nickname);
}
