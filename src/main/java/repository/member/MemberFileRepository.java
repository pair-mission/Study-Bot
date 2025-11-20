package repository.member;

import static global.enums.ErrorMessage.INVALID_FILE;

import domain.member.Member;
import global.exception.DataAccessException;
import global.utils.CsvReader;
import global.utils.parser.MemberParser;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public Member save(Member member) {
        try {
            Member newMember = Member.of(sequence++, member.getNickname());
            CsvReader.writeCsv(newMember, MEMBER_FILE_PATH, new MemberParser());
            return newMember;
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
    public Optional<Member> findByNickName(String nickname) {
        try {
            return CsvReader.readCsv(MEMBER_FILE_PATH, new MemberParser()).stream()
                    .filter(member -> member.isSameNickname(nickname)).findFirst();
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    @Override
    public Optional<Member> findById(long id) {
        try {
            return CsvReader.readCsv(MEMBER_FILE_PATH, new MemberParser()).stream()
                    .filter(member -> member.isSameId(id)).findFirst();
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
