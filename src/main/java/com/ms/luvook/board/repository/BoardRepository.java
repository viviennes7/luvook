package com.ms.luvook.board.repository;

import com.ms.luvook.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vivie on 2017-07-17.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{
}
