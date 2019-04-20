package com.msh.tracknpark;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msh.tracknpark.models.Barricade;
import com.msh.tracknpark.models.ParkingLot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    View disabledButton13;
    View disabledButton14;

    String TAG ="MainActivity";
    //barricade is open if the status is true and closed if it is false
    Barricade barricade0;
    Barricade barricade1;
    TextView mTodayDate;
    ArrayList<ParkingLot> parkingLots;
    ArrayList<ImageButton> parkingLotsButtons;
    DatabaseReference barricade0Ref;// disabled parking 13
    DatabaseReference barricade1Ref;// disabled parking 14
    DatabaseReference parkingRef;//reference to the database of all of the parking slots
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateClock();
        disabledButton13= findViewById(R.id.ps13);
        disabledButton14= findViewById(R.id.ps14);
        parkingLots=new ArrayList<ParkingLot>();
        parkingLotsButtons=new ArrayList<ImageButton>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        barricade0Ref = database.getReference("barricade/0");
        barricade1Ref = database.getReference("barricade/1");
        parkingRef = database.getReference("parking_lots");
        initalizeButtons();
//-----------------------------------------------------------------
        // Listen to barricade status changes
        // barricade 0 corresponds to disabled parking 13
        barricade0Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    barricade0 = dataSnapshot.getValue(Barricade.class);
                    Log.d(TAG, "Value is: " + barricade0.toString());
                } catch (Exception e) {
                    try{
                        String value=dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + value);
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // barricade 1 corresponds to disabled parking 14
        barricade1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                barricade1 = dataSnapshot.getValue(Barricade.class);
                Log.d(TAG, "Value is: " + barricade1.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

//-------------------------------------------------------------------------
        //disabled buttons listeners

        disabledButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!parkingLots.get(12).isStatus()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Actions")
                            .setMessage("Please choose one of the following actions")
                            .setPositiveButton("Open barricade",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            barricade0Ref.child("command").setValue("open");
                                            barricade0Ref.child("response").setValue(false);
                                        }
                                    }).setNegativeButton("Reserve spot", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        Reservation code
                            barricade0Ref.child("command").setValue("close");
                            barricade0Ref.child("response").setValue(false);
                        }
                    }).show();

                }
            }
        });
        disabledButton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!parkingLots.get(13).isStatus()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Actions")
                            .setMessage("Please choose one of the following actions")
                            .setPositiveButton("Open barricade",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            barricade1Ref.child("command").setValue("open");
                                            barricade1Ref.child("response").setValue(false);
                                        }
                                    }).setNegativeButton("Reserve spot", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        Reservation code
                            barricade1Ref.child("command").setValue("close");
                            barricade1Ref.child("response").setValue(false);
                        }
                    }).show();

                }
            }
        });
//-----------------------------------------------------------------------------
        // listener to the parking lots' status
        parkingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               parkingLots=new ArrayList<ParkingLot>();
               Iterator<DataSnapshot> iterator= dataSnapshot.getChildren().iterator();
               while(iterator.hasNext()){
                   DataSnapshot parking= iterator.next();
//                  Log.d(TAG, "Value is: " + dataSnapshot.getChildrenCount());
//
                   parkingLots.add(parking.getValue(ParkingLot.class));
                   Log.d(TAG, "Value is: " + parking.getKey().toString()+" "+parking.getValue(ParkingLot.class).toString());
               }
                Log.d(TAG, "Value is: " + parkingLots.size());
                updateStatus();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }

    public void initalizeButtons(){


        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps1));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps2));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps3));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps4));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps5));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps6));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps7));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps8));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps9));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps10));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps11));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps12));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps13));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps14));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps15));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps16));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps17));
        parkingLotsButtons.add((ImageButton)findViewById(R.id.ps18));


    }
    public void updateStatus() {

        Iterator<ImageButton> parkingButtonIter = parkingLotsButtons.iterator();
        Iterator<ParkingLot> parkingLotsIter = parkingLots.iterator();
        while (parkingLotsIter.hasNext()) {
            boolean status = parkingLotsIter.next().isStatus();
            ImageButton parkingButt = parkingButtonIter.next();
            if (!status) {
                if (parkingButt.getId() == R.id.ps13 || parkingButt.getId() == R.id.ps14) {
                    parkingButt.setBackgroundColor(Color.parseColor("#168733"));
                } else {
                    parkingButt.setImageResource(R.drawable.green);
                }

            } else {
                if (parkingButt.getId() == R.id.ps13 || parkingButt.getId() == R.id.ps14) {
                    parkingButt.setBackgroundColor(Color.parseColor("#a0111f"));
                } else {
                    parkingButt.setImageResource(R.drawable.red);
                }

            }
        }
    }

    public void updateClock(){
       final Handler handler= new Handler();
       handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTodayDate = (TextView)findViewById(R.id.date_time);
                //Get or Generate Date
                Date todayDate = new Date();
                //Get an instance of the formatter
                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                //Format date
                String todayDateTimeString = dateFormat.format(todayDate);

                //display Date
                mTodayDate.setText(todayDateTimeString);
                handler.postDelayed(this,500);
            }
        }, 500);
    }


}
