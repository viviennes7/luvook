package com.ms.luvook.member.service;

import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.luvook.common.service.JwtService;
import com.ms.luvook.common.util.Base64Utils;
import com.ms.luvook.common.util.EntityUtils;
import com.ms.luvook.common.util.FileSystemUtils;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
import com.ms.luvook.member.repository.MemberRepository;

/**
 * Created by vivie on 2017-06-08.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService{
	
	private static final String PROFILE_DEFAULT_PATH = "/img/0/profile_default.jpg";
	
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtService jwtService;
    
    public MemberMaster signup(MemberMaster memberMaster) {
        String email = memberMaster.getEmail();
        if( this.isExist(email) ){
            throw new IllegalStateException("이미 계정이 존재합니다.");
        }
        
        String password = memberMaster.getPassword();
        String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt());
        memberMaster.setPassword(encodePassword);
        memberMaster.setMemberType(MemberType.USER);
        memberMaster.setProfileImg(PROFILE_DEFAULT_PATH);
        EntityUtils.initializeRegAndModDate(memberMaster);
        
        MemberMaster createdMember = memberRepository.save(memberMaster);
        int memberId = createdMember.getMemberId();
        createdMember.setNickname("luVook(" + memberId + ")");
        memberRepository.save(memberMaster);
        return createdMember;
    }
    
    public boolean isExist(String email) {
        boolean isExist = false;
        MemberMaster member = memberRepository.findByEmail(email);
        if(member != null){
            isExist = true;
        }
        return isExist;
    }

    @Override
    public MemberMaster signin(String email, String password) {
        MemberMaster memberMaster = memberRepository.findByEmail(email);
        String encodePassword = null;
        boolean isAccordPassword = false;
    
        if(memberMaster != null){
            encodePassword = memberMaster.getPassword();
            isAccordPassword = BCrypt.checkpw(password, encodePassword);
        }

        if(memberMaster == null || !isAccordPassword){
            throw new IllegalStateException("로그인정보가 일치하지 않습니다.");
        }
        return memberMaster;
    }

	@Override
	public MemberMaster signinJwt() {
		Map<String, Object> memberMap = jwtService.get("member");
		String email = memberMap.get("email").toString();
		MemberMaster memberMaster = memberRepository.findByEmail(email);
		return memberMaster;
	}

	@Override
	public void updateInfo(String nickname, String password) {
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int)memberMap.get("memberId");
		MemberMaster currentMember = memberRepository.getOne(memberId);
		String currentNickname = currentMember.getNickname();
		
		MemberMaster searchedMember = memberRepository.findByNickname(nickname);
		
		if(currentNickname.equals(nickname) || searchedMember == null ){
			currentMember.setNickname(nickname);
		}else{
			throw new IllegalStateException("이미 닉네임이 존재합니다.");
		}
		
		if(!password.equals("")){
			String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt());
			currentMember.setPassword(encodePassword);
		}
		
		memberRepository.save(currentMember);
	}

    @Override
    public MemberMaster findByMemberId(int memberId) {
        return memberRepository.findById(memberId).get();
    }

	@Override
	public void uploadProfileImg(String encodeImg) {
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int) memberMap.get("memberId");
		byte[] img = Base64Utils.decodeBase64ToBytes(encodeImg);
		String fileName = FileSystemUtils.save(img, Integer.toString(memberId));
		String filePath =  "/img/profile/" + memberId + "/" + fileName;
		
		MemberMaster member = memberRepository.findById(memberId).get();
		member.setProfileImg(filePath);
		memberRepository.save(member);
	}
}
