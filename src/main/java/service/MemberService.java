package service;

import domain.member.Member;
import dto.MemberInfoDto;
import global.enums.ErrorMessage;
import java.util.List;
import repository.member.MemberRepository;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member register(String nickname) {

        validateExists(nickname);

        return memberRepository.save(Member.from(nickname));
    }

    private void validateExists(String nickname) {
        boolean existsByNickname = memberRepository.existsBy(nickname);

        if (existsByNickname) {
            throw new IllegalArgumentException(ErrorMessage.MEMBER_ALREADY_EXISTS.getMessage());
        }
    }

    public List<MemberInfoDto> findAllMember() {
        return memberRepository.findAll().stream().map(MemberInfoDto::from).toList();
    }

    public Member findByNickName(String nickname) {

        return memberRepository.findByNickName(nickname)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.MEMBER_NOT_FOUND.getMessage()));
    }

    public Member findById(long id) {

        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.MEMBER_NOT_FOUND.getMessage()));
    }

    public void updateRemindDay(Member member, int remindDay) {
        member.updateRemindDay(remindDay);
    }

}
