package service;

import domain.member.Member;
import domain.member.MemberRepository;

import java.util.List;

import static global.ErrorMessage.MEMBER_NOT_FOUND;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member register(String nickname) {
        boolean existsByNickname = memberRepository.existsBy(nickname);

        if (existsByNickname) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
        }

        Member member = Member.from(nickname);
        memberRepository.save(member);

        return member;
    }

    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }
}
