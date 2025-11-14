package domain;

public class Member {
    private String nickname;

    private Member(String nickname) {
        this.nickname = nickname;
    }

    public static Member from(String nickname) {
        return new Member(nickname);
    }

}
