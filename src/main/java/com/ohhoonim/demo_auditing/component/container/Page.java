package com.ohhoonim.demo_auditing.component.container;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;

public record Page(
		Integer totalCount,
		Integer limit,
		Id lastSeenKey) {

	public Page {
		if (limit == null || limit.equals(0)) {
			limit = 10;
		}
	}

	public Page() {
		this(null, null, null);
	}

	public Page(Id lastSeenKey) {
		this(null, 20, lastSeenKey);
	}

}