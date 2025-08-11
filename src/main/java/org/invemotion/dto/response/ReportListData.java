package org.invemotion.dto.response;

import org.invemotion.global.dto.PageResponse;

public record ReportListData(PageResponse<ReportResponse> reports) {
}
