package validatecode.xiaolian;

import android.support.v7.app.ActionBarActivity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	public static final int MSG_RECEIVED_CODE = 1;
	private EditText et_validateCode= null;
	
	private SmsObserver mObserver;
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == MSG_RECEIVED_CODE) {
				String code =(String) msg.obj;
				//更新UI
				et_validateCode.setText(code);
			}
		}
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et_validateCode = 	(EditText) findViewById(R.id.et_validateCode);
		
		mObserver = new SmsObserver(MainActivity.this , mHandler);
		Uri uri = 	Uri.parse("content://sms");
		getContentResolver().registerContentObserver(uri, true, mObserver);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getContentResolver().unregisterContentObserver(mObserver);
	}
}
