package domain.member;

import java.io.Serializable;

public class Member implements Serializable {
    private final String nickname;
    private Long id;

    private Member(String nickname) {
        this(null, nickname);
    }

    private Member(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static Member from(String nickname) {
        return new Member(nickname);
    }

    public static Member of(Long id, String nickname) {
        return new Member(id, nickname);
    }

    public boolean isSameNickname(String name) {
        return nickname.equals(name);
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public String getNickname() {
        return nickname;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "," + nickname;
    }
}
