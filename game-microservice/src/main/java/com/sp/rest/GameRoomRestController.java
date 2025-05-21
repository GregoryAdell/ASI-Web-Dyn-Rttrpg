package com.sp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.service.GameRoomService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/games")
public class GameRoomRestController {

    private final GameRoomService gameRoomService;

    @Autowired
    public GameRoomRestController(GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestHeader("Authorization") Long userId,
                                             @RequestBody Long cardId) {
        try {
            gameRoomService.createRoom(userId, cardId);
            return ResponseEntity.ok("Room créée");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur création : " + e.getMessage());
        }
    }

    @PatchMapping("/{roomId}/join")
    public ResponseEntity<String> joinRoom(@PathVariable Long roomId,
                                           @RequestHeader("Authorization") Long userId,
                                           @RequestBody Long cardId) {
        try {
            gameRoomService.joinRoom(roomId, userId, cardId);
            return ResponseEntity.ok("Room rejointe");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur join : " + e.getMessage());
        }
    }

    @GetMapping("/rooms")
    public ResponseEntity getRooms(@RequestHeader("Authorization") Long userId) {
        try {
            return ResponseEntity.ok().body(gameRoomService.waitingRooms(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur rooms : " + e.getMessage());
        }
    }
}


