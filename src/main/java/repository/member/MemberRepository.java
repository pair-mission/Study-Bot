package repository.member;

import domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    List<Member> findAll();

    Optional<Member> findByNickName(String nickname);

    Optional<Member> findById(long id);

    Boolean existsBy(String nickname);
}
