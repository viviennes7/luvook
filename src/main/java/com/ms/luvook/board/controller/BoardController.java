package com.ms.luvook.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.domain.MovieBoard;
import com.ms.luvook.board.service.BoardService;
import com.ms.luvook.common.domain.Result;
import com.ms.luvook.common.service.jwt.JwtService;

/**
 * Created by vivie on 2017-07-17.
 */
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/board/book")
    public Result saveBook(BookBoard bookBoard){
        Result result = Result.successInstance();
        int memberId = jwtService.getMemberId();
        Board savedBoard = boardService.save(bookBoard, memberId);
        result.setData(savedBoard);
    	return result;
    }
    
    @PostMapping("/board/movie")
    public Result saveMovie(MovieBoard movieBoard){
        Result result = Result.successInstance();
        int memberId = jwtService.getMemberId();
        Board savedBoard = boardService.save(movieBoard, memberId);
        result.setData(savedBoard);
    	return result;
    }

    @GetMapping("/board/{boardId}")
    public Result find(@PathVariable int boardId){
    	Result result = Result.successInstance();
        int memberId = jwtService.getMemberId();
    	Board board = boardService.find(boardId, memberId);
    	result.setData(board);
        return result;
    }

    @DeleteMapping("/board/{boardId}")
    public Result delete(@PathVariable int boardId){
    	Result result = Result.successInstance();
        int memberId = jwtService.getMemberId();
    	boardService.delete(boardId, memberId);
    	return result;
    }

    @PatchMapping(value = "/board/book/{boardId}")
    public Result update(BookBoard bookBoard){
    	Result result = Result.successInstance();
    	boardService.update(bookBoard);
    	return result;
    }

    @GetMapping("/boards/{pageNum}")
    public Result findAll(@PathVariable int pageNum){
    	Result result = Result.successInstance();
        int memberId = jwtService.getMemberId();
    	List<Board> boards = boardService.findAll(memberId, pageNum);
    	result.setData(boards);
        return result;
    }

    @GetMapping("/member/{memberId}/boards")
    public Result findAllByMember(@PathVariable int memberId){
    	Result result = Result.successInstance();
    	int connectingMemberId = jwtService.getMemberId();
    	List<Board> boards = boardService.findAllByMember(memberId, connectingMemberId);
    	result.setData(boards);
    	return result;
    }
    
    @PostMapping("/board/{boardId}/heart")
    public Result toggleHeart(@PathVariable int boardId){
    	Result result = Result.successInstance();
        int memberId = jwtService.getMemberId();
    	boardService.toggleHeart(boardId, memberId);
    	return result;
    }
    
    @GetMapping("/member/{memberId}/heart/count")
    public Result findAllReceivedHeartCount(@PathVariable int memberId){
    	Result result = Result.successInstance();
    	int heartCount = boardService.findAllReceivedHeartCount(memberId);
    	result.setTotalCount(heartCount);
    	return result;
    }
    
    @PostMapping("/board/{boardId}/comment")
    public Result saveComment(@PathVariable  int boardId, BoardComment comment){
        Result result = Result.successInstance();
        int memberId = jwtService.getMemberId();
        BoardComment savedComment = boardService.saveComment(comment, memberId);
        result.setData(savedComment);
        return result;
    }

    @GetMapping("/board/{boardId}/comments")
    public Result findAllComment(@PathVariable int boardId){
        Result result = Result.successInstance();
        List<BoardComment> comments = boardService.findAllComment(boardId);
        result.setData(comments);
        return result;
    }

    @PutMapping("/board/{boardId}/comment/{commentId}")
    public Result updateComment(@PathVariable int boardId, @PathVariable int commentId, BoardComment comment){
        Result result = Result.successInstance();
        boardService.updateComment(comment);
        return result;
    }

    @DeleteMapping("/board/comment/{commentId}")
    public Result deleteComment(@PathVariable int commentId){
        Result result = Result.successInstance();
        boardService.deleteComment(commentId);
        return result;
    }
}
