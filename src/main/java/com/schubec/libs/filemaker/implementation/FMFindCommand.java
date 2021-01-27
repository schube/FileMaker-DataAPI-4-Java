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
	private long limit=10;
	private long offset=1;
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
	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
		
		
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
		
	}

	@Override
	protected void buildCommand() {
		ArrayNode query= mapper.createArrayNode();
		getFmJsonObject().set("query", query);
		for(FMFindCriterion fmCriterion:findCriterions) {
			query.add(fmCriterion.asJsonNode());
		}
		
		getFmJsonObject().put("offset", offset);
		getFmJsonObject().put("limit", limit);
	}

	
	
}
