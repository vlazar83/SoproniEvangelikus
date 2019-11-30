package com.vlazar83.sopronievangelikus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String ANONYMOUS = "anonymous";
    private static final String LOG_TAG = MainActivity.class.getName();
    public static final String INTENT_EVENT_DETAILS = "INTENT_EVENT_DETAILS";
    public static final String LOG_TAG_FOR_DB_READ = "readFromFirestoreDB";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore mFirestoreDb;

    public static final int RC_SIGN_IN = 1;
    private EventAdapter mEventAdapter;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // switch off fab button until admin authentication does not happen
        final FloatingActionButton fabAddEvent = (FloatingActionButton) findViewById(R.id.fab_add_event);
        fabAddEvent.hide();

        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eventDetailsIntent = new Intent(MainActivity.this, CreateEventActivity.class);
                // Start the new activity
                startActivity(eventDetailsIntent);

            }
        });

        mUsername = ANONYMOUS;

        mFirebaseAuth = FirebaseAuth.getInstance();

        //setup the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Create swipe menu list
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.common_full_open_on_phone);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.eventlist);
        // set creator
        listView.setMenuCreator(creator);

        mEventAdapter = new EventAdapter(this, new ArrayList<Event>());

        listView.setAdapter(mEventAdapter);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent eventDetailsIntent = new Intent(MainActivity.this, EventDetailsActivity.class);
                eventDetailsIntent.putExtra(INTENT_EVENT_DETAILS,mEventAdapter.getItem(position));

                // Start the new activity
                startActivity(eventDetailsIntent);
            }
        });


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    // User is signed in
                    onSignedInInitialize(firebaseUser.getDisplayName());

                    // Enable fab button for event creation for admin
                    if (firebaseUser.getEmail().equals("lazar.viktor@gmail.com")){
                        fabAddEvent.show();
                    }


                }else {
                    // User is signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){

            } else if(resultCode == RESULT_CANCELED){
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_logout:
                // User chose the "LogOut" item, show the app settings UI...
                AuthUI.getInstance().signOut(MainActivity.this);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
        mEventAdapter.clear();
        readEventsFromDB();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        mEventAdapter.clear();
    }

    private void readEventsFromDB() {

        // Initialize Firestore DB components
        // https://firebase.google.com/docs/firestore/quickstart
        mFirestoreDb = FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        CollectionReference eventsRef = mFirestoreDb.collection("events");

        eventsRef.orderBy("eventDateAndTime")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(LOG_TAG_FOR_DB_READ, document.getId() + " => " + document.getData());

                                // convert the document to Event format and add it to adapter
                                Event event = Utils.convertDocumentDataToEventData(document);
                                mEventAdapter.add(event);
                            }
                        } else {
                            Log.w(LOG_TAG_FOR_DB_READ, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

}
