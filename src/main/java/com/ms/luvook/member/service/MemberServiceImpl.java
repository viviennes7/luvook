package com.ms.luvook.member.service;

import java.util.Objects;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.luvook.common.storage.StorageService;
import com.ms.luvook.common.util.EntityUtils;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
import com.ms.luvook.member.repository.MemberRepository;

/**
 * Created by vivie on 2017-06-08.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService{
	
	private static final String DEFAULT_NICKNAME = "번째러버";
	private static final String PROFILE_DEFAULT_PATH = "/profile/0/profile_default.jpg";
	private static final String SIGNIN_EXCEPTION_MSG = "로그인정보가 일치하지 않습니다.";
	private static final String EMAIL_EXIST_EXCEPTION_MSG = "이미 계정이 존재합니다.";
	private static final String NICKNAME_EXIST_EXCEPTION_MSG = "이미 닉네임이 존재합니다.";
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private StorageService awsService;

	public MemberMaster signup(MemberMaster memberMaster) {
		String email = memberMaster.getEmail();
		this.validate(email);
		this.setupForSave(memberMaster);
		MemberMaster createdMember = memberRepository.save(memberMaster);
		
		int memberId = createdMember.getMemberId();
		createdMember.setNickname(memberId + DEFAULT_NICKNAME);
		memberRepository.save(memberMaster);
		return createdMember;
	}

	private void setupForSave(MemberMaster memberMaster){
		String password = memberMaster.getPassword();
		String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		memberMaster.setPassword(encodedPassword);
		
		memberMaster.setMemberType(MemberType.USER);
		memberMaster.setProfileImg(PROFILE_DEFAULT_PATH);
		EntityUtils.initializeRegAndModDate(memberMaster);
	}

	@Override
	public boolean isExist(String email) {
		boolean isExist = false;
		MemberMaster member = memberRepository.findByEmail(email);
		if(member != null){
		    isExist = true;
		}
		return isExist;
	}
	
	@Override
	public void validate(String email){
		if( this.isExist(email) ){
			throw new IllegalStateException(EMAIL_EXIST_EXCEPTION_MSG);
		}
	}

	@Override
	public MemberMaster signin(String email, String password) {
		MemberMaster memberMaster = memberRepository.findByEmail(email);
		Objects.requireNonNull(memberMaster, SIGNIN_EXCEPTION_MSG);
		
		if( ! this.isAccordPassword(memberMaster, password)){
			throw new IllegalStateException(SIGNIN_EXCEPTION_MSG);
		}
		
		return memberMaster;
	}

	private boolean isAccordPassword(MemberMaster memberMaster, String password){
		String encodedPassword = memberMaster.getPassword();
		return BCrypt.checkpw(password, encodedPassword);
	}

	@Override
	public void updateInfo(String nickname, String password, int memberId) {
		MemberMaster currentMember = memberRepository.getOne(memberId);
		this.updateNickname(currentMember, nickname);
		this.updatePassword(currentMember, password);
		memberRepository.save(currentMember);
	}
	
	private void updateNickname(MemberMaster memberMaster, String nickname){
		String currentNickname = memberMaster.getNickname();
		MemberMaster searchedMember = memberRepository.findByNickname(nickname);
		if(currentNickname.equals(nickname) || searchedMember == null ){
			memberMaster.setNickname(nickname);
		}else{
			throw new IllegalStateException(NICKNAME_EXIST_EXCEPTION_MSG);
		}
	}
	
	private void updatePassword(MemberMaster memberMaster, String password){
		if(!password.equals("")){
			String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt());
			memberMaster.setPassword(encodePassword);
		}
	}

	@Override
	public MemberMaster findByMemberId(int memberId) {
		return memberRepository.findById(memberId).get();
	}

	@Override
	public String uploadProfileImg(String encodeImg, int memberId) {
		String fileName = this.getRandomImageName();
		String fileDir ="profile/" + memberId + "/";
		awsService.uploadFile(encodeImg, fileDir, fileName);
		
		MemberMaster member = memberRepository.findById(memberId).get();
		member.setProfileImg("/"+fileDir +fileName);
		memberRepository.save(member);
		return member.getProfileImg();
	}
	
	private String getRandomImageName(){
		return UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
	}
}
