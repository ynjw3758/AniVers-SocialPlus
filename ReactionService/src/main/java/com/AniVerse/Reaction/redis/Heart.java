package com.AniVerse.Reaction.redis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AniVerse.Reaction.mapper.Common_mapper;
import com.AniVerse.Reaction.mongo.Heart_Query;

import org.springframework.data.redis.core.RedisTemplate;


@Service
public class Heart {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String POST_LIKE_SET_PREFIX = "likesuser:";        // SET(userId)
	private static final String POST_LIKE_COUNT_PREFIX = "likecount:";  // INT
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private Common_mapper heart_mapper;
	
	@Autowired
	private Heart_Query Mongo;
	
	
	public Map<String, Object> Toggle_Heart(Map<String ,Object> infos) {
		logger.info("데이터 :" + infos);
		String content_id=infos.get("Contentid").toString();
		String User_id =infos.get("Id").toString();
	    String likeSetKey = POST_LIKE_SET_PREFIX + content_id;
	    String countKey = POST_LIKE_COUNT_PREFIX + content_id;
	    Map<String, Object> result= new HashMap<String ,Object>();
	    
	    Boolean alreadyLiked = redisTemplate.opsForSet().isMember(likeSetKey, User_id);
	    logger.info("존재 확인 : "+ alreadyLiked );
	    	try {
	    		
	    		if(Boolean.TRUE.equals(alreadyLiked)) {
	    			logger.info("cancel");
			    	   redisTemplate.opsForSet().remove(likeSetKey, User_id);
			           redisTemplate.opsForValue().decrement(countKey);
		    	        int updatedCount = (int) redisTemplate.opsForValue().get(countKey);
	    	            heart_mapper.delete_heart(infos); //psql 사용자 데이터 삭제
	    	        
			           result.put("count", updatedCount);
			           result.put("liked", false);
	    	           Mongo.decreate_Heart(content_id, User_id); //mongodb 카운트 감소
	    	        return result;
	    		}
	    		else {
	    			logger.info("add");
	    	        redisTemplate.opsForSet().add(likeSetKey, User_id);
	    	        redisTemplate.opsForValue().increment(countKey);
	    	        int updatedCount = (int) redisTemplate.opsForValue().get(countKey);
			           heart_mapper.insert_heart(infos);
			           Mongo.increate_Heart(content_id, User_id);
			           
			           result.put("count", updatedCount);
			           result.put("liked", true);
			           return result;
	    		}

	    	}catch(Exception e) {
	    		 logger.error("db 에러 발생");
	    		 if(Boolean.TRUE.equals(alreadyLiked)) {
		    			logger.info("cancel");
				    	   redisTemplate.opsForSet().remove(likeSetKey, User_id);
				           redisTemplate.opsForValue().decrement(countKey);
		    	        heart_mapper.delete_heart(infos);
		    		}
		    		else {
		    			logger.info("add");
		    	        redisTemplate.opsForSet().add(likeSetKey, User_id);
		    	        redisTemplate.opsForValue().increment(countKey);
				           heart_mapper.insert_heart(infos);
				           
		    		}
	    		 
	    		 throw new RuntimeException("좋아요 처리 중 오류 발생", e);
	    	}
	}

}
