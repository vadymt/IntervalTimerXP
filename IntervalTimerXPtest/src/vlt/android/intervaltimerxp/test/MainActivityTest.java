package vlt.android.intervaltimerxp.test;

import com.jayway.android.robotium.solo.Solo;

import vlt.android.intervaltimerxp.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity; // MyActivity is the class name of the app
									// under test
	private Solo solo;
	private Button bAdd;
	private ListView listView;
	public static final int ADAPTER_COUNT = 9;

	// private static Class<?> mainActivityClass;
	//
	// static
	// {
	// try
	// {
	// mainActivityClass =
	// Class.forName("vlt.android.intervaltimerxp.MainActivity");
	// }
	// catch(ClassNotFoundException e)
	// {
	// throw new RuntimeException(e);
	// }
	// }
	//
	// @SuppressWarnings("unchecked")
	public MainActivityTest() {
		super("vlt.android.intervaltimerxp", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		solo = new Solo(getInstrumentation(), getActivity());

		mActivity = (MainActivity) getActivity();
		listView = (ListView) mActivity
				.findViewById(vlt.android.intervaltimerxp.R.id.listTraining);
		bAdd = (Button) mActivity
				.findViewById(vlt.android.intervaltimerxp.R.id.buttonAddTraining);
	}

	public void testButtonAdd() {

		TouchUtils.clickView(this, bAdd);
		solo.assertCurrentActivity("main", MainActivity.class);
		getInstrumentation().waitForIdleSync();
		// Now do whatever you need to do to trigger your dialog.

		// Let's assume a properly lame dialog title.
		assertTrue(
				"Couldn't find dialog add training!",
				solo.searchText(mActivity.getResources().getString(
						vlt.android.intervaltimerxp.R.string.add)));

	}

	public void testListViewClickTest() {
		solo.clickInList(0);
		assertTrue(
				"Couldn't find dialog add training!",
				solo.searchText(mActivity.getResources().getString(
						vlt.android.intervaltimerxp.R.string.edit)));
	}

}
