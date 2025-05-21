package com.sp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gameroom")
public class GameRoom {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user1 = null;
    private Long card1 = null;
    private Long user2 = null;
    private Long card2 = null;

    public GameRoom() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser1() {
        return user1;
    }

    public void setUser1(Long user1) {
        this.user1 = user1;
    }

    public Long getCard1() {
        return card1;
    }

    public void setCard1(Long card1) {
        this.card1 = card1;
    }

    public Long getUser2() {
        return user2;
    }

    public void setUser2(Long user2) {
        this.user2 = user2;
    }

    public Long getCard2() {
        return card2;
    }

    public void setCard2(Long card2) {
        this.card2 = card2;
    }   

}
