package com.ms.luvook.board.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.common.domain.IsUse;

/**
 * Created by vivie on 2017-07-17.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>, BoardRepositoryQueryDsl{
	List<Board> findAllByMemberIdAndIsUseOrderByBoardIdDesc(int memberId, IsUse isUse);
	List<Board> findAllByIsUseOrderByBoardIdDesc(IsUse isUse, Pageable pageable);
	
	@Query(value="SELECT count(*) FROM luvook.board t1 JOIN luvook.board_heart t2 ON t1.board_id = t2.board_id WHERE t1.member_id = :member_id and t1.is_use = 'Y' and t2.is_use='Y'", nativeQuery = true)
	int findAllReceivedHeartCount(@Param("member_id")int memberId);
}
