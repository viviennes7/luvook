package com.ms.luvook.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.common.domain.IsUse;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment, Integer>{
	List<BoardComment> findByBoardIdAndIsUseOrderByBoardCommentId(int boardId, IsUse isUse);
}
