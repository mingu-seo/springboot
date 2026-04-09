package com.example.chapter03;

public class MemberServiceImpl implements MemberService {

    private MemberDAO dao;

    // setter 방식
    public void setDao(MemberDAO dao) {
        this.dao = dao;
    }

    @Override
    public void regist() {
        // 기존 방식 (non IoC, non DI)
//        MemberDAO dao = new MemberDAO();
        dao.regist();
    }
}
