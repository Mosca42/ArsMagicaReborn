package amreborn.network;

import java.util.HashMap;
import java.util.LinkedList;

import amreborn.ArsMagicaReborn;

public class SeventhSanctum {
	private static final String webURL = "http://www.seventhsanctum.com/generate.php?Genname=spell";
	private boolean failed = false;

	private static final HashMap<String, String> postOptions = new HashMap<String, String>();

	public static final SeventhSanctum instance = new SeventhSanctum();

	private LinkedList<String> suggestions;

	private SeventhSanctum() {
		suggestions = new LinkedList<String>();
	}

	public void init() {
		postOptions.clear();
		postOptions.put("selGenCount", "25");
		postOptions.put("selGenType", "SEEDALL");

		if (ArsMagicaReborn.config.suggestSpellNames()) {
			getSuggestions();
		} else {
			failed = true;
		}
	}

	public String getNextSuggestion() {
		if (suggestions.size() <= 0)
			getSuggestions();
		return suggestions.pop();
	}

	private void getSuggestions() {
		try {
			String s = amreborn.utils.WebRequestUtils.sendPost(webURL, postOptions);
			// LogHelper.info(s);
			int startIndex = s.lastIndexOf("SubSubContentTitle");
			if (startIndex == -1)
				return;
			startIndex = s.indexOf("<!--Title -->", startIndex) + 13;
			int endIndex = s.indexOf("&nbsp;", startIndex);
			if (endIndex == -1)
				return;

			s = s.substring(startIndex, endIndex);
			s = s.replace("\t", "");
			String[] suggestions = s.split("<div class=\"GeneratorResult");
			for (String suggestion : suggestions)
				parseAndAddSuggestion(suggestion);
		} catch (Throwable t) {
			t.printStackTrace();
			failed = true;
		}
	}

	private void parseAndAddSuggestion(String s) {
		if (!s.endsWith("</div>"))
			return;

		int startIndex = s.indexOf(">");
		if (startIndex == -1)
			return;
		int endIndex = s.indexOf("<", startIndex);
		if (endIndex == -1)
			return;

		s = s.substring(startIndex + 1, endIndex);
		if (s.length() <= 20)
			suggestions.add(s);
	}
}
