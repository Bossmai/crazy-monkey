package com.mead.android.crazymonkey.persistence;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mead.android.crazymonkey.CrazyMonkeyBuild;
import com.mead.android.crazymonkey.model.Task;

public class MongoTask implements TaskDAO{

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

	@Override
	public List<Task> getTasksListByDay(int times, String slaverMac, Date date) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<Task> taskList = new ArrayList<Task>();
		try {
			
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			String requestUrl = String.format("%s/task/getnew?slaver.slaverMAC=%s&planExecDate=%s", build.getNodeHttpServer(), slaverMac, format.format(date));
			
			HttpGet httpGet = new HttpGet(requestUrl);
			CloseableHttpResponse response = httpclient.execute(httpGet);

			try {
				HttpEntity entity = response.getEntity();
				
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					if (entity.getContent().available() > 1) {
						Task task = objectMapper.readValue(entity.getContent(), Task.class);
						taskList.add(task);
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
		// TODO Auto-generated method stub
		return null;
	}

}
