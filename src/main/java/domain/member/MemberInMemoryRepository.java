package domain.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberInMemoryRepository implements MemberRepository {
    private final Map<Long, Member> members = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public void save(Member member) {
        member.setId(sequence);
        members.put(sequence, member);
    }

    @Override
    public List<Member> findAll() {
        return members.values().stream().toList();
    }

    @Override
    public Boolean existsBy(String nickname) {
        return members.containsKey(nickname);
    }
}
