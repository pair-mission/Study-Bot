package untils;

import domain.member.Member;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<Member> readCsv() throws IOException {

        File csv = new File("src/main/resources/members.csv");
        BufferedReader br = new BufferedReader(new FileReader(csv));
        String line = br.readLine();
        List<Member> members = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] lineArr = line.split(",");
            Long id = InputParser.parseToLong(lineArr[0]);
            String nickname = lineArr[1];

            Member member = Member.of(id, nickname);

            members.add(member);
        }

        return members;
    }
}
