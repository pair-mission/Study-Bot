package dto;

import domain.member.Member;

public record MemberInfoDto(String nickname) {
    public static MemberInfoDto from(Member member) {
        return new MemberInfoDto(member.getNickname());
    }
}
