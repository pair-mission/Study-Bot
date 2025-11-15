package service;

import domain.member.Member;
import domain.member.MemberRepository;

import java.io.IOException;
import java.util.List;

import static global.ErrorMessage.MEMBER_NOT_FOUND;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member register(String nickname) throws IOException {
        boolean existsByNickname = memberRepository.existsBy(nickname);

        if (existsByNickname) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
        }

        Member member = Member.from(nickname);
        memberRepository.save(member);

        return member;
    }

    public List<Member> findAllMember() throws IOException {
        return memberRepository.findAll();
    }

    public Member findByNickName(String nickname) {
        Member member = null;
        try {
            member = memberRepository.findByNickName(nickname);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (member == null) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
        }

        return member;
    }

    public Member findById(long id) {

        Member member = memberRepository.findById(id);

        if (member == null) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
        }

        return member;

    }

}
