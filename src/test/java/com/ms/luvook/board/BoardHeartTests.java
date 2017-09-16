package com.ms.luvook.board;

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

    private Board savedBoard;
    private int savedBoardId;
    private MemberMaster savedMember;
    private int savedMemberId;

    @Before
    public void setup(){
        BookBoard bookBoard = new BookBoard(1,"즐거운책", 5, "책 제목", 12345, "img_url", "11111", "2222222");
        MemberMaster memberMaster = new MemberMaster("%test_nickname",  "test1@naver.com", "123123", "img", MemberType.USER, new Date(), new Date());
        savedBoard = boardService.save(bookBoard);
        savedBoardId = savedBoard.getBoardId();
        savedMember = memberService.signup(memberMaster);
        savedMemberId = savedMember.getMemberId();
    }


    @Test
    public void insertHeart(){
        //when
        boardService.toggleHeart(savedBoardId);
        BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBoardId);

        //then
        assertNotNull(boardHeart);

    }

    @Test
    public void updateHeartToIsUseN(){
        //given
        boardService.toggleHeart(savedBoardId);

        //when
        boardService.toggleHeart(savedBoardId);
        BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBoardId);

        //then
        assertThat(boardHeart.getIsUse(), is(IsUse.N));
    }

    @Test
    public void updateHeartToIsUseY(){
        //given
        boardService.toggleHeart(savedBoardId);
        boardService.toggleHeart(savedBoardId);

        //when
        boardService.toggleHeart(savedBoardId);
        BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBoardId);

        //then
        assertThat(boardHeart.getIsUse(), is(IsUse.Y));
    }
}
