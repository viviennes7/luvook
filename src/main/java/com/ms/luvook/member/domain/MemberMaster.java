package com.ms.luvook.member.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms.luvook.board.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by vivie on 2017-06-08.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "memberId")
@Entity
@Table(name = "member_master")
public class MemberMaster {

    public static final String S3_PATH = "https://s3.ap-northeast-2.amazonaws.com/kms-bucket-01";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    private String nickname;

    private String email;

    @Basic(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String profileImg;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Board> boards;

    public MemberMaster() {}

    public MemberMaster(String nickname, String email, String password,
                        String profileImg, MemberType memberType,
                        Date regDate, Date modDate) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.memberType = memberType;
        this.regDate = regDate;
        this.modDate = modDate;
    }

    public String getProfileImg(){
        return S3_PATH + this.profileImg;
    }

    public String getRegDate(){
        if(regDate == null){
            return null;
        }else {
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(regDate);
            return formattedDate;
        }
	}
	
	public String getModDate(){
        if(modDate == null){
            return null;
        }else {
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(modDate);
            return formattedDate;
        }
	}
}