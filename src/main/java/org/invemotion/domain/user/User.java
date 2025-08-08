package org.invemotion.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.invemotion.domain.user.enums.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {

    /* ---------- PK ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /* ---------- 투자 성향 및 국가 ---------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "investment_profile", length = 30, nullable = false)
    private InvestmentProfile investmentProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "residency_country", length = 30, nullable = false)
    private ResidencyCountry residencyCountry;

    /* ---------- 직업·소득·사용 목적 ---------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "job", length = 50, nullable = false)
    private Job job;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_source", length = 50, nullable = false)
    private IncomeSource incomeSource;

    @Enumerated(EnumType.STRING)
    @Column(name = "usage_purpose", length = 50, nullable = false)
    private UsagePurpose usagePurpose;

    /* ---------- 자산 비율 ---------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "financial_asset_ratio", length = 30, nullable = false)
    private FinancialAssetRatio financialAssetRatio;

    @Column(name = "guaranteed_ratio", columnDefinition = "INT")
    private Integer guaranteedRatio;

    @Column(name = "investment_ratio", columnDefinition = "INT")
    private Integer investmentRatio;

    @Column(name = "loan_ratio", columnDefinition = "INT")
    private Integer loanRatio;

    @Column(name = "deposit_ratio", columnDefinition = "INT")
    private Integer depositRatio;

    /* ---------- 투자 경험·기간 ---------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "investment_experience", length = 50, nullable = false)
    private InvestmentExperience investmentExperience;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_duration", length = 30, nullable = false)
    private InvestmentDuration investmentDuration;

    /* ---------- 파생상품 투자기간 ---------- */
    @Column(name = "derivative_invest_year", columnDefinition = "INT")
    private Integer derivativeInvestYear;

    @Column(name = "derivative_invest_month", columnDefinition = "INT")
    private Integer derivativeInvestMonth;

    /* ---------- 투자 목표 및 처분 목적 ---------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "investment_goal", length = 50, nullable = false)
    private InvestmentGoal investmentGoal;

    @Enumerated(EnumType.STRING)
    @Column(name = "disposal_purpose", length = 30, nullable = false)
    private DisposalPurpose disposalPurpose;

    /* ---------- 인구통계·투자 가능 기간 ---------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", length = 30, nullable = false)
    private AgeGroup ageGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_possible_period", length = 50, nullable = false)
    private InvestmentPossiblePeriod investmentPossiblePeriod;

    /* ---------- 소득 안정성·연소득 ---------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "income_stability", length = 50, nullable = false)
    private IncomeStability incomeStability;

    @Enumerated(EnumType.STRING)
    @Column(name = "annual_income", length = 30, nullable = false)
    private AnnualIncome annualIncome;

    /* ---------- 금융 지식·수익·손실허용 ---------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "financial_knowledge_level", length = 50, nullable = false)
    private FinancialKnowledgeLevel financialKnowledgeLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "expected_return", length = 30, nullable = false)
    private ExpectedReturn expectedReturn;

    @Enumerated(EnumType.STRING)
    @Column(name = "loss_tolerance_level", length = 50, nullable = false)
    private LossToleranceLevel lossToleranceLevel;

    /* ---------- 생성 일시 ---------- */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
