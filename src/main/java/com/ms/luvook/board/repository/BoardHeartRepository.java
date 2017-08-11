package com.ms.luvook.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms.luvook.board.domain.BoardHeart;

@Repository
public interface BoardHeartRepository extends JpaRepository<BoardHeart, Integer>{
	BoardHeart findByMemberIdAndBoardId(int memberId, int boardId);
}
