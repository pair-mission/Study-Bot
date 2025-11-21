package domain.member;

import java.io.Serializable;

public class Member implements Serializable {
    private final String nickname;
    private final Long id;
    private int remindDay; // 일 단위

    private Member(String nickname) {
        this(null, nickname, 1);
    }

    private Member(Long id, String nickname, int remindDay) {
        this.id = id;
        this.nickname = nickname;
        this.remindDay = remindDay;
    }

    public static Member from(String nickname) {
        return new Member(nickname);
    }

    public static Member of(Long id, String nickname) {
        return new Member(id, nickname, 1);
    }

    public static Member of(Long id, String nickname, int remindDay) {
        return new Member(id, nickname, remindDay);
    }

    public boolean isSameNickname(String name) {
        return nickname.equals(name);
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public void updateRemindDay(int remindDay) {
        this.remindDay = remindDay;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getId() {
        return id;
    }

    public int getRemindDay() {
        return remindDay;
    }

    @Override
    public String toString() {
        return id + "," + nickname;
    }
}
