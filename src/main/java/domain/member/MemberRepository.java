package domain.member;

import java.io.IOException;
import java.util.List;

public interface MemberRepository {
    void save(Member member) throws IOException;

    List<Member> findAll() throws IOException;

    Member findByNickName(String nickname) throws IOException, ClassNotFoundException;

    Member findById(long id);

    Boolean existsBy(String nickname);
}
