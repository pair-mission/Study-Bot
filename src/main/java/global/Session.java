package global;

import domain.member.Member;

public class Session {

    private Member loginMember;

    public void login(Member member) {
        this.loginMember = member;
    }

    public Member getLoginMember() {
        return loginMember;
    }

}
