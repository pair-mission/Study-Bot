package global.utils.parser;

import domain.member.Member;
import java.io.BufferedWriter;
import java.io.IOException;

public class MemberParser implements CsvParser<Member> {

    @Override
    public Member parse(String[] csv) {
        Long id = Long.parseLong(csv[0]);
        String nickname = csv[1];
        int remindDay = Integer.parseInt(csv[2]);

        return Member.of(id, nickname, remindDay);
    }

    @Override
    public void write(Member member, BufferedWriter writer, boolean isEmptyFile) throws IOException {
        if (isEmptyFile) {
            writer.write("id,nickname,remindDay\n");
        }

        writer.write(member.getId() + "," + member.getNickname() + "," + member.getRemindDay() + "\n");
    }

    @Override
    public void update(Member member, BufferedWriter writer, String[] csv) throws IOException {

        Long id = Long.parseLong(csv[0]); // 파일에 기록된 id

        int newRemindDay = Integer.parseInt(csv[2]);
        if (member.isSameId(id)) { // 파일에 기록된 id가 member의 id와 같으면
            newRemindDay = member.getRemindDay();
        }

        writer.write(csv[0] + "," + csv[1] + "," + newRemindDay + "\n");
        writer.flush();
    }

}
