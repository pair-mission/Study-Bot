package repository.member;

import domain.member.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemberInMemoryRepository implements MemberRepository {
    private final Map<Long, Member> members = new HashMap<>();
    private Long sequence = 0L;


    @Override
    public Member save(Member member) {
        Member newMember = Member.of(sequence, member.getNickname(), member.getRemindDay());
        members.put(sequence, newMember);
        sequence++;
        return newMember;
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
    public Optional<Member> findByNickName(String nickname) {
        return members.values().stream()
                .filter(member -> member.isSameNickname(nickname)).findFirst();
    }

    @Override
    public Optional<Member> findById(long id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public void update(Member member) {

    }
}
