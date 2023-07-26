package com.example.apmc

import org.ksoap2.HeaderProperty
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.kxml2.kdom.Element

class CallWebService {

    fun callApi(methodName:String, vararg params: Pair<String, String?>) : String {


        var result = ""
        val SOAP_ACTION = Utils.SOAP_NAMESPACE+methodName
        val soapObject = SoapObject(Utils.SOAP_NAMESPACE, methodName)


        params.forEach { (key, value) ->
            soapObject.addProperty(key, value)
        }



        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.setOutputSoapObject(soapObject)
        envelope.dotNet = true



        val httpTransportSE = HttpTransportSE(Utils.SOAP_URL)


        try {

            httpTransportSE.call(SOAP_ACTION,envelope)
            val soapPrimitive = envelope.response
            result = soapPrimitive.toString()
        }
        catch (e:Exception){
            e.printStackTrace()
        }

        return result
    }

}