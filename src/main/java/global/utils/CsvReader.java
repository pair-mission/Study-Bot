package global.utils;

import global.utils.parser.CsvParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static <T> List<T> readCsv(String filePath, CsvParser<T> parser) throws IOException {
        File csv = new File(filePath);
        List<T> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] lineArr = line.split(",");
                T item = parser.parse(lineArr);
                result.add(item);
            }
        }
        return result;
    }

    public static <T> void writeCsv(T t, String filePath, CsvParser<T> parser) throws IOException {
        File csv = new File(filePath);
        boolean isEmptyFile = !csv.exists() || csv.length() == 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true))) { // ★ 자동 close
            parser.write(t, bw, isEmptyFile);
        }
    }

    public static <T> void updateCsv(T t, String filePath, CsvParser<T> parser) throws IOException {
        File originFile = new File(filePath);
        File newFile = new File(filePath + ".txt");

        try (BufferedReader br = new BufferedReader(new FileReader(originFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {

            String header = br.readLine();
            if (header != null) {
                bw.write(header);
                bw.newLine();
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] csv = line.split(",");
                parser.update(t, bw, csv); // 수정 대상이면 수정된 내용으로, 아니면 그대로 write
                bw.flush();
            }
        }

        if (!originFile.delete()) {
            System.out.println("기존 csv 파일 실패");
        }

        if (!newFile.renameTo(originFile)) {
            System.out.println("임시 파일 이름 변경 실패!");
        }

//        originFile.renameTo(newFile);
//        originFile.delete();

    }

    public static boolean existsCsv(String nickname) throws IOException {
        File csv = new File("src/main/resources/members.csv");
        if (!csv.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.contains(nickname)) {
                    return true;
                }
            }
        }

        return false;
    }

}
