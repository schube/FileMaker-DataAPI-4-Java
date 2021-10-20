package com.schubec.libs.filemaker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
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
import org.apache.http.client.methods.HttpGet;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schubec.libs.filemaker.base.FMCommandBase;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.base.FMCommandWithDataAndFieldData;
import com.schubec.libs.filemaker.exceptions.FileMakerException;
import com.schubec.libs.filemaker.implementation.FMGetRecordByIdCommand;
import com.schubec.libs.filemaker.implementation.FMListScriptsCommand;
import com.schubec.libs.filemaker.implementation.FMUploadContainerCommand;
import com.schubec.libs.filemaker.results.FMResult;
import com.schubec.libs.filemaker.results.FMScriptsResult;

public class FMSession {

	private static final String SESSIONS = "/sessions";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private String host;
	private String schema;
	private int port;
	private String database;
	private String session;
	private CloseableHttpClient httpclient;

	public final static String DATE_PATTERN = "MM/dd/yyyy";
	public final static DateFormat DF_DATE = new SimpleDateFormat(DATE_PATTERN);

	public final static String TIMESTAMP_PATTERN = "MM/dd/yyyy HH:mm:ss";
	public final static DateFormat DF_TIMESTAMP = new SimpleDateFormat(TIMESTAMP_PATTERN);

	public FMSession(String session, String host, String database, String schema, int port) {
		this.session = session;
		this.host = host;
		this.schema = schema;
		this.port = port;
		this.database = database;
		httpclient = HttpClients.createDefault();

	}

	/**
	 * Expected the URL to a container field
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws FileMakerException
	 */
	public static Optional<byte[]> getContainerdata(String url) throws FileMakerException {
		if (url == null || url.isEmpty()) {
			return Optional.empty();
		}
		try {
			CloseableHttpClient httpclient = HttpClientBuilder.create()
					.build();
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			try {
				int responseCode = response.getStatusLine().getStatusCode();
				HttpEntity entity = response.getEntity();
				if (responseCode != 200) {
					throw new FileMakerException(FileMakerException.ERRORCODE_INVALID_HTTP_STATUS, "Host returned HTTP status code of [" + responseCode + "]");
				}
				byte[] bytes = IOUtils.toByteArray(entity.getContent());
				return Optional.of(bytes);

			} finally {
				response.close();
			}

		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR, "Error while retrieving data from host", e);
		}

	}

	public static FMSession login(String host, String database, String user, String password, String schema, int port)
			throws FileMakerException {
		try {
			URI uri = new URIBuilder().setPort(port).setScheme(schema).setHost(host)
					.setPath("/fmi/data/vLatest/databases/" + database + SESSIONS).build();

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

			CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider)

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
					throw new FileMakerException(FileMakerException.ERRORCODE_INVALID_HTTP_STATUS, "Host returned HTTP status code of [" + responseCode + "]");
				}
			} finally {
				response.close();
			}
		} catch (URISyntaxException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_URI_SYNTAX_ERROR, "URI syntax is not valid",
					e);
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		}
	}

	public static FMSession login(String host, String database, String user, String password)
			throws FileMakerException {
		return login(host, database, user, password, "https", 443);

	}

	public boolean logout() throws FileMakerException {
		try {
			URI uri = new URIBuilder().setScheme(schema).setHost(host).setPort(port)
					.setPath("/fmi/data/vLatest/databases/" + database + SESSIONS + "/" + session).build();

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
		} catch (URISyntaxException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_URI_SYNTAX_ERROR, "URI syntax is not valid",
					e);

		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		}
	}

	private URI getURI(FMCommandBase fmCommand) throws URISyntaxException {

		return new URIBuilder().setScheme(schema).setHost(host).setPort(port)
				.setPath("/fmi/data/vLatest/databases/" + database + fmCommand.getEndpoint()).build();

	}

	public FMResult uploadContainer(FMUploadContainerCommand fmCommand) throws FileMakerException {
		if (fmCommand.getFile() == null) {
			throw new FileMakerException(FileMakerException.ERRORCODE_MISSING_VALUE, "No File set");
		}
		try {
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

				return processFileMakerResponse(fmCommand, response);
			}
		} catch (URISyntaxException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_URI_SYNTAX_ERROR, "URI syntax is not valid",
					e);
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		}
	}

	public FMScriptsResult execute(FMListScriptsCommand fmCommand) throws FileMakerException {
		try {
			URI uri = getURI(fmCommand);

			HttpRequestBase httpCommand = fmCommand.getHttpCommand(uri);

			httpCommand.setHeader("Authorization", "Bearer " + session);
			httpCommand.setHeader("Accept", "application/json");
			httpCommand.setHeader("Content-type", "application/json");

			CloseableHttpResponse response = httpclient.execute(httpCommand);

			try {
				int responseCode = response.getStatusLine().getStatusCode();
				HttpEntity entity = response.getEntity();
				String entityString = (entity != null ? EntityUtils.toString(entity) : null);
				// System.out.println(responseCode + ": " + entityString);
				FMScriptsResult fmresult = objectMapper.readValue(entityString, FMScriptsResult.class);

				if (responseCode != 200) {
					throw new FileMakerException(fmresult.getMessages()[0].getCode(), fmresult.getMessagesAsString());
				}
				return fmresult;
			} finally {
				response.close();
			}
		} catch (URISyntaxException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_URI_SYNTAX_ERROR, "URI syntax is not valid",
					e);
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		}
	}

	public FMResult execute(FMCommandBase fmCommand) throws FileMakerException {
		if (fmCommand instanceof FMUploadContainerCommand) {
			return this.uploadContainer((FMUploadContainerCommand) fmCommand);
		}

		try {
			URI uri = getURI(fmCommand);

			HttpRequestBase httpCommand = fmCommand.getHttpCommand(uri);

			httpCommand.setHeader("Authorization", "Bearer " + session);
			httpCommand.setHeader("Accept", "application/json");
			httpCommand.setHeader("Content-type", "application/json");

			if (fmCommand instanceof FMCommandWithData) {
				FMCommandWithData fmCommandWithData = (FMCommandWithData) fmCommand;
				String json = objectMapper.writer().writeValueAsString(fmCommandWithData.asJsonNode());
				// System.out.println("Sending json body: " + json );
				StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
				((HttpEntityEnclosingRequestBase) httpCommand).setEntity(requestEntity);
			}
			CloseableHttpResponse response = httpclient.execute(httpCommand);
			return processFileMakerResponse(fmCommand, response);

		} catch (URISyntaxException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_URI_SYNTAX_ERROR, "URI syntax is not valid",
					e);
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		}
	}

	private FMResult processFileMakerResponse(FMCommandBase fmCommand, CloseableHttpResponse response) throws FileMakerException, IOException {
		try {
			int responseCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			String entityString = (entity != null ? EntityUtils.toString(entity) : null);
			// System.out.println(responseCode + ": " + entityString);
			FMResult fmresult = objectMapper.readValue(entityString, FMResult.class);
			fmresult.setHttpStatusCode(responseCode);
			if (responseCode != 200) {
				throw new FileMakerException(fmresult.getMessages()[0].getCode(), fmresult.getMessagesAsString());
			}
			if (fmCommand instanceof FMCommandWithDataAndFieldData) {
				if (((FMCommandWithDataAndFieldData) fmCommand).isReturnRecord()) {
					FMCommandBase fmGetbyId = new FMGetRecordByIdCommand(fmCommand.getLayout(),
							Long.parseLong(fmresult.getResponse().getRecordId()));
					return execute(fmGetbyId);
				}

			}
			return fmresult;
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		} finally {
			response.close();
		}

	}

}
