package com.sp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sp.model.GameRoom;
import com.sp.repository.GameRoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final WebClient webClient;

    private final String userServiceBaseUrl = "http://user-service:8081/api/users/";

    
    @Autowired
    public GameRoomService(GameRoomRepository gameRoomRepository, WebClient webClient) {
        this.gameRoomRepository = gameRoomRepository;
        this.webClient = webClient;
    }

    public void joinRoom(Long roomId, Long userId, Long cardId) throws Exception {
        Optional<GameRoom> optRoom = gameRoomRepository.findById(roomId);
        if (optRoom.isPresent()) {
            GameRoom room = optRoom.get();
            room.setUser2(userId);
        room.setCard2(cardId);
        gameRoomRepository.save(room);
        // 🔹 2. Ajouter de l'argent
        webClient.patch()
                .uri(userServiceBaseUrl + room.getUser1()+ "/money/add")
                .header("Authorization", String.valueOf(room.getUser1()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(50)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 🔹 3. Retirer de l'argent
        webClient.patch()
                .uri(userServiceBaseUrl + room.getUser2()+ "/money/remove")
                .header("Authorization", String.valueOf(room.getUser2()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(50)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        } else {
            System.out.println("Room non trouvée");
        }
    }
    


    public void createRoom(Long userId, Long cardId) throws Exception {
        GameRoom room = new GameRoom();
        room.setCard1(cardId);
        room.setUser1(userId);
        gameRoomRepository.save(room);        
    }

    public List<GameRoom> waitingRooms(Long userId) {
        List<GameRoom> allRooms = gameRoomRepository.findAll();
        List<GameRoom> rooms = new ArrayList();
        for (GameRoom room : allRooms) {
            if (room.getUser1() != userId) {
                rooms.add(room);
            }
        }
        return rooms;
    }
}
