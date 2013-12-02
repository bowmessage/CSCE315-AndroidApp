package com.example.chemiquiz;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class GameViewActivity extends Activity {

	ChemicalSubset curChemicalSubset;

	ImageView imageView;
	ArrayList<Button> buttons;

	// ArrayList<Integer> id_numbers;
	// int this_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		int curSubsetIndex = extras
				.getInt("com.exmaple.chemiquiz.PlayingSubsetID");
		curChemicalSubset = SubsetViewActivity.subsets.get(curSubsetIndex);

		setContentView(R.layout.activity_game_view);

		setupGlobals();

		setUIToQuestion(0);

		setUpListeners();
	}

	void setupGlobals() {
		imageView = (ImageView) findViewById(R.id.imageView1);
		Button button1 = (android.widget.Button) findViewById(R.id.Button01);
		Button button2 = (android.widget.Button) findViewById(R.id.Button02);
		Button button3 = (android.widget.Button) findViewById(R.id.Button03);
		Button button4 = (android.widget.Button) findViewById(R.id.Button04);

		buttons = new ArrayList<Button>();
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
	}

	void setUpListeners() {
		
		for(int i = 0; i < 4; i++){
			Button button = buttons.get(i);
			final CharSequence text = button.getText();
			button.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
					return true;
				}
			});
		}
	}

	void setUIToQuestion(int i) {
		Chemical correct = curChemicalSubset.get(i);
		new DrawableFromChemical().executeOnExecutor(
				AsyncTask.THREAD_POOL_EXECUTOR, correct);
		ArrayList<Chemical> incorrectChemicals = getThreeIncorrectChemicals(i);
		int buttonToPutCorrect = new Random().nextInt(4);
		buttons.get(buttonToPutCorrect).setText(correct.getName());
		int k = 0;
		for (int j = 0; j < 4; j++) {
			if (j == buttonToPutCorrect)
				continue;
			else {
				buttons.get(j).setText(incorrectChemicals.get(k).getName());
				k++;
			}
		}
	}

	ArrayList<Chemical> getThreeIncorrectChemicals(int not) {
		ArrayList<Chemical> ret = new ArrayList<Chemical>();
		ArrayList<Chemical> jumble = curChemicalSubset.toArrayList();
		jumble.remove(not);
		Collections.shuffle(jumble);
		for (int i = 0; i < 3; i++) {
			ret.add(jumble.get(i));
		}
		return ret;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game_view, menu);
		return true;
	}

	ArrayList<Integer> randomNumbers(int max, int size) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			Random r = new Random();
			ret.add(r.nextInt(max));
		}
		return ret;
	}

	class DrawableFromChemical extends AsyncTask<Chemical, Void, Drawable> {

		@Override
		protected Drawable doInBackground(Chemical... params) {
			try {
				Bitmap x;
				HttpURLConnection connection = (HttpURLConnection) new URL(
						"http://www.chemspider.com/ImagesHandler.ashx?id="
								+ params[0].getId() + "&w=300&h=300")
						.openConnection();
				connection.connect();
				InputStream input = connection.getInputStream();
				x = BitmapFactory.decodeStream(input);
				return new BitmapDrawable(GameViewActivity.this.getResources(),
						x);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Drawable res) {
			imageView.setImageDrawable(res);
		}
	}
}
