package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.NonUserCertDto;
import com.kh.pheonix.dto.NonUserDto;

@Repository
public class NonUserCertDao {
    
    @Autowired
    private SqlSession sqlSession;
    
    public void insert(NonUserCertDto nonUserCertDto) {
        sqlSession.insert("nonUserCert.insert", nonUserCertDto);
    }
    
    public boolean delete(String nonUserCertEmail) {
        return sqlSession.delete("nonUserCert.delete" ,nonUserCertEmail) > 0;
    }
    
    public NonUserDto selectOne(String nonUserCertEmail) {
        return sqlSession.selectOne("nonUserCert.selectOne", nonUserCertEmail);
    }
    
    // 전송된 인증번호 확인
    public boolean checkValid(String nonUserCertEmail, String nonUserCertCode) {
        Map<String, String> params = new HashMap<>();
        params.put("nonUserCertEmail", nonUserCertEmail);
        params.put("nonUserCertCode", nonUserCertCode);

        NonUserCertDto result = sqlSession.selectOne("nonUserCert.checkValid", params);
        boolean isValid = result != null;
        return isValid;
    }

    // 5분 지난거 삭제
    public boolean deleteExpiredCert() {
        return sqlSession.delete("nonUserCert.deleteExpiredCert") > 0;
    }

}