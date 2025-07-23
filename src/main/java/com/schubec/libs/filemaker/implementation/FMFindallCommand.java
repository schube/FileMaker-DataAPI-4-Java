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

/**
 * Command for retrieving all records from a FileMaker layout.
 * Supports sort rules and pagination via URL parameters.
 */
public class FMFindallCommand extends FMCommandWithoutBody {

	protected List<FMSortRule> sortRules = new ArrayList<>();
	protected long limit = 10;
	protected long offset = 1;

	/**
	 * Constructs a new FMFindallCommand for the given layout.
	 * @param layout The layout name.
	 */
	public FMFindallCommand(String layout) {
		super(layout);

	}

	/**
	 * Returns the endpoint path for this command.
	 * @return The endpoint path as a String.
	 */
	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/records";
	}

	/**
	 * Adds a sort rule to the query.
	 * @param fmCriterion The sort rule object.
	 * @return This command instance.
	 */
	public FMFindallCommand addSortRule(FMSortRule fmCriterion) {
		this.sortRules.add(fmCriterion);
		return this;
	}

	/**
	 * Adds a sort rule by field and value.
	 * @param field The field name.
	 * @param sortorder_or_valuelistname The sort order or value list name.
	 * @return This command instance.
	 */
	public FMFindallCommand addSortRule(String field, String sortorder_or_valuelistname) {
		FMSortRule fmSortRule = new FMSortRule(field, sortorder_or_valuelistname);
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
	public FMFindallCommand setLimit(long limit) {
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
	public FMFindallCommand setOffset(long offset) {
		this.offset = offset;
		return this;
	}

	/**
	 * Prepares the command by adding sort and pagination parameters to the URL.
	 */
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

	/**
	 * Returns the HTTP command object for this command.
	 * @param uri The URI to use for the request.
	 * @return The HTTP GET request object.
	 */
	@Override
	public HttpRequestBase getHttpCommand(URI uri) {
		return new HttpGet(uri);
	}

}
