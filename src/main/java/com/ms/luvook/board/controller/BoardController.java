package com.ms.luvook.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.domain.MovieBoard;
import com.ms.luvook.board.service.BoardService;
import com.ms.luvook.common.domain.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by vivie on 2017-07-17.
 */
@Slf4j
@RestController
public class BoardController {

    @Autowired
    private BoardService bookBoardService;

    @Autowired
    private BoardService movieBoardService;
    
    @Autowired
    private BoardService boardService;
    
    @PostMapping("/board/book")
    public Result saveBook(BookBoard bookBoard){
        Result result = Result.successInstance(); 
        int boardId = bookBoardService.save(bookBoard);
    	return result;
    }
    
    @PostMapping("/board/movie")
    public Result saveMovie(MovieBoard movieBoard){
        Result result = Result.successInstance();
        int boardId = movieBoardService.save(movieBoard);
    	return result;
    }

    @GetMapping("/board/{boardId}")
    public Result find(@PathVariable int boardId){
    	Result result = Result.successInstance();
    	Board board = boardService.find(boardId);
    	result.setData(board);
        return result;
    }

    @DeleteMapping("/board/{boardId}")
    public Result delete(@PathVariable int boardId){

    	return null;
    }

    @PutMapping("/board/{boardId}")
    public Result update(@PathVariable int boardId, Board board){

    	return null;
    }

    @GetMapping("/boards/{pageNum}")
    public Result findAll(@PathVariable int pageNum){
    	Result result = Result.successInstance();
    	List<Board> boards = boardService.findAll(pageNum);
    	result.setData(boards);
        return result;
    }

    @PostMapping("/board/heart/{boardId}")
    public Result toggleHeart(int memberId, @PathVariable int boardId){

    	return null;
    }

}
