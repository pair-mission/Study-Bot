package global.utils;

import global.utils.parser.CsvParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static <T> List<T> readCsv(String filePath, CsvParser<T> parser) throws IOException {
        File csv = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(csv));
        String line = br.readLine();
        List<T> result = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] lineArr = line.split(",");
            T item = parser.parse(lineArr);
            result.add(item);
        }

        return result;
    }

    public static <T> void writeCsv(T t, String filePath, CsvParser<T> parser) throws IOException {
        File csv = new File(filePath);
        boolean isEmptyFile = false;
        //두 번째 매개변수로 false를 전달하면 기존 파일이 있으면 내용을 덮어씀(overwrite)
        //true로 전달하면 이어붙이기(append)가 됨
        BufferedWriter br = new BufferedWriter(new FileWriter(csv, true));

        if (csv.length() <= 0) {
            isEmptyFile = true;
        }

        parser.write(t, br, isEmptyFile);

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
