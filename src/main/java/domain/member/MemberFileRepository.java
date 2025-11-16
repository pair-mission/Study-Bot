package domain.member;

import global.exception.DataAccessException;
import untils.CsvReader;

import java.io.IOException;
import java.util.List;

import static global.ErrorMessage.INVALID_FILE;

public class MemberFileRepository implements MemberRepository {
    private Long sequence;

    public MemberFileRepository() {
        try {
            List<Member> members = CsvReader.readCsv();
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
            member.setId(sequence);
            CsvReader.writeCsv(member);
            ++sequence;
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    @Override
    public List<Member> findAll() {
        try {
            return CsvReader.readCsv();
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    @Override
    public Member findByNickName(String nickname) {
        try {
            return CsvReader.readCsv().stream()
                    .filter(member -> member.isSameNickname(nickname)).findFirst().orElse(null);
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    @Override
    public Member findById(long id) {
        try {
            return CsvReader.readCsv().stream()
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
