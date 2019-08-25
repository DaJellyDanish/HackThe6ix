package com.example.hackthe6ix;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehendClient;
import com.amazonaws.services.comprehend.model.DetectEntitiesRequest;
import com.amazonaws.services.comprehend.model.DetectEntitiesResult;
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesRequest;
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private String accessKey = "AKIASY6PMWUCVCUTHXPL";
    private String secretAccessKey = "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";
    public String input ="test string";
    public String [] data = new String[3];

    TextView outputText;
    TextView inputText;
    Button confirmBtn;
    TextView questionText;
    String[] questions = {"How are you feeling today?", "What are your plans for this week", "","Q4","Q5"};
    int btnCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputText = findViewById(R.id.outputText);
        inputText = findViewById(R.id.inputText);
        confirmBtn = findViewById(R.id.confirmBtn);
        questionText = findViewById(R.id.questionText);
        questionText.setText(questions[btnCounter]);
        Log.d("Test", "This is a test");

        //Next Actions
    }

    public void lamdaFunc()
    {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:6dbfcd61-b6f6-446a-9f62-c8bdf54c9a20", // Identity pool ID
                Regions.US_WEST_2 // Region
        );

        LambdaInvokerFactory factory = new LambdaInvokerFactory(this.getApplicationContext(), Regions.US_WEST_2, credentialsProvider);

        // LambdaDataBinder
        final MyInterface myInterface = factory.build(MyInterface.class);

        String jsonStr = "";

        new AsyncTask< String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    Log.d("params", Arrays.toString(params));
                    return myInterface.matchingAlgo(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Rip", "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result == null) {
                    return;
                }
                else
                {
                    Log.d("WE DID IT",result);
                }

                // Do a toast
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
    }.execute(jsonStr);

    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public int confirm(View view)
    {
        if(btnCounter >= 4) {
            //Intent matching = new Intent(this, MainActivity.class);
            //matching.putExtra("Data",data);
            //startActivity(matching);

        }
        else {
            if (!inputText.getText().toString().equals("")) {
                input = inputText.getText().toString();
                new callAPI().execute();
                lamdaFunc();
                btnCounter++;
                questionText.setText(questions[btnCounter]);
            }
        }
        return 0;
    }

    class callAPI extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {
            try {
                return awsComprehend(input);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return "";
        }

        public String awsComprehend(String text)
        {
            try {
                String temp ="";
                Log.d("Init", "awsTest: Starting");
                AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
                AmazonComprehendClient comprehendClient = new AmazonComprehendClient(credentials);
                Log.d("Init", "Init complete");
                DetectEntitiesRequest detectEntitiesRequest = new DetectEntitiesRequest()
                        .withText(text)
                        .withLanguageCode("en");
                Log.d("Request", "Request Complete");
                DetectEntitiesResult detectEntitiesResult = comprehendClient.detectEntities(detectEntitiesRequest);
                temp += detectEntitiesResult.toString();
                data [0] = detectEntitiesResult.toString();

                DetectKeyPhrasesRequest detectKeyPhrasesRequest = new DetectKeyPhrasesRequest().withText(text).withLanguageCode("en");
                DetectKeyPhrasesResult detectKeyPhrasesResult = comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest);
                temp+= detectKeyPhrasesResult.toString();
                data [1] += detectKeyPhrasesResult.toString();

                DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(text).withLanguageCode("en");
                DetectSentimentResult detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
                temp+= detectSentimentResult.toString();
                data [2] += detectKeyPhrasesResult.toString();

                Log.d("Done", "Method Complete");
                return temp;
            }
            catch(Exception e)
            {
                Log.d("Error","sucks to suck");
            }
            return "Error";

        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            outputText.setText(s);
        }
    }

}
