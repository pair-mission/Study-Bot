package untils;

import domain.member.Member;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static void writeCsv(Member member) throws IOException {
        File csv = new File("src/main/resources/members.csv");

        //두 번째 매개변수로 false를 전달하면 기존 파일이 있으면 내용을 덮어씀(overwrite)
        //true로 전달하면 이어붙이기(append)가 됨
        BufferedWriter br = new BufferedWriter(new FileWriter(csv, true));

        if (csv.length() <= 0) {
            br.write("id,nickname");
        }

        System.out.println(member.toString());

        br.write("\n" + member);
        br.flush();
    }

    public static boolean existsCsv(String nickname) throws IOException {
        File csv = new File("src/main/resources/members.csv");
        BufferedReader br = new BufferedReader(new FileReader(csv));
        String line = br.readLine();

        while ((line = br.readLine()) != null) {
            if (line.contains(nickname)) {
                return true;
            }
        }
        return false;
    }

}
