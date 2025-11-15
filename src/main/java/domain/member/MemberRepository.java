package domain.member;

import java.util.List;

public interface MemberRepository {
    Member save();

    List<Member> findAll();

    Boolean existsBy(String nickname);
}
