package com.schubec.libs.filemaker;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schubec.libs.filemaker.base.FMCommandBase;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.base.FMCommandWithDataAndFieldData;
import com.schubec.libs.filemaker.exceptions.FileMakerException;
import com.schubec.libs.filemaker.implementation.FMEditCommand;
import com.schubec.libs.filemaker.implementation.FMFindCommand;
import com.schubec.libs.filemaker.implementation.FMGetRecordByIdCommand;
import com.schubec.libs.filemaker.implementation.FMLayoutCommand;
import com.schubec.libs.filemaker.implementation.FMListScriptsCommand;
import com.schubec.libs.filemaker.implementation.FMUploadContainerCommand;
import com.schubec.libs.filemaker.results.DataInfo;
import com.schubec.libs.filemaker.results.FMLayoutResponse;
import com.schubec.libs.filemaker.results.FMRecordsResponse;
import com.schubec.libs.filemaker.results.FMResult;
import com.schubec.libs.filemaker.results.FMScriptsResponse;

public class FMSession implements AutoCloseable {

	private static final String SESSIONS = "/sessions";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private String host;
	private String schema;
	private int port;
	private boolean debug = false;
	private String database;
	private String fmSessionToken;
	private URI uri;
	private CloseableHttpClient httpclient;

	public final static String DATE_PATTERN = "MM/dd/yyyy";
	public final static String TIMESTAMP_PATTERN = "MM/dd/yyyy HH:mm:ss";
	private HttpRequestBase httpCommand;
	private FMCommandBase fmCommand;
	private CloseableHttpResponse response;

	public FMSession(String fmSessionToken, String host, String database, String schema, int port) {
		this.fmSessionToken = fmSessionToken;
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
					FMSession fmSession = new FMSession(fmDataAccessToken, host, database, schema, port);
					fmSession.uri = uri;
					return fmSession;
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
		if (getFmSessionToken() == null) {
			return false;
		}
		try {
			uri = new URIBuilder().setScheme(schema).setHost(host).setPort(port)
					.setPath("/fmi/data/vLatest/databases/" + database + SESSIONS + "/" + getFmSessionToken()).build();

			HttpDelete httpPost = new HttpDelete(uri);

			httpPost.setHeader("Authorization", "Bearer " + getFmSessionToken());
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			response = httpclient.execute(httpPost);

			try {

				int responseCode = response.getStatusLine().getStatusCode();
				if (responseCode == 200) {
					fmSessionToken = null;
					return true;
				}
			} finally {
				response.close();
				httpclient.close();
				httpclient = null;
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

	private URI caluculateURI() throws URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder()
				.setScheme(schema)
				.setHost(host)
				.setPort(port)
				.setPath("/fmi/data/vLatest/databases/" + database + fmCommand.getEndpoint());
		if (!fmCommand.getUrlParameters().isEmpty()) {
			uriBuilder.setParameters(fmCommand.getUrlParameters());
		}

		return uriBuilder.build();

	}

	public FMResult<FMRecordsResponse> uploadContainer(FMUploadContainerCommand fmCommand) throws FileMakerException {
		this.fmCommand = fmCommand;
		if (fmCommand.getFile() == null) {
			throw new FileMakerException(FileMakerException.ERRORCODE_MISSING_VALUE, "No File set");
		}
		try {
			uri = caluculateURI();
			httpCommand = fmCommand.getHttpCommand(uri);
			httpCommand.setHeader("Authorization", "Bearer " + getFmSessionToken());
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
				response = httpclient.execute(httpCommand);

				return processFileMakerRecordsResponse();
			}
		} catch (URISyntaxException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_URI_SYNTAX_ERROR, "URI syntax is not valid",
					e);
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		}
	}

