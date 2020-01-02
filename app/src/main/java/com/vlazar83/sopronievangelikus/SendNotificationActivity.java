package com.vlazar83.sopronievangelikus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendNotificationActivity extends AppCompatActivity {

    private static final String LOG_TAG = SendNotificationActivity.class.getName();
    private Spinner targetSpinner;
    private FirebaseFunctions mFunctions;
    private String cloudFunction;
    private EditText titleTextView, bodyTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_send_notification);

        //  To call a function running in any location other than the default us-central1,
        //  you must set the appropriate value at initialization. For example, on Android you would initialize with getInstance(FirebaseApp app, String region).
        mFunctions = FirebaseFunctions.getInstance();

        // setup the Spinner in the UI
        targetSpinner = (Spinner) findViewById(R.id.send_notification_dropdown_list);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.notificationTargets, android.R.layout.simple_spinner_item);
        targetSpinner.setAdapter(locationAdapter);


        if(targetSpinner.getSelectedItem().toString().equals("Family")){
            cloudFunction = "sendNotificationToFamilyTopic";
        } else if (targetSpinner.getSelectedItem().toString().equals("All")){
            cloudFunction = "sendNotificationToAll";
        }

        titleTextView = findViewById(R.id.notificationTitleEditText);
        bodyTextView = findViewById(R.id.notificationBodyEditText);

        Button sendNotificationButton = (Button) findViewById(R.id.send_notification_button);
        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addMessage(titleTextView.getText().toString(), bodyTextView.getText().toString(), cloudFunction)
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (!task.isSuccessful()) {
                                        Exception e = task.getException();
                                        if (e instanceof FirebaseFunctionsException) {
                                            FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                            FirebaseFunctionsException.Code code = ffe.getCode();
                                            Object details = ffe.getDetails();
                                        }

                                        Log.d(LOG_TAG, "notification failed");
                                    } else {
                                        Log.d(LOG_TAG, "notification was successful");
                                    }

                                    // ...
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private Task<String> addMessage(String title, String body, String cloudFunction) throws JSONException {
        // Create the arguments to the callable function.
        //Map<String, Object> data = new HashMap<>();
        //data.put("title", title);
        //data.put("body", body);

        if (cloudFunction.equals("sendNotificationToFamilyTopic")){
            return mFunctions
                    .getHttpsCallable("sendNotificationToFamilyTopic")
                    .call(body)
                    .continueWith(new Continuation<HttpsCallableResult, String>() {
                        @Override
                        public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                            // This continuation runs on either success or failure, but if the task
                            // has failed then getResult() will throw an Exception which will be
                            // propagated down.
                            String result = (String) task.getResult().getData();
                            return result;
                        }
                    });
        } else {
            return mFunctions
                    .getHttpsCallable("sendNotificationToAll")
                    .call(body)
                    .continueWith(new Continuation<HttpsCallableResult, String>() {
                        @Override
                        public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                            // This continuation runs on either success or failure, but if the task
                            // has failed then getResult() will throw an Exception which will be
                            // propagated down.
                            String result = (String) task.getResult().getData();
                            return result;
                        }
                    });
        }

    }
}
