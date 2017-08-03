package com.ms.luvook.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
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
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService bookBoardService;

    @Autowired
    private BoardService movieBoardService;
    
    private Gson gson;
    
    @PostMapping("/book")
    public Result saveBook(BookBoard bookBoard){
        Result result = Result.newInstance();
        int boardId = bookBoardService.save(bookBoard);
        result.success();
    	return result;
    }
    
    @PostMapping("/movie")
    public Result saveMovie(MovieBoard movieBoard){
        Result result = Result.newInstance();
        int boardId = movieBoardService.save(movieBoard);
        result.success();
    	return result;
    }

    @GetMapping("/{boardId}")
    public Result find(@PathVariable int boardId){
    	Result result = Result.newInstance();
        return null;
    }

    @DeleteMapping("/{boardId")
    public Result delete(@PathVariable int boardId){

    	return null;
    }

    @PutMapping("/{boardId}")
    public Result update(@PathVariable int boardId, Board board){

    	return null;
    }

    @GetMapping("s")
    //페이징
    public Result findAll(){

        return null;
    }

    @PostMapping("/heart/{boardId}")
    public Result toggleHeart(int memberId, @PathVariable int boardId){

    	return null;
    }

    private BoardService getBoardService(String boardType){
        BoardService boardService = null;
        switch (boardType){
            case "book" : boardService = bookBoardService;  break;
            case "movie" : boardService = movieBoardService; break;
            default: throw new IllegalArgumentException("해당하는 BoardType은 존재하지 않습니다.");
        }
        return boardService;
    }
}
