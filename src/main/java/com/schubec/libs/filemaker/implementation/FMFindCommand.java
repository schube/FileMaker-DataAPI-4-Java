package com.schubec.libs.filemaker.implementation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.schubec.libs.filemaker.base.FMCommandWithData;

public class FMFindCommand extends FMCommandWithData {
	private List<FMFindCriterion> findCriterions = new ArrayList<>();
	private List<FMSortRule> sortRules = new ArrayList<>();
	private long limit = 10;
	private long offset = 1;

	public FMFindCommand(String layout) {
		super(layout);

	}

	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/_find";
	}

	@Override
	public HttpEntityEnclosingRequestBase getHttpCommand(URI uri) {
		return new HttpPost(uri);
	}

	public FMFindCriterion addFindCriterion(FMFindCriterion fmCriterion) {
		this.findCriterions.add(fmCriterion);
		return fmCriterion;
	}

	public FMFindCriterion addFindCriterion(String field, String criterion) {
		FMFindCriterion fmCriterion = new FMFindCriterion(field, criterion);
		addFindCriterion(fmCriterion);
		return fmCriterion;
	}

	public FMFindCriterion addFindCriterion(String field, int criterion) {
		FMFindCriterion fmCriterion = new FMFindCriterion(field, criterion);
		addFindCriterion(fmCriterion);
		return fmCriterion;
	}

	public FMFindCommand addSortRule(FMSortRule fmCriterion) {
		this.sortRules.add(fmCriterion);
		return this;
	}

	public FMFindCommand addSortRule(String field, String criterion) {
		FMSortRule fmSortRule = new FMSortRule(field, criterion);
		addSortRule(fmSortRule);
		return this;
	}

	public long getLimit() {
		return limit;
	}

	public FMFindCommand setLimit(long limit) {
		this.limit = limit;
		return this;

	}

	public long getOffset() {
		return offset;
	}

	public FMFindCommand setOffset(long offset) {
		this.offset = offset;
		return this;
	}

	@Override
	protected void buildCommand() {
		ArrayNode query = mapper.createArrayNode();
		getFmJsonObject().set("query", query);
		for (FMFindCriterion fmCriterion : findCriterions) {
			query.add(fmCriterion.asJsonNode());
		}
		if (!sortRules.isEmpty()) {
			ArrayNode sort = mapper.createArrayNode();
			getFmJsonObject().set("sort", sort);
			for (FMSortRule fmSortRule : sortRules) {
				sort.add(fmSortRule.asJsonNode());
			}
		}
		getFmJsonObject().put("offset", offset);
		getFmJsonObject().put("limit", limit);
	}

}
