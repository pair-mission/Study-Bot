package repository.member;

import static global.enums.ErrorMessage.INVALID_FILE;

import domain.member.Member;
import global.exception.DataAccessException;
import global.utils.CsvReader;
import global.utils.parser.MemberParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemberFileRepository implements MemberRepository {

    public static final String MEMBER_FILE_PATH = "src/main/resources/members.csv";
    private final Map<Long, Member> members = new HashMap<>();
    private Long sequence;

    public MemberFileRepository() {
        try {
            List<Member> csvMembers = CsvReader.readCsv(MEMBER_FILE_PATH, new MemberParser());

            for (Member member : csvMembers) {
                this.members.put(member.getId(), member);
            }

            this.sequence = readNextSequence(csvMembers);
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    private Long readNextSequence(List<Member> members) {
        return members.stream().mapToLong(Member::getId).max().orElse(-1L) + 1L;
    }

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

}
