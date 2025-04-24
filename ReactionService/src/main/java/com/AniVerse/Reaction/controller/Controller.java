package com.AniVerse.Reaction.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AniVerse.Reaction.services.Comment_Services;
import com.AniVerse.Reaction.services.Heart_Services;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Pets-social")
public class Controller {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Heart_Services common;
	
	@Autowired
	private Comment_Services Comment;
	
	
	@GetMapping("/Comment/list")
	public ResponseEntity<Map<String, Object>>Comments_list(@RequestParam("ContentId") String ContentId,
			@RequestParam("UserId") String UserId,
			@RequestParam("MyId") String MyId){
		Map<String , Object> result= new HashMap<>();
		logger.info("댓글 데이터 가져오기 : " +ContentId );
		result = Comment.Comment_list(ContentId, UserId, MyId);
		if(result.get("code").equals(500)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PostMapping("comment/likes")
	public ResponseEntity<Map<String, Object>>comments_like(@RequestBody Map<String, Object> info){
		Map<String, Object> response = new HashMap<>();
		response =Comment.Comments_like(info); 
		
		return ResponseEntity.status(HttpStatus.OK).body(response);		
	}
	
	@PostMapping("/Comment/owner")
	public void woner_text_register(@RequestBody Map<String, Object> info) {
		Map<String , Object> result= new HashMap<>();
		result= Comment.save_owner(info);
		return;
	}
	
	@PostMapping("/Heart/likes")
	public ResponseEntity<Map<String, Object>>Hear_likes(@RequestBody Map<String, Object> infos){
		Map<String, Object> result= new HashMap<String, Object>();
		result = common.LikeHeart(infos);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PostMapping("comment/reply")
	ResponseEntity<Map<String, Object>>send_reply(@RequestBody Map<String, Object> info){
		 Map<String, Object> result = new HashMap<>();
		 logger.info("대댓글 정보 :" + info);
		 result = Comment.send_reply(info);
		 
		 
		 return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PostMapping("/comment/Create")
	ResponseEntity<Map<String, Object>>Create_Comment(@RequestBody Map<String, Object> info){
		Map<String, Object> result= new HashMap<String, Object>();
		logger.info("요청 데이터 ;" + info);
		result = Comment.Create_Comment(info);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PostMapping("/comment/Edit")
	ResponseEntity<Map<String, Object>>Edit_Comment(){
		Map<String, Object> result= new HashMap<String, Object>();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PostMapping("/comment/Delete")
	ResponseEntity<Map<String, Object>>Delete_Comment(){
		Map<String, Object> result= new HashMap<String, Object>();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	
	
	

}
