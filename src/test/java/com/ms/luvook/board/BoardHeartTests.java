package com.ms.luvook.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardHeart;
import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.repository.BoardHeartRepository;
import com.ms.luvook.board.service.BoardService;
import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
import com.ms.luvook.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardHeartTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardHeartRepository boardHeartRepository;

    private MemberMaster savedMember;
    private int savedMemberId;
    private Board savedBoard;
    private int savedBoardId;

    @Before
    public void setup(){
        MemberMaster memberMaster = new MemberMaster("%test_nickname",  "%test1@naver.com", "123123", "img", MemberType.USER, new Date(), new Date());
        savedMember = memberService.signup(memberMaster);
        savedMemberId = savedMember.getMemberId();
        BookBoard bookBoard = new BookBoard(1,"즐거운책", 5, "책 제목", 12345, "img_url", "11111", "2222222");
        savedBoard = boardService.save(bookBoard, savedMemberId);
        savedBoardId = savedBoard.getBoardId();
    }


    @Test
    public void insertHeart(){
        //When
        boardService.toggleHeart(savedBoardId, savedMemberId);
        BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBoardId);

        //Then
        assertNotNull(boardHeart);

    }

    @Test
    public void updateHeartToIsUseN(){
        //Given
        boardService.toggleHeart(savedBoardId, savedMemberId);

        //When
        boardService.toggleHeart(savedBoardId, savedMemberId);
        BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBoardId);

        //Then
        assertThat(boardHeart.getIsUse(), is(IsUse.N));
    }

    @Test
    public void updateHeartToIsUseY(){
        //Given
        boardService.toggleHeart(savedBoardId, savedMemberId);
        boardService.toggleHeart(savedBoardId, savedMemberId);

        //When
        boardService.toggleHeart(savedBoardId, savedMemberId);
        BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBoardId);

        //Then
        assertThat(boardHeart.getIsUse(), is(IsUse.Y));
    }
}
