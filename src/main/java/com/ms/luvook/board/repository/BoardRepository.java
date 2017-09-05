package com.ms.luvook.board.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.common.domain.IsUse;

/**
 * Created by vivie on 2017-07-17.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{
	List<Board> findAllByMemberIdOrderByBoardIdDesc(int memberId);
	List<Board> findAllByIsUseOrderByBoardIdDesc(IsUse isUse, Pageable pageable);
}
