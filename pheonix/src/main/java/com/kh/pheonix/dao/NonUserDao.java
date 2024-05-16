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
        
      //nonUserAuth token 있는지 검색
        public NonUserDto tokenFind(String nonUserEmail) {
        	return sqlSession.selectOne("nonUser.tokenFind" ,nonUserEmail);
        }
        
    //조회
        public List<NonUserDto> selectList(){
            return sqlSession.selectList("nonUser.list");
        }
    
    
}