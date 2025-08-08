package org.invemotion.domain.trade;

import jakarta.persistence.*;
import lombok.*;
import org.invemotion.domain.trade.enums.*;
import org.invemotion.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "trades")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "trade_UUID", nullable = false, updatable = false)
    private UUID tradeUUID;

    @Column(name = "symbol_name", length = 50, nullable = false)
    private String symbolName;

    @Column(name = "symbol_code", length = 50, nullable = false)
    private String symbolCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "result_type")
    private ResultType resultType;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "completed_time", nullable = false)
    private LocalDateTime completedTime;

    @Column(name = "market_price_at_order", precision = 18, scale = 4)
    private BigDecimal marketPriceAtOrder;

    @Column(name = "price_per_buy", precision = 18, scale = 4)
    private BigDecimal pricePerBuy;

    @Column(name = "price_per_sell", precision = 18, scale = 4)
    private BigDecimal pricePerSell;

    @Column(name = "exchange_rate", precision = 10, scale = 4)
    private BigDecimal exchangeRate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;
}

