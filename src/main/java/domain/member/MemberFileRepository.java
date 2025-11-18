package domain.member;

import global.exception.DataAccessException;
import global.utils.CsvReader;
import global.utils.parser.MemberParser;

import java.io.IOException;
import java.util.List;

import static global.enums.ErrorMessage.INVALID_FILE;

public class MemberFileRepository implements MemberRepository {
    private static final String MEMBER_FILE_PATH = "src/main/resources/members.csv";
    private Long sequence;

    public MemberFileRepository() {
        try {
            List<Member> members = CsvReader.readCsv(MEMBER_FILE_PATH, new MemberParser());
            this.sequence = readNextSequence(members);
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    private Long readNextSequence(List<Member> members) {
        return members.stream().mapToLong(Member::getId).max().orElse(-1L) + 1L;
    }

    @Override
    public void save(Member member) {
        try {
            Member newMember = Member.of(sequence, member.getNickname());
            CsvReader.writeCsv(newMember, MEMBER_FILE_PATH, new MemberParser());
            sequence++;
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    @Override
    public List<Member> findAll() {
        try {
            return CsvReader.readCsv(MEMBER_FILE_PATH, new MemberParser());
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    @Override
    public Member findByNickName(String nickname) {
        try {
            return CsvReader.readCsv(MEMBER_FILE_PATH, new MemberParser()).stream()
                    .filter(member -> member.isSameNickname(nickname)).findFirst().orElse(null);
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    @Override
    public Member findById(long id) {
        try {
            return CsvReader.readCsv(MEMBER_FILE_PATH, new MemberParser()).stream()
                    .filter(member -> member.isSameId(id)).findFirst().orElse(null);
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    @Override
    public Boolean existsBy(String nickname) {
        try {
            return CsvReader.existsCsv(nickname);
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }
}
