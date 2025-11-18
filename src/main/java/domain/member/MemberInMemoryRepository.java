package domain.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberInMemoryRepository implements MemberRepository {
    private final Map<Long, Member> members = new HashMap<>();
    private Long sequence = 2L;

    @Override
    public void save(Member member) {
        Member newMember = Member.of(sequence, member.getNickname());
        members.put(sequence, newMember);
        sequence++;


    }

    @Override
    public List<Member> findAll() {
        return members.values().stream().toList();
    }

    @Override
    public Boolean existsBy(String nickname) {
        return members.values().stream().anyMatch(member -> member.getNickname().equals(nickname));
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
