package org.invemotion.domain.user.enums;

public enum InvestmentExperience {
    GOV_BOND_SAFE_FUNDS,         // 국채, 지방채, MMF 등
    SECURE_CORP_BOND_FUNDS,      // 고신용 회사채, 채권형 펀드
    MID_RISK_PRODUCTS,           // 중간 신용 회사채, 혼합형 펀드
    STOCKS_AND_HIGH_RISK_FUNDS,  // 주식, 저신용 회사채
    HIGH_RISK_DERIVATIVES        // 선물·옵션 등 파생상품
}
