package io.jchat.android.http;



import android.os.AsyncTask;
import io.jchat.android.utils.Utils;

/**
 * 异步请求类 Base
 * 
 * @author huanyu 类名称：AsyncTaskBase 创建时间:2014-11-4 下午7:15:11
 */
public class AsyncTaskBase {
    public String url;
	public String[] property_nm;// key
	public Object[] property_va;// value
	public String method;
	public HttpObjectResult soapResult;

	/**
	 * 异步请求类
	 * 
	 * @author huanyu 类名称：AsyncTaskCom 创建时间:2014-11-4 下午8:08:01
	 */
	class AsyncTaskCom extends AsyncTask<Object, Object, Object> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
//		    Object res_obj = HttpService.data(url,method, property_nm, property_va);
			Object res_obj = HttpPostService.data(url,method, property_nm, property_va);
			if (null != res_obj) {
				// String so = (String) res_obj;
				return res_obj;
			} else {
				AsyncTaskCom.this.cancel(true);//结束异步加载
				return null;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			Utils.removeProgressDialog();
			if (result == null) {
				soapResult.soapError();
			} else {
				soapResult.soapResult(result);
			}
			super.onPostExecute(result);
		}
		
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			
			super.onCancelled();
		}
	}

	/**
	 * this execute
	 */
	public void executeDo(HttpObjectResult soapResult) {
		this.soapResult = soapResult;
		AsyncTaskCom ast=new AsyncTaskCom();
		ast.execute();
		as=ast;
	}
	static AsyncTaskCom as;
	public static void asDestroy(){
		as.cancel(true);//结束异步加载
	}

    public void setUrl(String url ){
        this.url = url;
    }
	public void setProperty_nm(String[] property_nm) {
		this.property_nm = property_nm;
	}

	public void setProperty_va(Object[] property_va) {
		this.property_va = property_va;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setSoapResult(HttpObjectResult soapResult) {
		this.soapResult = soapResult;
	}

	/**
	 * soap回调接口
	 * 
	 * @author huanyu 类名称：SoapObjectResult 创建时间:2014-11-4 下午7:39:05
	 */
	public interface HttpObjectResult {
		void soapResult(Object obj);

		void soapError();
	}

}
