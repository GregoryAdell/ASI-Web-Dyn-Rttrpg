package com.sp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.sp.model.Card;

@Service
public class CardDao {
	private List<Card> myCardList;
	private Random randomGenerator;

	public CardDao() {
		myCardList=new ArrayList<>();
		randomGenerator = new Random();
		createCardList();
	}

	private void createCardList() {
  
		Card c1=new Card("The Fist of Reason", "A rock bear fighter", "https://i.redd.it/mm7nejrkhvaa1.jpg", "OURS", "ROCHE", 70, 50, 20, 0);
		Card c2=new Card("The Charismactic Song", "A bardic fire squirrel", "https://static.wikia.nocookie.net/here-to-slay/images/1/11/The_Chatismatic_Song.png/revision/latest?cb=20210201223312", "ECUREUIL", "FEU", 40, 20, 30, 25);
		Card c3=new Card("The Divine Arrow", "An elctric archer fox", "https://static.wikia.nocookie.net/here-to-slay/images/1/1d/The_Divine_Arrow_Here_to_Slay.png/revision/latest?cb=20210201225821", "RENARD", "ELECTRICITE", 50, 40, 30, 15);
		Card c4=new Card("The Protecting Horn", "A defensive light unicorn", "https://static.wikia.nocookie.net/here-to-slay/images/8/8e/The_Protecting_Horn_Here_to_Slay.png/revision/latest?cb=20210202130221", "LICORNE", "LUMIERE",100, 10, 40, 10);
		Card c5=new Card("The Shadow Claw", "A shadowy cat thief", "https://static.wikia.nocookie.net/here-to-slay/images/0/08/The_Shadow_Claw_Here_to_Slay.png/revision/latest/thumbnail/width/360/height/450?cb=20210201222453", "CHAT", "OMBRE", 30, 45, 20, 20);
        Card c6=new Card("The Cloaked Sage", "A poison wizard rabbit", "https://preview.redd.it/the-cloaked-sage-v0-jc7encrlr8rb1.jpg?width=171&format=pjpg&auto=webp&s=b3cc64b45ec83fba090d5edb947139aee745bd5d", "LAPIN", "POISON", 30, 5, 35, 40);

		myCardList.add(c1);
		myCardList.add(c2);
		myCardList.add(c3);
		myCardList.add(c4);
		myCardList.add(c5);
        myCardList.add(c6);
	}
	public List<Card> getCardList() {
		return this.myCardList;
	}
	public Card getCardByName(String name){
		for (Card CardBean : myCardList) {
			if(CardBean.getName().equals(name)){
				return CardBean;
			}
		}
		return null;
	}
	public Card getRandomCard(){
		int index=randomGenerator.nextInt(this.myCardList.size());
		return this.myCardList.get(index);
	}

	public Card addCard(String name, String description, String imgUrl, String family, String affinity, int hp, int attack,
    int defense, int energy) {
		Card p=new Card(name, description, imgUrl, family, affinity, hp, attack, defense, energy);
		this.myCardList.add(p);
		return p;
	}
}

