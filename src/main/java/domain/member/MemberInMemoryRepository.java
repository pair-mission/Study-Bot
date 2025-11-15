package domain.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberInMemoryRepository implements MemberRepository {

    private final Map<String, Member> members = new HashMap<>();

    @Override
    public Member save() {
        return null;
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public Boolean existsBy(String nickname) {
        return members.containsKey(nickname);
    }
}
