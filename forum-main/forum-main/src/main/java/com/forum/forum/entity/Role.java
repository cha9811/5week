package com.forum.forum.entity;

public enum Role {
    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한

    private final String authority;

    Role(String authority) {    //권한넣기
        this.authority = authority;
        System.out.println(authority);
    }

    public String getAuthority() {  //권한가져오기
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}