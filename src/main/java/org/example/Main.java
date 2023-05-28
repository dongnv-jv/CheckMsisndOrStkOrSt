package org.example;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String input = null;
        boolean check = true;
        int a;
        do {
            System.out.println("Nhập 1 để tiếp tục :");
            a = Integer.parseInt(in.nextLine());

            switch (a) {
                case 1:
                    System.out.print("Nhập :");
                    input = in.nextLine();
                    display(input);
                    break;
                case 2:
                    check = false;
                    break;
            }

        } while (check);


    }

    public static void display(String input) {
        Map<String, String> map = new HashMap<>();
        map = check(input);
        String k = map.get("type");
        String v = map.get("data");
        if (k.matches("SDT")) {
            System.out.println(v + " là SDT");
        } else if (k.matches("ST")) {
            System.out.println(v + " là ST");
        } else if (k.matches("STK")) {
            System.out.println(v + " là STK");
        } else if (k.matches("INPUTMISMATCH")) {
            System.out.println(" Nhập sai " + v);
        } else if (k.matches("ERROR")) {
            System.out.println(" Có lỗi xảy ra ");
        } else
            System.out.println("Không in gì cả");
    }


    public static Map<String, String> check(String input) {


        String[] vietnameseCharacters = {
                "à", "á", "ả", "ã", "ạ",
                "ă", "ằ", "ắ", "ẳ", "ẵ", "ặ",
                "â", "ầ", "ấ", "ẩ", "ẫ", "ậ",
                "è", "é", "ẻ", "ẽ", "ẹ",
                "ê", "ề", "ế", "ể", "ễ", "ệ",
                "ì", "í", "ỉ", "ĩ", "ị",
                "ò", "ó", "ỏ", "õ", "ọ",
                "ô", "ồ", "ố", "ổ", "ỗ", "ộ",
                "ơ", "ờ", "ớ", "ở", "ỡ", "ợ",
                "ù", "ú", "ủ", "ũ", "ụ",
                "ư", "ừ", "ứ", "ử", "ữ", "ự",
                "ỳ", "ý", "ỷ", "ỹ", "ỵ",
                "đ"
        };
        String[] escapedCharacters = Arrays.stream(vietnameseCharacters)
                .map(character -> character.replaceAll("([\\\\\\[\\]\\^\\-\\$\\.\\|\\?\\*\\+\\(\\)])", "\\\\$1"))
                .toArray(String[]::new);
        Map<String, String> map = new HashMap<>();
        String type = null;
        String data = null;
        String patternSdt = "^(\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$";
        String patternSt = "^(?:\\d{16}|\\d{19})$";
        String patternStk = "^(?!.*\\s)(?!.*[" + String.join("", escapedCharacters) + "])[A-Za-z0-9]{4,}$";
        int size = input.length();
        if (StringUtils.isNotBlank(input)) {
            if (input.matches(patternSdt)) {
                if (size == 10) {
                    data = input.replaceFirst("0", "84");
                } else if (input.startsWith("+")) {
                    data = input.replaceFirst("\\+", "");
                } else
                    data = input;
                type = "SDT";
            } else if (input.matches(patternSt)) {
                type = "ST";
                data = input;
            } else if (input.matches(patternStk)) {
                type = "STK";
                data = input;
            } else {
                type = "INPUTMISMATCH";
                data = input;
            }
        } else {
            type = "ERROR";
            data = input;
        }

        map.put("type", type);
        map.put("data", data);
        return map;
    }
}