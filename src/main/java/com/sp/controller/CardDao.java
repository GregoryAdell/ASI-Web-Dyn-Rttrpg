package com.sp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.sp.model.Affinity;
import com.sp.model.Family;
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
  
		Card c1=new Card("The Fist of Reason", "A rock bear fighter", "", Family.OURS, Affinity.ROCHE, 70, 50, 20, 0);
		Card c2=new Card("The Charismactic Song", "A bardic fire squirrel", "", Family.ECUREUIL, Affinity.FEU, 40, 20, 30, 25);
		Card c3=new Card("The Divine Arrow", "An elctric archer fox", "", Family.RENARD, Affinity.ELECTRICITE, 50, 40, 30, 15);
		Card c4=new Card("The Protecting Horn", "A defensive light unicorn", "", Family.LICORNE, Affinity.LUMIERE,100, 10, 40, 10);
		Card c5=new Card("The Shadow Claw", "A shadowy cat thief", "", Family.CHAT, Affinity.OMBRE, 30, 45, 20, 20);
        Card c6=new Card("The Cloaked Sage", "A poison wizard rabbit", "", Family.LAPIN, Affinity.POISON, 30, 5, 35, 40);

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
		Card p=new Card(name, description, imgUrl,Family.valueOf(family), Affinity.valueOf(affinity), hp, attack, defense, energy);
		this.myCardList.add(p);
		return p;
	}
}

