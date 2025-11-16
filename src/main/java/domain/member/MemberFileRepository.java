package domain.member;

import java.io.IOException;
import java.util.List;
import untils.CsvReader;

public class MemberFileRepository implements MemberRepository {
    private Long sequence;

    public MemberFileRepository() {
        try {
            List<Member> members = CsvReader.readCsv();
            this.sequence = readNextSequence(members);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Long readNextSequence(List<Member> members) {
        return members.stream().mapToLong(Member::getId).max().orElse(-1L) + 1L;
    }

    @Override
    public void save(Member member) throws IOException {
        member.setId(sequence);
        CsvReader.writeCsv(member);
        ++sequence;
    }

    @Override
    public List<Member> findAll() throws IOException {
        return CsvReader.readCsv();
    }

    @Override
    public Member findByNickName(String nickname) {
        return Member.from("제이");
    }

    @Override
    public Member findById(long id) {
        return null;
    }

    @Override
    public Boolean existsBy(String nickname) throws IOException {
        return CsvReader.existsCsv(nickname);
    }
}
