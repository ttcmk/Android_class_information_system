package com.example.popupmenutest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebServiceUtil {
	public static final String SERVICE_NAMESPACE = "http://tempuri.org/";
	public static final String SERVICE_URL = "http://10.15.151.70:8080/WebService.asmx";

	public static List<String> selectAllLectureInfor() {	
		String methodName = "selectAllLectureInfor";

		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		try {
			ht.debug = true;		
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			SoapObject soapObject = new SoapObject(SERVICE_NAMESPACE,methodName);
			envelope.bodyOut = soapObject;
			envelope.dotNet = true;

			ht.call(SERVICE_NAMESPACE + methodName, envelope);
			if (envelope.getResponse() != null) {
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(methodName + "Result");
				return parseInfo(detail);
			}
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	public static List<String> parseInfo(SoapObject detail) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < detail.getPropertyCount(); i++) {				
			result.add(detail.getProperty(i).toString().split(",")[0]);
		}		
		return result;
	}
}
