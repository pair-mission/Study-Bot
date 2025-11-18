package repository.member;

import domain.member.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);

    List<Member> findAll();

    Member findByNickName(String nickname);

    Member findById(long id);

    Boolean existsBy(String nickname);
}
