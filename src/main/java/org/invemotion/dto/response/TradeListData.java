package org.invemotion.dto.response;

import org.invemotion.global.dto.PageResponse;

public record TradeListData(PageResponse<TradeResponse> trades) {
}