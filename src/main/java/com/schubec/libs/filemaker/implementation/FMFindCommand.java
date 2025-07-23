package com.schubec.libs.filemaker.implementation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.schubec.libs.filemaker.base.FMCommandWithData;

/**
 * Command for performing a find query on a FileMaker layout.
 * Supports adding find criteria, sort rules, and pagination.
 */
public class FMFindCommand extends FMCommandWithData {
	private List<FMFindCriterion> findCriterions = new ArrayList<>();
	private List<FMSortRule> sortRules = new ArrayList<>();
	private long limit = 10;
	private long offset = 1;

	/**
	 * Constructs a new FMFindCommand for the given layout.
	 * @param layout The layout name.
	 */
	public FMFindCommand(String layout) {
		super(layout);

	}

	/**
	 * Returns the endpoint path for this command.
	 * @return The endpoint path as a String.
	 */
	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/_find";
	}

	/**
	 * Returns the HTTP command object for this command.
	 * @param uri The URI to use for the request.
	 * @return The HTTP POST request object.
	 */
	@Override
	public HttpEntityEnclosingRequestBase getHttpCommand(URI uri) {
		return new HttpPost(uri);
	}

	/**
	 * Adds a find criterion to the query.
	 * @param fmCriterion The find criterion object.
	 * @return The added FMFindCriterion.
	 */
	public FMFindCriterion addFindCriterion(FMFindCriterion fmCriterion) {
		this.findCriterions.add(fmCriterion);
		return fmCriterion;
	}

	/**
	 * Adds a find criterion by field and string value.
	 * @param field The field name.
	 * @param criterion The criterion value.
	 * @return The added FMFindCriterion.
	 */
	public FMFindCriterion addFindCriterion(String field, String criterion) {
		FMFindCriterion fmCriterion = new FMFindCriterion(field, criterion);
		addFindCriterion(fmCriterion);
		return fmCriterion;
	}

	/**
	 * Adds a find criterion by field and integer value.
	 * @param field The field name.
	 * @param criterion The criterion value.
	 * @return The added FMFindCriterion.
	 */
	public FMFindCriterion addFindCriterion(String field, int criterion) {
		FMFindCriterion fmCriterion = new FMFindCriterion(field, criterion);
		addFindCriterion(fmCriterion);
		return fmCriterion;
	}

	/**
	 * Adds a sort rule to the query.
	 * @param fmCriterion The sort rule object.
	 * @return This command instance.
	 */
	public FMFindCommand addSortRule(FMSortRule fmCriterion) {
		this.sortRules.add(fmCriterion);
		return this;
	}

	/**
	 * Adds a sort rule by field and criterion.
	 * @param field The field name.
	 * @param criterion The sort order or value list name.
	 * @return This command instance.
	 */
	public FMFindCommand addSortRule(String field, String criterion) {
		FMSortRule fmSortRule = new FMSortRule(field, criterion);
		addSortRule(fmSortRule);
		return this;
	}

	/**
	 * Returns the current limit for the query.
	 * @return The limit value.
	 */
	public long getLimit() {
		return limit;
	}

	/**
	 * Sets the limit for the query.
	 * @param limit The maximum number of records to return.
	 * @return This command instance.
	 */
	public FMFindCommand setLimit(long limit) {
		this.limit = limit;
		return this;

	}

	/**
	 * Returns the current offset for the query.
	 * @return The offset value.
	 */
	public long getOffset() {
		return offset;
	}

	/**
	 * Sets the offset for the query.
	 * @param offset The record offset (1-based).
	 * @return This command instance.
	 */
	public FMFindCommand setOffset(long offset) {
		this.offset = offset;
		return this;
	}

	/**
	 * Builds the command's JSON body with query and sort information.
	 */
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
