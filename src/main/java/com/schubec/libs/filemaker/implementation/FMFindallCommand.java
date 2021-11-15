package com.schubec.libs.filemaker.implementation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.base.FMCommandWithoutBody;

public class FMFindallCommand extends FMCommandWithoutBody {

	protected List<FMSortRule> sortRules = new ArrayList<>();
	protected long limit = 10;
	protected long offset = 1;

	public FMFindallCommand(String layout) {
		super(layout);

	}

	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/records";
	}

	public FMFindallCommand addSortRule(FMSortRule fmCriterion) {
		this.sortRules.add(fmCriterion);
		return this;
	}

	public FMFindallCommand addSortRule(String field, String sortorder_or_valuelistname) {
		FMSortRule fmSortRule = new FMSortRule(field, sortorder_or_valuelistname);
		addSortRule(fmSortRule);
		return this;
	}

	public long getLimit() {
		return limit;
	}

	public FMFindallCommand setLimit(long limit) {
		this.limit = limit;
		return this;

	}

	public long getOffset() {
		return offset;
	}

	public FMFindallCommand setOffset(long offset) {
		this.offset = offset;
		return this;
	}

	@Override
	public void prepareCommand() {
		super.prepareCommand();

		addUrlParameter("_offset", offset + "");
		addUrlParameter("_limit", limit + "");

		try {
			final ObjectMapper mapper = new ObjectMapper();
			ArrayNode sort = mapper.createArrayNode();
			if (!sortRules.isEmpty()) {
				for (FMSortRule fmSortRule : sortRules) {
					sort.add(fmSortRule.asJsonNode());
				}
				String jsonSort = mapper.writer().writeValueAsString(sort);
				addUrlParameter("_sort", jsonSort);
			}
		} catch (JsonProcessingException e) {
			// do nothing
		}
	}

	@Override
	public HttpRequestBase getHttpCommand(URI uri) {
		return new HttpGet(uri);
	}

}
