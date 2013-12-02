package com.example.chemiquiz;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameViewActivity extends Activity {

	ChemicalSubset curChemicalSubset;

	ImageView imageView;
	ArrayList<Button> buttons;
	Drawable imageBuffer;
	
	int curQuestion = 0;
	int curCorrectButton = -1;
	int score = 0;

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
		
		new nextQuestion().executeOnExecutor(
				AsyncTask.THREAD_POOL_EXECUTOR, 0);

		setUpListeners();
	}

	void setupGlobals() {
		imageView = (ImageView) findViewById(R.id.imageView1);
		
		buttons = new ArrayList<Button>();
		buttons.add((Button) findViewById(R.id.Button01));
		buttons.add((Button) findViewById(R.id.Button02));
		buttons.add((Button) findViewById(R.id.Button03));
		buttons.add((Button) findViewById(R.id.Button04));
	}

	void setUpListeners() {
		
		for(int i = 0; i < buttons.size(); i++){
			final int finalI = i;
			Button button = buttons.get(i);
			final CharSequence text = button.getText();
			
			button.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					submitAnswer(finalI);
				}
			});
			
			
			button.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
					return true;
				}
			});
		}
	}

	protected void submitAnswer(int i) {
		if(i == curCorrectButton){
			score++;
		}
		curQuestion++;
		if(curQuestion == curChemicalSubset.size()){
			clearUI();
			showScorePopup();
		}
		else{
			new nextQuestion().executeOnExecutor(
					AsyncTask.THREAD_POOL_EXECUTOR, curQuestion);
		}
	}
	
	void clearUI(){
		View sv = findViewById(R.id.scrollView1);
		((RelativeLayout) sv.getParent()).removeView(sv);
	}
	
	void setUiToQuestion(Question q){
		imageView.setImageDrawable(q.image);
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).setText(q.buttonLabels.get(i));
		}
		TextView scoreView = (TextView) findViewById(R.id.gameScore);
		scoreView.setText(score + "/10");
	}
	
	ArrayList<String> getQuestionNames(int i){
		Chemical correct = curChemicalSubset.get(i);
        ArrayList<Chemical> incorrectChemicals = getThreeIncorrectChemicals(i);
        int buttonToPutCorrect = new Random().nextInt(4);
        ArrayList<String> ret = new ArrayList<String>();
        int k = 0;
        for (int j = 0; j < 4; j++) {
                if (j == buttonToPutCorrect)
                	ret.add(correct.getName());
                else {
                	ret.add(incorrectChemicals.get(k).getName());
                	k++;
                }
        }
        return ret;
	}
	
	void showScorePopup(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameViewActivity.this);
        
		alertDialogBuilder.setTitle("Game Over");

		alertDialogBuilder
			.setMessage("This subset quiz is over. Your score was " + score + " out of 10.")
			.setCancelable(false)
			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					finish();
				}
			  });

			AlertDialog alertDialog = alertDialogBuilder.create();
			
			alertDialog.show();
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
	class Question{
		public Question(Drawable i, ArrayList<String> l){
			image = i;
			buttonLabels = l;
		}
		public Drawable image;
		public ArrayList<String> buttonLabels;
	}
	
	class nextQuestion extends AsyncTask<Integer, Void, Question>{

		@Override
		protected Question doInBackground(Integer... i) {
			try {
				Bitmap x;
				HttpURLConnection connection = (HttpURLConnection) new URL(
						"http://www.chemspider.com/ImagesHandler.ashx?id="
								+ curChemicalSubset.get(curQuestion).getId() + "&w=500&h=500")
						.openConnection();
				connection.connect();
				InputStream input = connection.getInputStream();
				x = BitmapFactory.decodeStream(input);
				
				ArrayList<String> buttonVals = new ArrayList<String>();
				
				return new Question(new BitmapDrawable(GameViewActivity.this.getResources(),
						x),
						getQuestionNames(i[1])
						);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(Question q) {
			score++;
			setUiToQuestion(q);
		}
		
	}
}