	public FMResult<FMScriptsResponse> execute(FMListScriptsCommand fmCommand) throws FileMakerException {
		try {
			this.fmCommand = fmCommand;
			fmCommand.prepareCommand();

			uri = caluculateURI();

			httpCommand = fmCommand.getHttpCommand(uri);

			httpCommand.setHeader("Authorization", "Bearer " + getFmSessionToken());
			httpCommand.setHeader("Accept", "application/json");
			httpCommand.setHeader("Content-type", "application/json");

			response = httpclient.execute(httpCommand);
			try {
				int responseCode = response.getStatusLine().getStatusCode();
				HttpEntity entity = response.getEntity();
				String entityString = (entity != null ? EntityUtils.toString(entity) : null);

				// System.out.println(responseCode + ": " + entityString);
				FMResult<FMScriptsResponse> fmresult = objectMapper.readValue(entityString,new TypeReference<FMResult<FMScriptsResponse>>(){});
				if (isDebug()) {
					fmresult.setHttpBodyString(entityString);
					fmresult.setRequestUri(uri);
				}

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
	public FMResult<FMLayoutResponse> execute(FMLayoutCommand fmCommand) throws FileMakerException {
		this.fmCommand = fmCommand;
		try {
			fmCommand.prepareCommand();
			uri = caluculateURI();

			httpCommand = fmCommand.getHttpCommand(uri);

			httpCommand.setHeader("Authorization", "Bearer " + getFmSessionToken());
			httpCommand.setHeader("Accept", "application/json");
			httpCommand.setHeader("Content-type", "application/json");

			response = httpclient.execute(httpCommand);
			return processFileMakerLayoutResponse();

		} catch (URISyntaxException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_URI_SYNTAX_ERROR, "URI syntax is not valid",
					e);
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		}
	}
	
	private FMResult<FMLayoutResponse> processFileMakerLayoutResponse() throws FileMakerException, IOException {

		try {

			int responseCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			String entityString = (entity != null ? EntityUtils.toString(entity) : null);
			
			FMResult<FMLayoutResponse> fmresult = objectMapper.readValue(entityString, new TypeReference<FMResult<FMLayoutResponse>>(){});
			fmresult.setHttpStatusCode(responseCode);
			if (isDebug()) {
				fmresult.setHttpBodyString(entityString);
				fmresult.setRequestUri(uri);
			}
			if (responseCode != 200) {
				throw new FileMakerException(fmresult.getMessages()[0].getCode(), fmresult.getMessagesAsString());
			}
			return fmresult;
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		} finally {
			response.close();
		}

	}

	
	public FMResult<FMRecordsResponse> execute(FMCommandBase fmCommand) throws FileMakerException {
		this.fmCommand = fmCommand;
		if (fmCommand instanceof FMUploadContainerCommand) {
			return this.uploadContainer((FMUploadContainerCommand) fmCommand);
		}

		try {
			fmCommand.prepareCommand();
			uri = caluculateURI();

			httpCommand = fmCommand.getHttpCommand(uri);

			httpCommand.setHeader("Authorization", "Bearer " + getFmSessionToken());
			httpCommand.setHeader("Accept", "application/json");
			httpCommand.setHeader("Content-type", "application/json");

			if (fmCommand instanceof FMCommandWithData) {
				FMCommandWithData fmCommandWithData = (FMCommandWithData) fmCommand;
				String json = objectMapper.writer().writeValueAsString(fmCommandWithData.asJsonNode());
				// System.out.println("Sending json body: " + json );
				StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
				((HttpEntityEnclosingRequestBase) httpCommand).setEntity(requestEntity);
			}
			response = httpclient.execute(httpCommand);
			return processFileMakerRecordsResponse();

		} catch (URISyntaxException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_URI_SYNTAX_ERROR, "URI syntax is not valid",
					e);
		} catch (IOException e) {
			throw new FileMakerException(FileMakerException.ERRORCODE_IO_ERROR,
					"Error while retrieving data from host", e);
		}
	}

	private FMResult<FMRecordsResponse> processFileMakerRecordsResponse() throws FileMakerException, IOException {

		try {

			int responseCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			String entityString = (entity != null ? EntityUtils.toString(entity) : null);
			//System.out.println(responseCode + ": " + entityString);
			FMResult<FMRecordsResponse> fmresult = objectMapper.readValue(entityString, new TypeReference<FMResult<FMRecordsResponse>>(){});
			fmresult.setHttpStatusCode(responseCode);
			if (isDebug()) {
				fmresult.setHttpBodyString(entityString);
				fmresult.setRequestUri(uri);
			}
			if (responseCode != 200) {
				//Now check, if we have a FileMaker 401 error which means no record found for a Find Request
				//In this case we will NOT throw an exception but return an empty FMResult
				if (fmCommand instanceof FMFindCommand) {
					if(fmresult.getMessages()[0].getCode() == 401) {
						fmresult.getResponse().setDataInfo(new DataInfo());
						return fmresult;
					}
				}
				throw new FileMakerException(fmresult.getMessages()[0].getCode(), fmresult.getMessagesAsString());
			}
			if (fmCommand instanceof FMCommandWithDataAndFieldData) {
				if (((FMCommandWithDataAndFieldData) fmCommand).isReturnRecord()) {
					Long recordId;
					//Edit command sends no record ID in response
					if (fmCommand instanceof FMEditCommand) {
						recordId = ((FMEditCommand) fmCommand).getRecordId();
					} else {
						recordId = Long.parseLong(fmresult.getResponse().getRecordId());
					}
					 
					FMCommandBase fmGetbyId = new FMGetRecordByIdCommand(
							fmCommand.getLayout(),recordId);
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

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public URI getUri() {
		return uri;
	}

	@Override
	public void close() throws Exception {
		logout();

	}

	public String getFmSessionToken() {
		return fmSessionToken;
	}

}
