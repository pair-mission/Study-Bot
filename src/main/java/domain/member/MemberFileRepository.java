package domain.member;

import untils.CsvReader;

import java.io.IOException;
import java.util.List;

public class MemberFileRepository implements MemberRepository {


    @Override
    public void save(Member member) {

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
    public Boolean existsBy(String nickname) {
        return null;
    }
}
