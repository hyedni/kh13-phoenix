package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.NonUserDto;

@Repository
public class NonUserDao {
    
    @Autowired
    private SqlSession sqlSession;
    
    //등록
    public void insert(NonUserDto nonUserDto) {
        sqlSession.insert("nonUser.input", nonUserDto);
    }
    
    //삭제
    public boolean delete(String nonUserEmail) {
        return sqlSession.delete("nonUser.delete", nonUserEmail) > 0;
    }
    
    //검색
        public NonUserDto selectOne(String nonUserEmail) {
            return sqlSession.selectOne("nonUser.find", nonUserEmail);
        }
        
      //token으로 이메일 있는지 확인
        public NonUserDto tokenFind(String token) {
        	return sqlSession.selectOne("nonUser.tokenFind" ,token);
        }
        //token으로 모든 정보 확인
        public NonUserDto allFind(String token) {
        	return sqlSession.selectOne("nonUser.allFind", token);
        }
        
    //조회
        public List<NonUserDto> selectList(){
            return sqlSession.selectList("nonUser.list");
        }
    
    
}