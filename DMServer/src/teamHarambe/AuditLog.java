package teamHarambe;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONObject;

public class AuditLog {
	private static final int LOG_CAPACITY = 100;
	
	private ArrayList<JSONObject> logs;
	
	public AuditLog() {
		logs = new ArrayList<>();
	}
	
	public AuditLog(JSONObject JSONlogs) {
		this();
		String[] keyNames = JSONObject.getNames(JSONlogs);
		keyNames = (keyNames == null) ? new String[0] : keyNames;
		
		for (int i=0; i < keyNames.length; i++) {
			logs.add(JSONlogs.getJSONObject(i+""));
		}
	}
	
	public void logAction(String action, Referee referee) {
		Calendar today = Calendar.getInstance();
		
		if (logs.size() == LOG_CAPACITY) {
			logs.remove(logs.size()-1);
		}

		JSONObject data = new JSONObject();
		data.put("ActionType", action);
		data.put("Referee", referee.getEmail());
		data.put("Date", today.getTimeInMillis());
		
		logs.add(0, data);
		try {
			Server.saveData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<JSONObject> getLogs() {
		return logs;
	}
	
	public JSONObject getJSONLogs() {
		JSONObject logsJSON = new JSONObject();
		
		for (int i=0; i < logs.size(); i++) {
			logsJSON.put(i+"", logs.get(i));
		}
		
		return logsJSON;
	}
}
