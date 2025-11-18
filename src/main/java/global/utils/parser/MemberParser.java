package global.utils.parser;

import domain.member.Member;

import java.io.BufferedWriter;
import java.io.IOException;

public class MemberParser implements CsvParser<Member> {

    @Override
    public Member parse(String[] csv) {
        Long id = Long.parseLong(csv[0]);
        String nickname = csv[1];

        return Member.of(id, nickname);
    }

    @Override
    public void write(Member member, BufferedWriter writer, boolean isEmptyFile) throws IOException {

    }
}
