package domain.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberInMemoryRepository implements MemberRepository {
    private final Map<Long, Member> members = new HashMap<>();
    private Long sequence = 2L;

    public MemberInMemoryRepository() {
        members.put(0L, Member.of(0L, "제오"));
        members.put(1L, Member.of(1L, "제이"));
    }

    @Override
    public void save(Member member) {
        member.setId(sequence);
        members.put(sequence, member);
        sequence++;
    }

    @Override
    public List<Member> findAll() {
        return members.values().stream().toList();
    }

    @Override
    public Boolean existsBy(String nickname) {
        return members.containsKey(nickname);
    }

    @Override
    public Member findByNickName(String nickname) {
        return members.values().stream()
                .filter(member -> member.isSameNickname(nickname)).findFirst().orElse(null);
    }

    @Override
    public Member findById(long id) {
        return members.get(id);
    }
}
