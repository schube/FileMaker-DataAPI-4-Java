package com.schubec.libs.filemaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schubec.libs.filemaker.base.FMCommandBase;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.implementation.FMUploadContainerCommand;
import com.schubec.libs.filemaker.results.FMResult;

public class FMSession {

	private static final String SESSIONS = "/sessions";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private String host;
	private String schema;
	private int port;
	private String database;
	private String session;
	private CloseableHttpClient httpclient;

	public FMSession(String session, String host, String database, String schema, int port) {
		this.session = session;
		this.host = host;
		this.schema = schema;
		this.port = port;
		this.database = database;
		httpclient = HttpClients.createDefault();
	}

	public static FMSession login(String host, String database, String user, String password, String schema, int port) throws IOException, URISyntaxException {
		URI uri = new URIBuilder()
				.setPort(port)
				.setScheme(schema)
				.setHost(host)
				.setPath("/fmi/data/v1/databases/" + database + SESSIONS)
				.build();

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
		provider.setCredentials(AuthScope.ANY, credentials);

		HttpHost targetHost = new HttpHost(host, port, "https");
		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		// Add AuthCache to the execution context
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(provider);
		context.setAuthCache(authCache);

		CloseableHttpClient httpclient = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider)

				.build();
		HttpPost httpPost = new HttpPost(uri);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = httpclient.execute(httpPost, context);
		try {

			int responseCode = response.getStatusLine().getStatusCode();

			if (responseCode == 200) {
				String fmDataAccessToken = response.getFirstHeader("X-FM-Data-Access-Token").getValue();
				return new FMSession(fmDataAccessToken, host, database, schema, port);
			} else {
				System.err.println("HTTP Status Code" + responseCode);
			}
		} finally {
			response.close();
		}

		throw new IOException("Ging nicht.");
	}

	public static FMSession login(String host, String database, String user, String password) throws IOException, URISyntaxException {
		return login(host, database, user, password, "https", 443);

	}

	public boolean logout() throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder()
				.setScheme(schema)
				.setHost(host)
				.setPort(port)
				.setPath("/fmi/data/v1/databases/" + database + SESSIONS + "/" + session)
				.build();

		HttpDelete httpPost = new HttpDelete(uri);

		httpPost.setHeader("Authorization", "Bearer " + session);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {

			int responseCode = response.getStatusLine().getStatusCode();
			if (responseCode == 200) {
				return true;
			}
		} finally {
			response.close();
		}

		return false;

	}

	private URI getURI(FMCommandBase fmCommand) throws URISyntaxException {
		return new URIBuilder()
				.setScheme(schema)
				.setHost(host)
				.setPort(port)
				.setPath("/fmi/data/vLatest/databases/" + database + fmCommand.getEndpoint())
				.build();
	}

	public FMResult uploadContainer(FMUploadContainerCommand fmCommand) throws ClientProtocolException, IOException, URISyntaxException {
		if (fmCommand.getFile() == null) {
			throw new IOException("No File set");
		}
		URI uri = getURI(fmCommand);
		HttpRequestBase httpCommand = fmCommand.getHttpCommand(uri);
		httpCommand.setHeader("Authorization", "Bearer " + session);
		httpCommand.setHeader("Accept", "application/json");

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		try (FileInputStream fis = new FileInputStream(fmCommand.getFile())) {

			builder.addBinaryBody(
					"upload", // This MUST be named "upload", otherwise FileMaker does not accept it
					fis,
					ContentType.APPLICATION_OCTET_STREAM,
					fmCommand.getFileName());

			HttpEntity multipart = builder.build();
			((HttpPost) httpCommand).setEntity(multipart);
			CloseableHttpResponse response = httpclient.execute(httpCommand);

			return processFileMakerResponse(response);
		}
	}

	public FMResult execute(FMCommandBase fmCommand) throws ClientProtocolException, IOException, URISyntaxException {
		if (fmCommand instanceof FMUploadContainerCommand) {
			return this.uploadContainer((FMUploadContainerCommand) fmCommand);
		}
		URI uri = getURI(fmCommand);

		HttpRequestBase httpCommand = fmCommand.getHttpCommand(uri);

		httpCommand.setHeader("Authorization", "Bearer " + session);
		httpCommand.setHeader("Accept", "application/json");
		httpCommand.setHeader("Content-type", "application/json");

		if (fmCommand instanceof FMCommandWithData) {
			FMCommandWithData fmCommandWithData = (FMCommandWithData) fmCommand;
			String json = objectMapper.writer().writeValueAsString(fmCommandWithData.asJsonNode());
			// System.out.println("Sending json body: " + json );
			StringEntity requestEntity = new StringEntity(
					json,
					ContentType.APPLICATION_JSON);
			((HttpEntityEnclosingRequestBase) httpCommand).setEntity(requestEntity);
		}
		CloseableHttpResponse response = httpclient.execute(httpCommand);
		return processFileMakerResponse(response);

	}

	private FMResult processFileMakerResponse(CloseableHttpResponse response) throws IOException, JsonProcessingException, JsonMappingException {
		try {
			int responseCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			String entityString = (entity != null ? EntityUtils.toString(entity) : null);
			// System.out.println(responseCode + ": " + entityString);
			FMResult fmresult = objectMapper.readValue(entityString, FMResult.class);
			fmresult.setHttpStatusCode(responseCode);
			// if (responseCode == 200) {
			// return true;
			// }
			return fmresult;
		} finally {
			response.close();
		}
	}

}
