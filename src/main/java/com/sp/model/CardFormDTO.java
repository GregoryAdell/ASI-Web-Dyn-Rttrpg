package com.sp.model;

public class CardFormDTO {
	private String name;
	private String description;
	private String imgUrl;
	private Family family;
    private Affinity affinity;
    private int hp;
    private int attack;
    private int defense;
    private int energy;

	public CardFormDTO() {
		this.name = "";
		this.description = "";
		this.imgUrl = "";
		this.family = Family.MOINEAU;
		this.affinity = Affinity.AIR;
		this.hp = 0;
		this.attack = 0;
		this.defense = 0;
		this.energy = 0;
	}

    public CardFormDTO(String name, String description, String imgUrl, String family, String affinity, int hp,
			int attack, int defense, int energy) {
		this.name = name;
		this.description = description;
		this.imgUrl = imgUrl;
		this.family = Family.valueOf(family);
		this.affinity = Affinity.valueOf(affinity);
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.energy = energy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getFamily() {
		return family.toString();
	}

	public void setFamily(String family) {
		this.family = Family.valueOf(family);
	}

	public String getAffinity() {
		return affinity.toString();
	}

	public void setAffinity(String affinity) {
		this.affinity = Affinity.valueOf(affinity);
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	
    
}
