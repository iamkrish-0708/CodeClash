package com.codeclash.repository;

import com.codeclash.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomCode(String roomCode);
    List<Room> findByStatus(Room.RoomStatus status);
}
