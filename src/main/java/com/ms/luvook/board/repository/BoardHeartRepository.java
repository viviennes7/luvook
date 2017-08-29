package com.ms.luvook.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms.luvook.board.domain.BoardHeart;
import com.ms.luvook.common.domain.IsUse;

@Repository
public interface BoardHeartRepository extends JpaRepository<BoardHeart, Integer>{
	BoardHeart findByMemberIdAndBoardId(int memberId, int boardId);
	BoardHeart findByMemberIdAndBoardIdAndIsUse(int memberId, int boardId, IsUse isUse);
	List<BoardHeart> findByBoardIdAndIsUse(int boardId, IsUse isUse);
}
