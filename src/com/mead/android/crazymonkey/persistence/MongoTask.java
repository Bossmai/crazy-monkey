package com.mead.android.crazymonkey.persistence;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.mead.android.crazymonkey.AndroidEmulator;
import com.mead.android.crazymonkey.CrazyMonkeyBuild;
import com.mead.android.crazymonkey.model.Task;

public class MongoTask implements TaskDAO {

	private static ObjectMapper objectMapper = new ObjectMapper();

	private CrazyMonkeyBuild build;

	public CrazyMonkeyBuild getBuild() {
		return build;
	}

	public void setBuild(CrazyMonkeyBuild build) {
		this.build = build;
	}

	public MongoTask(CrazyMonkeyBuild build) {
		super();
		this.build = build;
	}

	private String getRequestUrl(String slaverMac, Date date) {
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String requestUrl = null;
		try {
			
			requestUrl = String.format("%s/task/?slaver.slaverMAC=%s&planExecDate=%s", build.getNodeHttpServer(),
					URLEncoder.encode(slaverMac, StandardCharsets.UTF_8.toString()),
					URLEncoder.encode(format.format(date), StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return requestUrl;
	}

	private String getRequestUrl(String slaverMac) {
		String requestUrl = null;
		try {
			requestUrl = String.format("%s/task/getnew?slaver.slaverMAC=%s", build.getNodeHttpServer(),
					URLEncoder.encode(slaverMac, StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return requestUrl;
	}

	private String getRequestUrl(Date date) {
		String requestUrl = null;
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		try {
			requestUrl = String.format("%s/task/getnew?planExecDate=%s", build.getNodeHttpServer(),
					URLEncoder.encode(format.format(date), StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return requestUrl;
	}

	public List<Task> getTaskList(String requestUrl) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<Task> taskList = new ArrayList<Task>();
		try {
			HttpGet httpGet = new HttpGet(requestUrl);
			CloseableHttpResponse response = httpclient.execute(httpGet);

			try {
				HttpEntity entity = response.getEntity();

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					if (entity.getContentLength() > 1) {

						taskList = objectMapper.readValue(entity.getContent(), new TypeReference<List<Task>>() {
						});

						if (taskList != null && !taskList.isEmpty()) {
							for (int i = 0; i < taskList.size(); i++) {
								Task task = taskList.get(i);
								AndroidEmulator emulator = new AndroidEmulator();
								emulator.setAvdName(String.format("Android_Monkey_%d", i));
								task.setEmulator(emulator);

							}
						}
					}
				}

				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return taskList;
	}

	@Override
	public List<Task> getTasks(int times, String slaverMac, Date date) {
		// get the tasks by the slaver and the today
		List<Task> taskList = this.getTaskList(this.getRequestUrl(slaverMac, date));
		if (taskList == null || taskList.isEmpty()) {
			// if the tasks of the slaver is empty, get the tasks before in this machine
			taskList = this.getTaskList(this.getRequestUrl(slaverMac));
		}
		if (taskList == null || taskList.isEmpty()) {
			// if the tasks of the slaver is empty, get the tasks today from other machine
			taskList = this.getTaskList(this.getRequestUrl(date));
		}
		return taskList;
	}
	
	public boolean updateTask(Task task) {
		boolean result = false;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(String.format("%s/task/update?%s", build.getNodeHttpServer(), task.getId()));
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		nvps.add(new BasicNameValuePair("status", task.getStatus().toString()));
		nvps.add(new BasicNameValuePair("slaver.slaverMAC", task.getSlaver().getSlaverMAC()));
		nvps.add(new BasicNameValuePair("execStartTime", format.format(task.getExecStartTime())));
		nvps.add(new BasicNameValuePair("exceEndTime", format.format(task.getExceEndTime())));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			System.out.println(entity.getContent());
			EntityUtils.consume(entity);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
