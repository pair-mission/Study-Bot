package domain.member;

public class Member {
    private final String nickname;
    private Long id;

    private Member(String nickname) {
        this.nickname = nickname;
    }

    public static Member from(String nickname) {
        return new Member(nickname);
    }

    void setId(Long id) {
        this.id = id;
    }

    public boolean isSameNickname(String name) {
        return true;
    }

    public String getNickname() {
        return nickname;
    }
}
