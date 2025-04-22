package com.AniVerse.Reaction.repository;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Document(collection = "upload")
@NoArgsConstructor
@AllArgsConstructor
public class Heart_favorite {
	
	private int favorite;
	
	
	public int getFavorite() {
		return favorite;
	}
	public void setFavorite(int favorite) {
		this.favorite = this.favorite+ favorite;
	}

}
