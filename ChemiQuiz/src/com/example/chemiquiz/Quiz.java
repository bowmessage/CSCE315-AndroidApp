package com.example.chemiquiz;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class Quiz extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_layout);
	}

	Drawable drawable_from_url(String url, String src_name)
			throws java.net.MalformedURLException, java.io.IOException {
		return Drawable.createFromStream(
				((java.io.InputStream) new java.net.URL(url).getContent()),
				src_name);
	}

}
