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
        return true;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "," + nickname;
    }
}
