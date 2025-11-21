package service;

import domain.member.Member;
import global.enums.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.member.MemberInMemoryRepository;
import repository.member.MemberRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest {

    private final MemberRepository memberRepository = new MemberInMemoryRepository();
    private final MemberService memberService = new MemberService(memberRepository);

    @Test
    @DisplayName("멤버 등록 - 이미 등록한 멤버인 경우 예외가 발생한다.")
    void testMemberRegisterWhenAlreadyRegistered() {
        // given
        Member savedMember = memberRepository.save(Member.from("뫄뫄"));

        // when
        assertThatThrownBy(() -> memberService.register(savedMember.getNickname()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.MEMBER_ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("닉네임으로 회원 조회 - 해당 닉네임을 가진 스터디 멤버가 없는 경우 예외가 발생한다.")
    void testLoginWhenNotMember() {
        // given
        String requesterNickname = "밥도둑";

        // when
        assertThatThrownBy(() -> memberService.findByNickName(requesterNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.MEMBER_NOT_FOUND.getMessage());
    }
}