package com.sp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sp.model.GameRoom;

@Repository
public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {
    List<GameRoom> findByUser2(long userId);
}
