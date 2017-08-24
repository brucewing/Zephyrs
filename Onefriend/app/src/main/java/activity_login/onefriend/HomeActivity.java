package activity_login.onefriend;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


public class HomeActivity extends Fragment {

    ImageButton stresstester;
    ImageButton mindfulness;
    ImageButton report;
    ImageButton information;
    Button b1;
    View vMain;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.activity_home, container, false);
        stresstester = (ImageButton) vMain.findViewById(R.id.stresstester);
        mindfulness = (ImageButton) vMain.findViewById(R.id.mindfulness);
        report = (ImageButton) vMain.findViewById(R.id.report);
        information = (ImageButton) vMain.findViewById(R.id.do_you_know);

        mindfulness.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View arg0) {
                            getFragmentManager()
                               .beginTransaction()
                                 .addToBackStack(null)  //将当前fragment加入到返回栈中
                                .replace(R.id.content_frame, new MindfulnessTest()).commit();
                         }
       });
        stresstester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                mindfulness.setImageDrawable(getResources().getDrawable(R.drawable.mindfulness2_press));
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)  //将当前fragment加入到返回栈中
                        .replace(R.id.content_frame, new StressTest()).commit();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                report.setImageDrawable(getResources().getDrawable(R.drawable.report2_press));
                Toast.makeText(getActivity(),"It would be develop later", Toast.LENGTH_SHORT).show();
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                information.setImageDrawable(getResources().getDrawable(R.drawable.information2_press));
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)  //将当前fragment加入到返回栈中
                        .replace(R.id.content_frame, new InformationPage()).commit();
            }
        });

        //writing to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        //read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


//        vMain.findViewById(R.id.stresstester).setOnClickListener(new View.OnClickListener() {
//
//                   @Override
//             public void onClick(View arg0) {
//                            getFragmentManager()
//                               .beginTransaction()
//                                 .addToBackStack(null)  //将当前fragment加入到返回栈中
//                                .replace(R.id.container, new Testtt()).commit();
//                         }
//       });


        return vMain;
    }




//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        stresstester = (ImageButton) findViewById(R.id.stresstester);
//        mindfulness = (ImageButton) findViewById(R.id.mindfulness);
//        report = (ImageButton) findViewById(R.id.report);
//        information = (ImageButton) findViewById(R.id.do_you_know);
//
//
//        stresstester.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    stresstester.setImageDrawable(getResources().getDrawable(R.drawable.stress2_press));
//
//                    startActivity(testintent);
//            }
//        });
//        mindfulness.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mindfulness.setImageDrawable(getResources().getDrawable(R.drawable.mindfulness2_press));
//
//            }
//        });
//        report.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                report.setImageDrawable(getResources().getDrawable(R.drawable.report2_press));
//
//            }
//        });
//        information.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                information.setImageDrawable(getResources().getDrawable(R.drawable.information2_press));
//
//            }
//        });
//
//
//    }

}