import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
	public JSONObject connectionUrlToJSON(String turn) {
		try {
			URL url = new URL("https://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo="+turn); //중요
			HttpsURLConnection conn = null;
			HostnameVerifier hnv = new HostnameVerifier() { //https URL을 연결
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true; //중요
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hnv);
			conn = (HttpsURLConnection) url.openConnection(); //기본 return UrlConnection -> 연결코드 //중요
			
	//		InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); //감싸는거 복잡함
	//		System.out.println(br.readLine());
			String iLine = br.readLine(); //접속오류가 났을때 받아오는값이 쓰레기값이기때문에 ull에 제공하는 메소드가 없음
			JSONParser ps = new JSONParser();
			JSONObject jObj = (JSONObject)ps.parse(iLine); //JSONObject 형태로 만들어주려면 parse가 필요함 //중요
			return jObj;
		} catch(Exception e) { //쓰레기값(null) 받아왔을때
			System.out.println("접속 실패");
			return null;
		}
	}
}
